import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class P05EmployeesFromDepartment {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        String currentDep = "Research and Development";

        entityManager.createQuery("SELECT e FROM Employee e" +
                " WHERE e.department.name = :department" +
                " ORDER BY e.salary ASC, e.id ASC", Employee.class)
                .setParameter("department", currentDep)
                        .getResultStream()
                                .forEach(System.out::println);

        entityManager.getTransaction().commit();
    }
}
