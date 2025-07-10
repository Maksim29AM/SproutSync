package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
