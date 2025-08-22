package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.announcementDto.request.AnnouncementCreateRequestDto;
import com.sproutsync.userservice.dto.announcementDto.response.AnnouncementResponseDto;
import com.sproutsync.userservice.dto.announcementDto.request.AnnouncementUpdateRequestDto;
import com.sproutsync.userservice.mapper.AnnouncementMapper;
import com.sproutsync.userservice.model.Announcement;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.service.AnnouncementService;
import com.sproutsync.userservice.service.GroupService;
import com.sproutsync.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Tag(name = "Announcements", description = "CRUD operations for group announcements")
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

    @Operation(summary = "Create announcement", description = "Creates a new announcement for a given group")
    @PostMapping
    public AnnouncementResponseDto createAnnouncement(@PathVariable Long idGroup, @RequestBody @Valid AnnouncementCreateRequestDto announcementCreateDtoRequestDto, Authentication authentication) {
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + authentication));
        Announcement saved = announcementService.createAnnouncement(idGroup, AnnouncementMapper.toEntity(announcementCreateDtoRequestDto, group, user));
        return AnnouncementMapper.toDto(saved);
    }

    @Operation(summary = "Update announcement", description = "Updates an existing announcement by ID for a given group")
    @PutMapping("/{announceId}")
    public AnnouncementResponseDto updateAnnouncement(@PathVariable Long idGroup, @PathVariable Long announceId, @RequestBody @Valid AnnouncementUpdateRequestDto updateDto) {
        Announcement updated = announcementService.updateAnnouncement(idGroup, announceId, updateDto);
        return AnnouncementMapper.toDto(updated);
    }

    @Operation(summary = "Delete announcement", description = "Deletes an announcement by ID for a given group")
    @DeleteMapping("/{announceId}")
    public void deleteAnnouncement(@PathVariable Long idGroup, @PathVariable Long announceId) {
        announcementService.deleteAnnouncement(idGroup, announceId);
    }

    @Operation(summary = "Get announcement by ID", description = "Returns an announcement by ID for a given group")
    @GetMapping("/{announceId}")
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public AnnouncementResponseDto getAnnouncementByGroupId(@PathVariable Long idGroup, @PathVariable Long announceId) {
        Announcement announcement = announcementService.getAnnouncementByGroup(idGroup, announceId);
        return AnnouncementMapper.toDto(announcement);
    }

    @Operation(summary = "List all announcements", description = "Returns all announcements for a given group")
    @GetMapping
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public List<AnnouncementResponseDto> getAllAnnouncementsByGroupId(@PathVariable Long idGroup) {
        return announcementService.getAllAnnouncementsByGroupId(idGroup)
                .stream()
                .map(AnnouncementMapper::toDto)
                .collect(Collectors.toList());
    }
}
