package com.sproutsync.userservice.dto.userDto.request;


import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateRequestDto {
    private String username;
    private String surname;
    private String email;
    private String password;
    private Set<Long> roleIds;
}

