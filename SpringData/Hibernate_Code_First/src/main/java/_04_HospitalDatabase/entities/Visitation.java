package _04_HospitalDatabase.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "_04_visitations")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate date;

    private String comments;

    @ManyToOne
    private Patient patient;

    @OneToOne(targetEntity = Diagnose.class, mappedBy = "visitation")
    private Diagnose diagnose;

    public Visitation() {}

    public Visitation(Diagnose diagnose) {
        this.diagnose = diagnose;
        this.date = LocalDate.now();
    }

    public Visitation(String comments, Diagnose diagnose) {
        this();
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }
}
