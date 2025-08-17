package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.menuDto.request.MenuDayUpdateDto;
import com.sproutsync.userservice.model.MenuDay;

import java.time.LocalDate;
import java.util.List;

public interface MenuDayService {

    MenuDay createMenuDay(Long groupId, MenuDay menuDay);

    MenuDay updateMenuDay(Long groupId, Long menuId, MenuDayUpdateDto menuDayUpdateDto);

    void deleteMenuDay(Long groupId, Long id);

    MenuDay getMenuDayByGroupId(Long groupId, Long menuId);

    List<MenuDay> getAllMenuByGroupId(Long groupId);

    MenuDay getMenuDayByData(Long groupId, LocalDate date);


}
