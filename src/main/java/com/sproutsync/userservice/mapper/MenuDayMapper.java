package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.menuDto.response.AllergenDto;
import com.sproutsync.userservice.dto.menuDto.response.MealDto;
import com.sproutsync.userservice.dto.menuDto.request.MenuDayCreateDto;
import com.sproutsync.userservice.dto.menuDto.response.MenuDayDto;
import com.sproutsync.userservice.model.*;
import com.sproutsync.userservice.repository.AllergenRepository;
import com.sproutsync.userservice.repository.MealTypeRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MenuDayMapper {

    private MenuDayMapper() {
    }

    public static MenuDay toEntity(MenuDayCreateDto dto, Group group, MealTypeRepository mealTypeRepository, AllergenRepository allergenRepository) {
        MenuDay menuDay = new MenuDay();
        menuDay.setDate(dto.getDate());
        menuDay.setGroup(group);

        List<Meal> meals = dto.getMeals().stream()
                .map(mealDto -> MealMapper.toEntity(mealDto, menuDay, mealTypeRepository))
                .collect(Collectors.toList());
        menuDay.setMeals(meals);

        Set<Allergen> allergens = dto.getAllergens().stream()
                .map(dtoAllergen -> allergenRepository.findById(dtoAllergen.getId()).orElseThrow(() -> new RuntimeException("Allergen not found with id: " + dtoAllergen.getId())))
                .collect(Collectors.toSet());
        menuDay.setAllergens(allergens);

        return menuDay;
    }

    public static MenuDayDto toDto(MenuDay menuDay) {
        List<MealDto> meals = menuDay.getMeals().stream().map(MealMapper::toDto).toList();

        Set<AllergenDto> allergens = menuDay.getAllergens().stream().map(AllergenMapper::toDto)
                .collect(Collectors.toSet());

        MenuDayDto dto = new MenuDayDto();
        dto.setId(menuDay.getId());
        dto.setDate(menuDay.getDate());
        dto.setMeals(meals);
        dto.setAllergens(allergens);
        dto.setCreatedAt(menuDay.getCreatedAt());
        dto.setUpdatedAt(menuDay.getUpdatedAt());
        return dto;
    }
}