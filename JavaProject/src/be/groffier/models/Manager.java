package be.groffier.models;

public class Manager extends Person{
	private Category category;

	public Manager(String name, String firstname, String tel, String password, Category category) {
		super(name, firstname, tel, password);
		setCategory(category);
	}
		
	public Category getCategory() { return category; }
	public void setCategory(Category value) { category = value; }
	public void publishCalendar() {}
	public void calculateRideBudget(Ride ride) {}
}