package com.sproutsync.userservice.service;

import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User fromBD = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        fromBD.setUsername(user.getUsername());
        fromBD.setSurname(user.getSurname());
        fromBD.setPassword(user.getPassword());
        return userRepository.save(fromBD);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
