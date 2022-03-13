package com.example.spring_data_intro_exercise.services;

import com.example.spring_data_intro_exercise.models.Category;

import java.util.HashSet;

public interface CategoryService {
    HashSet<Category> getRandomCategory();
}
