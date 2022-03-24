package com.example.xml_product_shop.services;

import com.example.xml_product_shop.entities.products.Product;
import com.example.xml_product_shop.entities.categories.CategoriesImporterDTO;
import com.example.xml_product_shop.entities.categories.Category;
import com.example.xml_product_shop.entities.products.ProductsImporterDTO;
import com.example.xml_product_shop.entities.users.User;
import com.example.xml_product_shop.entities.users.UsersImportDTO;
import com.example.xml_product_shop.repositories.CategoryRepository;
import com.example.xml_product_shop.repositories.ProductRepository;
import com.example.xml_product_shop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {
    private static final String FILE_DIRECTORY_USERS = "D:\\Documents_Valio\\JavaProjects\\" +
            "JavaDB\\XML_Product_Shop\\src\\main\\resources\\users.xml";
    private static final String FILE_DIRECTORY_PRODUCTS = "D:\\Documents_Valio\\JavaProjects\\" +
            "JavaDB\\XML_Product_Shop\\src\\main\\resources\\products.xml";
    private static final String FILE_DIRECTORY_CATEGORIES = "D:\\Documents_Valio\\JavaProjects\\" +
            "JavaDB\\XML_Product_Shop\\src\\main\\resources\\categories.xml";

    private ModelMapper mapper;
    private FileReader fileReader;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, ProductRepository productRepository,
                           CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

        this.mapper = new ModelMapper();
    }


    @Override
    public void seedUsers() throws IOException, JAXBException {

        Path path = Path.of(FILE_DIRECTORY_USERS);
        fileReader = new FileReader(FILE_DIRECTORY_USERS);
        BufferedReader xmlReader = Files.newBufferedReader(path);

        JAXBContext context = JAXBContext.newInstance(UsersImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        UsersImportDTO usersImportDTO = (UsersImportDTO) unmarshaller.unmarshal(fileReader);

        List<User> collect = usersImportDTO.getUsers()
                .stream()
                .map(users -> mapper.map(users, User.class))
                .collect(Collectors.toList());


        this.userRepository.saveAll(collect);
    }

    @Override
    public void seedCategories() throws FileNotFoundException, JAXBException {
        fileReader = new FileReader(FILE_DIRECTORY_CATEGORIES);

        JAXBContext context = JAXBContext.newInstance(CategoriesImporterDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CategoriesImporterDTO categoriesImporterDTO = (CategoriesImporterDTO) unmarshaller.unmarshal(fileReader);

        List<Category> categoryList = categoriesImporterDTO
                .getCategories()
                .stream()
                .map(categories -> mapper.map(categories, Category.class))
                .collect(Collectors.toList());

        this.categoryRepository.saveAll(categoryList);
    }

    @Override
    public void seedProducts() throws FileNotFoundException, JAXBException {
        fileReader = new FileReader(FILE_DIRECTORY_PRODUCTS);
        JAXBContext context = JAXBContext.newInstance(ProductsImporterDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ProductsImporterDTO productsImporterDTO = (ProductsImporterDTO) unmarshaller.unmarshal(fileReader);

        List<Product> products = productsImporterDTO.getProducts()
                .stream()
                .map(p -> mapper.map(p, Product.class))
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