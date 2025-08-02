package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.MenuDay;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuDayRepository extends JpaRepository<MenuDay, Long> {

    Optional<MenuDay> findByGroupIdAndId(Long group_id, Long id);

    List<MenuDay> getAllByGroupId(Long group);

   Optional<MenuDay> findByGroupIdAndDate(Long groupId, LocalDate date);
}
