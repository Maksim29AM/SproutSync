package com.sproutsync.userservice.service;

import com.sproutsync.userservice.model.Role;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.RoleRepository;
import com.sproutsync.userservice.repository.UserRepository;
import com.sproutsync.userservice.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

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
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_PARENT")
                    .orElseThrow(() -> new RuntimeException("Default role ROLE_PARENT not found"));
            user.setRoles(Set.of(defaultRole));
        }
        return userRepository.save(user);
    }

    @Override
    public String verify(User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
           return jwtUtil.generateToken(user.getEmail());
        }
        throw new BadCredentialsException("Invalid credentials");
    }
}
