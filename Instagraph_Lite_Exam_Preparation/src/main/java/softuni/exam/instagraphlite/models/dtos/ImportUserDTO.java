package softuni.exam.instagraphlite.models.dtos;

import javax.validation.constraints.Size;

public class ImportUserDTO {
    @Size(min = 2, max = 18)
    private String username;

    @Size(min = 4)
    private String password;

    private String profilePicture;

    public ImportUserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
