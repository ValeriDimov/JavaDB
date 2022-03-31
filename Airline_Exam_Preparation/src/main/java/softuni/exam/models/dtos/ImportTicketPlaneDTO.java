package softuni.exam.models.dtos;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTicketPlaneDTO {

    @XmlElement(name = "register-number")
    private String registerNumber;

    public ImportTicketPlaneDTO() {
    }

    public String getRegisterNumber() {
        return registerNumber;
    }
}
