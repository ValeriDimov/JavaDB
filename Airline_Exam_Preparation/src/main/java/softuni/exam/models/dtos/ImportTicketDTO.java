package softuni.exam.models.dtos;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTicketDTO {

    @XmlElement(name = "serial-number")
    @Size(min = 2)
    private String serialNumber;

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeOff;

    @XmlElement(name = "from-town")
    private ImportTicketTownFromDTO fromTown;

    @XmlElement(name = "to-town")
    private ImportTicketTownToDTO toTown;

    @XmlElement
    private ImportTicketPassengerDTO passenger;

    @XmlElement
    private ImportTicketPlaneDTO plane;

    public ImportTicketDTO() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTakeOff() {
        return takeOff;
    }

    public ImportTicketTownFromDTO getFromTown() {
        return fromTown;
    }

    public ImportTicketTownToDTO getToTown() {
        return toTown;
    }

    public ImportTicketPassengerDTO getPassenger() {
        return passenger;
    }

    public ImportTicketPlaneDTO getPlane() {
        return plane;
    }
}
