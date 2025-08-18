package com.sproutsync.userservice.dto.menuDto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MealResponse", description = "Response DTO representing a meal")
public class MealDto {

    @Schema(description = "Meal type", example = "Zupa")
    private MealTypeDto mealType;

    @Schema(description = "Description of the meal", example = "Rosół z makaronem.")
    private String description;

}
