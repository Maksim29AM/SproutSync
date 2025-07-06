package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.UserDto;
import com.sproutsync.userservice.model.User;

public class UserMapper {

    public static User toEntity(UserDto dto) {
        return new User(null, dto.getUsername(), dto.getSurname(), dto.getEmail(), dto.getPassword());
    }

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
