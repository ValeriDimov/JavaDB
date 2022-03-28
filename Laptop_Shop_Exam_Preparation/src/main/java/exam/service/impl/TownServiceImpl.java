package exam.service.impl;

import exam.model.dtos.ImportTownDTO;
import exam.model.dtos.ImportTownsRootDTO;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TownServiceImpl implements TownService {
    private static final Path FILE_DIRECTORY_TOWNS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Laptop_Shop_Exam_Preparation\\src\\main\\resources\\files\\xml\\towns.xml");
    private static final String FILE_DIRECTORY_TOWNS_STRING = ("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Laptop_Shop_Exam_Preparation\\src\\main\\resources\\files\\xml\\towns.xml");

    private final TownRepository townRepository;

    private ModelMapper mapper;
    private Validator validator;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_TOWNS);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ImportTownsRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportTownsRootDTO townsRootDTO = (ImportTownsRootDTO) unmarshaller
                .unmarshal(new FileReader(FILE_DIRECTORY_TOWNS.toAbsolutePath().toString()));

        List<String> results = new ArrayList<>();

        List<ImportTownDTO> dtoTowns = townsRootDTO.getTowns();

        for (ImportTownDTO dtoTown : dtoTowns) {
            Set<ConstraintViolation<ImportTownDTO>> validateErrors = this.validator.validate(dtoTown);

            if (validateErrors.isEmpty()) {
                Optional<Town> townByName = this.townRepository.findByName(dtoTown.getName());
                if (townByName.isEmpty()) {
                    Town town = mapper.map(dtoTown, Town.class);

                    this.townRepository.save(town);
                    results.add(String.format("Successfully imported Town %s", town.getName()));

                } else {
                    results.add("Invalid town");
                }

            } else {
                results.add("Invalid town");
            }
        }

        return String.join("\n", results);
    }
}
