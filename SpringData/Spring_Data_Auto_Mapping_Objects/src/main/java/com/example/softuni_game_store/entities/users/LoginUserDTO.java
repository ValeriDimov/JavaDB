package com.example.softuni_game_store.entities.users;

import com.example.softuni_game_store.repositories.UserRepository;

public class LoginUserDTO {
    private String email;
    private String password;

    public LoginUserDTO(String emailLogin, String passwordLogin) {
        this.email = emailLogin;
        this.password = passwordLogin;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
