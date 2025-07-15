package com.sproutsync.userservice.dto;

import com.sproutsync.userservice.util.AccessStatus;
import lombok.Data;

@Data
public class AccessRequestDto {

    private Long id;
    private Long userId;
    private Long groupId;
    private AccessStatus accessStatus;


}
