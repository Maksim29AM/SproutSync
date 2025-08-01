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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/announce")
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
    public AnnouncementDto createAnnouncement(@RequestBody @Valid AnnouncementDto announcementDto, Authentication authentication) {
        Group group = groupService.getGroupById(announcementDto.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + announcementDto.getGroupId()));
        User user = userService.findByEmail(authentication.getName());
        Announcement saved = announcementService.createAnnouncement(AnnouncementMapper.toEntity(announcementDto, group, user));
        return AnnouncementMapper.toDto(saved);
    }

    @PutMapping()
    public AnnouncementDto updateAnnouncement(@RequestParam(name = "group") Long idGroup, @RequestParam(name = "announcement") Long idAnnouncement, @RequestBody @Valid AnnouncementUpdateDto updateDto) {
        Announcement existing = announcementService.getAnnouncement(idAnnouncement)
                .orElseThrow(() -> new EntityNotFoundException("Announcement not found with id: " + idAnnouncement));
        Group existingGroup = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        List<Announcement> allAnnouncements = announcementService.findByGroup_Id(idGroup);
        if (!allAnnouncements.contains(existing)) {
            throw new EntityNotFoundException("Group " + idGroup + " not has announcement with id: " + idAnnouncement);
        }
        Announcement updated = announcementService.updateAnnouncement(idAnnouncement, updateDto);
        return AnnouncementMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteAnnouncement(@PathVariable Long id) {
        Announcement existing = announcementService.getAnnouncement(id)
                .orElseThrow(() -> new EntityNotFoundException("Announcement not found with id: " + id));
        announcementService.deleteAnnouncement(existing.getId());
    }

    @GetMapping("/{id}")
    public AnnouncementDto getAnnouncement(@PathVariable Long id) {
        return announcementService.getAnnouncement(id)
                .map(AnnouncementMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Announcement not found with id: " + id));
    }

    @GetMapping
    public List<AnnouncementDto> getAllAnnouncements() {
        return announcementService.getAllAnnouncements()
                .stream()
                .map(AnnouncementMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/group/{id}/announcement")
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #id)")
    public List<AnnouncementDto> getAnnouncementsByGroupId(@PathVariable Long id) {
        return announcementService.findByGroup_Id(id)
                .stream()
                .map(AnnouncementMapper::toDto)
                .collect(Collectors.toList());
    }
}
