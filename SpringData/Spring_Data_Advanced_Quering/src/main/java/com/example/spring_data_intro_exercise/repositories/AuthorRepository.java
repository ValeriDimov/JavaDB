package com.example.spring_data_intro_exercise.repositories;

import com.example.spring_data_intro_exercise.models.Author;
import com.example.spring_data_intro_exercise.models.AuthorsTotalCopies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findAllDistinctByBooksReleaseDateBefore(LocalDate releaseDate);

    Author findAuthorByFirstNameAndLastName(String firstName, String lastName);

    List<Author> findAuthorsByFirstNameEndingWith(String input);

    @Query("SELECT a.firstName as firstName, a.lastName AS lastName, sum(b.copies) AS copiesCount FROM authors a" +
            " join a.books as b" +
            " group by b.author" +
            " order by copiesCount DESC")
    List<AuthorsTotalCopies> findAuthorsBooksCopies();
}
