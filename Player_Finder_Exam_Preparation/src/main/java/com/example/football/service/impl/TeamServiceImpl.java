package com.example.football.service.impl;

import com.example.football.models.dto.ImportTeamDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {
    private static final Path FILE_DIRECTORY_TEAMS = Path.of("D:\\Documents_Valio\\JavaProjects\\" +
            "JavaDB\\Player_Finder_Exam_Preparation\\src\\main\\resources\\files\\json\\teams.json");

    private final TeamRepository teamRepository;
    private final TownRepository townRepository;

    private Gson gson;
    private Validator validator;
    private ModelMapper mapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;

        this.gson = new GsonBuilder().create();
        this.validator = new Validation().buildDefaultValidatorFactory().getValidator();
        this.mapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_TEAMS);
    }

    @Override
    public String importTeams() throws IOException {
        ImportTeamDTO[] importTeamsDTOS = gson.fromJson(readTeamsFileContent(), ImportTeamDTO[].class);

        List<String> results = new ArrayList<>();
        for (ImportTeamDTO teamDTO : importTeamsDTOS) {

            Set<ConstraintViolation<ImportTeamDTO>> validateErrors = this.validator.validate(teamDTO);
            if (validateErrors.isEmpty()) {
                String teamDTOName = teamDTO.getName();

                Optional<Team> optionalTeam = this.teamRepository.findByName(teamDTOName);

                if (optionalTeam.isEmpty()) {
                    Team mappedTeam = mapper.map(teamDTO, Team.class);
                    Optional<Town> optionalTown = this.townRepository.findByName(teamDTO.getTownName());
                    mappedTeam.setTown(optionalTown.get());
                    this.teamRepository.save(mappedTeam);

                    results.add(String.format("Successfully imported Team %s - %d",
                            teamDTO.getName(), teamDTO.getFanBase()));

                }else  {
                    results.add("Invalid Team");
                }

            } else {
                results.add("Invalid Team");
            }
        }

        return String.join("\n", results);
    }
}
