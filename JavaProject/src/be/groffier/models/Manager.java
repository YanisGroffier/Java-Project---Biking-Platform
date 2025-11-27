package be.groffier.models;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Person{
	private Category category;

	public Manager(String name, String firstname, String tel, String password, Category category) {
		super(name, firstname, tel, password);
		setCategory(category);
	}
		
	public Category getCategory() { return category; }
	public void setCategory(Category value) { category = value; }
	
	public void publishCalendar() {}
	public void calculateRideBudget(Ride ride) {
	    int totalPassengers = ride.getInscriptions().stream().filter(Inscription::getIsPassenger).toList().size();
	    int totalBikes = ride.getInscriptions().stream().filter(Inscription::getIsBike).toList().size();

	    // Ex : 0.35 €/km/passager + 0.10 €/km/vélo (hypothèse 100 km)
	    double forfait = (totalPassengers * 35.0) + (totalBikes * 10.0);
	    ride.setFee(forfait);
	}
}
