package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.ImportPictureDTO;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;

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
public class PictureServiceImpl implements PictureService {
    private static final Path FILE_DIRECTORY_PICTURES = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Instagraph_Lite_Exam_Preparation\\src\\main\\resources\\files\\pictures.json");

    private final PictureRepository pictureRepository;

    private ModelMapper mapper;
    private Validator validator;
    private Gson gson;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;

        this.mapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_PICTURES);
    }

    @Override
    public String importPictures() throws IOException {
        ImportPictureDTO[] dtos = this.gson.fromJson(readFromFileContent(), ImportPictureDTO[].class);

        List<String> results = new ArrayList<>();

        for (ImportPictureDTO dto : dtos) {
            Set<ConstraintViolation<ImportPictureDTO>> validateErrors = this.validator.validate(dto);


            if (validateErrors.isEmpty()) {
                Picture picture = this.mapper.map(dto, Picture.class);

                Optional<Picture> optionalPicture = this.pictureRepository.findByPath(dto.getPath());

                if (picture.getPath() != null) {

                    if (optionalPicture.isEmpty()) {
                        this.pictureRepository.save(picture);
                        results.add(String.format("Successfully imported Picture, with size %.2f", picture.getSize()));

                    } else {
                        results.add("Invalid Picture");
                    }

                } else {
                    results.add("Invalid Picture");
                }

            } else {
                results.add("Invalid Picture");
            }
        }

        return String.join("\n", results);
    }

    @Override
    public String exportPictures() {
        List<Picture> all = this.pictureRepository.findAllBySizeGreaterThanOrderBySizeAsc(30000);
       return all
                .stream()
                .map(Picture::toString)
                .collect(Collectors.joining("\n"));
    }
}
