import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P10IncreaseSalaries {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        String depEngineering = "Engineering";
        String depToolDesign = "Tool Design";
        String depMarketing = "Marketing";
        String depInformationServices = "Information Services";

        List<Integer> idList = entityManager.createQuery("SELECT e.id FROM Employee e WHERE" +
                        " e.department.name = :nameEngineering OR " +
                        " e.department.name = :nameToolDesign OR " +
                        " e.department.name = :nameMarketing OR " +
                        " e.department.name = :nameInformationServices", Integer.class)
                .setParameter("nameEngineering", depEngineering)
                .setParameter("nameToolDesign", depToolDesign)
                .setParameter("nameMarketing", depMarketing)
                .setParameter("nameInformationServices", depInformationServices)
                .getResultList();

        entityManager.createQuery("UPDATE Employee e SET e.salary = e.salary * 1.12 WHERE e.id IN (:empList)")
                .setParameter("empList", idList)
                .executeUpdate();

        entityManager.createQuery("SELECT e FROM Employee e WHERE" +
                        " e.department.name = :nameEngineering OR " +
                        " e.department.name = :nameToolDesign OR " +
                        " e.department.name = :nameMarketing OR " +
                        " e.department.name = :nameInformationServices", Employee.class)
                .setParameter("nameEngineering", depEngineering)
                .setParameter("nameToolDesign", depToolDesign)
                .setParameter("nameMarketing", depMarketing)
                .setParameter("nameInformationServices", depInformationServices)
                .getResultStream()
                .forEach(e -> {

                    System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary());
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
