package _05_BillsPaymentSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "_05_bank_accounts")
public class BankAccount extends BillingDetails {
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "swift_code", nullable = false)
    private String swiftCode;

    @ManyToOne
    private User user;

    public BankAccount() {
        super();
    }

    public BankAccount(int number, String bankName, String swiftCode, User user) {
        super(number);
        this.bankName = bankName;
        this.swiftCode = swiftCode;
        this.user = user;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
