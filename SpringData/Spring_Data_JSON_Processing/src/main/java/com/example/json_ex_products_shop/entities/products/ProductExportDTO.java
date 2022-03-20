package com.example.json_ex_products_shop.entities.products;

import com.example.json_ex_products_shop.entities.users.User;

import java.math.BigDecimal;

public class ProductExportDTO {
    private String name;
    private BigDecimal price;
    private String seller;

    public ProductExportDTO(String name, BigDecimal price, String seller) {
        this.name = name;
        this.price = price;
        this.seller = seller;
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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
