package com.sproutsync.userservice.dto.userDto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(name = "UserResponse", description = "Response DTO representing a user")
public class UserResponseDto {

    @Schema(description = "User ID", example = "101")
    private Long id;

    @Schema(description = "User's first name / username", example = "Jan")
    private String username;

    @Schema(description = "User's surname", example = "Kowalski")
    private String surname;

    @Schema(description = "User's email address", example = "jan.kowalski@mail.com", format = "email")
    private String email;

    @Schema(description = "Set of role names assigned to the user", example = "ROLE_USER, ROLE_ADMIN")
    private Set<String> roles;
}
