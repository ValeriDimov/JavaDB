package com.example.xml_product_shop.services;

import com.example.xml_product_shop.entities.users.User;
import com.example.xml_product_shop.entities.users.UserExportDTO;
import com.example.xml_product_shop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

        this.mapper = new ModelMapper();
    }

    @Override
    @Transactional
    public List<UserExportDTO> getUsersWithSalesAndBuyer() {
        List<User> users = this.userRepository.findAllByIdProductsSoldIsNotNullAndHaveBuyer();

        List<UserExportDTO> userExportDTOS = users
                .stream()
                .map(u -> mapper.map(u, UserExportDTO.class))
                .collect(Collectors.toList());

        return userExportDTOS
                .stream()
                .filter(u -> u.getProductsSold().stream().allMatch(p -> p.getBuyerFirstName() != null))
                .collect(Collectors.toList());


    }
}
