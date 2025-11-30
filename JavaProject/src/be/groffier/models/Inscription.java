package be.groffier.models;

import java.io.Serializable;
import java.util.List;
import be.groffier.dao.InscriptionDAO;

public class Inscription implements Serializable{

	private static final long serialVersionUID = -8086417882667331321L;
	private static int ID = 0;
	private int id;
	private boolean isPassenger;
	private boolean isBike;
	
	private Member member;
	private Bike bike;
	private Ride ride;
	
	public Inscription() {}
	public Inscription(boolean isPassenger, boolean isBike, 
					   Member member, Bike bike, Ride ride) {
		setId(ID++);
		setIsPassenger(isPassenger);
		setIsBike(isBike);
		
		setMember(member);
		setBike(bike);
		setRide(ride);
	}
	
    public int getId() { return id; }
    public void setId(int value) { id = value; }

    public boolean getIsPassenger() { return isPassenger; }
    public void setIsPassenger(boolean value) { isPassenger = value; }
    
    public boolean getIsBike() { return isBike; }
    public void setIsBike(boolean value) { isBike = value; }

    public Member getMember() { return member; }
    public void setMember(Member value) { member = value; }

    public Bike getBike() { return bike; }
    public void setBike(Bike value) { bike = value; }
    
    public Ride getRide() { return ride; }
    public void setRide(Ride value) { ride = value; }
    
    public boolean saveToDatabase() {
        InscriptionDAO inscriptionDAO = new InscriptionDAO();
        return inscriptionDAO.createInscription(this.member, this.ride, this.isPassenger, this.isBike);
    }
    
    public boolean deleteFromDatabase() {
        InscriptionDAO inscriptionDAO = new InscriptionDAO();
        return inscriptionDAO.deleteInscription(this.member.getId(), this.ride.getNum());
    }
    
    public static boolean isAlreadyRegistered(int memberId, int rideId) {
        InscriptionDAO inscriptionDAO = new InscriptionDAO();
        return inscriptionDAO.isAlreadyRegistered(memberId, rideId);
    }
    
    public static List<Ride> loadAllByMemberId(int memberId) {
        InscriptionDAO inscriptionDAO = new InscriptionDAO();
        return inscriptionDAO.getAllInscriptionsByMemberId(memberId);
    }
    
    public static List<Ride> loadCyclistInscriptionsByMemberId(int memberId) {
        InscriptionDAO inscriptionDAO = new InscriptionDAO();
        return inscriptionDAO.getCyclistInscriptionsByMemberId(memberId);
    }
    
    public static List<Ride> loadDriverInscriptionsByMemberId(int memberId) {
        InscriptionDAO inscriptionDAO = new InscriptionDAO();
        return inscriptionDAO.getDriverInscriptionsByMemberId(memberId);
    }
}