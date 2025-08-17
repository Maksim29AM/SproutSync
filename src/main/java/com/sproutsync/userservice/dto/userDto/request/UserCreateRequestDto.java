package com.sproutsync.userservice.dto.userDto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserCreateRequestDto {

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

}
