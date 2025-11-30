package be.groffier.models;

import java.io.Serializable;

public class Manager extends Person implements Serializable{

	private static final long serialVersionUID = 6213104686795809542L;
	private Category category;

	public Manager() {}
	public Manager(String name, String firstname, String tel, String password, Category category) {
		super(name, firstname, tel, password);
		setCategory(category);
	}
		
	public Category getCategory() { return category; }
	public void setCategory(Category value) { category = value; }
	public void publishCalendar() {}
	public void calculateRideBudget(Ride ride) {}
}