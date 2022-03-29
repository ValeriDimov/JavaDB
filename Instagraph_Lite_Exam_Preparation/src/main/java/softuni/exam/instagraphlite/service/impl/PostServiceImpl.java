package softuni.exam.instagraphlite.service.impl;

import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.ImportPostDTO;
import softuni.exam.instagraphlite.models.dtos.ImportPostsRootDTO;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;

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
public class PostServiceImpl implements PostService {
    private static final Path FILE_DIRECTORY_POSTS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Instagraph_Lite_Exam_Preparation\\src\\main\\resources\\files\\posts.xml");

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final PostRepository postRepository;

    private ModelMapper mapper;
    private Validator validator;

    public PostServiceImpl(UserRepository userRepository, PictureRepository pictureRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.postRepository = postRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_POSTS);
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ImportPostsRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ImportPostsRootDTO postsRootDTO = (ImportPostsRootDTO) unmarshaller.unmarshal(FILE_DIRECTORY_POSTS.toFile());

        List<ImportPostDTO> postDTOS = postsRootDTO.getPosts();

        List<String> results = new ArrayList<>();

        for (ImportPostDTO dto : postDTOS) {
            if (dto.getCaption() != null && dto.getPicture() != null && dto.getUser() != null) {
                Set<ConstraintViolation<ImportPostDTO>> validateErrors = this.validator.validate(dto);

                if (validateErrors.isEmpty()) {
                    Optional<User> optionalUser = this.userRepository.findByUsername(dto.getUser().getUsername());
                    Optional<Picture> optionalPicture = this.pictureRepository.findByPath(dto.getPicture().getPath());

                    if (optionalPicture.isPresent() && optionalUser.isPresent()) {
                        Post post = mapper.map(dto, Post.class);
                        post.setPicture(optionalPicture.get());
                        post.setUser(optionalUser.get());

                        this.postRepository.save(post);

                        results.add(String.format("Successfully imported Post, made by %s", post.getUser().getUsername()));

                    } else {
                        results.add("Invalid Post");
                    }

                } else {
                    results.add("Invalid Post");
                }

            } else {
                results.add("Invalid Post");
            }
        }

        return String.join("\n", results);
    }
}
