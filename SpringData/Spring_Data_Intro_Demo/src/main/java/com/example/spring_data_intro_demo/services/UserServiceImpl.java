package com.example.spring_data_intro_demo.services;

import com.example.spring_data_intro_demo.entities.User;
import com.example.spring_data_intro_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        String username = user.getUsername();
        User userByUsername = userRepository.getUserByUsername(username);
        if (userByUsername == null) {
            userRepository.save(user);
        }
    }
}
