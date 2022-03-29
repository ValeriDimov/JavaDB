package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.ImportUserDTO;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;

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
public class UserServiceImpl implements UserService {
    private static final Path FILE_DIRECTORY_USERS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Instagraph_Lite_Exam_Preparation\\src\\main\\resources\\files\\users.json");

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    private ModelMapper mapper;
    private Validator validator;
    private Gson gson;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;

        this.mapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_USERS);
    }

    @Override
    public String importUsers() throws IOException {
        ImportUserDTO[] dtos = this.gson.fromJson(readFromFileContent(), ImportUserDTO[].class);

        List<String> results = new ArrayList<>();

        for (ImportUserDTO dto : dtos) {
            if (dto.getProfilePicture() != null && dto.getUsername() != null && dto.getPassword() != null) {
                Set<ConstraintViolation<ImportUserDTO>> validateErrors = this.validator.validate(dto);

                if (validateErrors.isEmpty()) {
                    Optional<Picture> optionalPicture = this.pictureRepository.findByPath(dto.getProfilePicture());

                    if (optionalPicture.isPresent()) {
                        User user = new User();
                        user.setUsername(dto.getUsername());
                        user.setPassword(dto.getPassword());
                        user.setProfilePicture(optionalPicture.get());

                        this.userRepository.save(user);

                        results.add(String.format("Successfully imported User: %s", user.getUsername()));

                    } else {
                        results.add("Invalid User");
                    }

                } else {
                    results.add("Invalid User");
                }

            } else {
                results.add("Invalid User");
            }

        }

        return String.join("\n", results);
    }

    @Override
    public String exportUsersWithTheirPosts() {
        List<User> users = this.userRepository.findAllCustomRequest();
        StringBuilder sb = new StringBuilder();

        for (User user : users) {
            sb.append(user.toString());

            Set<Post> posts = user.getPosts();

            for (Post post : posts) {
                sb.append(post.toString());
            }
        }

        return sb.toString();
    }
}
