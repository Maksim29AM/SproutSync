package com.sproutsync.userservice.dto.activityDto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "ActivityUpdateRequest", description = "Request payload for updating an existing activity")
public class ActivityUpdateRequestDto {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future
    @Schema(description = "New date and time of the activity (must be in the future)",
            example = "2025-09-02T15:00",
            type = "string", format = "date-time")
    private LocalDateTime dateTime;

    @Schema(description = "Updated description of the activity", example = "Drawing and painting workshop")
    private String activities;
}
