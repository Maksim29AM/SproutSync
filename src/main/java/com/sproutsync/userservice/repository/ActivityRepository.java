package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.Activity;
import com.sproutsync.userservice.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByGroup(Group group);
}

