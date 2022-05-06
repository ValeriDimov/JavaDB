package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportApartmentsRootDTO;
import softuni.exam.models.dto.ImportOfferDTO;
import softuni.exam.models.dto.ImportOffersRootDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private static final Path FILE_DIRECTORY_OFFERS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Real_Estate_Agency\\src\\main\\resources\\files\\xml\\offers.xml");

    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    private ModelMapper mapper;
    private Validator validator;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository,
                            ApartmentRepository apartmentRepository) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

        this.mapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.class, LocalDate.class);
    }


    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_OFFERS);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ImportOffersRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportOffersRootDTO offersRootDTO  = (ImportOffersRootDTO) unmarshaller
                .unmarshal(FILE_DIRECTORY_OFFERS.toFile());

        List<ImportOfferDTO> dtos = offersRootDTO.getOffers();

        List<String> results = new ArrayList<>();

        for (ImportOfferDTO dto : dtos) {
            Set<ConstraintViolation<ImportOfferDTO>> validateErrors = this.validator.validate(dto);

            if (validateErrors.isEmpty()) {
                Optional<Agent> optionalAgent = this.agentRepository.findByFirstName(dto.getAgent().getName());

                if (optionalAgent.isPresent()) {
                    Optional<Apartment> apartment = this.apartmentRepository.findById(dto.getApartment().getId());

                    Offer offer = this.mapper.map(dto, Offer.class);

                    offer.setAgent(optionalAgent.get());
                    offer.setApartment(apartment.get());

                    this.offerRepository.save(offer);

                    results.add(String.format("Successfully imported offer %.2f", offer.getPrice()));

                } else {
                    results.add("Invalid offer");

                }
            } else {
                results.add("Invalid offer");

            }
        }
        return String.join("\n", results);

    }

    @Override
    public String exportOffers() {
        List<Offer> offers = this.offerRepository.findAllCustomQuery(ApartmentType.three_rooms);

        return offers
                .stream()
                .map(Offer::toString)
                .collect(Collectors.joining("\n"));
    }
}
