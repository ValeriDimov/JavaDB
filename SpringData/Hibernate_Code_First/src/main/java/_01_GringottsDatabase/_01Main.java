package _01_GringottsDatabase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _01Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Hibernate_Code_First");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        WizardDeposit wizardDeposit = new WizardDeposit("Johnson", 32);
        entityManager.persist(wizardDeposit);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
