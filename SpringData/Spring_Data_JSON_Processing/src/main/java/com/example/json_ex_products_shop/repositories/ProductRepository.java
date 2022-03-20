package com.example.json_ex_products_shop.repositories;

import com.example.json_ex_products_shop.entities.categories.CategoryStatsDTO;
import com.example.json_ex_products_shop.entities.products.Product;
import com.example.json_ex_products_shop.entities.products.ProductExportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Transactional
    @Query("SELECT new com.example.json_ex_products_shop.entities.products.ProductExportDTO(" +
            " p.name, p.price, concat(p.seller.firstName, ' ', p.seller.lastName)) FROM Product p" +
            " WHERE p.price BETWEEN :fromPriceBigDecimal AND :toPriceBigDecimal" +
            " AND p.buyer IS NULL" +
            " ORDER BY p.price ASC")
    List<ProductExportDTO> findByPriceBetweenAndByBuyerIsNull(
            BigDecimal fromPriceBigDecimal, BigDecimal toPriceBigDecimal);

    @Query("SELECT new com.example.json_ex_products_shop.entities.categories.CategoryStatsDTO(" +
            "c.name, COUNT(p) AS productsCount, AVG(p.price), SUM(p.price))" +
            " FROM Product p" +
            " JOIN p.categories c" +
            " GROUP BY c" +
            " ORDER BY productsCount DESC")
    List<CategoryStatsDTO> getCategoryStats();

}
