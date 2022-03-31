package softuni.exam.models.dtos;

import javax.validation.constraints.Email;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTicketPassengerDTO {

    @XmlElement
    private String email;

    public ImportTicketPassengerDTO() {
    }

    public String getEmail() {
        return email;
    }
}
