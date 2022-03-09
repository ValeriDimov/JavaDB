package com.example.spring_data_intro_exercise.repositories;

import com.example.spring_data_intro_exercise.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findAllDistinctByBooksReleaseDateBefore(LocalDate releaseDate);

    Author findAuthorByFirstNameAndLastName(String firstName, String lastName);
}
