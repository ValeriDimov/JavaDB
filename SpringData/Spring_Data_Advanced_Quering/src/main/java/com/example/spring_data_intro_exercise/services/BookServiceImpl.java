package com.example.spring_data_intro_exercise.services;

import com.example.spring_data_intro_exercise.enums.AgeRestriction;
import com.example.spring_data_intro_exercise.enums.EditionType;
import com.example.spring_data_intro_exercise.models.Book;
import com.example.spring_data_intro_exercise.models.ReducedBook;
import com.example.spring_data_intro_exercise.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void save(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    public List<Book> findAllBooksByAgeRestriction(String inputAgeRestriction) {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(inputAgeRestriction.toUpperCase());

        return this.bookRepository.findBooksByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> findAllGoldenBooksWithLessThan5000Copies() {
        EditionType editionType = EditionType.GOLD;
        int numberCopies = 5000;
        return this.bookRepository.findBooksByEditionTypeAndCopiesLessThan(editionType, numberCopies);
    }

    @Override
    public List<Book> findAllBooksByPriceNotBetween() {
        BigDecimal lowerPrice = BigDecimal.valueOf(5);
        BigDecimal upperPrice = BigDecimal.valueOf(40);
        return this.bookRepository.findBooksByPriceLessThanOrPriceGreaterThan(lowerPrice, upperPrice);
    }

    @Override
    public List<Book> findAllBooksNotReleasedIn(int year) {
        LocalDate dateBefore = LocalDate.of(year,1, 1);
        LocalDate dateAfter = LocalDate.of(year,12, 31);
        return this.bookRepository.findBooksByReleaseDateLessThanOrReleaseDateGreaterThan(dateBefore, dateAfter);
    }

    @Override
    public List<Book> findAllBooksReleasedBefore(String dateBefore) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateBefore = LocalDate.parse(dateBefore, formatter);

        return this.bookRepository.findAllByReleaseDateBefore(localDateBefore);
    }

    @Override
    public List<Book> findAllBooksByTitleContaining(String lettersContaining) {
        return this.bookRepository.findBooksByTitleContaining(lettersContaining);
    }

    @Override
    public List<Book> findAllBooksByAuthorLastNameContaining(String lettersContaining) {

        return this.bookRepository.findBooksByAuthorLastNameStartingWith(lettersContaining);
    }

    @Override
    public int countBooksWithTitleLongerThan(int length) {
        return this.bookRepository.countBooksByTitleLengthGreaterThan(length);
    }

    @Override
    public ReducedBook getBookByTitle(String titleInput) {

        return this.bookRepository.findReducedBookByTitle(titleInput);
    }

    @Override
    public int increaseBooksCopiesReleasedAfter(String date, int bookCopies) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate localDateAfter = LocalDate.parse(date, formatter);
        return this.bookRepository.increaseCopiesBooksReleasedAfter(localDateAfter, bookCopies);
    }

    @Override
    public int deleteBooksWithCopiesLowerThan(int underCopiesCount) {
        return this.bookRepository.deleteAllByCopiesLessThan(underCopiesCount);
    }


}
