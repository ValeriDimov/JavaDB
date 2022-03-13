package com.example.spring_data_intro_exercise.services;

import com.example.spring_data_intro_exercise.models.Book;
import com.example.spring_data_intro_exercise.models.ReducedBook;

import java.util.List;

public interface BookService {
    void save(Book book);

    List<Book> findAllBooksByAgeRestriction(String inputAgeRestriction);

    List<Book> findAllGoldenBooksWithLessThan5000Copies();

    List<Book> findAllBooksByPriceNotBetween();

    List<Book> findAllBooksNotReleasedIn(int year);

    List<Book> findAllBooksReleasedBefore(String dateBefore);

    List<Book> findAllBooksByTitleContaining(String lettersContaining);

    List<Book> findAllBooksByAuthorLastNameContaining(String lettersContaining);

    int countBooksWithTitleLongerThan(int length);

    ReducedBook getBookByTitle(String titleInput);

    int increaseBooksCopiesReleasedAfter(String date, int bookCopies);

    int deleteBooksWithCopiesLowerThan(int underCopiesCount);
}
