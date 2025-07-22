package com.sproutsync.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MenuDto {

    private Long id;

    @NotNull
    private Long groupId;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String meals;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
