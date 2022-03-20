package com.example.json_ex_products_shop.entities.users;

import com.example.json_ex_products_shop.entities.products.ProductBoughtDTO;

import java.util.List;

public class UserSellerDTO {
    private String firstName;
    private String lastName;
    private List<ProductBoughtDTO> productsSold;


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

    public List<ProductBoughtDTO> getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(List<ProductBoughtDTO> productsSold) {
        this.productsSold = productsSold;
    }
}
