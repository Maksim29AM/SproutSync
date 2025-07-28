package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.MenuDayUpdateDto;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.MenuDay;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuDayService {

    MenuDay createMenuDay(MenuDay menuDay);

    MenuDay updateMenuDay(Long id,Long groupId, MenuDayUpdateDto menuDayDto);

    void deleteMenuDay(Group groupId, Long id);

    Optional<MenuDay> getMenuDay(Long groupId, Long menuId);

    List<MenuDay> getAllByGroup(Group groupId);

    Optional<MenuDay> getMenuDayByData(Long groupId,LocalDate date);



}
