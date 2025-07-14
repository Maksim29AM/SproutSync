package com.sproutsync.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String mainFoto;

}
