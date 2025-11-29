package be.groffier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.groffier.database.DBConnection;
import be.groffier.models.Bike;
import be.groffier.models.Member;

public class BikeDAO {

	public boolean addBike(int memberId, String name, int length, double weight, String type) {
		String sql = "INSERT INTO bike (personID, bikeName, length, weight, type) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, memberId);
			stmt.setString(2, name);
			stmt.setInt(3, length);
			stmt.setDouble(4, weight);
			stmt.setString(5, type);
			
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
			
		} catch (SQLException e) {
            e.printStackTrace();
            return false;
		}
	}

	public List<Bike> getBikesByMember(int memberId) {
		List<Bike> bikes = new ArrayList<>();
		String sql = "SELECT ID, bikeName, length, weight, type FROM bike WHERE personID = ?";
		
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, memberId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				int bikeId = rs.getInt("ID");
				String bikeName = rs.getString("bikeName");
				int length = rs.getInt("length");
				double weight = rs.getDouble("weight");
				String type = rs.getString("type");
				
				Bike bike = new Bike(type, weight, length, null, null);
				bike.setId(bikeId);
				bike.setName(bikeName);
				
				bikes.add(bike);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bikes;
	}

	public boolean deleteBike(int bikeId) {
		String sql = "DELETE FROM bike WHERE ID = ?";
		
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, bikeId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}