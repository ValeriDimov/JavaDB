package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportApartmentDTO;
import softuni.exam.models.dto.ImportApartmentsRootDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;

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
public class ApartmentServiceImpl implements ApartmentService {
    private static final Path FILE_DIRECTORY_APARTMENTS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Real_Estate_Agency\\src\\main\\resources\\files\\xml\\apartments.xml");

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;

    private ModelMapper mapper;
    private Validator validator;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(FILE_DIRECTORY_APARTMENTS);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ImportApartmentsRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportApartmentsRootDTO apartmentsRootDTO = (ImportApartmentsRootDTO) unmarshaller
                .unmarshal(FILE_DIRECTORY_APARTMENTS.toFile());

        List<ImportApartmentDTO> dtos = apartmentsRootDTO.getApartments();

        List<String> results = new ArrayList<>();

        for (ImportApartmentDTO dto : dtos) {
            Set<ConstraintViolation<ImportApartmentDTO>> validateErrors = this.validator.validate(dto);

            if (validateErrors.isEmpty()) {
                Optional<Town> town = this.townRepository.findByTownName(dto.getTown());

                Optional<Apartment> optionalApartment = this.apartmentRepository
                        .findByAreaAndByTownName(dto.getArea(), dto.getTown());

                if (optionalApartment.isEmpty()) {
                    Apartment apartment = this.mapper.map(dto, Apartment.class);
                    apartment.setTown(town.get());

                    this.apartmentRepository.save(apartment);

                    results.add(String.format("Successfully imported apartment %s - %.2f",
                            apartment.getApartmentType(), apartment.getArea()));

                } else {
                    results.add("Invalid apartment");

                }
            } else {
                results.add("Invalid apartment");

            }
        }
        return String.join("\n", results);

    }
}
