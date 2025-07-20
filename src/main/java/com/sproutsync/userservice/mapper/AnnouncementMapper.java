package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.AnnouncementDto;
import com.sproutsync.userservice.dto.AnnouncementUpdateDto;
import com.sproutsync.userservice.model.Announcement;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;

public class AnnouncementMapper {

    private AnnouncementMapper() {
    }

    public static Announcement toEntity(AnnouncementDto dto, Group group, User user) {
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

    public static Announcement toEntity(AnnouncementUpdateDto dto, Group group, User user) {
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

    public static AnnouncementDto toDto(Announcement entity) {
        AnnouncementDto dto = new AnnouncementDto();
        dto.setId(entity.getId());
        dto.setGroupId(entity.getGroup().getId());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setPhoto(entity.getPhoto());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy().getId());
        return dto;
    }
}
