package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportOfferDTO {

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement
    private ImportOfferAgentDTO agent;

    @XmlElement
    private ImportOfferApartmentDTO apartment;

    @XmlElement
    private String publishedOn;

    public ImportOfferDTO() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ImportOfferAgentDTO getAgent() {
        return agent;
    }

    public ImportOfferApartmentDTO getApartment() {
        return apartment;
    }

    public String getPublishedOn() {
        return publishedOn;
    }
}
