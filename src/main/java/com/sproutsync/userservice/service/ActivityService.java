package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.ActivityUpdateDto;
import com.sproutsync.userservice.model.Activity;


import java.util.List;
import java.util.Optional;

public interface ActivityService {

    Activity createActivity(Activity activity);

    Activity updateActivity(Long id, ActivityUpdateDto activityUpdateDto);

    void deleteActivity(Long id);

    Optional<Activity> getActivity(Long id);

    List<Activity> getAllActivities();

    List<Activity> findAllActivitiesByGroupId(Long groupId);

}
