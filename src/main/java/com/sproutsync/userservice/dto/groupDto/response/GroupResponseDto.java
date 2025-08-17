package com.sproutsync.userservice.dto.groupDto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupResponseDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String mainFoto;

}
