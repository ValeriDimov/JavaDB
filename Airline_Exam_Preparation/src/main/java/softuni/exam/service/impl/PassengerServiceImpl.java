package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.ImportPassengerDTO;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;

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
public class PassengerServiceImpl implements PassengerService {

private final static Path FILE_DIRECTORY_PASSENGERS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
        "Airline_Exam_Preparation\\src\\main\\resources\\files\\json\\passengers.json");

    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;

    private ModelMapper mapper;
    private Validator validator;
    private Gson gson;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, TownRepository townRepository) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().create();
    }


    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_PASSENGERS);
    }

    @Override
    public String importPassengers() throws IOException {
        ImportPassengerDTO[] dtos = this.gson.fromJson(readPassengersFileContent(), ImportPassengerDTO[].class);

        List<String> results = new ArrayList<>();

        for (ImportPassengerDTO dto : dtos) {
            Set<ConstraintViolation<ImportPassengerDTO>> validateErrors = this.validator.validate(dto);

            if (validateErrors.isEmpty()) {
                Optional<Town> optionalTown = this.townRepository.findByName(dto.getTown());

                if (optionalTown.isPresent()) {
                    Passenger passenger = this.mapper.map(dto, Passenger.class);
                    passenger.setTown(optionalTown.get());

                    this.passengerRepository.save(passenger);

                    results.add(String.format("Successfully imported Passenger %s - %s",
                            passenger.getLastName(), passenger.getEmail()));

                } else {
                    results.add("Invalid Passenger");
                }

            } else {
                results.add("Invalid Passenger");
            }

        }
        return String.join("\n", results);
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        List<Passenger> passengers = this.passengerRepository.findAllByTicketCount();

       return passengers
                .stream()
                .map(Passenger::toString)
                .collect(Collectors.joining("\n"));
    }
}
