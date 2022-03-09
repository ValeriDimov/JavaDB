package com.example.spring_data_intro_exercise.services;

import com.example.spring_data_intro_exercise.enums.AgeRestriction;
import com.example.spring_data_intro_exercise.enums.EditionType;
import com.example.spring_data_intro_exercise.models.Author;
import com.example.spring_data_intro_exercise.models.Book;
import com.example.spring_data_intro_exercise.models.Category;
import com.example.spring_data_intro_exercise.repositories.AuthorRepository;
import com.example.spring_data_intro_exercise.repositories.BookRepository;
import com.example.spring_data_intro_exercise.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {
    private static final String RESOURCE_PATH = "D:\\Documents_Valio\\JavaProjects" +
            "\\JavaDB\\Spring_Data_Intro_Exercise\\src\\main\\resources\\files";
    private static final String AUTHORS_FILE_PATH = RESOURCE_PATH + "\\authors.txt";
    private static final String CATEGORIES_FILE_PATH = RESOURCE_PATH + "\\categories.txt";
    private static final String BOOKS_FILE_PATH = RESOURCE_PATH + "\\books.txt";

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final CategoryService categoryService;

    @Autowired
    public SeedServiceImpl(AuthorRepository authorRepository, CategoryRepository categoryRepository
            , BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedAuthors() throws IOException {
        Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                .stream()
                .filter(s -> !s.isBlank())
                .map(s -> s.split(" "))
                .map(names -> new Author(names[0], names[1]))
                .forEach(authorRepository::save);
    }

    @Override
    public void seedCategories() throws IOException {
        Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))
                .stream()
                .filter(s -> !s.isBlank())
                .map(Category::new)
                .forEach(categoryRepository::save);

    }


    @Override
    public void seedBooks() throws IOException {
        Files.readAllLines(Path.of(BOOKS_FILE_PATH))
                .stream()
                .filter(s -> !s.isBlank())
                .map(this::getBookObject)
                .forEach(bookRepository::save);
    }

    private Book getBookObject(String line) {
        String[] bookParts = line.split(" ");

        int editionTypeIndex = Integer.parseInt(bookParts[0]);
        EditionType editionType = EditionType.values()[editionTypeIndex];

        LocalDate publishDate =
                LocalDate.parse(bookParts[1], DateTimeFormatter.ofPattern("d/M/yyyy"));

        int copies = Integer.parseInt(bookParts[2]);
        BigDecimal price = new BigDecimal(bookParts[3]);

        int ageRestrictionIndex = Integer.parseInt(bookParts[4]);
        AgeRestriction ageRestriction = AgeRestriction.values()[ageRestrictionIndex];

        String title = Arrays.stream(bookParts)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService.getRandomCategory();

        return new Book(title, editionType, price, copies, publishDate,
                ageRestriction, author, categories);
    }
}
