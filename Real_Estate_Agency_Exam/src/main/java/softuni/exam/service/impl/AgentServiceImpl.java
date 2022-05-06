package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportAgentDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;

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
public class AgentServiceImpl implements AgentService {
    private static final Path FILE_DIREDTORY_AGENTS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Real_Estate_Agency\\src\\main\\resources\\files\\json\\agents.json");

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;

    private ModelMapper mapper;
    private Validator validator;
    private Gson gson;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().create();
    }


    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(FILE_DIREDTORY_AGENTS);
    }

    @Override
    public String importAgents() throws IOException {
        ImportAgentDTO[] dtos = this.gson.fromJson(readAgentsFromFile(), ImportAgentDTO[].class);

        List<String> results = new ArrayList<>();

        for (ImportAgentDTO dto : dtos) {
            Set<ConstraintViolation<ImportAgentDTO>> validateErrors = this.validator.validate(dto);

            if (validateErrors.isEmpty()) {
                Optional<Agent> optionalAgent = this.agentRepository.findByFirstName(dto.getFirstName());

                if (optionalAgent.isEmpty()) {
                    Optional<Town> optionalTown = this.townRepository.findByTownName(dto.getTown());
                    Agent agent = this.mapper.map(dto, Agent.class);

                    agent.setTown(optionalTown.get());

                    this.agentRepository.save(agent);

                    results.add(String.format("Successfully imported agent - %s %s",
                            agent.getFirstName(), agent.getLastName()));

                } else {
                    results.add("Invalid agent");
                }

            } else {
                results.add("Invalid agent");
            }
        }

        return String.join("\n", results);
    }
}
