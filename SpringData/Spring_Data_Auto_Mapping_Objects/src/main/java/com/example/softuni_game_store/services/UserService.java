package com.example.softuni_game_store.services;

import com.example.softuni_game_store.entities.users.LoginUserDTO;
import com.example.softuni_game_store.entities.users.User;
import com.example.softuni_game_store.entities.users.UserDTO;

public interface UserService {
    void register(UserDTO user);

    User login(LoginUserDTO loginUserDTO);

    String logout();

    String fullName(String email);

    User findUserByEmailAndPassword(String email, String password);
}
