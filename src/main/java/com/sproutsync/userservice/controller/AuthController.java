package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.authDto.request.AuthLoginRequestDto;
import com.sproutsync.userservice.dto.authDto.request.AuthRegisterRequestDto;
import com.sproutsync.userservice.dto.authDto.response.AuthResponseDto;
import com.sproutsync.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Authentication and Registration")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new account")
    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody @Valid AuthRegisterRequestDto user) {
        return authService.register(user);
    }

    @Operation(summary = "Login", description = "Verifies user credentials and returns a JWT token as plain text.")
    @PostMapping("/login")
    public String login(@RequestBody @Valid AuthLoginRequestDto user) {
        return authService.verify(user);
    }
}
