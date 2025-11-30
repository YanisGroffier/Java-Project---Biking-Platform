package be.groffier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.groffier.database.DBConnection;
import be.groffier.models.Vehicle;

public class VehicleDAO extends DAO<Vehicle>{

    public VehicleDAO() {}
    
	@Override
	public boolean create(Vehicle obj) {
		return false;
	}

	@Override
	public boolean delete(Vehicle obj) {
		return false;
	}

	@Override
	public boolean update(Vehicle obj) {
		return false;
	}

	@Override
	public Vehicle find(int id) {
		return null;
	}

	public boolean addVehicle(int memberId, int seatNumber, int bikeSpotNumber) {
        String sql = "INSERT INTO vehicle (personID, seatNumber, bikeSpotNumber) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, seatNumber);
            stmt.setInt(3, bikeSpotNumber);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du véhicule pour le membre #" + memberId);
            e.printStackTrace();
            return false;
        }
    }

    public List<Vehicle> getAllVehicles(int memberId) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT id, seatNumber, bikeSpotNumber FROM vehicle WHERE personID = ? ORDER BY seatNumber DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                    rs.getInt("seatNumber"),
                    rs.getInt("bikeSpotNumber"),
                    null,
                    null
                );
                vehicle.setId(rs.getInt("id"));
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des véhicules du membre #" + memberId);
            e.printStackTrace();
        }

        return vehicles;
    }

    public boolean deleteVehicle(int vehicleId) {
        String sql = "DELETE FROM vehicle WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}