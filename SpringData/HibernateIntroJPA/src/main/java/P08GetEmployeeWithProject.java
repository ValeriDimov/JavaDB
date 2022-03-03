import entities.Address;
import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class P08GetEmployeeWithProject {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        int idInput = Integer.parseInt(scanner.nextLine());

        entityManager.createQuery("SELECT e FROM Employee e WHERE e.id = :employee_id", Employee.class)
                .setParameter("employee_id", idInput)
                .getResultStream()
                .forEach(e -> {
                    List<String> prNames = e.getProjects()
                            .stream()
                            .map(Project::getName)
                            .collect(Collectors.toList());

                    String collect = prNames
                            .stream()
                            .sorted(String::compareTo)
                            .collect(Collectors.joining(System.lineSeparator()));

                    String empInfo = String.format("%s %s - %s%n%s",
                            e.getFirstName(),
                            e.getLastName(),
                            e.getJobTitle(),
                            collect);
                    System.out.println(empInfo);
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
