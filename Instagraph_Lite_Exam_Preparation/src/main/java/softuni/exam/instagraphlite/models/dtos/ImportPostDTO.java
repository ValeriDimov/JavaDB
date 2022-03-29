package softuni.exam.instagraphlite.models.dtos;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPostDTO {

    @XmlElement
    @Size(min = 21)
    private String caption;

    @XmlElement
    private ImportUserPostDTO user;

    @XmlElement
    private ImportPicturePostDTO picture;

    public ImportPostDTO() {
    }

    public String getCaption() {
        return caption;
    }

    public ImportUserPostDTO getUser() {
        return user;
    }

    public ImportPicturePostDTO getPicture() {
        return picture;
    }
}
