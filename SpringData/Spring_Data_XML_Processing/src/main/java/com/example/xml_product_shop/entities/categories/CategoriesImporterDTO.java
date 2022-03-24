package com.example.xml_product_shop.entities.categories;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
public class CategoriesImporterDTO {

    @XmlElement(name = "category")
    private List<CategoryImporterDTO> categories;

    public CategoriesImporterDTO() {
    }

    public List<CategoryImporterDTO> getCategories() {
        return categories;
    }
}


