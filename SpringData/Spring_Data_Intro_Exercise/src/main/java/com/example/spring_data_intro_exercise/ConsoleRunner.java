package com.example.spring_data_intro_exercise;

import com.example.spring_data_intro_exercise.models.Author;
import com.example.spring_data_intro_exercise.repositories.AuthorRepository;
import com.example.spring_data_intro_exercise.repositories.BookRepository;
import com.example.spring_data_intro_exercise.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws IOException {
//       seedService.seedAll();

//        query01
//        bookRepository.findAllByReleaseDateAfter(LocalDate.of(2000, 12, 31))
//                .forEach(book -> System.out.println(book.getTitle()));

//        query02
//        authorRepository.findAllDistinctByBooksReleaseDateBefore(LocalDate.of(1990,1,1))
//                .forEach(a -> System.out.println(a.getFirstName() +" " + a.getLastName()));

//        query03
//        List<Author> authors = authorRepository.findAll();
//        authors.stream()
//                .sorted((a1, a2) -> a2.getBooks().size() - a1.getBooks().size())
//                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName() + " " + a.getBooks().size()));

//      query04
        Author author = authorRepository.findAuthorByFirstNameAndLastName("George", "Powell");

        bookRepository.findAllBooksByAuthorOrderByReleaseDateDescTitleAsc(author)
                .forEach(b -> System.out.println(b.getTitle() + " " + b.getReleaseDate() + " " + b.getCopies()));

    }
}