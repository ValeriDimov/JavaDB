package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.ImportPlaneDTO;
import softuni.exam.models.dtos.ImportPlanesRootDTO;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;

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
import java.util.Set;

@Service
public class PlaneServiceImpl implements PlaneService {
    private static final Path FILE_DIRECTORY_PLANES = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Airline_Exam_Preparation\\src\\main\\resources\\files\\xml\\planes.xml");

    private final PlaneRepository planeRepository;

    private ModelMapper mapper;
    private Validator validator;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;

        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.mapper = new ModelMapper();
    }


    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_PLANES);
    }

    @Override
    public String importPlanes() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ImportPlanesRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ImportPlanesRootDTO planesRootDTO = (ImportPlanesRootDTO) unmarshaller
                .unmarshal(FILE_DIRECTORY_PLANES.toFile());

        List<ImportPlaneDTO> dtos = planesRootDTO.getPlanes();

        List<String> results = new ArrayList<>();

        for (ImportPlaneDTO dto : dtos) {
            Set<ConstraintViolation<ImportPlaneDTO>> validateErrors = this.validator.validate(dto);

            if (validateErrors.isEmpty()) {
                Plane plane = this.mapper.map(dto, Plane.class);

                this.planeRepository.save(plane);

                results.add(String.format("Successfully imported Plane %s", plane.getRegisterNumber()));

            } else {
                results.add("Invalid Plane");
            }
        }

        return null;
    }
}
