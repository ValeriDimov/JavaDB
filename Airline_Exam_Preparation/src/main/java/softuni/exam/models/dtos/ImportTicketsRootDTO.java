package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTicketsRootDTO {

    @XmlElement(name = "ticket")
    private List<ImportTicketDTO> tickets;

    public ImportTicketsRootDTO() {
    }

    public List<ImportTicketDTO> getTickets() {
        return tickets;
    }
}
