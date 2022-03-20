package com.example.json_ex_products_shop.services;

import com.example.json_ex_products_shop.entities.users.User;
import com.example.json_ex_products_shop.entities.users.UserProductSoldDTO;
import com.example.json_ex_products_shop.entities.users.UserProductsSoldMainDTO;
import com.example.json_ex_products_shop.entities.users.UserSellerDTO;

import java.util.List;

public interface UserService {

    public List<UserSellerDTO> getSellers();

    List<UserProductSoldDTO> getSellersWithAtLeastOneProduct();
}
