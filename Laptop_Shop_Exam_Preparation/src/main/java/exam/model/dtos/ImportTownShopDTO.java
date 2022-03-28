package exam.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTownShopDTO {

    @XmlElement
    private String name;

    public ImportTownShopDTO() {
    }

    public String getName() {
        return name;
    }
}
