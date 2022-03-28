package exam.service.impl;

import exam.model.dtos.ImportShopDTO;
import exam.model.dtos.ImportShopsRootDTO;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShopServiceImpl implements ShopService {
    private static final Path FILE_DIRECTORY_SHOPS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Laptop_Shop_Exam_Preparation\\src\\main\\resources\\files\\xml\\shops.xml");

    private final ShopRepository shopRepository;
    private final TownRepository townRepository;

    private ModelMapper mapper;
    private Validator validator;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException, JAXBException {
       return Files.readString(FILE_DIRECTORY_SHOPS);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ImportShopsRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportShopsRootDTO shopsRootDTO = (ImportShopsRootDTO) unmarshaller
                .unmarshal(FILE_DIRECTORY_SHOPS.toFile());

        List<String> results = new ArrayList<>();

        List<ImportShopDTO> dtoShops = shopsRootDTO.getShops();

        for (ImportShopDTO dtoShop : dtoShops) {
            Set<ConstraintViolation<ImportShopDTO>> validateErrors = this.validator.validate(dtoShop);

            Optional<Shop> optionalShop = this.shopRepository.findByName(dtoShop.getName());

            if (validateErrors.isEmpty()) {
                if (optionalShop.isEmpty()) {
                    Shop shop = mapper.map(dtoShop, Shop.class);
                    Optional<Town> town = this.townRepository.findByName(dtoShop.getTown().getName());
                    shop.setTown(town.get());

                    this.shopRepository.save(shop);

                    results.add(String.format("Successfully imported Shop %s - %.0f ",
                            shop.getName(), shop.getIncome()));

                } else {
                    results.add("Invalid shop");
                }

            } else {
                results.add("Invalid shop");
            }

        }

        return String.join("\n", results);
    }
}
