package com.sproutsync.userservice.dto;


import com.sproutsync.userservice.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateDto {
    private String username;
    private String surname;
    private String email;
    private String password;
    private Set<Long> roleIds;
}

