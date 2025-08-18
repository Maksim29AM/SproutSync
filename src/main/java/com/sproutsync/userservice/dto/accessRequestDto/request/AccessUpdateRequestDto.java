package com.sproutsync.userservice.dto.accessRequestDto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AccessUpdateRequest", description = "Request payload for updating access request status")
public class AccessUpdateRequestDto {

    @Schema(description = "ID of the access request", example = "13")
    private Long id;

    @Schema(
            description = "New access status",
            example = "APPROVED",
            allowableValues = {"PENDING", "APPROVED", "REJECTED"}
    )
    private String accessStatus;

}
