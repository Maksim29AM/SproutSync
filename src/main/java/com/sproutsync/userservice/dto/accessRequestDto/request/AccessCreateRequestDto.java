package com.sproutsync.userservice.dto.accessRequestDto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AccessCreateRequest", description = "Request payload for creating a new access request")
public class AccessCreateRequestDto {

    @Schema(description = "ID of the user who creates the request", example = "5")
    private Long userId;

    @Schema(description = "ID of the group the user requests access to", example = "3")
    private Long groupId;
}
