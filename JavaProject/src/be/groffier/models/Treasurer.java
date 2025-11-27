package be.groffier.models;

public class Treasurer extends Person {
    public Treasurer(String name, String firstname, String tel, String password) {
        super(name, firstname, tel, password);
    }

    public void sendReminderLetter() {}
    public void payDriver(Member m, double amount) {
        if (m != null && amount > 0) m.calculateBalance(amount);
    }
    public void claimFee() {}
}