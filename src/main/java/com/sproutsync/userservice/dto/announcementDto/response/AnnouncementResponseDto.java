package com.sproutsync.userservice.dto.announcementDto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "AnnouncementResponse", description = "Response DTO representing an announcement")
public class AnnouncementResponseDto {

    @Schema(description = "Announcement ID", example = "15")
    private Long id;

    @Schema(description = "Group ID the announcement belongs to", example = "7")
    private Long groupId;

    @Schema(description = "Title of the announcement", example = "Parent Meeting")
    private String title;

    @Schema(description = "Message content of the announcement", example = "We invite all parents to the meeting scheduled for next Monday.")
    private String message;

    @Schema(description = "URL to a photo attached to the announcement", example = "https://cdn.example.com/announcements/meeting.jpg")
    private String photo;

    @Schema(description = "Timestamp when the announcement was created", example = "2025-08-01T09:15:00", type = "string", format = "date-time")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the announcement was last updated", example = "2025-08-05T14:30:00", type = "string", format = "date-time")
    private LocalDateTime updatedAt;

    @Schema(description = "ID of the user who created the announcement", example = "3")
    private Long createdByUserId;
}
