package com.example.xml_product_shop.repositories;

import com.example.xml_product_shop.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u" +
            " JOIN u.productsSold ps" +
            " WHERE u.productsSold.size > 0" +
            " ORDER BY u.lastName ASC, u.firstName ASC")
    List<User> findAllByIdProductsSoldIsNotNullAndHaveBuyer();
}
