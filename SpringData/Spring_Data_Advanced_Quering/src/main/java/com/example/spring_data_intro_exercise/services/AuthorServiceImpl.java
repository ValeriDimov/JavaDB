package com.example.spring_data_intro_exercise.services;

import com.example.spring_data_intro_exercise.models.Author;
import com.example.spring_data_intro_exercise.models.AuthorsTotalCopies;
import com.example.spring_data_intro_exercise.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        long size = this.authorRepository.count();

        // 10 -> [0..9] -> [1..10]
        int authorId = new Random().nextInt((int) size) + 1;

        return this.authorRepository.findById(authorId).get();
    }

    @Override
    public List<Author> findAuthorsWhichFirstNameEnds(String input) {
        return this.authorRepository.findAuthorsByFirstNameEndingWith(input);
    }

    @Override
    public List<AuthorsTotalCopies> getTotalCopiesByAuthor() {

        return this.authorRepository.findAuthorsBooksCopies();
    }
}
