package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.MenuDto;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.Menu;


public class MenuMapper {

    private MenuMapper() {
    }

    public static Menu toEntity(MenuDto menuDto, Group group) {
        return new Menu(
                null,
                group,
                menuDto.getDate(),
                menuDto.getMeals(),
                null,
                null);
    }

    public static MenuDto toDto(Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setGroupId(menu.getGroup().getId());
        menuDto.setDate(menu.getDate());
        menuDto.setMeals(menu.getMeals());
        menuDto.setCreatedAt(menu.getCreatedAt());
        menuDto.setUpdatedAt(menu.getUpdatedAt());
        return menuDto;
    }
}
