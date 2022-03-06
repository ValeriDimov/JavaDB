package _04_HospitalDatabase;

import _04_HospitalDatabase.entities.Diagnose;
import _04_HospitalDatabase.entities.Medicament;
import _04_HospitalDatabase.entities.Patient;
import _04_HospitalDatabase.entities.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class _04Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Hibernate_Code_First");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter patient's first name");
        String patientFirstName = scanner.nextLine();

        System.out.println("Please enter patient's last name");
        String patientLastName = scanner.nextLine();

        System.out.println("Please enter patient's address");
        String patientAddress = scanner.nextLine();

        System.out.println("Please enter patient's email");
        String patientEmail = scanner.nextLine();

        System.out.println("Please enter 1 if the patient has insurance or 0 if not");
        int patientInsuranceIndicator = Integer.parseInt(scanner.nextLine());

        System.out.println("What is the reason for visitation");
        String visitationReason = scanner.nextLine();

        System.out.println("What is the diagnose");
        String visitationDiagnose = scanner.nextLine();

        System.out.println("What is the needed medicament");
        String patientMedicament = scanner.nextLine();

        boolean patientHasInsurance = false;
        if (patientInsuranceIndicator == 1) {
            patientHasInsurance = true;
        }

        Diagnose diagnose = new Diagnose(visitationDiagnose, visitationReason);
        Medicament medicament = new Medicament(patientMedicament, diagnose);
        diagnose.getMedicaments().add(medicament);
        Visitation visitation = new Visitation(diagnose);

        visitation.setDiagnose(diagnose);
        diagnose.setVisitation(visitation);

        entityManager.persist(diagnose);
        entityManager.persist(medicament);
        entityManager.persist(visitation);

        List<Patient> patients = entityManager.createQuery("select e from Patient e WHERE e.email = :emailParam", Patient.class)
                .setParameter("emailParam", patientEmail)
                .getResultList();

        if (patients.isEmpty()) {
            Patient newPatient = new Patient(patientFirstName, patientLastName,
                    patientAddress, patientEmail, patientHasInsurance);
            newPatient.getVisitations().add(visitation);

            visitation.setPatient(newPatient);

            entityManager.persist(newPatient);

        } else {
            Patient existingPatient = patients.get(0);
            visitation.setPatient(existingPatient);
            entityManager.persist(existingPatient);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
