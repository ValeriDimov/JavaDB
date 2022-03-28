package exam.model.dtos;

import exam.model.entities.LaptopWarrantyType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ImportLaptopDTO {
    @Size(min = 8)
    private String macAddress;

    @Positive
    private double cpuSpeed;

    @Min(8)
    @Max(128)
    private int ram;

    @Min(128)
    @Max(1024)
    private int storage;

    @Size(min = 10)
    private String description;

    @Positive
    private BigDecimal price;

    private LaptopWarrantyType warrantyType;

    private ImportShopCustomerDTO shop;

    public ImportLaptopDTO() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public double getCpuSpeed() {
        return cpuSpeed;
    }

    public int getRam() {
        return ram;
    }

    public int getStorage() {
        return storage;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ImportShopCustomerDTO getShop() {
        return shop;
    }

    public LaptopWarrantyType getWarrantyType() {
        return warrantyType;
    }
}
