package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.AllergenDto;
import com.sproutsync.userservice.model.Allergen;


public class AllergenMapper {

    private AllergenMapper() {
    }

    public static AllergenDto toDto(Allergen allergen) {
        AllergenDto allergenDto = new AllergenDto();
        allergenDto.setId(allergen.getId());
        allergenDto.setName(allergen.getName());
        return allergenDto;
    }
}
