package com.example.football.service.impl;

import com.example.football.models.dto.ImportPlayerDTO;
import com.example.football.models.dto.ImportPlayersRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final Path FILE_DIRECTORY_PLAYERS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Player_Finder_Exam_Preparation\\src\\main\\resources\\files\\xml\\players.xml");
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;

    private ModelMapper mapper;
    private Validator validator;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,TownRepository townRepository,
                             TeamRepository teamRepository, StatRepository statRepository) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

        this.mapper.addConverter(ctx -> LocalDate.parse(ctx.getSource()
                , DateTimeFormatter.ofPattern("dd/MM/yyyy")), String.class, LocalDate.class);

    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_PLAYERS);

    }

    @Override
    public String importPlayers() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ImportPlayersRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ImportPlayersRootDTO playersRootDTO = (ImportPlayersRootDTO) unmarshaller
                .unmarshal(FILE_DIRECTORY_PLAYERS.toFile());

        List<ImportPlayerDTO> playersDTO = playersRootDTO.getPlayers();

        List<String> results = new ArrayList<>();

        for (ImportPlayerDTO playerDTO : playersDTO) {
            String dtoEmail = playerDTO.getEmail();
            Optional<Player> playerByEmail = this.playerRepository.findByEmail(dtoEmail);

            Set<ConstraintViolation<ImportPlayerDTO>> validateErrors = this.validator.validate(playerDTO);
            if (validateErrors.isEmpty()) {
                if (playerByEmail.isEmpty()) {
                    Player player = mapper.map(playerDTO, Player.class);
                    player.setTown(this.townRepository.findByName(playerDTO.getTown().getName()).get());
                    player.setTeam(this.teamRepository.findByName(playerDTO.getTeam().getName()).get());
                    player.setStat(this.statRepository.findById(playerDTO.getStat().getId()).get());

                    this.playerRepository.save(player);

                    results.add(String.format("Successfully imported Player %s %s - %s",
                            player.getFirstName(), player.getLastName(), player.getPosition()));

                } else {
                    results.add("Invalid Player");
                }

            } else {
                results.add("Invalid Player");
            }
        }

        return String.join("\n", results);
    }

    @Override
    public String exportBestPlayers() {
        LocalDate dateBefore = LocalDate.of(2003, 1, 1);
        LocalDate dateAfter = LocalDate.of(1995, 1, 1);

        List<Player> bestPlayers = this.playerRepository.findAllByBirthDateBetween(dateAfter, dateBefore);

       return bestPlayers
                .stream()
                .map(Player::toString)
                .collect(Collectors.joining("\n"));


    }
}
