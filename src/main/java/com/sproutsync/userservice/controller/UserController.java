package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.userDto.request.UserCreateRequestDto;
import com.sproutsync.userservice.dto.userDto.response.UserResponseDto;
import com.sproutsync.userservice.dto.userDto.request.UserUpdateRequestDto;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.RoleRepository;
import com.sproutsync.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.sproutsync.userservice.mapper.UserMapper;

import java.util.List;

@Tag(name = "Users", description = "CRUD operations for users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Operation(summary = "List all users", description = "Returns a list of all users")
    @GetMapping
    public List<UserResponseDto> findAll() {
        return userService.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Operation(summary = "Get user by ID", description = "Returns a single user by their ID")
    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Operation(summary = "Create a new user", description = "Creates and returns a new user")
    @PostMapping
    public UserResponseDto create(@RequestBody @Valid UserCreateRequestDto dto) {
        User saved = userService.create(UserMapper.toEntity(dto, roleRepository));
        return UserMapper.toDto(saved);
    }

    @Operation(summary = "Update user", description = "Updates an existing user by ID")
    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto dto) {
        User updated = userService.update(id, dto);
        return UserMapper.toDto(updated);
    }

    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }
}