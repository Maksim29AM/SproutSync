package com.sproutsync.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityUpdateDto {

    private LocalDateTime dateTime;
    private String activities;



}
