package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.photoDto.response.PhotoResponseDto;
import com.sproutsync.userservice.dto.photoDto.request.PhotoUploadRequestDto;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.Photo;
import com.sproutsync.userservice.model.User;


public class PhotoMapper {

    public static PhotoResponseDto toDto(Photo entity) {
        PhotoResponseDto dto = new PhotoResponseDto();
        dto.setId(entity.getId());
        dto.setGroupId(entity.getGroup().getId());
        dto.setUrl(entity.getUrl());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedById(entity.getCreatedBy().getId());

        return dto;
    }

    public static Photo toEntity(PhotoUploadRequestDto dto, Group group, User user) {
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