package com.example.xml_product_shop.entities.products;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductImporterDTO {
    @XmlElement
    private String name;

    @XmlElement
    private BigDecimal price;

    public ProductImporterDTO() {
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
