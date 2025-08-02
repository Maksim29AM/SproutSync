package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.MenuDayDto;
import com.sproutsync.userservice.dto.MenuDayUpdateDto;
import com.sproutsync.userservice.mapper.MenuDayMapper;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.MenuDay;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.AllergenRepository;
import com.sproutsync.userservice.repository.MealTypeRepository;
import com.sproutsync.userservice.repository.MenuDayRepository;
import com.sproutsync.userservice.service.GroupService;
import com.sproutsync.userservice.service.MenuDayService;
import com.sproutsync.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups/{idGroup}/menu")
public class MenuDayController {

    private final MenuDayService menuDayservice;
    private final GroupService groupService;
    private final UserService userService;
    private final MealTypeRepository mealTypeRepository;
    private final AllergenRepository allergenRepository;

    public MenuDayController(MenuDayService menuDayservice, GroupService groupService, UserService userService, MealTypeRepository mealTypeRepository, MenuDayRepository menuDayRepository, AllergenRepository allergenRepository) {
        this.menuDayservice = menuDayservice;
        this.groupService = groupService;
        this.userService = userService;
        this.mealTypeRepository = mealTypeRepository;
        this.allergenRepository = allergenRepository;
    }

    @PostMapping
    public MenuDayDto createMenuDay(@PathVariable Long idGroup, @RequestBody @Valid MenuDayDto menuDayDto, Authentication authentication) {
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        User user = userService.findByEmail(authentication.getName());
        MenuDay saved = menuDayservice.createMenuDay(group.getId(), MenuDayMapper.toEntity(menuDayDto, group, mealTypeRepository, allergenRepository));
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
