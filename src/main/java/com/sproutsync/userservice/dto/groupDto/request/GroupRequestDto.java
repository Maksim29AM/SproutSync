package com.sproutsync.userservice.dto.groupDto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String mainFoto;
}
