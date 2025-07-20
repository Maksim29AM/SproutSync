package com.sproutsync.userservice.dto;

import lombok.Data;

@Data
public class AnnouncementUpdateDto {
    private String title;
    private String message;
    private String photo;
}