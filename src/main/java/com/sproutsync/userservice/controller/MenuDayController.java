package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.menuDto.request.MenuDayCreateDto;
import com.sproutsync.userservice.dto.menuDto.response.MenuDayDto;
import com.sproutsync.userservice.dto.menuDto.request.MenuDayUpdateDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping
    public MenuDayDto createMenuDay(@PathVariable Long idGroup, @RequestBody @Valid MenuDayCreateDto menuDayCreateDto) {
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        MenuDay saved = menuDayservice.createMenuDay(group.getId(), MenuDayMapper.toEntity(menuDayCreateDto, group, mealTypeRepository, allergenRepository));
        return MenuDayMapper.toDto(saved);
    }

    @PutMapping("/{menuId}")
    public MenuDayDto updateMenuDay(@PathVariable Long idGroup, @PathVariable Long menuId, @RequestBody MenuDayUpdateDto menuDayDto) {
        MenuDay updated = menuDayservice.updateMenuDay(idGroup, menuId, menuDayDto);
        return MenuDayMapper.toDto(updated);
    }

    @DeleteMapping("/{menuId}")
    public void deleteMenuDayByGroup(@PathVariable Long idGroup, @PathVariable Long menuId) {
        menuDayservice.deleteMenuDay(idGroup, menuId);
    }

    @GetMapping("/id/{menuId}")
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public MenuDayDto getMenuDayByGroupId(@PathVariable Long idGroup, @PathVariable Long menuId) {
        MenuDay menu = menuDayservice.getMenuDayByGroupId(idGroup, menuId);
        return MenuDayMapper.toDto(menu);
    }

    @GetMapping
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public List<MenuDayDto> getAllMenusByGroup(@PathVariable Long idGroup) {
        return menuDayservice.getAllMenuByGroupId(idGroup)
                .stream()
                .map(MenuDayMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public MenuDayDto findMenuByDate(@PathVariable LocalDate date, @PathVariable Long idGroup) {
        MenuDay menu = menuDayservice.getMenuDayByData(idGroup, date);
        return MenuDayMapper.toDto(menu);
    }
}
