package com.example.json_ex_products_shop.entities.users;

import com.example.json_ex_products_shop.entities.products.ProductSoldDTO;

import java.util.List;

public class UserProductSoldDTO {
    private String firstName;
    private String lastName;
    private int age;
    private List<ProductSoldDTO> productsSold;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<ProductSoldDTO> getSoldProducts() {
        return productsSold;
    }

    public void setSoldProducts(List<ProductSoldDTO> productsSold) {
        this.productsSold = productsSold;
    }
}
