package be.groffier.models;

public class Inscription {
	private static int ID = 0;
	private int id;
	private boolean isPassenger;
	private boolean isBike;
	
	private Member member;
	private Bike bike;
	private Ride ride;
	
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

}
