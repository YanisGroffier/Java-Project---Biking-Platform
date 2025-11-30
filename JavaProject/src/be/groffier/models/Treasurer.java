package be.groffier.models;

import java.io.Serializable;

public class Treasurer extends Person implements Serializable{

	private static final long serialVersionUID = 2652803221877918003L;
	
	public Treasurer() {}
	public Treasurer(String name, String firstname, String tel, String password) {
        super(name, firstname, tel, password);
    }

    public void sendReminderLetter() {}
    public void payDriver(Member m, double amount) {
        if (m != null && amount > 0) m.calculateBalance(amount);
    }
    public void claimFee() {}
}