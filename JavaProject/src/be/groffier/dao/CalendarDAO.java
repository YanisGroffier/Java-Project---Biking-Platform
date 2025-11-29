package be.groffier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import be.groffier.database.DBConnection;
import be.groffier.models.Calendar;
import be.groffier.models.Ride;

public class CalendarDAO{
    
    public Calendar getCalendar() {
        Calendar calendar = new Calendar();
        RideDAO rideDAO = new RideDAO();
        
        List<Ride> rides = rideDAO.getUpcomingRides();
        
        for (Ride ride : rides) {
            calendar.addRide(ride);
        }
        
        return calendar;
    }
    
    public Calendar getCalendarById(int calendarId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return null;
        
        String query = "SELECT * FROM Calendar WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, calendarId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Calendar calendar = new Calendar();
                calendar.setId(rs.getInt("id"));
                
                RideDAO rideDAO = new RideDAO();
                List<Ride> rides = rideDAO.getRidesByCalendarId(calendarId);
                
                for (Ride ride : rides) {
                    calendar.addRide(ride);
                }
                
                return calendar;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du calendrier!");
            e.printStackTrace();
        }
        
        return null;
    }

    public boolean createCalendar(Calendar calendar) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;
        
        String query = "INSERT INTO Calendar DEFAULT VALUES";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du calendrier!");
            e.printStackTrace();
            return false;
        }
    }
}