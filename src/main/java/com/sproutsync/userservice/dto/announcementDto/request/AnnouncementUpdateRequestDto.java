package com.sproutsync.userservice.dto.announcementDto.request;

import lombok.Data;

@Data
public class AnnouncementUpdateRequestDto {

    private String title;
    private String message;
    private String photo;
}