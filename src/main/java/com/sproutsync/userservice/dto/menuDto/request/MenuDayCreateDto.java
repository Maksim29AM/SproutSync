package com.sproutsync.userservice.dto.menuDto.request;

import com.sproutsync.userservice.dto.menuDto.response.MealDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class MenuDayCreateDto {

    private LocalDate date;

    private List<MealDto> meals;

    private Set<AllergenCreateDto> allergens;
}
