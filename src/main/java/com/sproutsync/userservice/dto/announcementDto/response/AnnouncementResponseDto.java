package com.sproutsync.userservice.dto.announcementDto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementResponseDto {

    private Long id;

    private Long groupId;

    private String title;

    private String message;
    private String photo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdByUserId;
}
