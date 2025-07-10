package com.sproutsync.userservice.dto;

import com.sproutsync.userservice.model.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class UserRequestDto {

    @NotBlank
    @Size(min = 3, max = 25)
    private String username;

    @NotBlank
    @Size(min = 3, max = 25)
    private String surname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    private Set<Long> roleIds;
}
