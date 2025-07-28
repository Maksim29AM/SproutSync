package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.MenuDayDto;
import com.sproutsync.userservice.dto.MenuDayUpdateDto;
import com.sproutsync.userservice.mapper.MenuDayMapper;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.MenuDay;
import com.sproutsync.userservice.repository.AllergenRepository;
import com.sproutsync.userservice.repository.MealTypeRepository;
import com.sproutsync.userservice.repository.MenuDayRepository;
import com.sproutsync.userservice.service.GroupService;
import com.sproutsync.userservice.service.MenuDayService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/groups/{idGroup}/menu")
public class MenuDayController {

    private final MenuDayService menuDayservice;
    private final GroupService groupService;
    private final MealTypeRepository mealTypeRepository;
    private final AllergenRepository allergenRepository;

    public MenuDayController(MenuDayService menuDayservice, GroupService groupService, MealTypeRepository mealTypeRepository, MenuDayRepository menuDayRepository, AllergenRepository allergenRepository) {
        this.menuDayservice = menuDayservice;
        this.groupService = groupService;
        this.mealTypeRepository = mealTypeRepository;
        this.allergenRepository = allergenRepository;
    }

    @GetMapping("/id/{menuId}")
    public MenuDayDto getMenuDay(@PathVariable Long idGroup, @PathVariable Long menuId) {
        Group existingGroup = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        MenuDay menuDay = menuDayservice.getMenuDay(existingGroup.getId(), menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + menuId));
        return MenuDayMapper.toDto(menuDay);
    }

    @GetMapping("/date/{date}")
    public MenuDayDto findMenuByDate(@PathVariable LocalDate date, @PathVariable Long idGroup) {
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        MenuDay meny = menuDayservice.getMenuDayByData(group.getId(), date)
                .orElseThrow(() -> new EntityNotFoundException("Menu day not found with date: " + date));
        return MenuDayMapper.toDto(meny);
    }

    @GetMapping
    public List<MenuDayDto> getAllMenusByGroup(@PathVariable Group idGroup) {
        return menuDayservice.getAllByGroup(idGroup)
                .stream()
                .map(MenuDayMapper::toDto)
                .collect(Collectors.toList());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public MenuDayDto createMenuDay(@PathVariable Long idGroup, @RequestBody @Valid MenuDayDto menuDayDto) {
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        Optional<MenuDay> existingData = menuDayservice.getMenuDay(idGroup, menuDayDto.getId());
        if (existingData.isPresent()) {
            throw new IllegalStateException("Menu day already exists");
        }
        MenuDay saved = menuDayservice.createMenuDay(MenuDayMapper.toEntity(menuDayDto, group, mealTypeRepository, allergenRepository));
        return MenuDayMapper.toDto(saved);
    }

    @DeleteMapping("/{menuId}")
    public void deleteMenuDayByGroup(@PathVariable Long idGroup, @PathVariable Long menuId) {
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        MenuDay menu = menuDayservice.getMenuDay(idGroup, menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu day not found with id: " + menuId));
        menuDayservice.deleteMenuDay(group, menu.getId());
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{menuId}")
    public MenuDayDto updateMenuDay(@PathVariable Long idGroup, @PathVariable Long menuId, @RequestBody MenuDayUpdateDto menuDayDto) {
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        MenuDay menu = menuDayservice.getMenuDay(idGroup, menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu day not found with id: " + menuId));
        List<MenuDay> allMenus = menuDayservice.getAllByGroup(group);
        if (!allMenus.contains(menu)) {
            throw new EntityNotFoundException("Group " + group + " not has menu with id: " + menuId);
        }
        MenuDay updated = menuDayservice.updateMenuDay(menuId, group.getId(), menuDayDto);
        return MenuDayMapper.toDto(updated);
    }
}

