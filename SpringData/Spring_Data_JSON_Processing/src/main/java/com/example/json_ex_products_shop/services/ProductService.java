package com.example.json_ex_products_shop.services;

import com.example.json_ex_products_shop.entities.categories.CategoryStatsDTO;
import com.example.json_ex_products_shop.entities.products.Product;
import com.example.json_ex_products_shop.entities.products.ProductExportDTO;

import java.util.List;

public interface ProductService {

    List<ProductExportDTO> getProductsInPriceRangeAndNoBuyer(double priceFrom, double priceTo);
    List<CategoryStatsDTO> getCategoryStats();
}
