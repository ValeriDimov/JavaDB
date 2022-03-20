package com.example.json_ex_products_shop.repositories;

import com.example.json_ex_products_shop.entities.products.ProductExportDTO;
import com.example.json_ex_products_shop.entities.users.User;
import com.example.json_ex_products_shop.entities.users.UserSellerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u" +
            " JOIN u.productsSold pb" +
            " WHERE pb.buyer IS NOT NULL" +
            " ORDER BY u.lastName ASC, u.firstName ASC")
    List<User> findAllBySoldProducts();

    @Transactional
    @Query("SELECT u FROM User u" +
            " JOIN u.productsSold p" +
            " WHERE u.productsSold.size > 0")
    List<User> findAllByProductsSoldAtLeastOne();
}
