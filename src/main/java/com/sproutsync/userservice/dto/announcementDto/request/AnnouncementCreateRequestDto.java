package com.sproutsync.userservice.dto.announcementDto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AnnouncementCreateRequestDto {

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank
    @Size(min = 5, max = 2000)
    private String message;

    private String photo;
}
