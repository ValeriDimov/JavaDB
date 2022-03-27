package com.example.football.service.impl;

import com.example.football.models.dto.ImportTownDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

@Service
public class TownServiceImpl implements TownService {
    private final static Path FILE_DIRECTORY_TOWN = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB" +
            "\\Player_Finder_Exam_Preparation\\src\\main\\resources\\files\\json\\towns.json");

    private final TownRepository townRepository;

    private final Validator validator;
    private Gson gson;
    private ModelMapper mapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;

        this.gson = new GsonBuilder().create();
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_TOWN);
    }

    @Override
    public String importTowns() throws IOException {
        ImportTownDTO[] townsDTO = gson.fromJson(readTownsFileContent(), ImportTownDTO[].class);

        List<String> results = new ArrayList<>();

        for (ImportTownDTO townDTO : townsDTO) {
            Set<ConstraintViolation<ImportTownDTO>> validateErrors = this.validator.validate(townDTO);

            if (validateErrors.isEmpty()) {
                String townDTOName = townDTO.getName();
                Optional<Town> byName = this.townRepository.findByName(townDTOName);

                if (byName.isEmpty()) {
                    Town townMapped = mapper.map(townDTO, Town.class);
                    this.townRepository.save(townMapped);

                    results.add(String.format("Successfully imported Town %s %d",
                            townMapped.getName(), townMapped.getPopulation()));
                }

            } else {
                results.add("Invalid Town");
            }
        }

        return String.join("\n", results);
    }
}
