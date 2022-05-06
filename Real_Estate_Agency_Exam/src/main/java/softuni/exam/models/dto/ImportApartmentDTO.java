package softuni.exam.models.dto;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentDTO {

    @XmlElement
    private String apartmentType;

    @XmlElement
    @Min(40)
    private double area;

    @XmlElement
    private String town;

    public ImportApartmentDTO() {
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public double getArea() {
        return area;
    }

    public String getTown() {
        return town;
    }
}
