package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.UserRequestDto;
import com.sproutsync.userservice.dto.UserResponseDto;
import com.sproutsync.userservice.dto.UserUpdateDto;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.sproutsync.userservice.mapper.UserMapper;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        return userService.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @PostMapping
    public UserResponseDto create(@RequestBody @Valid UserRequestDto dto) {
        User saved = userService.create(UserMapper.toEntity(dto));
        return UserMapper.toDto(saved);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id, @RequestBody @Valid UserUpdateDto dto) {
        User updated = userService.update(id, dto);
        return UserMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }
}