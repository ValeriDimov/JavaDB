package com.example.json_ex_products_shop.repositories;

import com.example.json_ex_products_shop.entities.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c.name AS category," +
            " avg(p.price) AS averagePrice, sum(p.price) AS totalRevenue FROM Category c" +
            " JOIN Product p")
    List<Category> findAllByCountByProducts();

}
