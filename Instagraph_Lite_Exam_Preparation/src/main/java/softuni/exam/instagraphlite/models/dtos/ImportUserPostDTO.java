package softuni.exam.instagraphlite.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportUserPostDTO {

    @XmlElement
    private String username;

    public ImportUserPostDTO() {
    }

    public String getUsername() {
        return username;
    }
}
