package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.ImportTicketDTO;
import softuni.exam.models.dtos.ImportTicketsRootDTO;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Path FILE_DIRECTORY_PLANES = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Airline_Exam_Preparation\\src\\main\\resources\\files\\xml\\tickets.xml");

    private final TicketRepository ticketRepository;
    private final PlaneRepository planeRepository;
    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;

    private ModelMapper mapper;
    private Validator validator;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, PlaneRepository planeRepository, PassengerRepository passengerRepository, TownRepository townRepository) {
        this.ticketRepository = ticketRepository;
        this.planeRepository = planeRepository;
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;

        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.mapper = new ModelMapper();

        this.mapper.addConverter(ctx -> LocalDateTime.parse(ctx.getSource(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), String.class, LocalDateTime.class);
    }


    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_PLANES);
    }

    @Override
    public String importTickets() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ImportTicketsRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ImportTicketsRootDTO ticketsRootDTO = (ImportTicketsRootDTO) unmarshaller
                .unmarshal(FILE_DIRECTORY_PLANES.toFile());

        List<ImportTicketDTO> dtos = ticketsRootDTO.getTickets();

        List<String> results = new ArrayList<>();

        for (ImportTicketDTO dto : dtos) {
            Set<ConstraintViolation<ImportTicketDTO>> validateErrors = this.validator.validate(dto);

            if (validateErrors.isEmpty()) {
                Optional<Town> optionalTownFrom = this.townRepository.findByName(dto.getFromTown().getName());
                Optional<Town> optionalTownTo = this.townRepository.findByName(dto.getToTown().getName());
                Optional<Plane> optionalPlane = this.planeRepository.findByRegisterNumber(dto.getPlane()
                        .getRegisterNumber());
                Optional<Passenger> optionalPassenger = this.passengerRepository.findByEmail(dto.getPassenger()
                        .getEmail());

                if (optionalPassenger.isPresent() && optionalPlane.isPresent() &&
                        optionalTownTo.isPresent() && optionalTownFrom.isPresent()) {

                    Ticket ticket = this.mapper.map(dto, Ticket.class);

                    ticket.setFromTown(optionalTownFrom.get());
                    ticket.setToTown(optionalTownTo.get());
                    ticket.setPlane(optionalPlane.get());
                    ticket.setPassenger(optionalPassenger.get());

                    this.ticketRepository.save(ticket);

                    results.add(String.format("Successfully imported Ticket %s - %s",
                            ticket.getFromTown().getName(), ticket.getToTown().getName()));

                } else {
                    results.add("Invalid Ticket");
                }

            } else {
                results.add("Invalid Ticket");
            }

        }
        return String.join("\n", results);
    }
}
