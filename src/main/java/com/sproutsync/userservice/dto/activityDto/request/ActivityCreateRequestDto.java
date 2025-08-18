package com.sproutsync.userservice.dto.activityDto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "ActivityCreateRequest", description = "Request payload for creating a new activity")
public class ActivityCreateRequestDto {

    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(description = "Date and time of the activity (must be in the future)",
            example = "2025-09-01T10:30",
            type = "string", format = "date-time")
    private LocalDateTime dateTime;

    @NotBlank
    @Schema(description = "Description of the activity", example = "Outdoor play in the garden")
    private String activities;

}
