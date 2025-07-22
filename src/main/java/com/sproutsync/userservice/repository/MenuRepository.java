package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findAllByGroup(Group group);
}
