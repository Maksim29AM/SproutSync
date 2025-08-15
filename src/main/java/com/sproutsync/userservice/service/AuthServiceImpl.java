package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.authDto.request.AuthLoginRequestDto;
import com.sproutsync.userservice.dto.authDto.request.AuthRegisterRequestDto;
import com.sproutsync.userservice.dto.authDto.response.AuthResponseDto;
import com.sproutsync.userservice.mapper.AuthRegisterMapper;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.RoleRepository;
import com.sproutsync.userservice.repository.UserRepository;
import com.sproutsync.userservice.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(RoleRepository roleRepository, JwtUtil jwtUtil, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDto register(AuthRegisterRequestDto user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User saved = userRepository.save(AuthRegisterMapper.toEntity(user, roleRepository));
        return AuthRegisterMapper.toDto(saved);
    }

    @Override
    public String verify(AuthLoginRequestDto credentials) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        String subject = authentication.getName();
        return jwtUtil.generateToken(subject);
    }
}
