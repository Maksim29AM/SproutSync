package com.sproutsync.userservice.service;

import com.sproutsync.userservice.model.User;

public interface AuthService {

    User register(User user);

    String verify(User user);
}
