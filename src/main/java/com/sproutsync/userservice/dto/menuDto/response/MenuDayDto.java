package com.sproutsync.userservice.dto.menuDto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Schema(name = "MenuDayResponse", description = "Response DTO representing a daily menu")
public class MenuDayDto {

    @Schema(description = "Menu ID", example = "101")
    private Long id;

    @Schema(description = "Date of the menu", example = "2025-09-01", type = "string", format = "date")
    private LocalDate date;

    @Schema(description = "List of meals for the day")
    private List<MealDto> meals;

    @Schema(description = "Set of allergens for the menu")
    private Set<AllergenDto> allergens;

    @Schema(description = "Timestamp when the menu was created", example = "2025-08-01T09:15:00", type = "string", format = "date-time")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the menu was last updated", example = "2025-08-05T14:30:00", type = "string", format = "date-time")
    private LocalDateTime updatedAt;

}
