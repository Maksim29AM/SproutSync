package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.MealType;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface MealTypeRepository extends JpaRepository<MealType,Long> {

    Optional<MealType> findByName(String name);}
