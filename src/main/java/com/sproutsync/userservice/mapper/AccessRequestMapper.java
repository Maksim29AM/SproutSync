package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.AccessRequestDto;
import com.sproutsync.userservice.model.AccessRequest;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.util.AccessStatus;

import java.time.LocalDateTime;

public class AccessRequestMapper {

    private AccessRequestMapper() {
    }

    public static AccessRequest toEntity(User user, Group group) {
        return new AccessRequest(
                null,
                user,
                group,
                AccessStatus.PENDING,
                LocalDateTime.now(),
                null);
    }

    public static AccessRequestDto toDto(AccessRequest accessRequest) {
        AccessRequestDto dto = new AccessRequestDto();
        dto.setId(accessRequest.getId());
        dto.setUserId(accessRequest.getParent().getId());
        dto.setUserName(accessRequest.getParent().getUsername());
        dto.setUserSurname(accessRequest.getParent().getSurname());
        dto.setGroupId(accessRequest.getGroup().getId());
        dto.setAccessStatus(accessRequest.getAccessStatus());
        return dto;
    }
}
