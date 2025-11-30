package be.groffier.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import be.groffier.models.Person;
import be.groffier.models.Member;
import be.groffier.models.Manager;
import be.groffier.models.Treasurer;

public class MainFrame {
    
    private boolean isLoggedIn = false;
    private Person currentUser = null;
    private LoginFrame loginFrame;
    private JFrame currentDashboard;
    
    public MainFrame() {
        showLoginFrame();
    }
    
    private void showLoginFrame() {
        loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }
    
    private void showDashboard() {
        if (currentUser instanceof Treasurer) currentDashboard = new TreasurerDashboard(this, (Treasurer) currentUser);
        else if (currentUser instanceof Manager) currentDashboard = new ManagerDashboard(this, (Manager) currentUser);
        else if (currentUser instanceof Member) currentDashboard = new MemberDashboard(this, (Member) currentUser);
        
        else {
            System.err.println("Erreur à l'authentification du type d'utilisateur.");
            return;
        }
        currentDashboard.setVisible(true);
    }
    
    public void onLogin(Person person) {
        this.isLoggedIn = true;
        this.currentUser = person;
        showDashboard();
    }

    public void onLogout() {
        this.isLoggedIn = false;
        this.currentUser = null;
        showLoginFrame();
    }
    
    public Person getCurrentUser() {
        return currentUser;
    }
    
    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}