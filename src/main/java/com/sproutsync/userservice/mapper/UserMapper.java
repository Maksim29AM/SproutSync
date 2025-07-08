package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.UserRequestDto;
import com.sproutsync.userservice.dto.UserResponseDto;
import com.sproutsync.userservice.model.Role;
import com.sproutsync.userservice.model.User;

public class UserMapper {

    public static User toEntity(UserRequestDto dto) {
        return new User(null, dto.getUsername(), dto.getSurname(), dto.getEmail(), dto.getPassword(), Role.PARENT);
    }

    public static UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}
