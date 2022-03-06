package _03_UniversitySystem.entities;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "_03_students")
public class Student extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "average_grade")
    private double averageGrade;

    private int attendance;

    @ManyToMany
    @JoinTable(
            name = "_03_students_courses",
            joinColumns =
            @JoinColumn(name = "students_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "courses_id", referencedColumnName = "id")
    )
    private Set<Course> courses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String phoneNumber, double averageGrade, int attendance) {
        super(firstName, lastName, phoneNumber);

        this.averageGrade = averageGrade;
        this.attendance = attendance;

        this.courses = new HashSet<>();
    }

    public Student(String firstName, String lastName, String phoneNumber, double averageGrade, int attendance, Set<Course> courses) {
        super(firstName, lastName, phoneNumber);

        this.averageGrade = averageGrade;
        this.attendance = attendance;
        this.courses = courses;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public Set<Course> getCourses() {
        return Collections.unmodifiableSet(courses);
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
