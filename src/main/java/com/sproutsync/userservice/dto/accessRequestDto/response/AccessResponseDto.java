package com.sproutsync.userservice.dto.accessRequestDto.response;

import com.sproutsync.userservice.util.AccessStatus;
import lombok.Data;

@Data
public class AccessResponseDto {

    private Long id;
    private Long userId;
    private String userName;
    private String userSurname;
    private Long groupId;
    private AccessStatus accessStatus;


}
