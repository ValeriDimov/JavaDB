package softuni.exam.instagraphlite.models.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ImportPictureDTO {
    private String path;

    @Min(500)
    @Max(60000)
    private double size;

    public ImportPictureDTO() {
    }

    public String getPath() {
        return path;
    }

    public double getSize() {
        return size;
    }
}
