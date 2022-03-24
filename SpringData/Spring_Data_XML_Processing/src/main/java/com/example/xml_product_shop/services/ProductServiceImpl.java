package com.example.xml_product_shop.services;

import com.example.xml_product_shop.entities.products.Product;
import com.example.xml_product_shop.entities.products.ProductExportDTO;
import com.example.xml_product_shop.entities.products.ProductsExportDTO;
import com.example.xml_product_shop.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

        mapper = new ModelMapper();
    }


    @Override
    public List<ProductExportDTO> getProductInPriceRangeWithNoBuyer(double priceFrom, double priceTo) {
        BigDecimal fromAmount = BigDecimal.valueOf(priceFrom);
        BigDecimal toAmount = BigDecimal.valueOf(priceTo);

        List<ProductExportDTO> allByPriceBetweenAndBuyerIsNull = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNull(fromAmount, toAmount);

        return allByPriceBetweenAndBuyerIsNull;
    }
}
