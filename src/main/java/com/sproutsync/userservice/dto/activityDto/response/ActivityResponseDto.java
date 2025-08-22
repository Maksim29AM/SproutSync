package com.sproutsync.userservice.dto.activityDto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "ActivityResponse", description = "Response DTO representing an activity")
public class ActivityResponseDto {

    @Schema(description = "Activity ID", example = "101")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(description = "Date and time of the activity",
            example = "2025-09-01T10:30",
            type = "string", format = "date-time")
    private LocalDateTime dateTime;

    @Schema(description = "Description of the activity", example = "Outdoor play in the garden")
    private String activities;

    @Schema(description = "Activity creation timestamp", example = "2025-08-01T09:15", type = "string", format = "date-time")
    private LocalDateTime createdAt;

    @Schema(description = "Activity last update timestamp", example = "2025-08-10T12:45", type = "string", format = "date-time")
    private LocalDateTime updatedAt;

    @Schema(description = "ID of the group associated with this activity", example = "3")
    private Long groupId;

    @Schema(description = "ID of the user who created the activity", example = "5")
    private Long createdBy;

}
