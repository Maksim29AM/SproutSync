package com.sproutsync.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhotoDto {

    private Long id;
    private Long groupId;
    private String url;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdById;

}
