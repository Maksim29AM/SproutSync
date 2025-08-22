package com.sproutsync.userservice.dto.menuDto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AllergenResponse", description = "Response DTO representing an allergen")
public class AllergenDto {

    @Schema(description = "Allergen ID", example = "4")
    private Long id;

    @Schema(description = "Name of the allergen", example = "Mleko")
    private String name;

}
