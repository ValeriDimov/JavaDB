package _04_HospitalDatabase.entities;

import javax.persistence.*;

@Entity(name = "_04_medicaments")
public class Medicament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Diagnose diagnose;

    public Medicament() {}

    public Medicament(String name, Diagnose diagnose) {
        this.name = name;
        this.diagnose = diagnose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
