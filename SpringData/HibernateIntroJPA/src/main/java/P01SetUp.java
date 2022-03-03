import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class P01SetUp {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("PU_Name");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query townName = entityManager.createQuery("select t from Town t", Town.class);

        List<Town> resultList = townName.getResultList();
        for (Town town : resultList) {
            String name = town.getName();

            if (name.length() <= 5) {
                town.setName(name.toUpperCase());
            }
            entityManager.persist(town);
        }


        entityManager.getTransaction().commit();
    }
}
