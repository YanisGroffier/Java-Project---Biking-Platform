package be.groffier.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import be.groffier.dao.CalendarDAO;
import be.groffier.dao.RideDAO;

public class Calendar implements Serializable{

	private static final long serialVersionUID = -4044558274512669568L;
	private static int ID = 0;
	private int id;
	private List<Ride> rides;
	
	public Calendar() {
		setId(ID++);
		setRides(new ArrayList<>());
	}
	
	public int getId() { return id; }
	public void setId(int value) { id = value; }

	public List<Ride> getRides() { return rides; }
	public void setRides(List<Ride> value) { rides = value; }
	
	public boolean addRide(Ride r) {
		if (rides.contains(r)) return false;
		else {
			rides.add(r); return true;
		}
	}
	
	public void loadUpcomingRides() {
		RideDAO rideDAO = new RideDAO();
		List<Ride> upcomingRides = rideDAO.getUpcomingRides();
		
		this.rides.clear();
		for (Ride ride : upcomingRides) {
			this.addRide(ride);
		}
	}
	
	public void loadRidesByCalendarId(int calendarId) {
		RideDAO rideDAO = new RideDAO();
		List<Ride> calendarRides = rideDAO.getRidesByCalendarId(calendarId);
		
		this.rides.clear();
		for (Ride ride : calendarRides) {
			this.addRide(ride);
		}
	}
	
	public boolean saveToDatabase() {
		CalendarDAO calendarDAO = new CalendarDAO();
		return calendarDAO.createCalendar(this);
	}
	
	public static Calendar loadFromDatabase(int calendarId) {
		CalendarDAO calendarDAO = new CalendarDAO();
		return calendarDAO.getCalendarById(calendarId);
	}
	
	public static Calendar loadCurrentCalendar() {
		CalendarDAO calendarDAO = new CalendarDAO();
		return calendarDAO.getCalendar();
	}
}