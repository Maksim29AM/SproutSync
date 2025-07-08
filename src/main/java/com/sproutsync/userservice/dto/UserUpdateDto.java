package com.sproutsync.userservice.dto;


import lombok.Data;

@Data
public class UserUpdateDto {
    private String username;
    private String surname;
    private String email;
    private String password;

}

