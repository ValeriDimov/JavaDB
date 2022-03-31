package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTicketTownFromDTO {

    @XmlElement
    private String name;

    public ImportTicketTownFromDTO() {
    }

    public String getName() {
        return name;
    }
}
