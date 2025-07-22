package com.sproutsync.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MenuUpdateDto {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String meals;
}
