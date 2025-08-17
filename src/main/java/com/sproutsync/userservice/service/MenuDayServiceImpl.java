package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.menuDto.response.MealDto;
import com.sproutsync.userservice.dto.menuDto.request.MenuDayUpdateDto;
import com.sproutsync.userservice.model.*;
import com.sproutsync.userservice.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MenuDayServiceImpl implements MenuDayService {

    private final MenuDayRepository menuDayRepository;
    private final AllergenRepository allergenRepository;
    private final GroupRepository groupRepository;


    public MenuDayServiceImpl(MenuDayRepository menuDayRepository, AllergenRepository allergenRepository, GroupRepository groupRepository) {
        this.menuDayRepository = menuDayRepository;
        this.allergenRepository = allergenRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public MenuDay createMenuDay(Long groupId, MenuDay menuDay) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        if (menuDayRepository.findByGroupIdAndDate(group.getId(), menuDay.getDate()).isPresent()) {
            throw new IllegalStateException("Menu for date " + menuDay.getDate() + " already exists for group with id " + groupId);
        }
        return menuDayRepository.save(menuDay);
    }

    @Override
    @Transactional
    public MenuDay updateMenuDay(Long groupId, Long menuId, MenuDayUpdateDto menuDayDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        MenuDay updateMenu = menuDayRepository.findByGroupIdAndId(group.getId(), menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu with id " + menuId + " not found"));

        if (menuDayDto.getDate() != null) {
            updateMenu.setDate(menuDayDto.getDate());
        }

        if (menuDayDto.getMeals() != null && !menuDayDto.getMeals().isEmpty()) {
            Map<String, String> descriptionMap = menuDayDto.getMeals().stream()
                    .filter(mealDto -> mealDto.getMealType() != null
                            && mealDto.getMealType().getName() != null
                            && mealDto.getDescription() != null)
                    .collect(Collectors.toMap(
                            mealDto -> mealDto.getMealType().getName().toLowerCase(),
                            MealDto::getDescription,
                            (desc1, desc2) -> desc2));
            updateMenu.getMeals().forEach(existingMeal -> {
                String typeName = existingMeal.getMealType().getName().toLowerCase();
                if (descriptionMap.containsKey(typeName)) {
                    existingMeal.setDescription(descriptionMap.get(typeName));
                }
            });
        }

        if (menuDayDto.getAllergens() != null) {
            Set<Allergen> allergens = menuDayDto.getAllergens().stream()
                    .map(dto -> allergenRepository.findById(dto.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Allergen not found: " + dto.getId())))
                    .collect(Collectors.toSet());
            updateMenu.setAllergens(allergens);
        }
        return menuDayRepository.save(updateMenu);
    }


    @Override
    @Transactional
    public void deleteMenuDay(Long groupId, Long menuId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        MenuDay menuDay = menuDayRepository.findByGroupIdAndId(group.getId(), menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu with id " + menuId + " not found"));
        menuDay.getAllergens().clear();
        menuDayRepository.save(menuDay);
        menuDayRepository.delete(menuDay);
    }


    @Override
    public MenuDay getMenuDayByGroupId(Long groupId, Long menuId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        return menuDayRepository.findByGroupIdAndId(group.getId(), menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu with id " + menuId + " not found"));
    }

    @Override
    public List<MenuDay> getAllMenuByGroupId(Long groupId) {
        return menuDayRepository.getAllByGroupId(groupId);
    }

    @Override
    public MenuDay getMenuDayByData(Long groupId, LocalDate date) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        return menuDayRepository.findByGroupIdAndDate(groupId,date)
                .orElseThrow(() -> new EntityNotFoundException("Menu for date " + date + " not found for group with id " + groupId));
    }

}
