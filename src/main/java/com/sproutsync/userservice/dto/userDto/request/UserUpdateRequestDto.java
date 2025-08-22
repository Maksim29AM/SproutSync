package com.sproutsync.userservice.dto.userDto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(name = "UserUpdateRequest", description = "Request payload for updating an existing user")
public class UserUpdateRequestDto {

    @Schema(description = "Updated first name / username", example = "Marek")
    private String username;

    @Schema(description = "Updated surname", example = "Nowak")
    private String surname;

    @Schema(description = "Updated email address", example = "marek.nowak@mail.com", format = "email")
    private String email;

    @Schema(description = "Updated password", example = "New_Update_Pass!")
    private String password;

    @Schema(description = "IDs of roles assigned to the user", example = "[1, 2,3]")
    private Set<Long> roleIds;
}

