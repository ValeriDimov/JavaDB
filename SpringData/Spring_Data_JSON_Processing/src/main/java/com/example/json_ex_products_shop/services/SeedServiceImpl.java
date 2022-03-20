package com.example.json_ex_products_shop.services;

import com.example.json_ex_products_shop.entities.categories.Category;
import com.example.json_ex_products_shop.entities.categories.CategoriesImportDTO;
import com.example.json_ex_products_shop.entities.products.Product;
import com.example.json_ex_products_shop.entities.users.User;
import com.example.json_ex_products_shop.entities.products.ProductsImportDTO;
import com.example.json_ex_products_shop.entities.users.UserImportDTO;
import com.example.json_ex_products_shop.repositories.CategoryRepository;
import com.example.json_ex_products_shop.repositories.ProductRepository;
import com.example.json_ex_products_shop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService, Serializable {
    private static final String FILE_DIRECTORY_USERS = "D:\\Documents_Valio\\JavaProjects\\" +
            "JavaDB\\JSON_Ex_Products_Shop\\src\\main\\resources\\users.json";
    private static final String FILE_DIRECTORY_PRODUCTS = "D:\\Documents_Valio\\JavaProjects\\" +
            "JavaDB\\JSON_Ex_Products_Shop\\src\\main\\resources\\products.json";
    private static final String FILE_DIRECTORY_CATEGORIES = "D:\\Documents_Valio\\JavaProjects\\" +
            "JavaDB\\JSON_Ex_Products_Shop\\src\\main\\resources\\categories.json";

    private ModelMapper mapper;
    private Gson gson;
    private FileReader fileReader;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SeedServiceImpl(UserRepository userRepository,
                           ProductRepository productRepository,
                           CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

        mapper = new ModelMapper();
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void seedUsers() throws FileNotFoundException {

        fileReader = new FileReader(FILE_DIRECTORY_USERS);
        UserImportDTO[] userImportDTOS = gson.fromJson(fileReader, UserImportDTO[].class);

        List<User> users = Arrays.stream(userImportDTOS)
                .map(userImportDTO -> mapper.map(userImportDTO, User.class))
                .collect(Collectors.toList());

        this.userRepository.saveAll(users);
    }

    @Override
    public void seedCategories() throws FileNotFoundException {
        fileReader = new FileReader(FILE_DIRECTORY_CATEGORIES);
        CategoriesImportDTO[] categoriesImportDTOS = gson.fromJson(fileReader, CategoriesImportDTO[].class);

        List<Category> categories = Arrays
                .stream(categoriesImportDTOS)
                .map(categoriesImportDTO -> mapper.map(categoriesImportDTO, Category.class))
                .collect(Collectors.toList());

        this.categoryRepository.saveAll(categories);
    }

    @Override
    public void seedProducts() throws FileNotFoundException {
        fileReader = new FileReader(FILE_DIRECTORY_PRODUCTS);
        ProductsImportDTO[] productsImportDTOS = gson.fromJson(fileReader, ProductsImportDTO[].class);

        List<Product> products = Arrays
                .stream(productsImportDTOS)
                .map(productsImportDTO -> mapper.map(productsImportDTO, Product.class))
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .map(this::sendRandomCategories)
                .collect(Collectors.toList());

        this.productRepository.saveAll(products);
    }

    private Product sendRandomCategories(Product product) {
        Random random = new Random();
        long categoriesCount = this.categoryRepository.count();

        int randomCategories = random.nextInt((int) categoriesCount);

        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < randomCategories; i++) {
            int randomId = random.nextInt((int) categoriesCount) + 1;

            Optional<Category> randomCategory = this.categoryRepository.findById(randomId);

            categories.add(randomCategory.get());
        }
        product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(1000)) > 0) {
            return product;
        }
        Optional<User> randomBuyer = getRandomUser();
        product.setBuyer(randomBuyer.get());
        return product;
    }

    private Product setRandomSeller(Product product) {
        Optional<User> randomSeller = getRandomUser();
        product.setSeller(randomSeller.get());
        return product;
    }

    private Optional<User> getRandomUser() {
        long userCount = userRepository.count();

        int randomUserId = new Random().nextInt((int) userCount) + 1;
        Optional<User> seller = this.userRepository.findById(randomUserId);
        return seller;
    }


}
