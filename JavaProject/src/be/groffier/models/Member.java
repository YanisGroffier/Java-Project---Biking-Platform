package be.groffier.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import be.groffier.dao.PersonDAO;
import be.groffier.dao.CategoryMemberDAO;

public class Member extends Person implements Serializable{

	private static final long serialVersionUID = -2017721049981578030L;
	private double balance;	
	private List<Category> categories;
	private List<Inscription> inscriptions;
	private List<Bike> bikes;
	private Vehicle ownedVehicle;
	private List<Vehicle> vehicles;
	private Date lastPaymentDate;

	public Member() {}
	public Member(String name, String firstname, String tel, String password, double balance, Date lastPaymentDate,
				  Vehicle ownedVehicle, Category category, Bike bike) {
		super(name, firstname, tel, password);
		setBalance(balance);
		
		setCategories(new ArrayList<Category>());
		if (category != null) addCategory(category);
		setInscriptions(new ArrayList<Inscription>());
		setBikes(new ArrayList<Bike>());
		if (bike != null) addBike(bike);
		setOwnedVehicle(ownedVehicle);
		setVehicles(new ArrayList<Vehicle>());
		setLastPaymentDate(lastPaymentDate);
	}
	
	public double getBalance() { return balance; }
	public void setBalance(double value) { balance = value; }

	public List<Category> getCategories() { return categories; }
	public void setCategories(List<Category> value) { categories = value; }
	
	public List<Inscription> getInscriptions() { return inscriptions; }
	public void setInscriptions(List<Inscription> value) { inscriptions = value; }	
	
	public List<Bike> getBikes() { return bikes; }
	public void setBikes(List<Bike> value) { bikes = value; }
	
	public Vehicle getOwnedVehicle() { return ownedVehicle; }
	public void setOwnedVehicle(Vehicle value) { ownedVehicle = value; }
	
	public List<Vehicle> getVehicles() { return vehicles; }
	public void setVehicles(List<Vehicle> value) { vehicles = value; }
	
	public Date getLastPaymentDate() { return lastPaymentDate; }
	public void setLastPaymentDate(Date value) { lastPaymentDate = value; }
	
	public boolean addCategory(Category c) {
		if (categories.contains(c)) {
			return false;
		} else {
			categories.add(c);
			return true;
		}
	}
	
	public boolean addBike(Bike b) {
		if (bikes.contains(b)) {
			return false;
		} else {
			bikes.add(b);
			return true;
		}
	}
	
	public void calculateBalance(double amount) {
		setBalance(balance + amount);
	}
	
	public boolean addFunds(double amount) {
		if (amount <= 0) {
			return false;
		}
		
		PersonDAO personDAO = new PersonDAO();
		boolean success = personDAO.updateBalance(this.getId(), amount);
		
		if (success) {
			this.balance += amount;
		}
		
		return success;
	}
	
	public double calculateFeeAmount() {
		PersonDAO personDAO = new PersonDAO();
		return personDAO.getFeeAmount(this.getId());
	}
	
	public boolean paySubscriptionFee() {
		double feeAmount = calculateFeeAmount();
		
		if (this.balance < feeAmount) {
			return false;
		}
		
		PersonDAO personDAO = new PersonDAO();
		boolean success = personDAO.payFee(this.getId(), feeAmount);
		
		if (success) {
			this.balance -= feeAmount;
			this.lastPaymentDate = new Date();
		}
		
		return success;
	}
	
	public boolean hasSufficientBalance(double amount) {
		return this.balance >= amount;
	}
	
	public boolean isInCategory(int categoryId) {
		CategoryMemberDAO categoryMemberDAO = new CategoryMemberDAO();
		return categoryMemberDAO.isMemberInCategory(this.getId(), categoryId);
	}
	
	public boolean subscribeToCategory(int categoryId) {
		CategoryMemberDAO categoryMemberDAO = new CategoryMemberDAO();
		return categoryMemberDAO.addMemberToCategory(this.getId(), categoryId);
	}
	
	public static List<Member> loadAllWithHistory() {
		PersonDAO personDAO = new PersonDAO();
		return personDAO.getAllMembersWithHistory();
	}
}