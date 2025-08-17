package com.sproutsync.userservice.dto.menuDto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class MenuDayDto {

    private Long id;

    private LocalDate date;

    private List<MealDto> meals;

    private Set<AllergenDto> allergens;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
