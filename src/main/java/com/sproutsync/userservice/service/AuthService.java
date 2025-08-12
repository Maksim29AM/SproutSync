package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.authDto.request.AuthLoginRequestDto;
import com.sproutsync.userservice.dto.authDto.request.AuthRegisterRequestDto;
import com.sproutsync.userservice.dto.authDto.responce.AuthResponseDto;

public interface AuthService {

    AuthResponseDto register(AuthRegisterRequestDto user);

    String verify(AuthLoginRequestDto user);
}
