package exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.model.dtos.ImportLaptopDTO;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {
    private static final Path FILE_DIRECTORY_LAPTOPS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Laptop_Shop_Exam_Preparation\\src\\main\\resources\\files\\json\\laptops.json");

    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;

    private ModelMapper mapper;
    private Gson gson;
    private Validator validator;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;

        this.mapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_LAPTOPS);
    }

    @Override
    public String importLaptops() throws IOException {
        ImportLaptopDTO[] dtos = gson.fromJson(readLaptopsFileContent(), ImportLaptopDTO[].class);

        List<String> results = new ArrayList<>();

        for (ImportLaptopDTO dto : dtos) {
            Set<ConstraintViolation<ImportLaptopDTO>> validateErrors = this.validator.validate(dto);

            if (validateErrors.isEmpty()) {
                Optional<Laptop> optionalLaptop = this.laptopRepository.findByMacAddress(dto.getMacAddress());

                if (optionalLaptop.isEmpty()) {
                    Optional<Shop> optionalShop = this.shopRepository.findByName(dto.getShop().getName());

                    Laptop laptop = this.mapper.map(dto, Laptop.class);
                    laptop.setShop(optionalShop.get());

                    if (laptop.getWarrantyType() != null) {
                        this.laptopRepository.save(laptop);

                        results.add(String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                                laptop.getMacAddress(), laptop.getCpuSpeed(), laptop.getRam(), laptop.getStorage() ));

                    } else {
                        results.add("Invalid Laptop");
                    }

                } else {
                    results.add("Invalid Laptop");
                }

            } else {
                results.add("Invalid Laptop");
            }
        }

        return String.join("\n", results);
    }

    @Override
    public String exportBestLaptops() {
        List<Laptop> laptops = this.laptopRepository.findAllCustomRequest();

        String result = laptops
                .stream()
                .map(Laptop::toString)
                .collect(Collectors.joining("\n"));

        return result;
    }
}
