package com.sproutsync.userservice.dto.menuDto.response;

import lombok.Data;

@Data
public class MealDto {

    private MealTypeDto mealType;

    private String description;

}
