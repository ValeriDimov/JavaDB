package _05_BillsPaymentSystem.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BillingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int number;

    protected BillingDetails() {
    }

    public BillingDetails(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
