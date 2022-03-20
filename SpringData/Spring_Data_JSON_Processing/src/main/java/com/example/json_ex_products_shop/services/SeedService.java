package com.example.json_ex_products_shop.services;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedUsers() throws FileNotFoundException;
    void seedProducts() throws FileNotFoundException;
    void seedCategories() throws FileNotFoundException;

    default void seedAll() throws FileNotFoundException {
        seedUsers();
        seedCategories();
        seedProducts();
    }
}
