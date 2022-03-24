package com.example.xml_product_shop;

import com.example.xml_product_shop.entities.users.UserExportDTO;
import com.example.xml_product_shop.entities.users.UsersExportDTO;
import com.example.xml_product_shop.services.ProductService;
import com.example.xml_product_shop.services.SeedService;
import com.example.xml_product_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private ModelMapper mapper;

    @Autowired
    public ConsoleRunner(SeedService seedService, ProductService productService, UserService userService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;

        mapper = new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
//        query 2
        List<UserExportDTO> usersWithSalesAndBuyer = this.userService.getUsersWithSalesAndBuyer();
        UsersExportDTO usersExportDTO = new UsersExportDTO(usersWithSalesAndBuyer);

        JAXBContext context = JAXBContext.newInstance(UsersExportDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(usersExportDTO, System.out);


//        query 1
//        List<ProductExportDTO> products = this.productService
//                .getProductInPriceRangeWithNoBuyer(800.00, 1200.00);
//
//        ProductsExportDTO productsExportDTO = new ProductsExportDTO(products);
//
//        JAXBContext context = JAXBContext.newInstance(ProductsExportDTO.class);
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(productsExportDTO, System.out);


//        this.seedService.seedUsers();

//        this.seedService.seedCategories();

//        this.seedService.seedProducts();
    }
}
