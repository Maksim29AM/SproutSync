package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.MenuDto;
import com.sproutsync.userservice.dto.MenuUpdateDto;
import com.sproutsync.userservice.mapper.MenuMapper;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.Menu;
import com.sproutsync.userservice.service.GroupService;
import com.sproutsync.userservice.service.MenuService;
import com.sproutsync.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;
    private final GroupService groupService;

    public MenuController(MenuService menuService, GroupService groupService) {
        this.menuService = menuService;
        this.groupService = groupService;
    }

    @PostMapping
    public MenuDto createMenu(@RequestBody @Valid MenuDto menuDto) {
        Group group = groupService.getGroupById(menuDto.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + menuDto.getGroupId()));
        Menu saved = menuService.createMenu(MenuMapper.toEntity(menuDto, group));
        return MenuMapper.toDto(saved);
    }

    @PutMapping
    public MenuDto updateMenu(@RequestParam(name = "group") Long groupId, @RequestParam(name = "menu") Long menuId, @RequestBody MenuUpdateDto updateDto) {
        Menu existing = menuService.getMenu(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + menuId));
        Group existingGroup = groupService.getGroupById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));
        List<Menu> allMenus = menuService.findAllMenuByGroupId(groupId);
        if (!allMenus.contains(existing)) {
            throw new EntityNotFoundException("Group " + groupId + " not has menu with id: " + menuId);
        }
        Menu updated = menuService.updateMenu(menuId, updateDto);
        return MenuMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Long id) {
        Menu existing = menuService.getMenu(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + id));
        menuService.deleteMenu(existing.getId());
    }

    @GetMapping("/{id}")
    public MenuDto getActivity(@PathVariable Long id) {
        return menuService.getMenu(id)
                .map(MenuMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + id));
    }

    @GetMapping
    public List<MenuDto> getAllActivities() {
        return menuService.getAllMenu()
                .stream()
                .map(MenuMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/group/{id}/menu")
    public List<MenuDto> getActivitiesByGroup(@PathVariable(name = "id") Long groupId) {
        return menuService.findAllMenuByGroupId(groupId)
                .stream()
                .map(MenuMapper::toDto)
                .collect(Collectors.toList());
    }
}
