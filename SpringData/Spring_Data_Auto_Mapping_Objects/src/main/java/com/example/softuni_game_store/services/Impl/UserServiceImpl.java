package com.example.softuni_game_store.services.Impl;

import com.example.softuni_game_store.entities.users.LoginUserDTO;
import com.example.softuni_game_store.entities.users.User;
import com.example.softuni_game_store.entities.users.UserDTO;
import com.example.softuni_game_store.exeptions.ValidationException;
import com.example.softuni_game_store.repositories.UserRepository;
import com.example.softuni_game_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private User currentUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserDTO user) {

        ModelMapper mapper = new ModelMapper();
        User toRegister = mapper.map(user, User.class);

        long usersCount = userRepository.count();
        if (usersCount == 0) {
            toRegister.setAdministrator(true);
        }

        userRepository.save(toRegister);
    }

    @Override
    public User login(LoginUserDTO loginUserDTO) {
        ModelMapper mapperLogin = new ModelMapper();
        User mapUser = mapperLogin.map(loginUserDTO, User.class);
        User userCheck = userRepository.findUserByEmailAndPassword(mapUser.getEmail(), mapUser.getPassword());

        if (userCheck == null) {
            throw new ValidationException("Incorrect username / password");
        }

         this.currentUser = mapUser;

        return this.currentUser;
    }

    @Override
    public String logout() {
        if (this.currentUser == null) {
           throw new ValidationException("Cannot log out. No user was logged in.");
        }
        String fullName = userRepository.findUserByEmailAndPassword(currentUser.getEmail(), currentUser.getPassword())
                .getFullName();

        this.currentUser = null;
        return String.format("User %s successfully logged out", fullName);
    }

    @Override
    public String fullName(String email) {
        return userRepository.findUserByEmail(email).getFullName();
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }


}
