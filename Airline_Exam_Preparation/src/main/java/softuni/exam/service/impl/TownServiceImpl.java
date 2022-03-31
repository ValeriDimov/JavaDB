package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.ImportTownDTO;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TownServiceImpl implements TownService {
    private final static Path FILE_DIRECTORY_TOWNS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Airline_Exam_Preparation\\src\\main\\resources\\files\\json\\towns.json");

    private final TownRepository townRepository;

    private ModelMapper mapper;
    private Validator validator;
    private Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().create();
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
    public String importTowns() throws IOException {
        ImportTownDTO[] dtos = this.gson.fromJson(readTownsFileContent(), ImportTownDTO[].class);

        List<String> results = new ArrayList<>();

        for (ImportTownDTO dto : dtos) {
            Set<ConstraintViolation<ImportTownDTO>> validateErrors = this.validator.validate(dto);

            if (validateErrors.isEmpty()) {
                if (dto.getPopulation() > 0) {
                    Town town = this.mapper.map(dto, Town.class);
                    this.townRepository.save(town);

                    results.add(String.format("Successfully imported Town %s - %d",
                            town.getName(), town.getPopulation()));

                } else {
                    results.add("Invalid Towns");

                }

            } else {
                results.add("Invalid Towns");
            }
        }
        return String.join("\n", results);
    }
}
