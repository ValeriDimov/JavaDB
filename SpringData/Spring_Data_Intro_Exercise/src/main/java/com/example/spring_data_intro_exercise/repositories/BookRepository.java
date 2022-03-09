package com.example.spring_data_intro_exercise.repositories;

import com.example.spring_data_intro_exercise.models.Author;
import com.example.spring_data_intro_exercise.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByTitle(String title);

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDate);

    List<Book> findAllBooksByAuthorOrderByReleaseDateDescTitleAsc(Author author);
}
