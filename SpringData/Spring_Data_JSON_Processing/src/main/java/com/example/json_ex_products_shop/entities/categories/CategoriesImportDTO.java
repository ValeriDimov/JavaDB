package com.example.json_ex_products_shop.entities.categories;

import com.example.json_ex_products_shop.exceptions.ValidationException;

public class CategoriesImportDTO {
    private String name;

    public CategoriesImportDTO(String name) {
        this.name = name;

        this.validate(name);
    }

    private void validate(String name) {
        if (name.length() < 3 || name.length() > 15) {
            throw new ValidationException("Name must be between 3 and 15 symbols");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
