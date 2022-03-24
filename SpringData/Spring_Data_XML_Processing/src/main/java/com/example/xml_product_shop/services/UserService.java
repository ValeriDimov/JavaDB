package com.example.xml_product_shop.services;

import com.example.xml_product_shop.entities.users.UserExportDTO;

import java.util.List;

public interface UserService {
    List<UserExportDTO> getUsersWithSalesAndBuyer();
}
