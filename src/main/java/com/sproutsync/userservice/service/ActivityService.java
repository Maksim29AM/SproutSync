package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.ActivityUpdateDto;
import com.sproutsync.userservice.model.Activity;


import java.util.List;
import java.util.Optional;

public interface ActivityService {

    Activity createActivity(Long groupId, Activity activity);

    Activity updateActivity(Long group_id, Long id, ActivityUpdateDto activityUpdateDto);

    void deleteActivity(Long groupId, Long activityId);

    Optional<Activity> getActivityByGroup(Long groupId, Long activityId);

    List<Activity> getAllActivitiesByGroupId(Long groupId);

}
