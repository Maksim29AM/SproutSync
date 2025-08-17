package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.userDto.request.UserUpdateRequestDto;
import com.sproutsync.userservice.model.Role;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.RoleRepository;
import com.sproutsync.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_PARENT")
                    .orElseThrow(() -> new RuntimeException("Default role ROLE_PARENT not found"));
            user.setRoles(Set.of(defaultRole));
        }
        return userRepository.save(user);
    }


    @Override
    public User update(Long id, UserUpdateRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getSurname() != null) {
            user.setSurname(dto.getSurname());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : dto.getRoleIds()) {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(role);
            }
            user.setRoles(roles);
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
