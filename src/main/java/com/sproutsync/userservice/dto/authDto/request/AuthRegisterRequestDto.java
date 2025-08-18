package com.sproutsync.userservice.dto.authDto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

@Schema(name = "AuthRegisterRequest", description = "Registration payload for a new user")
public class AuthRegisterRequestDto {

    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(example="Jan", minLength=3, maxLength=20)
    private String username;

    @NotBlank
    @Schema(example="Kowalski", minLength=3, maxLength=25)
    @Size(min = 3, max = 25)
    private String surname;

    @Email
    @NotBlank
    @Schema(example="kowalski@mail.com", format="email")
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    @Schema(example="Password_Example", minLength=8, maxLength=100)
    private String password;
}
