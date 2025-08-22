package com.sproutsync.userservice.dto.accessRequestDto.response;

import com.sproutsync.userservice.util.AccessStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AccessResponse", description = "Response DTO representing an access request")
public class AccessResponseDto {

    @Schema(description = "Access request ID", example = "42")
    private Long id;

    @Schema(description = "ID of the user who made the request", example = "5")
    private Long userId;

    @Schema(description = "First name of the requesting user", example = "Jan")
    private String userName;

    @Schema(description = "Surname of the requesting user", example = "Kowalski")
    private String userSurname;

    @Schema(description = "ID of the group", example = "3")
    private Long groupId;

    @Schema(
            description = "Current status of the access request",
            example = "PENDING"
    )
    private AccessStatus accessStatus;


}
