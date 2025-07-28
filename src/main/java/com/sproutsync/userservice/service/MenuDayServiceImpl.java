package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.MealDto;
import com.sproutsync.userservice.dto.MenuDayUpdateDto;
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



    public MenuDayServiceImpl(MenuDayRepository menuDayRepository, AllergenRepository allergenRepository) {
        this.menuDayRepository = menuDayRepository;
        this.allergenRepository = allergenRepository;

    }

    @Override
    public MenuDay createMenuDay(MenuDay menuDay) {
        return menuDayRepository.save(menuDay);
    }

    @Override
    @Transactional
    public MenuDay updateMenuDay(Long menuId, Long groupId, MenuDayUpdateDto menuDayDto) {
        MenuDay updateMenu = menuDayRepository.findByGroupIdAndId(groupId, menuId)
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
                            mealDto -> mealDto.getMealType().getName(),
                            MealDto::getDescription,
                            (desc1, desc2) -> desc2));

            updateMenu.getMeals().forEach(existingMeal -> {
                String typeName = existingMeal.getMealType().getName();
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
    public void deleteMenuDay(Group group, Long menuId) {
        MenuDay menuDay = menuDayRepository.findByGroupIdAndId(group.getId(), menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu day not found"));
        menuDay.getAllergens().clear();
        menuDayRepository.save(menuDay);
        menuDayRepository.delete(menuDay);
    }


    @Override
    public Optional<MenuDay> getMenuDay(Long groupId, Long menuId) {
        return menuDayRepository.findByGroupIdAndId(groupId, menuId);
    }

    @Override
    public List<MenuDay> getAllByGroup(Group groupId) {
        return menuDayRepository.getAllByGroup(groupId);
    }

    @Override
    public Optional<MenuDay> getMenuDayByData(Long groupId,LocalDate date) {
        return menuDayRepository.findByGroupIdAndDate(groupId, date);
    }

}
