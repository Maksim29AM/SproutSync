package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.UserRequestDto;
import com.sproutsync.userservice.dto.UserResponseDto;
import com.sproutsync.userservice.model.Role;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {

    public static User toEntity(UserRequestDto dto, RoleRepository roleRepository) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(dto.getRoleIds()));
        return new User(
                null,
                dto.getUsername(),
                dto.getSurname(),
                dto.getEmail(),
                dto.getPassword(),
                roles
        );
    }


    public static UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());

        Set<String> roleNames = new HashSet<>();
        for (Role role : user.getRoles()) {
            roleNames.add(role.getName());
        }
        dto.setRoles(roleNames);

        return dto;
    }

}
