import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class P03ContainsEmployee {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        String[] inputName = scanner.nextLine().split("\\s+");

        entityManager.getTransaction().begin();

        Long singleResult = entityManager.createQuery("SELECT count(e) FROM Employee e" +
                        " WHERE e.firstName = :first_name" +
                        " AND e.lastName = :last_name", Long.class)
                .setParameter("first_name", inputName[0])
                .setParameter("last_name", inputName[1]).getSingleResult();

        if (singleResult > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }


        entityManager.getTransaction().commit();
    }
}
