package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergenRepository extends JpaRepository<Allergen,Long> {
}
