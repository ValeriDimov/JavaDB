import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class P12EmployeesMaximumSalaries {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        List<BigDecimal> resultList = entityManager.createQuery("SELECT max(e.salary) AS maxSalary FROM Employee e " +
                        "GROUP BY e.department.id", BigDecimal.class)
                .getResultList();

        List<Double> resultListInDouble = new ArrayList<>();

        for (BigDecimal bigDecimal : resultList) {
            double valueToConvert = bigDecimal.doubleValue();
            resultListInDouble.add(valueToConvert);
        }

        List<String> depNamesResults = entityManager.createQuery("SELECT d.name FROM Department d", String.class)
                .getResultList();

        for (int i = 0; i < resultListInDouble.size(); i++) {
            if (resultListInDouble.get(i) < 30000 || resultListInDouble.get(i) > 70000) {
                System.out.printf("%s %.2f%n", depNamesResults.get(i), resultListInDouble.get(i));
            }
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
