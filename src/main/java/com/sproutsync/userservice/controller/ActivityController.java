package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.activityDto.request.ActivityCreateRequestDto;
import com.sproutsync.userservice.dto.activityDto.response.ActivityResponseDto;
import com.sproutsync.userservice.dto.activityDto.request.ActivityUpdateRequestDto;
import com.sproutsync.userservice.mapper.ActivityMapper;
import com.sproutsync.userservice.model.Activity;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.service.ActivityService;
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

@Tag(name = "Activities", description = "CRUD operations for group activities")
@RestController
@RequestMapping("/api/groups/{idGroup}/activity")
public class ActivityController {

    private final ActivityService activityService;
    private final GroupService groupService;
    private final UserService userService;

    public ActivityController(ActivityService activityService, GroupService groupService, UserService userService) {
        this.activityService = activityService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @Operation(summary = "Create activity", description = "Creates a new activity for a given group")
    @PostMapping
    public ActivityResponseDto createActivity(@PathVariable Long idGroup, @RequestBody @Valid ActivityCreateRequestDto activityCreateRequestDtoDto, Authentication authentication) {
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + authentication));
        Activity saved = activityService.createActivity(idGroup, ActivityMapper.toEntity(activityCreateRequestDtoDto, group, user));
        return ActivityMapper.toDto(saved);
    }

    @Operation(summary = "Update activity", description = "Updates an existing activity by ID for a given group")
    @PutMapping("/{activityId}")
    public ActivityResponseDto updateActivity(@PathVariable Long idGroup, @PathVariable Long activityId, @RequestBody @Valid ActivityUpdateRequestDto activityUpdateRequestDto) {
        Activity updated = activityService.updateActivity(idGroup, activityId, activityUpdateRequestDto);
        return ActivityMapper.toDto(updated);
    }

    @Operation(summary = "Delete activity", description = "Deletes an activity by ID for a given group")
    @DeleteMapping("/{activityId}")
    public void deleteActivity(@PathVariable Long idGroup, @PathVariable Long activityId) {
        activityService.deleteActivity(idGroup, activityId);
    }

    @Operation(summary = "Get activity by ID", description = "Returns an activity by ID for a given group")
    @GetMapping("/{activityId}")
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public ActivityResponseDto getActivity(@PathVariable Long idGroup, @PathVariable Long activityId) {
        return activityService.getActivityByGroup(idGroup, activityId)
                .map(ActivityMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + activityId));
    }

    @Operation(summary = "List all activities", description = "Returns all activities for a given group")
    @GetMapping
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public List<ActivityResponseDto> getActivitiesByGroup(@PathVariable Long idGroup) {
        return activityService.getAllActivitiesByGroupId(idGroup)
                .stream()
                .map(ActivityMapper::toDto)
                .collect(Collectors.toList());
    }
}
