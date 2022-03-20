package com.example.json_ex_products_shop.entities.users;

import com.example.json_ex_products_shop.exceptions.ValidationException;

public class UserImportDTO {
    private String firstName;
    private String lastName;
    private Integer age;

    public UserImportDTO(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;

        this.validate(lastName);
    }

    private void validate(String lastName) {
        if (lastName.length() < 3) {
            throw new ValidationException("Name must be at least 3 symbols");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
