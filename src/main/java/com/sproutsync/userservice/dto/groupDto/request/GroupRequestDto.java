package com.sproutsync.userservice.dto.groupDto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "GroupRequest", description = "Request payload for creating or updating a group")
public class GroupRequestDto {

    @NotBlank
    @Schema(description = "Name of the group", example = "Biedronki")
    private String name;

    @NotBlank
    @Schema(description = "Short description of the group", example = "This is the preschool group for 3-4 year olds")
    private String description;

    @NotBlank
    @Schema(description = "URL of the main photo representing the group", example = "https://example.com/groups/biedronki.jpg")
    private String mainFoto;
}
