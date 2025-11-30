package be.groffier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.groffier.database.DBConnection;
import be.groffier.models.Person;
import be.groffier.models.Member;
import be.groffier.models.Manager;
import be.groffier.models.Treasurer;
import be.groffier.models.Category;
import be.groffier.models.CategoryEnum;

public class PersonDAO extends DAO<Person>{
	
	public PersonDAO() {}
   
	@Override
	public boolean create(Person obj) {
		return false;
	}

	@Override
	public boolean delete(Person obj) {
		return false;
	}

	@Override
	public boolean update(Person obj) {
		return false;
	}

	@Override
	public Person find(int id) {
		return null;
	}
	
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
                
                if (rs.getObject("treasurer_id") != null) {
                    Treasurer treasurer = new Treasurer(personName, firstname, tel, pwd);
                    treasurer.setId(personId);
                    System.out.println("Authentification réussie - Trésorier: " + name);
                    return treasurer;
                }
                
                if (rs.getObject("categoryID") != null) {
                    int categoryId = rs.getInt("categoryID");
                    Category category = getCategoryById(categoryId);
                    
                    Manager manager = new Manager(personName, firstname, tel, pwd, category);
                    System.out.println(manager.getCategory());
                    manager.setId(personId);
                    System.out.println("Authentification réussie - Manager: " + name);
                    return manager;
                }
                
                if (rs.getObject("balance") != null) {
                    double balance = rs.getDouble("balance");
                    
                    Member member = new Member(personName, firstname, tel, pwd, balance, null, null, null, null);
                    member.setId(personId);
                    System.out.println("Authentification réussie - Membre: " + name);
                    return member;
                }
                
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
    

    private Category getCategoryById(int categoryId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return null;
        
        String query = "SELECT * FROM Category WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String categoryName = rs.getString("CategoryName");
                
                // Convertir le String en CategoryEnum
                CategoryEnum categoryEnum = CategoryEnum.valueOf(categoryName);
                
                // Créer la Category avec le constructeur minimal
                Category category = new Category(id, categoryEnum);
                
                return category;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la Category!");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("CategoryName invalide en base de données!");
            e.printStackTrace();
        }
        
        return null;
    }    
    public Person getPersonByName(String name) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return null;
        }
        
        String query = "SELECT p.*, " +
                       "m.balance, m.lastPaymentDate" +
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
                Date lastPaymentDate = rs.getDate("lastPaymentDate");
                
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
                    Member member = new Member(personName, firstname, tel, pwd, balance, lastPaymentDate,
                                              null, null, null);
                    member.setId(personId);
                    return member;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration de l'utilisateur!");
            e.printStackTrace();
        }
        
        return null;
    }
    
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
            System.err.println("Erreur lors de la crÃ©ation de l'utilisateur!");
            e.printStackTrace();
            return false;
        }
    }
    
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
            System.err.println("Erreur lors de la mise Ã  jour de l'utilisateur!");
            e.printStackTrace();
            return false;
        }
    }
    
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
            System.err.println("Erreur lors de la mise Ã  jour de la balance du membre #" + memberId);
            e.printStackTrace();
            return false;
        }
    }
    
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
            System.err.println("Erreur lors de la rÃ©cupÃ©ration de la balance du membre #" + memberId);
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    public List<Member> getAllMembersWithHistory() {
        List<Member> members = new java.util.ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return members;
        }
        
        String query = "SELECT p.id, p.name, p.firstname, p.phone, p.password, m.balance, m.lastPaymentDate " +
                       "FROM Person p " +
                       "INNER JOIN Member m ON p.id = m.id " +
                       "ORDER BY p.id";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String firstname = rs.getString("firstname");
                String phone = rs.getString("phone");
                String password = rs.getString("password");
                double balance = rs.getDouble("balance");
                Date lastPaymentDate = rs.getDate("lastPaymentDate");
                
                Member member = new Member(name, firstname, phone, password, balance, lastPaymentDate,
                                          null, null, null);
                member.setId(id);
                members.add(member);
            }
            
            System.out.println("Récupération de " + members.size() + " membres");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des membres!");
            e.printStackTrace();
        }
        
        return members;
    }
    
    public boolean payFee(int memberId, double feeAmount) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return false;
        }

        String checkQuery = "SELECT balance FROM Member WHERE id = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, memberId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");

                if (currentBalance < feeAmount) {
                    System.err.println("Solde insuffisant pour le membre #" + memberId +". Solde: " + currentBalance + ", Requis: " + feeAmount);
                    return false;
                }
            } else {
                System.err.println("Membre #" + memberId + " introuvable");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du solde du membre #" + memberId);
            e.printStackTrace();
            return false;
        }

        String updateQuery = "UPDATE Member SET balance = balance - ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, feeAmount);
            pstmt.setInt(2, memberId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Paiement de cotisation effectué pour le membre #" + memberId + ": " + feeAmount + "€");
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du paiement de la cotisation du membre #" + memberId);
            e.printStackTrace();
            return false;
        }

        String updateDateQuery = "UPDATE Member SET LastPaymentDate = Date() WHERE id = ?";

        try (PreparedStatement dateStmt = conn.prepareStatement(updateDateQuery)) {
            dateStmt.setInt(1, memberId);

            int rowsAffected = dateStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Date de paiement mise à jour pour le membre #" + memberId);
                return true;
            }

            return false;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la date de paiement du membre #" + memberId);
            e.printStackTrace();
            return false;
        }
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
    
    public int getNumberOfCategories(int memberId) {
    	return getMemberCategories(memberId).size();
    }
    
    public double getFeeAmount(int memberId) {
    	int size = getNumberOfCategories(memberId);
	    return size > 1 ? 20 + (size - 1) * 5 : 20;
    }
}