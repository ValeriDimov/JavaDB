package com.example.spring_data_intro_exercise.services;

import com.example.spring_data_intro_exercise.models.Author;
import com.example.spring_data_intro_exercise.models.AuthorsTotalCopies;

import java.util.List;

public interface AuthorService {
    Author getRandomAuthor();

    List<Author> findAuthorsWhichFirstNameEnds(String input);

    List<AuthorsTotalCopies> getTotalCopiesByAuthor();
}
