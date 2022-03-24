package com.example.xml_product_shop.services;

import com.example.xml_product_shop.entities.products.ProductExportDTO;
import com.example.xml_product_shop.entities.products.ProductsExportDTO;

import java.util.List;

public interface ProductService {

    List<ProductExportDTO> getProductInPriceRangeWithNoBuyer(double priceFrom, double priceTo);
}
