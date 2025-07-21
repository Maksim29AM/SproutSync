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
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(Long id, ActivityUpdateDto updateDto) {
        Activity updateActivity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity with id " + id + " not found"));
        if (updateDto.getActivities() != null) {
            updateActivity.setActivities(updateDto.getActivities());
        }
        if (updateDto.getDateTime() != null) {
            updateActivity.setDateTime(updateDto.getDateTime());
        }

        return activityRepository.save(updateActivity);
    }

    @Override
    public void deleteActivity(Long id) {
        Activity existing = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity with id " + id + " not found"));
        activityRepository.deleteById(existing.getId());
    }

    @Override
    public Optional<Activity> getActivity(Long id) {
        return activityRepository.findById(id);
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public List<Activity> findAllActivitiesByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        return activityRepository.findAllByGroup(group);
    }
}
