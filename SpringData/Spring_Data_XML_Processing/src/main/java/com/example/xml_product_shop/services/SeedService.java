package com.example.xml_product_shop.services;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {
    void seedUsers() throws IOException, JAXBException;
    void seedProducts() throws FileNotFoundException, JAXBException;
    void seedCategories() throws FileNotFoundException, JAXBException;

//    default void seedAll() throws FileNotFoundException {
//        seedUsers();
//        seedCategories();
//        seedProducts();
//    }
}
