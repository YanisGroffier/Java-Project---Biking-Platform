package be.groffier.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import be.groffier.dao.BikeDAO;

public class Bike implements Serializable{

	private static final long serialVersionUID = 781546991745771122L;
	private static int ID = 0;
	private int id;
	private String name;
	private String type;
	private double weight;
	private double length;
	
	private Member member;
	private List<Inscription> inscriptions;
	private Vehicle vehicle;
	
	public Bike() {}
	public Bike(String type, double weight, double length, 
				Member member, Vehicle vehicle) {
		setId(ID++);
		setType(type);
		setWeight(weight);
		setLength(length);
		
		setMember(member);
		setInscriptions(new ArrayList<Inscription>());
		setVehicle(vehicle);
	}	
	
    public int getId() { return id; }
    public void setId(int value) { id = value; }
    
    public String getName() { return name; }
    public void setName(String value) { name = value; }
    
    public String getType() { return type; }
    public void setType(String value) { type = value; }
    
    public double getWeight() { return weight; }
    public void setWeight(double value) { weight = value; }
    
    public double getLength() { return length; }
    public void setLength(double value) { length = value; }
    
    public Member getMember() { return member; }
    public void setMember(Member value) { member = value; }
    
    public List<Inscription> getInscriptions() { return inscriptions; }
    public void setInscriptions(List<Inscription> value) { inscriptions = value; }
    
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle value) { vehicle = value; }
    
    public boolean saveToDatabase(int memberId) {
        BikeDAO bikeDAO = new BikeDAO();
        return bikeDAO.addBike(memberId, this.name, (int)this.length, this.weight, this.type);
    }
    
    public boolean deleteFromDatabase() {
        BikeDAO bikeDAO = new BikeDAO();
        return bikeDAO.deleteBike(this.id);
    }
    
    public static List<Bike> loadByMemberId(int memberId) {
        BikeDAO bikeDAO = new BikeDAO();
        return bikeDAO.getBikesByMember(memberId);
    }
}