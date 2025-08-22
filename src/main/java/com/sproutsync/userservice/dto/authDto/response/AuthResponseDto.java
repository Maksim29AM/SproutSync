package com.sproutsync.userservice.dto.authDto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "AuthResponse", description = "Authentication response with user details and assigned roles")
public class AuthResponseDto {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User first name / username", example = "Jan")
    private String username;

    @Schema(description = "User last name", example = "Kowalski")
    private String surname;

    @Schema(description = "User email", example = "kowalski@mail.com", format = "email")
    private String email;

    @Schema(description = "Assigned roles", example = "ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN")
    private String roles;
}