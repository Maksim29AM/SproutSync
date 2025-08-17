package com.sproutsync.userservice.dto.accessRequestDto.request;

import lombok.Data;

@Data
public class AccessCreateRequestDto {

    private Long userId;
    private Long groupId;
}
