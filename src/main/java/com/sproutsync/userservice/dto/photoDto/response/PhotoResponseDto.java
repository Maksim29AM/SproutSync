package com.sproutsync.userservice.dto.photoDto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "PhotoResponse", description = "Response DTO representing a photo in the gallery")
public class PhotoResponseDto {

    @Schema(description = "Photo ID", example = "55")
    private Long id;

    @Schema(description = "ID of the group the photo belongs to", example = "3")
    private Long groupId;

    @Schema(description = "URL of the stored photo", example = "https://example.com/example.png")
    private String url;

    @Schema(description = "Description of the photo", example = "Własna praca")
    private String description;

    @Schema(description = "Timestamp when the photo was uploaded", example = "2025-08-15T10:30:00", type = "string", format = "date-time")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the photo metadata was last updated", example = "2025-08-16T09:45:00", type = "string", format = "date-time")
    private LocalDateTime updatedAt;

    @Schema(description = "ID of the user who uploaded the photo", example = "3")
    private Long createdById;

}
