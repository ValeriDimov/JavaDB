package com.example.xml_product_shop.repositories;

import com.example.xml_product_shop.entities.products.Product;
import com.example.xml_product_shop.entities.products.ProductExportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select new com.example.xml_product_shop.entities.products.ProductExportDTO(" +
            " p.name, p.price, concat(s.firstName, ' ', s.lastName)) from Product p" +
            " JOIN p.seller AS s" +
            " where p.price between :fromAmount and :toAmount and p.buyer is null" +
            " ORDER BY p.price ASC")
    List<ProductExportDTO> findAllByPriceBetweenAndBuyerIsNull(BigDecimal fromAmount, BigDecimal toAmount);
}
