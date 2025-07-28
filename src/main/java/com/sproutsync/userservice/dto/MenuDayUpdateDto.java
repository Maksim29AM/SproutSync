package com.sproutsync.userservice.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class MenuDayUpdateDto {

    private LocalDate date;

    private List<MealDto> meals;

    private Set<AllergenDto> allergens;
}
