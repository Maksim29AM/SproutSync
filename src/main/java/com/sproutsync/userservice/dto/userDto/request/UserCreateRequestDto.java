package com.sproutsync.userservice.dto.userDto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(name = "UserCreateRequest", description = "Request payload for creating a new user")
public class UserCreateRequestDto {

    @NotBlank
    @Size(min = 3, max = 25)
    @Schema(description = "User's first name / username", example = "Jan", minLength = 3, maxLength = 25)
    private String username;

    @NotBlank
    @Size(min = 3, max = 25)
    @Schema(description = "User's surname", example = "Kowalski", minLength = 3, maxLength = 25)
    private String surname;

    @Email
    @NotBlank
    @Schema(description = "User's email address", example = "jan.kowalski@mail.com", format = "email")
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    @Schema(description = "Password", example = "Password_Example", minLength = 8, maxLength = 100)
    private String password;

}
