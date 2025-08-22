package com.sproutsync.userservice.dto.menuDto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MealTypeResponse", description = "Response DTO representing a meal type")
public class MealTypeDto {

    @Schema(description = "Name of the meal type", example = "Śniadanie")
    private String name;

}
