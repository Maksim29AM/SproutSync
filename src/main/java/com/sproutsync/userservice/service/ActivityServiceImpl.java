package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.ActivityUpdateDto;
import com.sproutsync.userservice.model.Activity;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.repository.ActivityRepository;
import com.sproutsync.userservice.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final GroupRepository groupRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository, GroupRepository groupRepository) {
        this.activityRepository = activityRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Activity createActivity(Long groupId,Activity activity) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        return activityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(Long groupId, Long activityId, ActivityUpdateDto updateDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        Activity updateActivity = activityRepository.findByGroupIdAndId(group.getId(), activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with id " + activityId + " not found"));
        if (updateDto.getActivities() != null) {
            updateActivity.setActivities(updateDto.getActivities());
        }
        if (updateDto.getDateTime() != null) {
            updateActivity.setDateTime(updateDto.getDateTime());
        }

        return activityRepository.save(updateActivity);
    }

    @Override
    public void deleteActivity(Long groupId,Long activityId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        Activity existing = activityRepository.findByGroupIdAndId(group.getId(), activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with id " + activityId + " not found"));
        activityRepository.deleteById(existing.getId());
    }

    @Override
    public Optional<Activity> getActivityByGroup(Long groupId, Long activityId) {
        return activityRepository.findByGroupIdAndId(groupId, activityId);
    }

    @Override
    public List<Activity> getAllActivitiesByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        return activityRepository.findAllByGroup(group);
    }
}
