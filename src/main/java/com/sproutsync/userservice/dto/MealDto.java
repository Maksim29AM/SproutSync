package com.sproutsync.userservice.dto;

import lombok.Data;

@Data
public class MealDto {

    private MealTypeDto mealType;

    private Long id;

    private String description;

}
