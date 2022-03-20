package com.example.json_ex_products_shop.services;

import com.example.json_ex_products_shop.entities.products.Product;
import com.example.json_ex_products_shop.entities.users.User;
import com.example.json_ex_products_shop.entities.users.UserProductSoldDTO;
import com.example.json_ex_products_shop.entities.users.UserProductsSoldMainDTO;
import com.example.json_ex_products_shop.entities.users.UserSellerDTO;
import com.example.json_ex_products_shop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

        mapper = new ModelMapper();
    }

    @Transactional
    @Override
    public List<UserSellerDTO> getSellers() {

        List<User> allBySoldProducts = this.userRepository.findAllBySoldProducts();

        return allBySoldProducts
                .stream()
                .map(user -> this.mapper.map(user, UserSellerDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<UserProductSoldDTO> getSellersWithAtLeastOneProduct() {
        List<User> allByProductsSoldAtLeastOne = this.userRepository.findAllByProductsSoldAtLeastOne();

        return allByProductsSoldAtLeastOne
                .stream()
                .map(user -> this.mapper.map(user, UserProductSoldDTO.class))
                .collect(Collectors.toList());

    }
}
