package com.example.xml_product_shop.entities.users;

import com.example.xml_product_shop.entities.products.ProductsListDTO;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UserExportDTO implements Serializable {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    private List<ProductsListDTO> productsSold;

    public UserExportDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<ProductsListDTO> getProductsSold() {
        return productsSold;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProductsSold(List<ProductsListDTO> productsSold) {
        this.productsSold = productsSold;
    }
}
