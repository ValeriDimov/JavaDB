package com.example.softuni_game_store.services;

import com.example.softuni_game_store.entities.users.LoginUserDTO;
import com.example.softuni_game_store.entities.users.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecuteImpl implements Execute {
    private UserService userService;

    @Autowired
    public ExecuteImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(String[] input) {
        String result = null;
        String command = input[0];
        switch (command) {
            case "RegisterUser":
                String email = input[1];
                String password = input[2];
                String confirmPassword = input[3];
                String fullName = input[4];

                UserDTO userDTO = new UserDTO(email, password, confirmPassword, fullName);
                userService.register(userDTO);
                result = String.format("%s was registered%n", userDTO.getFullName());
                break;

            case "LoginUser":
                String emailLogin = input[1];
                String passwordLogin = input[2];

                LoginUserDTO loginUserDTO = new LoginUserDTO(emailLogin, passwordLogin);

                userService.login(loginUserDTO);

                result = String.format("Successfully logged in %s", userService.fullName(emailLogin));
                break;

            case "Logout":

                result = userService.logout();
        }
        return result;
    }
}
