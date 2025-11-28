package be.groffier.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static final String DB_PATH = "Database.accdb";
    private static final String CONNECTION_STRING = "jdbc:ucanaccess://" + DB_PATH;
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                
                connection = DriverManager.getConnection(CONNECTION_STRING);
                System.out.println("Connexion à la base de données réussie.");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver UCanAccess non trouvé.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données.");
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion.");
            e.printStackTrace();
        }
    }
    
    public static boolean testConnection() {
        Connection conn = getConnection();
        return conn != null;
    }
}