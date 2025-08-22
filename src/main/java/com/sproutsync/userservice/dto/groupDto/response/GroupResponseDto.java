package com.sproutsync.userservice.dto.groupDto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "GroupResponse", description = "Response DTO representing a group")
public class GroupResponseDto {

    @Schema(description = "Unique ID of the group", example = "12")
    private Long id;

    @NotBlank
    @Schema(description = "Name of the group", example = "Biedronki")
    private String name;

    @NotBlank
    @Schema(description = "Short description of the group", example = "This is the preschool group for 3-4 year olds")
    private String description;

    @NotBlank
    @Schema(description = "URL of the main photo representing the group", example = "https://example.com/groups/groupA.jpg")
    private String mainFoto;

}
