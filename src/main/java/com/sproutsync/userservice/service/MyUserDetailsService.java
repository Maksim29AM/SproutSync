package com.sproutsync.userservice.service;

import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.model.UserPrincipal;
import com.sproutsync.userservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        System.out.println("Loaded user: " + user.getEmail());
        System.out.println("Roles: " + user.getRoles());
        return new UserPrincipal(user);
    }
}
