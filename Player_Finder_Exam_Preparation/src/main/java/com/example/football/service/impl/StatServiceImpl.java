package com.example.football.service.impl;

import com.example.football.models.dto.ImportStatDTO;
import com.example.football.models.dto.ImportStatsRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StatServiceImpl implements StatService {
    private static final Path FILE_DIRECTORY_STAT = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB" +
            "\\Player_Finder_Exam_Preparation\\src\\main\\resources\\files\\xml\\stats.xml");

    private final StatRepository statRepository;

    private ModelMapper mapper;
    private Validator validator;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;

        mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_STAT);
    }

    @Override
    public String importStats() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(ImportStatsRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportStatsRootDTO statsRootDTO = (ImportStatsRootDTO) unmarshaller
                .unmarshal(FILE_DIRECTORY_STAT.toFile());

        List<ImportStatDTO> dtoStats = statsRootDTO.getStats();

        List<String> results = new ArrayList<>();

        for (ImportStatDTO dtoStat : dtoStats) {
            Set<ConstraintViolation<ImportStatDTO>> validateErrors = this.validator.validate(dtoStat);

            if (validateErrors.isEmpty()) {
                Optional<Stat> optionalStat = this.statRepository.findByPassingByAndByShootingAndByEndurance(
                        dtoStat.getPassing(), dtoStat.getShooting(), dtoStat.getEndurance());

                if (optionalStat.isEmpty()) {
                    Stat mappedStat = mapper.map(dtoStat, Stat.class);
                    this.statRepository.save(mappedStat);

                    results.add(String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                            dtoStat.getPassing(), dtoStat.getShooting(), dtoStat.getEndurance()));

                } else {
                    results.add("Invalid Stat");
                }

            } else {
                results.add("Invalid Stat");
            }
        }

        return String.join("\n", results);
    }
}
