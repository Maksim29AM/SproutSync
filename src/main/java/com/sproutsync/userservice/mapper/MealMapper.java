package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.MealDto;
import com.sproutsync.userservice.dto.MealTypeDto;
import com.sproutsync.userservice.model.Meal;
import com.sproutsync.userservice.model.MealType;
import com.sproutsync.userservice.model.MenuDay;
import com.sproutsync.userservice.repository.MealTypeRepository;

public class MealMapper {

    private MealMapper() {
    }

    public static Meal toEntity(MealDto mealDto, MenuDay menuDay, MealTypeRepository mealTypeRepository) {
        MealType mealType = mealTypeRepository.findByName(mealDto.getMealType().getName())
                .orElseThrow(() -> new RuntimeException("MealType not found with name: " + mealDto.getMealType().getName()));


        return new Meal(
                null,
                menuDay,
                mealType,
                mealDto.getDescription()
        );
    }

    public static MealDto toDto(Meal meal) {
        MealTypeDto typeDto = new MealTypeDto();
        typeDto.setName(meal.getMealType().getName());

        MealDto dto = new MealDto();
        dto.setId(meal.getId());
        dto.setDescription(meal.getDescription());

        dto.setMealType(typeDto);

        return dto;
    }
}