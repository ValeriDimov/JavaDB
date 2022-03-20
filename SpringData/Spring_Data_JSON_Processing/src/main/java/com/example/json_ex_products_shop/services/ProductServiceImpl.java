package com.example.json_ex_products_shop.services;

import com.example.json_ex_products_shop.entities.categories.CategoryStatsDTO;
import com.example.json_ex_products_shop.entities.products.Product;
import com.example.json_ex_products_shop.entities.products.ProductExportDTO;
import com.example.json_ex_products_shop.repositories.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public List<ProductExportDTO> getProductsInPriceRangeAndNoBuyer(double priceFrom, double priceTo) {
        BigDecimal fromPriceBigDecimal = BigDecimal.valueOf(priceFrom);
        BigDecimal toPriceBigDecimal = BigDecimal.valueOf(priceTo);

        return this.productRepository
                .findByPriceBetweenAndByBuyerIsNull(fromPriceBigDecimal, toPriceBigDecimal);
    }

    @Override
    public List<CategoryStatsDTO> getCategoryStats() {
        return this.productRepository.getCategoryStats();

    }

}
