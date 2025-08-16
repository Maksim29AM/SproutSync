package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.activityDto.request.ActivityCreateRequestDto;
import com.sproutsync.userservice.dto.activityDto.response.ActivityResponseDto;
import com.sproutsync.userservice.model.Activity;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;

public class ActivityMapper {

    private ActivityMapper() {
    }

    public static Activity toEntity(ActivityCreateRequestDto dto, Group group, User user) {
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
    public static ActivityResponseDto toDto(Activity entity) {
        ActivityResponseDto activityResponseDto = new ActivityResponseDto();
        activityResponseDto.setId(entity.getId());
        activityResponseDto.setGroupId(entity.getGroup().getId());
        activityResponseDto.setDateTime(entity.getDateTime());
        activityResponseDto.setActivities(entity.getActivities());
        activityResponseDto.setCreatedAt(entity.getCreatedAt());
        activityResponseDto.setUpdatedAt(entity.getUpdatedAt());
        activityResponseDto.setCreatedBy(entity.getCreatedBy().getId());
        return activityResponseDto;
    }
}
