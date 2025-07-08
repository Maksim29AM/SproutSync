package com.sproutsync.userservice.dto;

import com.sproutsync.userservice.model.Role;
import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String surname;
    private String email;
    private Role role;
}
