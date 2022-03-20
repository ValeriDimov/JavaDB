package com.example.json_ex_products_shop.services;

import com.example.json_ex_products_shop.entities.categories.CategoryStatsDTO;
import com.example.json_ex_products_shop.repositories.CategoryRepository;
import com.example.json_ex_products_shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

}
