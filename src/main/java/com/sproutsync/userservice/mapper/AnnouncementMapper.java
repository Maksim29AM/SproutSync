package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.announcementDto.request.AnnouncementCreateRequestDto;
import com.sproutsync.userservice.dto.announcementDto.response.AnnouncementResponseDto;
import com.sproutsync.userservice.dto.announcementDto.request.AnnouncementUpdateRequestDto;
import com.sproutsync.userservice.model.Announcement;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;

public class AnnouncementMapper {

    private AnnouncementMapper() {
    }

    public static Announcement toEntity(AnnouncementResponseDto dto, Group group, User user) {
        return new Announcement(
                null,
                group,
                dto.getTitle(),
                dto.getMessage(),
                dto.getPhoto(),
                null,
                null,
                user
        );
    }

    public static Announcement toEntity(AnnouncementUpdateRequestDto dto, Group group, User user) {
        return new Announcement(
                null,
                group,
                dto.getTitle(),
                dto.getMessage(),
                dto.getPhoto(),
                null,
                null,
                user
        );
    }

    public static Announcement toEntity(AnnouncementCreateRequestDto dto, Group group, User user) {
        return new Announcement(
                null,
                group,
                dto.getTitle(),
                dto.getMessage(),
                dto.getPhoto(),
                null,
                null,
                user
        );
    }

    public static AnnouncementResponseDto toDto(Announcement entity) {
        AnnouncementResponseDto dto = new AnnouncementResponseDto();
        dto.setId(entity.getId());
        dto.setGroupId(entity.getGroup().getId());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setPhoto(entity.getPhoto());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedByUserId(entity.getCreatedBy().getId());
        return dto;
    }
}
