package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.ActivityDto;
import com.sproutsync.userservice.dto.ActivityUpdateDto;
import com.sproutsync.userservice.mapper.ActivityMapper;
import com.sproutsync.userservice.model.Activity;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.service.ActivityService;
import com.sproutsync.userservice.service.GroupService;
import com.sproutsync.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityService activityService;
    private final GroupService groupService;
    private final UserService userService;

    public ActivityController(ActivityService activityService, GroupService groupService, UserService userService) {
        this.activityService = activityService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @PostMapping
    public ActivityDto createAnnouncement(@RequestBody @Valid ActivityDto activityDto, Authentication authentication) {
        Group group = groupService.getGroupById(activityDto.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + activityDto.getGroupId()));
        User user = userService.findByEmail(authentication.getName());
        Activity saved = activityService.createActivity(ActivityMapper.toEntity(activityDto, group, user));
        return ActivityMapper.toDto(saved);
    }

    @PutMapping
    public ActivityDto updateActivity(@RequestParam(name = "group") Long groupId, @RequestParam(name = "activity") Long activityId, @RequestBody @Valid ActivityUpdateDto activityUpdateDto) {
        Activity existing = activityService.getActivity(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + activityId));
        Group existingGroup = groupService.getGroupById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));
        List<Activity> allActivities = activityService.findAllActivitiesByGroupId(groupId);
        if (!allActivities.contains(existing)) {
            throw new EntityNotFoundException("Group " + groupId + " not has activity with id: " + activityId);
        }
        Activity updated = activityService.updateActivity(activityId, activityUpdateDto);
        return ActivityMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Long id) {
        Activity existing = activityService.getActivity(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + id));
        activityService.deleteActivity(existing.getId());
    }

    @GetMapping("/{id}")
    public ActivityDto getActivity(@PathVariable Long id) {
        return activityService.getActivity(id)
                .map(ActivityMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + id));
    }

    @GetMapping
    public List<ActivityDto> getAllActivities() {
        return activityService.getAllActivities()
                .stream()
                .map(ActivityMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/group/{id}/activity")
    public List<ActivityDto> getActivitiesByGroup(@PathVariable(name = "id") Long groupId) {
        return activityService.findAllActivitiesByGroupId(groupId)
                .stream()
                .map(ActivityMapper::toDto)
                .collect(Collectors.toList());
    }
}
