package be.groffier.models;

import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

public class Calendar {
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
}
