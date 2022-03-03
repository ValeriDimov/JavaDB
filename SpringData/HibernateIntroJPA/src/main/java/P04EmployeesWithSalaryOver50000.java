import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class P04EmployeesWithSalaryOver50000 {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        BigDecimal salaryLimit = new BigDecimal(50000);

        entityManager.createQuery("SELECT e.firstName FROM Employee e where e.salary > :salary", String.class)
                .setParameter("salary", salaryLimit)
                .getResultStream()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
    }
}
