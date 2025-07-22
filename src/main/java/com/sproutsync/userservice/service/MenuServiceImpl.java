package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.MenuUpdateDto;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.Menu;
import com.sproutsync.userservice.repository.GroupRepository;
import com.sproutsync.userservice.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final GroupRepository groupRepository;

    public MenuServiceImpl(MenuRepository menuRepository, GroupRepository groupRepository) {
        this.menuRepository = menuRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenu(Long id, MenuUpdateDto menuUpdateDto) {
        Menu updateMenu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu with id " + id + " not found"));
        if (menuUpdateDto.getDate() != null) {
            updateMenu.setDate(menuUpdateDto.getDate());
        }
        if (menuUpdateDto.getMeals() != null) {
            updateMenu.setMeals(menuUpdateDto.getMeals());
        }
        return menuRepository.save(updateMenu);
    }

    @Override
    public void deleteMenu(Long id) {
        Menu existing = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu with id " + id + " not found"));
        menuRepository.deleteById(existing.getId());
    }

    @Override
    public Optional<Menu> getMenu(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> findAllMenuByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        return menuRepository.findAllByGroup(group);
    }
}
