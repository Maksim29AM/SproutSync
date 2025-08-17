package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.userDto.request.UserUpdateRequestDto;
import com.sproutsync.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User create(User user);

    User update(Long id, UserUpdateRequestDto dto);

    void delete(Long id);

    Optional<User> findByEmail(String email);
}
