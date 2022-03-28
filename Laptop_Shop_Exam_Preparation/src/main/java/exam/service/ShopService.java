package exam.service;


import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface ShopService {

    boolean areImported();

    String readShopsFileContent() throws IOException, JAXBException;

    String importShops() throws JAXBException, FileNotFoundException;
}
