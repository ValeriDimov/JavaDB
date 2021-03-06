package com.example.spring_data_intro_exercise.repositories;

import com.example.spring_data_intro_exercise.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
