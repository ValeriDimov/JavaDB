package com.example.spring_data_intro_exercise;

import com.example.spring_data_intro_exercise.services.AuthorService;
import com.example.spring_data_intro_exercise.services.BookService;
import com.example.spring_data_intro_exercise.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public ConsoleRunner(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }


    //---------------------------------------------------------------
//    private final SeedService seedService;
//    private final BookRepository bookRepository;
//    private final AuthorRepository authorRepository;

//    @Autowired
//    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository) {
//        this.seedService = seedService;
//        this.bookRepository = bookRepository;
//        this.authorRepository = authorRepository;
//    }

    @Override
    public void run(String... args) throws IOException {
        Scanner scanner = new Scanner(System.in);
//        13
        int underCopiesCount = Integer.parseInt(scanner.nextLine());
        int deletedBooks = this.bookService.deleteBooksWithCopiesLowerThan(underCopiesCount);
        System.out.println(deletedBooks);

//        12
//        String date = scanner.nextLine();
//        int bookCopies = Integer.parseInt(scanner.nextLine());
//        int increasedCopies = this.bookService.increaseBooksCopiesReleasedAfter(date, bookCopies);
//        System.out.println(increasedCopies * bookCopies);

//        11
//        String titleInput = scanner.nextLine();
//        ReducedBook bookByTitle = this.bookService.getBookByTitle(titleInput);
//        System.out.printf("%s %s %s %.2f",
//                bookByTitle.getTitle(),
//                bookByTitle.getEditionType(),
//                bookByTitle.getAgeRestriction(),
//                bookByTitle.getPrice());

//        10
//        this.authorService.getTotalCopiesByAuthor()
//                .stream()
//                .map(a -> a.getFirstName() + " " + a.getLastName() + " - " + a.getCopiesCount())
//                .forEach(System.out::println);

//        09
//        int length = Integer.parseInt(scanner.nextLine());
//        int booksCount = this.bookService.countBooksWithTitleLongerThan(length);
//        System.out.printf("There are %d books with longer title than %d symbols", booksCount, length);

//        08
//        String lettersContaining = scanner.nextLine();
//
//        this.bookService.findAllBooksByAuthorLastNameContaining(lettersContaining)
//                .stream()
//                .map(b -> b.getTitle() + " (" + b.getAuthor().getFirstName() + " " + b.getAuthor().getLastName() + ")")
//                .forEach(System.out::println);

//        07
//        String lettersContaining = scanner.nextLine();
//        this.bookService.findAllBooksByTitleContaining(lettersContaining)
//                .stream().map(Book::getTitle)
//                .forEach(System.out::println);

//        06
//        String input = scanner.nextLine();
//        this.authorService.findAuthorsWhichFirstNameEnds(input)
//                .stream().map(a -> a.getFirstName() + " " + a.getLastName())
//                .forEach(System.out::println);

//        05
//        String dateBefore = scanner.nextLine();
//        this.bookService.findAllBooksReleasedBefore(dateBefore)
//                .stream().map(b -> b.getTitle() + " " + b.getEditionType() + " " + b.getPrice())
//                .forEach(System.out::println);

//        04
//        int year = Integer.parseInt(scanner.nextLine());
//        this.bookService.findAllBooksNotReleasedIn(year)
//                .stream().map(Book::getTitle)
//                .forEach(System.out::println);

//        03
//        this.bookService.findAllBooksByPriceNotBetween()
//                .stream()
//                .map(b -> b.getTitle() + " - $"  + b.getPrice())
//                .forEach(System.out::println);

//        02
//        this.bookService.findAllGoldenBooksWithLessThan5000Copies()
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);

//        01
//        String inputAgeRestriction = scanner.nextLine();
//
//        this.bookService.findAllBooksByAgeRestriction(inputAgeRestriction)
//                .stream().map(Book::getTitle)
//                .forEach(System.out::println);

// ---------------------------------------------------------------------------------------------------
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
//        Author author = authorRepository.findAuthorByFirstNameAndLastName("George", "Powell");
//
//        bookRepository.findAllBooksByAuthorOrderByReleaseDateDescTitleAsc(author)
//                .forEach(b -> System.out.println(b.getTitle() + " " + b.getReleaseDate() + " " + b.getCopies()));

    }


}