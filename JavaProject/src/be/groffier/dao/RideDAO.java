package be.groffier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.groffier.database.DBConnection;
import be.groffier.models.Ride;

public class RideDAO extends DAO<Ride>{
    
    public RideDAO() {}
    
	@Override
	public boolean create(Ride obj) {
		return false;
	}

	@Override
	public boolean delete(Ride obj) {
		return false;
	}

	@Override
	public boolean update(Ride obj) {
		return false;
	}

	@Override
	public Ride find(int id) {
		return null;
	}

	public List<Ride> getAllRides() {
        List<Ride> rides = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) return rides;
        
        String query = "SELECT * FROM Ride ORDER BY categoryID";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ride ride = new Ride(
                	rs.getString("name"),
                    rs.getString("startPlace"),
                    rs.getDate("startDate"),
                    rs.getDouble("fee")
                );
                ride.setNum(rs.getInt("categoryID"));
                
                rides.add(ride);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des sorties!");
            e.printStackTrace();
        }
        
        return rides;
    }

    public List<Ride> getUpcomingRides() {
        List<Ride> rides = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) return rides;
        
        String query = "SELECT * FROM Ride WHERE startDate >= ? ORDER BY startDate";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ride ride = new Ride(
                    	rs.getString("name"),
                        rs.getString("startPlace"),
                        rs.getDate("startDate"),
                        rs.getDouble("fee")
                    );
                ride.setNum(rs.getInt("categoryID"));
                
                rides.add(ride);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des sorties à venir!");
            e.printStackTrace();
        }
        
        return rides;
    }
    
    public List<Ride> getRidesByCalendarId(int calendarId) {
        List<Ride> rides = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) return rides;
        
        String query = "SELECT * FROM Ride WHERE calendarId = ? ORDER BY startDate";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, calendarId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ride ride = new Ride(
                    	rs.getString("name"),
                        rs.getString("startPlace"),
                        rs.getDate("startDate"),
                        rs.getDouble("fee")
                    );
                ride.setNum(rs.getInt("categoryID"));
                
                rides.add(ride);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des sorties du calendrier!");
            e.printStackTrace();
        }
        
        return rides;
    }

    public Ride getRideByNum(int num) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return null;
        
        String query = "SELECT * FROM Ride WHERE num = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, num);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Ride ride = new Ride(
                    	rs.getString("name"),
                        rs.getString("startPlace"),
                        rs.getDate("startDate"),
                        rs.getDouble("fee")
                    );
                ride.setNum(rs.getInt("categoryID"));
                return ride;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la sortie!");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean createRide(Ride ride, int category) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;
        
        String query = "INSERT INTO Ride (name, categoryID, startPlace, startDate, fee) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        	pstmt.setString(1, ride.getName());
            pstmt.setInt(2, category);
            pstmt.setString(3, ride.getStartPlace());
            pstmt.setDate(4, new java.sql.Date(ride.getStartDate().getTime()));
            pstmt.setDouble(5, ride.getFee());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la sortie!");
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteRide(int num) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;
        
        String query = "DELETE FROM Ride WHERE categoryID = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, num);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la sortie!");
            e.printStackTrace();
            return false;
        }
    }
}