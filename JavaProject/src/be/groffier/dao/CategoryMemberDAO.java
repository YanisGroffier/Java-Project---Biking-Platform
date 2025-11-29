package be.groffier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.groffier.database.DBConnection;

public class CategoryMemberDAO {

    public boolean isMemberInCategory(int memberId, int categoryId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return false;
        }

        String query = "SELECT COUNT(*) as count FROM CategoryMember WHERE memberID = ? AND categoryID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, categoryId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count") > 0;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'appartenance à la catégorie!");
            e.printStackTrace();
        }

        return false;
    }

    public boolean addMemberToCategory(int memberId, int categoryId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return false;
        }

        if (isMemberInCategory(memberId, categoryId)) {
            System.err.println("Le membre #" + memberId + " est déjà inscrit dans la catégorie #" + categoryId);
            return false;
        }

        String query = "INSERT INTO CategoryMember (memberID, categoryID) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, categoryId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Membre #" + memberId + " inscrit dans la catégorie #" + categoryId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du membre à la catégorie!");
            e.printStackTrace();
        }

        return false;
    }

    public List<Integer> getMemberCategories(int memberId) {
        List<Integer> categories = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return categories;
        }

        String query = "SELECT categoryID FROM CategoryMember WHERE memberID = ? ORDER BY categoryID";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                categories.add(rs.getInt("categoryID"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des catégories du membre!");
            e.printStackTrace();
        }

        return categories;
    }

    public boolean removeMemberFromCategory(int memberId, int categoryId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return false;
        }

        String query = "DELETE FROM CategoryMember WHERE memberID = ? AND categoryID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, categoryId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Membre #" + memberId + " retiré de la catégorie #" + categoryId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du membre de la catégorie!");
            e.printStackTrace();
        }

        return false;
    }

    public List<Integer> getCategoryMembers(int categoryId) {
        List<Integer> members = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return members;
        }

        String query = "SELECT memberID FROM CategoryMember WHERE categoryID = ? ORDER BY memberID";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                members.add(rs.getInt("memberID"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des membres de la catégorie!");
            e.printStackTrace();
        }

        return members;
    }

    public int countMembersInCategory(int categoryId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return 0;
        }

        String query = "SELECT COUNT(*) as count FROM CategoryMember WHERE categoryID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des membres de la catégorie!");
            e.printStackTrace();
        }

        return 0;
    }
}