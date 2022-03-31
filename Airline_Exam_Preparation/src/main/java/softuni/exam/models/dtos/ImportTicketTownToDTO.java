package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTicketTownToDTO {

    @XmlElement
    private String name;

    public ImportTicketTownToDTO() {
    }

    public String getName() {
        return name;
    }
}
