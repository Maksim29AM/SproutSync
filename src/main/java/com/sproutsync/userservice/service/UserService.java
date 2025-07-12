package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.UserUpdateDto;
import com.sproutsync.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User create(User user);

    User update(Long id, UserUpdateDto dto);

    void delete(Long id);

    User findByUserName(String name);
}
