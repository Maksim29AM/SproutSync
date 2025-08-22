package com.sproutsync.userservice.dto.announcementDto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AnnouncementUpdateRequest", description = "Request payload for updating an existing announcement")
public class AnnouncementUpdateRequestDto {

    @Schema(description = "Updated title of the announcement", example = "Parent Meeting (Rescheduled)")
    private String title;

    @Schema(description = "Updated message content", example = "The meeting has been moved to Wednesday due to public holiday.")
    private String message;

    @Schema(description = "Updated photo URL", example = "https://example.com/announcements/meeting_updated.jpg")
    private String photo;
}