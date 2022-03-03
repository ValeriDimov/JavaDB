import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class P06AddingNewAddressAndUpdatingEmployee {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String newAddress = "Vitoshka 15";

        Address addressToAdd = new Address();
        addressToAdd.setText(newAddress);
        entityManager.persist(addressToAdd);

        String lastNameInput = scanner.nextLine();

        entityManager.createQuery("UPDATE Employee e" +
                                    " SET e.address = :address" +
                                    " WHERE e.lastName = :inputName")
                .setParameter("address", addressToAdd)
                .setParameter("inputName", lastNameInput)
                .executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
