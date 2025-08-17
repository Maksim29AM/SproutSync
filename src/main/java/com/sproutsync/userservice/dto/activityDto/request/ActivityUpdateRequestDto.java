package com.sproutsync.userservice.dto.activityDto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityUpdateRequestDto {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future
    private LocalDateTime dateTime;
    private String activities;
}
