package com.example.xml_product_shop.entities.products;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
public class ProductsExportDTO {
    @XmlElement(name = "product")
    private List<ProductExportDTO> products;

    public ProductsExportDTO() {
    }

    public ProductsExportDTO(List<ProductExportDTO> products) {
        this.products = products;
    }

    public List<ProductExportDTO> getProducts() {
        return products;
    }

}
