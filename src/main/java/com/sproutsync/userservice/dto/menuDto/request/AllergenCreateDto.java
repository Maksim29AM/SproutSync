package com.sproutsync.userservice.dto.menuDto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AllergenCreateRequest", description = "DTO for assigning an allergen by ID")
public class AllergenCreateDto {

    @Schema(description = "Allergen ID", example = "3")
    private Long id;
}
