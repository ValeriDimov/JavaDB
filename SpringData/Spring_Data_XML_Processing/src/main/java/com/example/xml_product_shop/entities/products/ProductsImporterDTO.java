package com.example.xml_product_shop.entities.products;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductsImporterDTO {

    @XmlElement(name = "product")
    private List<ProductImporterDTO> products;

    public ProductsImporterDTO() {
    }

    public List<ProductImporterDTO> getProducts() {
        return products;
    }
}


