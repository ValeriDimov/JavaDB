package com.example.spring_data_intro_exercise.repositories;

import com.example.spring_data_intro_exercise.enums.AgeRestriction;
import com.example.spring_data_intro_exercise.enums.EditionType;
import com.example.spring_data_intro_exercise.models.Author;
import com.example.spring_data_intro_exercise.models.Book;
import com.example.spring_data_intro_exercise.models.ReducedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByTitle(String title);

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDate);

    List<Book> findAllBooksByAuthorOrderByReleaseDateDescTitleAsc(Author author);

    List<Book> findBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findBooksByEditionTypeAndCopiesLessThan(EditionType editionType, int numberCopies);

    List<Book> findBooksByPriceLessThanOrPriceGreaterThan(BigDecimal lowerPrice, BigDecimal upperPrice);

    List<Book> findBooksByReleaseDateLessThanOrReleaseDateGreaterThan(LocalDate dateBefore, LocalDate dateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate localDateBefore);

    List<Book> findBooksByTitleContaining(String lettersContaining);

    List<Book> findBooksByAuthorLastNameStartingWith(String lettersContaining);

    @Query("SELECT count(b) FROM books b WHERE length(b.title) > :length")
    int countBooksByTitleLengthGreaterThan(int length);


    @Query("select b.title AS title, b.ageRestriction AS ageRestriction, " +
            "b.editionType AS editionType, b.price AS price from books b where b.title = :titleInput")
    ReducedBook findReducedBookByTitle(String titleInput);

    @Transactional
    @Modifying
    @Query("UPDATE books b SET b.copies = b.copies + :bookCopies WHERE b.releaseDate > :localDateAfter")
    int increaseCopiesBooksReleasedAfter(LocalDate localDateAfter, int bookCopies);

    @Transactional
    @Modifying
    @Query("delete from books b where b.copies < :underCopiesCount")
    int deleteAllByCopiesLessThan(int underCopiesCount);
}
