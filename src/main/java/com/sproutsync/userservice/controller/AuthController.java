package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.authDto.request.AuthLoginRequestDto;
import com.sproutsync.userservice.dto.authDto.request.AuthRegisterRequestDto;
import com.sproutsync.userservice.dto.authDto.responce.AuthResponseDto;
import com.sproutsync.userservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody @Valid AuthRegisterRequestDto user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid AuthLoginRequestDto user) {
        return authService.verify(user);
    }
}
