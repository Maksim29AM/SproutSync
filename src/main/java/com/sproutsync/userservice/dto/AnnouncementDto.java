package com.sproutsync.userservice.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementDto {

    private Long id;

    private Long groupId;

    @NotBlank
    private String title;

    @NotBlank
    private String message;
    private String photo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;

}
