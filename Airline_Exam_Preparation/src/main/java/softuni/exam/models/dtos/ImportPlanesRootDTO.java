package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlanesRootDTO {

    @XmlElement(name = "plane")
    private List<ImportPlaneDTO> planes;

    public ImportPlanesRootDTO() {
    }

    public List<ImportPlaneDTO> getPlanes() {
        return planes;
    }
}
