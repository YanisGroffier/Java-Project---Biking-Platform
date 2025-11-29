package be.groffier.models;

import java.util.ArrayList;
import java.util.List;

public class Member extends Person{
	private double balance;
	
	private List<Category> categories;
	private List<Inscription> inscriptions;
	private List<Bike> bikes;
	private Vehicle ownedVehicle;
	private List<Vehicle> vehicles;

	public Member(String name, String firstname, String tel,String password, double balance, 
				  Vehicle ownedVehicle, Category category, Bike bike){
			super(name, firstname, tel, password);
			setBalance(balance);
			
			setCategories(new ArrayList<Category>());
			addCategory(category);
			setInscriptions(new ArrayList<Inscription>());
			setBikes(new ArrayList<Bike>());
			addBike(bike);
			setOwnedVehicle(ownedVehicle);
			setVehicles(new ArrayList<Vehicle>());
	}
	
	public double getBalance() {return balance; }
	public void setBalance(double value) {balance = value; }

	public List<Category> getCategories() {return categories; }
	public void setCategories(List<Category> value) {categories = value; }
	
	public List<Inscription> getInscriptions() {return inscriptions; }
	public void setInscriptions(List<Inscription> value) {inscriptions = value; }	
	
	public List<Bike> getBikes () {return bikes; }
	public void setBikes(List<Bike> value) {bikes = value; }
	
	public Vehicle getOwnedVehicle() {return ownedVehicle; }
	public void setOwnedVehicle(Vehicle value) {ownedVehicle = value; }
	
	public List<Vehicle> getVehicles() {return vehicles; }
	public void setVehicles(List<Vehicle> value) {vehicles = value; }
	
	public boolean addCategory(Category c) {
		if (categories.contains(c)) {return false;}
		else {categories.add(c); return true;}
	}
	
	public boolean addBike(Bike b) {
		if (bikes.contains(b)) {return false;}
		else {bikes.add(b); return true;}
	}
	
	public void calculateBalance(double amount) {
		setBalance (balance + amount);
	}

	public double checkBalance() {
		return getBalance();
	}
}
