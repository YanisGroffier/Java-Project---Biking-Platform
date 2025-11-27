package be.groffier.models;

import java.util.*;

public class Ride {
	private static int ID = 0;
	private int num;
	private String startPlace;
	private Date startDate;
	private double fee;
	
	private List<Inscription> inscriptions;
	private List<Vehicle> vehicles;
	
	
	public Ride(String startPlace, Date startDate, double fee) {
		setNum(ID++);
		setStartPlace(startPlace);
		setStartDate(startDate);
		setFee(fee);
		
		setInscriptions(new ArrayList<Inscription>());
		setVehicles(new ArrayList<Vehicle>());
	}

	public int getNum() { return num; }
	public void setNum(int value) { num = value; }
	
	public String getStartPlace() { return startPlace; }
	public void setStartPlace (String value) {startPlace = value; }
	
	public Date getStartDate() { return startDate; }
	public void setStartDate (Date value) {startDate = value; }
	
	public double getFee() { return fee; }
	void setFee (double value) {fee = value; }
	
	public List<Inscription> getInscriptions() { return inscriptions; }
	public void setInscriptions (List<Inscription> value) {inscriptions = value; }
	
	public List<Vehicle> getVehicles() { return vehicles; }
	public void setVehicles (List<Vehicle> value) {vehicles = value; }
	
	public boolean addInscription(Inscription i) {
		if (inscriptions.contains(i)) {return false;}
		else {inscriptions.add(i); return true;}
	}
	
	public boolean addVehicle(Vehicle v) {
		if (vehicles.contains(v)) {return false;}
		else {vehicles.add(v); return true;}
	}
	
	public int getTotalInscriptionNumber() {
		return inscriptions.size();
	}
	
	public int getAvailableSeatNumber() {return 0;} 	//TODO
	public int getTotalBikeSpotNumber() {return 0;}		//TODO
	public int getAvailableBikeSpotNumber() {return 0;}	//TODO
	public int getNeededSeatNumber() {return 0;}		//TODO
	public int getNeededBikeSpotNumber() {return 0;}	//TODO
}
