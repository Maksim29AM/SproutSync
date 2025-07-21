package com.sproutsync.userservice.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityDto {

    private Long id;

    @NotNull
    @Future
    private LocalDateTime dateTime;

    @NotBlank
    private String activities;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long groupId;

    private Long createdBy;

}
