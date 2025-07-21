package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.ActivityDto;
import com.sproutsync.userservice.dto.ActivityUpdateDto;
import com.sproutsync.userservice.model.Activity;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;

public class ActivityMapper {

    private ActivityMapper() {
    }

    public static Activity toEntity(ActivityDto dto, Group group, User user) {
        return new Activity(
                null,
                group,
                dto.getDateTime(),
                dto.getActivities(),
                null,
                null,
                user
        );

    }
    public static ActivityDto toDto(Activity entity) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setId(entity.getId());
        activityDto.setGroupId(entity.getGroup().getId());
        activityDto.setDateTime(entity.getDateTime());
        activityDto.setActivities(entity.getActivities());
        activityDto.setCreatedAt(entity.getCreatedAt());
        activityDto.setUpdatedAt(entity.getUpdatedAt());
        activityDto.setCreatedBy(entity.getCreatedBy().getId());
        return activityDto;
    }
}
