package _03_UniversitySystem;

import _03_UniversitySystem.entities.Course;
import _03_UniversitySystem.entities.Student;
import _03_UniversitySystem.entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class _03Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Hibernate_Code_First");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Teacher teacher1 = new Teacher("Peter", "Petrov",
                "0876500289", "peter@abv.bg", BigDecimal.valueOf(12.5));
        Teacher teacher2 = new Teacher("Petra", "Petrova",
                "0876555289", "petra@abv.bg", BigDecimal.valueOf(20.5));
        Teacher teacher3 = new Teacher("Teria", "Terova",
                "0899555289", "teri@abv.bg", BigDecimal.valueOf(25.5));
        Course course1 = new Course("Math", "Math Advanced"
                ,LocalDate.now(), LocalDate.of(2022, 12, 1),25, teacher1);
        Course course2 = new Course("Math2", "Math Master"
                ,LocalDate.now(), LocalDate.of(2023, 11, 1),15, teacher2);
        Course course3 = new Course("Math3", "Math Super"
                ,LocalDate.now(), LocalDate.of(2024, 6, 1),10, teacher3);
        Course course4 = new Course("English", "English Advanced"
                ,LocalDate.now(), LocalDate.of(2022, 6, 1),20, teacher1);

        Set<Course> courses = new HashSet<>();
        Set<Course> courses2 = new HashSet<>();
        courses.add(course1);
        courses.add(course2);
        courses2.add(course3);
        courses2.add(course4);

        Student student1 = new Student("John", "Anderson",
                "0899713545", 5.45, 30, courses);
        Student student2 = new Student("Linda", "Anderson",
                "0899713001", 4.95, 24, courses2);

        Student student3 = new Student("Lin", "And",
                "089=======", 5.95, 4);

        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.persist(teacher1);
        entityManager.persist(teacher2);
        entityManager.persist(teacher3);
        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.persist(course3);
        entityManager.persist(course4);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
