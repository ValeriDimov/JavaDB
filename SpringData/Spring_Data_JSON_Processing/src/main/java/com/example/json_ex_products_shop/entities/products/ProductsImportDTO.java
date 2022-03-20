package com.example.json_ex_products_shop.entities.products;

import com.example.json_ex_products_shop.exceptions.ValidationException;

import java.math.BigDecimal;

public class ProductsImportDTO {
    private String name;
    private BigDecimal price;

    public ProductsImportDTO(String name, BigDecimal price) {
        this.name = name;
        this.price = price;

        this.validate(name);
    }

    private void validate(String name) {
        if (name.length() < 3) {
            throw new ValidationException("Name must be at least 3 symbols");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
