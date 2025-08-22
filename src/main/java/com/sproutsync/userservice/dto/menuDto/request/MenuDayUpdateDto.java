package com.sproutsync.userservice.dto.menuDto.request;


import com.sproutsync.userservice.dto.menuDto.response.MealDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Schema(name = "MenuDayUpdateRequest", description = "Request payload for updating an existing menu")
public class MenuDayUpdateDto {

    @Schema(description = "Updated date of the menu", example = "2025-09-02", type = "string", format = "date")
    private LocalDate date;

    @Schema(description = "Updated list of meals")
    private List<MealDto> meals;

    @Schema(description = "Updated set of allergens")
    private Set<AllergenCreateDto> allergens;
}
