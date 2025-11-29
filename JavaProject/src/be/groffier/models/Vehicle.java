package be.groffier.models;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
	private static int ID = 0;
	private int id;
	private int seatNumber;
	private int bikeSpotNumber;
	
	private Member driver;
	private List<Member> passengers;
	private List<Bike> bikes;
	private List<Ride> rides;
	
	public Vehicle(int seatNumber, int bikeSpotNumber, 
				   Member driver, Ride ride) {
		setId(ID++);
		setSeatNumber(seatNumber);
		setBikeSpotNumber(bikeSpotNumber);
		
		setDriver(driver);
		setPassengers(new ArrayList<Member>());
		setBikes(new ArrayList<Bike>());
		setRides(new ArrayList<Ride>());
		addRide(ride);
		
	}
	
    public int getId() { return id; }
    public void setId(int value) { id = value; }
    
    public int getSeatNumber() { return seatNumber; }
    private void setSeatNumber(int value) { seatNumber = value; }
    
    public int getBikeSpotNumber() { return bikeSpotNumber; }
    private void setBikeSpotNumber(int value) { bikeSpotNumber = value; }
    
    public Member getDriver() { return driver; }
    private void setDriver(Member value) { driver = value; }
    
    public List<Member> getPassengers() {return passengers; }
    private void setPassengers(List<Member> value) {passengers = value;}
    
    public List<Bike> getBikes() { return bikes; }
    private void setBikes(List<Bike> value) { bikes = value; }

    public List<Ride> getRides() { return rides; }
    private void setRides(List<Ride> value) { rides = value; }
    
    
	public boolean addPassenger(Member p) {
		if (passengers.contains(p)) {return false;}
		else {passengers.add(p); return true;}
	}

	public boolean addBike(Bike b) {
		if (bikes.contains(b)) {return false;}
		else {bikes.add(b); return true;}
	}
	
	public boolean addRide(Ride r) {
		if (rides.contains(r)) {return false;}
		else {rides.add(r); return true;}
	}

}
