package com.sproutsync.userservice.dto.authDto.responce;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {

    private Long id;
    private String username;
    private String surname;
    private String email;
    private String roles;
}