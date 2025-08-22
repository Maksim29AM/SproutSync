package com.sproutsync.userservice.dto.menuDto.request;

import com.sproutsync.userservice.dto.menuDto.response.MealDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Schema(name = "MenuDayCreateRequest", description = "Request payload for creating a new menu for a group")
public class MenuDayCreateDto {

    @Schema(description = "Date of the menu", example = "2025-09-01", type = "string", format = "date")
    private LocalDate date;

    @Schema(description = "List of meals for the day")
    private List<MealDto> meals;

    @Schema(description = "Set of allergens for the menu")
    private Set<AllergenCreateDto> allergens;
}
