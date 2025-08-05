package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.PhotoDto;
import com.sproutsync.userservice.dto.PhotoUploadDto;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.Photo;
import com.sproutsync.userservice.model.User;


public class PhotoMapper {

    public static PhotoDto toDto(Photo entity) {
        PhotoDto dto = new PhotoDto();
        dto.setId(entity.getId());
        dto.setGroupId(entity.getGroup().getId());
        dto.setUrl(entity.getUrl());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedById(entity.getCreatedBy().getId());

        return dto;
    }

    public static Photo toEntity(PhotoUploadDto dto, Group group, User user) {
        return new Photo(
                null,
                group,
                null,
                dto.getDescription(),
                null,
                null,
                user
        );
    }
}