import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class P13RemoveTowns {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String townName = scanner.nextLine();

        Integer cityID = entityManager.createQuery("SELECT t.id FROM Town t WHERE t.name = :cityName"
                        , Integer.class)
                .setParameter("cityName", townName)
                .getSingleResult();

        Optional<Town> optional = entityManager.createQuery("SELECT t FROM Town t WHERE t.name = :town_param", Town.class)
                .setParameter("town_param", townName).getResultStream().findFirst();

        if (optional.isPresent()) {
            Stream<Integer> idsStream = entityManager.createQuery(" SELECT e.id FROM Employee AS e WHERE e.address.town.id = :town_id", Integer.class)
                    .setParameter("town_id", cityID)
                    .getResultStream();

            String employeeIds = idsStream.map(String::valueOf).collect(Collectors.joining(", "));

            String sql = String.format("Update Employee AS e SET e.address.id = null WHERE e.id IN (%s)", employeeIds);

            entityManager.createQuery(sql)
                    .executeUpdate();

            int affectedAddresses = entityManager.createQuery("DELETE FROM Address AS a WHERE a.town.id = :town_id")
                    .setParameter("town_id", cityID)
                    .executeUpdate();

            entityManager.createQuery("DELETE FROM Town AS t WHERE t.name = :town_name")
                    .setParameter("town_name", townName)
                    .executeUpdate();

            if (affectedAddresses == 1) {
                System.out.printf("%d address in %s deleted", affectedAddresses, townName);
            } else {
                System.out.printf("%d addresses in %s deleted", affectedAddresses, townName);
            }

        } else {
            System.out.printf("Town %s doesn't exist in the database", townName);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
