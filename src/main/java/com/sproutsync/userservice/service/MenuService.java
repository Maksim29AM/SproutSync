package com.sproutsync.userservice.service;


import com.sproutsync.userservice.dto.MenuUpdateDto;
import com.sproutsync.userservice.model.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    Menu createMenu(Menu menu);

    Menu updateMenu(Long id, MenuUpdateDto menuUpdateDto);

    void deleteMenu(Long id);

    Optional<Menu> getMenu(Long id);

    List<Menu> getAllMenu();

    List<Menu> findAllMenuByGroupId(Long groupId);

}
