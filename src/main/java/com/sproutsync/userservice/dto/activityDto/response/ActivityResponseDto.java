package com.sproutsync.userservice.dto.activityDto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityResponseDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    private String activities;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long groupId;

    private Long createdBy;

}
