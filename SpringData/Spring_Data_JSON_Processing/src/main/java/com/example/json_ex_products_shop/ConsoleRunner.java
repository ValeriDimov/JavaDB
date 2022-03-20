package com.example.json_ex_products_shop;

import com.example.json_ex_products_shop.entities.categories.CategoryStatsDTO;
import com.example.json_ex_products_shop.entities.users.UserProductSoldDTO;
import com.example.json_ex_products_shop.entities.users.UserProductsSoldMainDTO;
import com.example.json_ex_products_shop.services.ProductService;
import com.example.json_ex_products_shop.services.SeedService;
import com.example.json_ex_products_shop.services.CategoryService;
import com.example.json_ex_products_shop.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    private ModelMapper mapper;
    private Gson gson;

    @Autowired
    public ConsoleRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;

        mapper = new ModelMapper();
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void run(String... args) throws Exception {

//        query 04
        List<UserProductSoldDTO> userSoldAtLeastOne = this.userService.getSellersWithAtLeastOneProduct();

        UserProductsSoldMainDTO collect = new UserProductsSoldMainDTO(userSoldAtLeastOne);

        String toJson = gson.toJson(collect);
        System.out.println(toJson);

//        query 03
//        List<CategoryStatsDTO> categoryStats = this.productService.getCategoryStats();
//        String toJson = this.gson.toJson(categoryStats);
//        System.out.println(toJson);

//        query 02
//        List<UserSellerDTO> sellers = this.userService.getSellers();
//        List<UserSellerDTO> collect = sellers
//                .stream()
//                .filter(u -> u.getProductsSold().stream().allMatch(p -> p.getBuyerFirstName() != null))
//                .collect(Collectors.toList());
//
//        String toJson = gson.toJson(collect);
//        System.out.println(toJson);


//        query 01
//        List<ProductExportDTO> products = this.productService
//                .getProductsInPriceRangeAndNoBuyer(800.00, 1200.00);
//
//        String toPrint = this.gson.toJson(products);
//
//        System.out.println(toPrint);

//        this.seedService.seedUsers();
//        this.seedService.seedCategories();
//        this.seedService.seedProducts();
    }
}
