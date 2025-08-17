package com.sproutsync.userservice.dto.userDto.response;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String surname;
    private String email;
    private Set<String> roles;
}
