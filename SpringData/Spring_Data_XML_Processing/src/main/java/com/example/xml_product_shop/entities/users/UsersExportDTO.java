package com.example.xml_product_shop.entities.users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UsersExportDTO implements Serializable {
    @XmlElement(name = "user")
    private List<UserExportDTO> users;

    public UsersExportDTO() {
    }

    public UsersExportDTO(List<UserExportDTO> users) {
        this.users = users;
    }

    public List<UserExportDTO> getUsers() {
        return users;
    }
}
