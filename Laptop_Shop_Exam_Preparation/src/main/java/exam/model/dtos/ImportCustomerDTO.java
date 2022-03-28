package exam.model.dtos;

import exam.model.entities.Town;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ImportCustomerDTO {
    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    @Email
    private String email;

    private String registeredOn;

    private ImportTownCustomerDTO town;

    public ImportCustomerDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }


    public ImportTownCustomerDTO getTown() {
        return town;
    }
}
