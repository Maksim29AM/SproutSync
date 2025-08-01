package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.AnnouncementDto;
import com.sproutsync.userservice.dto.AnnouncementUpdateDto;
import com.sproutsync.userservice.mapper.AnnouncementMapper;
import com.sproutsync.userservice.model.Announcement;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.service.AnnouncementService;
import com.sproutsync.userservice.service.GroupService;
import com.sproutsync.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/groups/{idGroup}/announce")
public class AnnounceController {

    private final AnnouncementService announcementService;
    private final GroupService groupService;
    private final UserService userService;

    public AnnounceController(AnnouncementService announcementService, GroupService groupService, UserService userService) {
        this.announcementService = announcementService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @PostMapping
    public AnnouncementDto createAnnouncement(@PathVariable Long idGroup, @RequestBody @Valid AnnouncementDto announcementDto, Authentication authentication) {
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        User user = userService.findByEmail(authentication.getName());
        Announcement saved = announcementService.createAnnouncement(idGroup, AnnouncementMapper.toEntity(announcementDto, group, user));
        return AnnouncementMapper.toDto(saved);
    }

    @PutMapping("/{announceId}")
    public AnnouncementDto updateAnnouncement(@PathVariable Long idGroup, @PathVariable Long announceId, @RequestBody @Valid AnnouncementUpdateDto updateDto) {
        Announcement updated = announcementService.updateAnnouncement(idGroup, announceId, updateDto);
        return AnnouncementMapper.toDto(updated);
    }

    @DeleteMapping("/{announceId}")
    public void deleteAnnouncement(@PathVariable Long idGroup, @PathVariable Long announceId) {
        announcementService.deleteAnnouncement(idGroup, announceId);
    }

    @GetMapping("/{announceId}")
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public AnnouncementDto getAnnouncementByGroupId(@PathVariable Long idGroup, @PathVariable Long announceId) {
        Announcement announcement = announcementService.getAnnouncementByGroup(idGroup, announceId);
        return AnnouncementMapper.toDto(announcement);
    }

    @GetMapping
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public List<AnnouncementDto> getAllAnnouncementsByGroupId(@PathVariable Long idGroup) {
        return announcementService.getAllAnnouncementsByGroupId(idGroup)
                .stream()
                .map(AnnouncementMapper::toDto)
                .collect(Collectors.toList());
    }
}
