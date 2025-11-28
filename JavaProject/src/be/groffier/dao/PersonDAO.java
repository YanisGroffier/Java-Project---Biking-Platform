package be.groffier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.groffier.database.DBConnection;
import be.groffier.models.Person;
import be.groffier.models.Member;
import be.groffier.models.Manager;
import be.groffier.models.Treasurer;
import be.groffier.models.Category;

public class PersonDAO {
    
    /**
     * Vérifie les credentials et retourne le bon type de Person (Member, Manager ou Treasurer)
     * Utilise LEFT JOIN pour récupérer le rôle en une seule requête
     * @param name Nom d'utilisateur
     * @param password Mot de passe
     * @return Person object (Member/Manager/Treasurer) si authentification réussie, null sinon
     */
    public Person authenticate(String name, String password) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return null;
        }
        
        String query = "SELECT p.*, " +
                       "m.balance, " +
                       "mg.categoryID, " +
                       "t.id AS treasurer_id " +
                       "FROM Person p " +
                       "LEFT JOIN Member m ON p.id = m.id " +
                       "LEFT JOIN Manager mg ON p.id = mg.id " +
                       "LEFT JOIN Treasurer t ON p.id = t.id " +
                       "WHERE p.name = ? AND p.password = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int personId = rs.getInt("id");
                String personName = rs.getString("name");
                String firstname = rs.getString("firstname");
                String tel = rs.getString("phone");
                String pwd = rs.getString("password");
                
                // Vérifier quel rôle la personne a (priorité: Treasurer > Manager > Member)
                
                // Est-ce un Treasurer ?
                if (rs.getObject("treasurer_id") != null) {
                    Treasurer treasurer = new Treasurer(personName, firstname, tel, pwd);
                    treasurer.setId(personId);
                    System.out.println("Authentification réussie - Treasurer: " + name);
                    return treasurer;
                }
                
                // Est-ce un Manager ?
                if (rs.getObject("categoryID") != null) {
                    int categoryId = rs.getInt("categoryID");
                    Category category = getCategoryById(categoryId);
                    
                    Manager manager = new Manager(personName, firstname, tel, pwd, category);
                    manager.setId(personId);
                    System.out.println("Authentification réussie - Manager: " + name);
                    return manager;
                }
                
                // Est-ce un Member ?
                if (rs.getObject("balance") != null) {
                    double balance = rs.getDouble("balance");
                    
                    Member member = new Member(personName, firstname, tel, pwd, balance, 
                                              null, null, null); // ownedVehicle, category, bike à null temporairement
                    member.setId(personId);
                    System.out.println("Authentification réussie - Member: " + name);
                    return member;
                }
                
                // Si aucun rôle trouvé
                System.out.println("Personne trouvée mais aucun rôle assigné: " + name);
                return null;
                
            } else {
                System.out.println("Authentification échouée pour: " + name);
                return null;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification!");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Récupère une catégorie par son ID
     * @param categoryId L'ID de la catégorie
     * @return Category object ou null
     */
    private Category getCategoryById(int categoryId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return null;
        
        String query = "SELECT * FROM Category WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // TODO: Adapter selon les attributs réels de votre table Category
                // Exemple si Category a: id, name, description
                // Category category = new Category(rs.getString("name"), rs.getString("description"));
                // category.setId(rs.getInt("id"));
                // return category;
                
                // Pour l'instant, retourner null si vous n'avez pas encore la table Category
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la Category!");
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Récupère un utilisateur par son nom
     * @param name Nom de l'utilisateur
     * @return Person object ou null
     */
    public Person getPersonByName(String name) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return null;
        }
        
        String query = "SELECT p.*, " +
                       "m.balance, " +
                       "mg.categoryID, " +
                       "t.id AS treasurer_id " +
                       "FROM Person p " +
                       "LEFT JOIN Member m ON p.id = m.id " +
                       "LEFT JOIN Manager mg ON p.id = mg.id " +
                       "LEFT JOIN Treasurer t ON p.id = t.id " +
                       "WHERE p.name = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int personId = rs.getInt("id");
                String personName = rs.getString("name");
                String firstname = rs.getString("firstname");
                String tel = rs.getString("phone");
                String pwd = rs.getString("password");
                
                // Vérifier quel rôle
                if (rs.getObject("treasurer_id") != null) {
                    Treasurer treasurer = new Treasurer(personName, firstname, tel, pwd);
                    treasurer.setId(personId);
                    return treasurer;
                }
                
                if (rs.getObject("categoryID") != null) {
                    int categoryId = rs.getInt("categoryID");
                    Category category = getCategoryById(categoryId);
                    Manager manager = new Manager(personName, firstname, tel, pwd, category);
                    manager.setId(personId);
                    return manager;
                }
                
                if (rs.getObject("balance") != null) {
                    double balance = rs.getDouble("balance");
                    Member member = new Member(personName, firstname, tel, pwd, balance, 
                                              null, null, null);
                    member.setId(personId);
                    return member;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur!");
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Crée un nouvel utilisateur dans la base de données
     * @param person L'objet Person à insérer
     * @return true si l'insertion réussit, false sinon
     */
    public boolean createPerson(Person person) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return false;
        }
        
        String query = "INSERT INTO Person (name, firstname, phone, password) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, person.getName());
            pstmt.setString(2, person.getFirstname());
            pstmt.setString(3, person.getTel());
            pstmt.setString(4, person.getPassword());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'utilisateur!");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Met à jour un utilisateur existant
     * @param person L'objet Person avec les nouvelles données
     * @return true si la mise à jour réussit, false sinon
     */
    public boolean updatePerson(Person person) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return false;
        }
        
        String query = "UPDATE Person SET name = ?, firstname = ?, phone = ?, password = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, person.getName());
            pstmt.setString(2, person.getFirstname());
            pstmt.setString(3, person.getTel());
            pstmt.setString(4, person.getPassword());
            pstmt.setInt(5, person.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'utilisateur!");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Supprime un utilisateur de la base de données
     * @param id L'ID de l'utilisateur à supprimer
     * @return true si la suppression réussit, false sinon
     */
    public boolean deletePerson(int id) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return false;
        }
        
        String query = "DELETE FROM Person WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur!");
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateBalance(int memberId, double amount) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return false;
        }
        
        String query = "UPDATE Member SET balance = balance + ? WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, memberId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la balance du membre #" + memberId);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère la balance actuelle d'un membre depuis la DB
     * @param memberId L'ID du membre
     * @return La balance du membre, ou 0.0 en cas d'erreur
     */
    public double getMemberBalance(int memberId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return 0.0;
        }
        
        String query = "SELECT balance FROM Member WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("balance");
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la balance du membre #" + memberId);
            e.printStackTrace();
        }
        
        return 0.0;
    }
}