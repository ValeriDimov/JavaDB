package com.example.softuni_game_store.entities.users;

import com.example.softuni_game_store.exeptions.ValidationException;
import org.springframework.validation.annotation.Validated;

@Validated
public class UserDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserDTO(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;

        this.validate();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void validate() {
        if (!email.contains("@") || (!email.contains(".")) ||
                (email.lastIndexOf(".") < email.indexOf("@"))) {
            throw new ValidationException("Invalid email format");
        }

        if (password.length() < 6) {
            throw new ValidationException("Invalid password length");
        }

        boolean digitSymbol = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                digitSymbol = true;
            }
        }
        boolean lowerCaseSymbol = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                lowerCaseSymbol = true;
            }
        }

        boolean upperCaseSymbol = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                upperCaseSymbol = true;
            }
        }

        if ((!digitSymbol) || (!lowerCaseSymbol) || (!upperCaseSymbol)) {
            throw new ValidationException("Invalid password requirements");
        }

        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Passwords do not match");
        }
    }
}