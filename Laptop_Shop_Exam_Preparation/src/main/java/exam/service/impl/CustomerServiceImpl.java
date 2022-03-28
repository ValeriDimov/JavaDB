package exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.model.dtos.ImportCustomerDTO;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Path FILE_DIRECTORY_CUSTOMERS = Path.of("D:\\Documents_Valio\\JavaProjects\\JavaDB\\" +
            "Laptop_Shop_Exam_Preparation\\src\\main\\resources\\files\\json\\customers.json");

    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;

    private ModelMapper mapper;
    private Validator validator;
    private Gson gson;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this. gson = new GsonBuilder().create();

        this.mapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.class, LocalDate.class);
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(FILE_DIRECTORY_CUSTOMERS);
    }

    @Override
    public String importCustomers() throws IOException {
        ImportCustomerDTO[] dtos = gson.fromJson(readCustomersFileContent(), ImportCustomerDTO[].class);

        List<String> results = new ArrayList<>();

        for (ImportCustomerDTO customerDTO : dtos) {
            Set<ConstraintViolation<ImportCustomerDTO>> validateErrors = this.validator.validate(customerDTO);

            if (validateErrors.isEmpty()) {
                Optional<Customer> optionalCustomer = this.customerRepository
                        .findByEmail(customerDTO.getEmail());

                if (optionalCustomer.isEmpty()) {
                    Optional<Town> optionalTown = this.townRepository.findByName(customerDTO.getTown().getName());

                    Customer customer = this.mapper.map(customerDTO, Customer.class);
                    customer.setTown(optionalTown.get());

                    this.customerRepository.save(customer);

                    results.add(String.format("Successfully imported Customer %s %s - %s",
                            customer.getFirstName(), customer.getLastName(), customer.getEmail()));

                } else {
                    results.add("Invalid Customer");
                }

            } else {
                results.add("Invalid Customer");
            }
        }

        return String.join("\n", results);
    }
}
