package be.groffier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.groffier.database.DBConnection;
import be.groffier.models.Member;
import be.groffier.models.Ride;
import be.groffier.models.Bike;
import be.groffier.models.Inscription;

public class InscriptionDAO {

    public boolean createInscription(Member member, Ride ride, boolean isPassenger, boolean hasBike) {
        String sql = "INSERT INTO inscription (personID, rideID, passenger, bike) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, member.getId());
            stmt.setInt(2, ride.getNum());
            stmt.setBoolean(3, isPassenger);
            stmt.setBoolean(4, hasBike);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'inscription du membre #" + member.getId() + " à la sortie #" + ride.getNum());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAlreadyRegistered(int memberId, int rideId) {
        String sql = "SELECT COUNT(*) FROM inscription WHERE personID = ? AND rideID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, rideId);

            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteInscription(int memberId, int rideId) {
        String sql = "DELETE FROM inscription WHERE personID = ? AND rideID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, rideId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Ride> getAllInscriptionsByMemberId(int memberId) {
        List<Ride> rides = new ArrayList<>();

        String sql = """
            SELECT DISTINCT 
                r.ID,
                r.name,
                r.startPlace,
                r.startDate,
                r.fee,
                r.categoryID
            FROM Ride r
            INNER JOIN Inscription i ON r.categoryID = i.rideID
            WHERE i.personID = ?
            ORDER BY r.startDate DESC
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ride ride = new Ride(
                    rs.getString("name"),
                    rs.getString("startPlace"),
                    rs.getTimestamp("startDate"),
                    rs.getDouble("fee")
                );
                ride.setNum(rs.getInt("categoryID"));

                rides.add(ride);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des inscriptions du membre " + memberId);
            e.printStackTrace();
        }

        return rides;
    }

    public List<Ride> getCyclistInscriptionsByMemberId(int memberId) {
        List<Ride> rides = new ArrayList<>();

        String sql = """
            SELECT DISTINCT 
                r.ID,
                r.name,
                r.startPlace,
                r.startDate,
                r.fee,
                r.categoryID
            FROM Ride r
            INNER JOIN Inscription i ON r.categoryID = i.rideID
            WHERE i.personID = ? AND i.passenger = true
            ORDER BY r.startDate DESC
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ride ride = new Ride(
                    rs.getString("name"),
                    rs.getString("startPlace"),
                    rs.getTimestamp("startDate"),
                    rs.getDouble("fee")
                );
                ride.setNum(rs.getInt("categoryID"));
                rides.add(ride);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des inscriptions cycliste du membre " + memberId);
            e.printStackTrace();
        }

        return rides;
    }

    public List<Ride> getDriverInscriptionsByMemberId(int memberId) {
        List<Ride> rides = new ArrayList<>();

        String sql = """
            SELECT DISTINCT 
                r.ID,
                r.name,
                r.startPlace,
                r.startDate,
                r.fee,
                r.categoryID
            FROM Ride r
            INNER JOIN Inscription i ON r.categoryID = i.rideID
            WHERE i.personID = ? AND i.passenger = false
            ORDER BY r.startDate DESC
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ride ride = new Ride(
                    rs.getString("name"),
                    rs.getString("startPlace"),
                    rs.getTimestamp("startDate"),
                    rs.getDouble("fee")
                );
                ride.setNum(rs.getInt("categoryID"));
                rides.add(ride);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des inscriptions conducteur du membre " + memberId);
            e.printStackTrace();
        }

        return rides;
    }
}