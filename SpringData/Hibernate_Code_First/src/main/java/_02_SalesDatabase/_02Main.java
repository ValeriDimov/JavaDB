package _02_SalesDatabase;

import _02_SalesDatabase.entities.Customer;
import _02_SalesDatabase.entities.Product;
import _02_SalesDatabase.entities.Sale;
import _02_SalesDatabase.entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class _02Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Hibernate_Code_First");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Product product1 = new Product("Paste", 25.00, BigDecimal.TEN);
        Product product2 = new Product("Meat", 5.00, BigDecimal.valueOf(12.49));
        Customer customer1 = new Customer("Peter", "p@abv.bg","4554-5550-8885-1234");
        Customer customer2 = new Customer("John", "john@abv.bg","4554-6688-0097-5894");
        StoreLocation storeSofia = new StoreLocation("Sofia");
        StoreLocation storePlovdiv = new StoreLocation("Plovdiv");

        Sale sale1 = new Sale(product1, customer1,storeSofia);
        Sale sale2 = new Sale(product2, customer2,storePlovdiv);

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.persist(storeSofia);
        entityManager.persist(storePlovdiv);
        entityManager.persist(sale1);
        entityManager.persist(sale2);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
