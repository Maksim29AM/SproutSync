package com.sproutsync.userservice.dto.announcementDto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "AnnouncementCreateRequest", description = "Request payload for creating a new announcement")
public class AnnouncementCreateRequestDto {

    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Title of the announcement", example = "Parent Meeting", minLength = 3, maxLength = 100)
    private String title;

    @NotBlank
    @Size(min = 5, max = 2000)
    @Schema(description = "Message content of the announcement", example = "We invite all parents to the meeting scheduled for next Monday.", minLength = 5, maxLength = 2000)
    private String message;

    @Schema(description = "Optional URL to a photo attached to the announcement", example = "https://example.com/announcements/meeting.jpg")
    private String photo;
}
