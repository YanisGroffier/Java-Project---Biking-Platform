package be.groffier.uitest;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import be.groffier.models.Manager;

public class ManagerDashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private MainFrame mainFrame;
    private Manager manager;

    public ManagerDashboard(MainFrame mainFrame, Manager manager) {
        this.mainFrame = mainFrame;
        this.manager = manager;
        
        setTitle("Dashboard Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(255, 248, 240));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblRole = new JLabel("MANAGER");
        lblRole.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblRole.setForeground(new Color(255, 140, 0));
        lblRole.setBounds(30, 20, 150, 25);
        contentPane.add(lblRole);
        
        JLabel lblWelcome = new JLabel("Bienvenue, " + manager.getFirstname() + " " + manager.getName() + " !");
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblWelcome.setBounds(30, 50, 600, 30);
        contentPane.add(lblWelcome);
        
        String categoryInfo = (manager.getCategory() != null) ? 
            manager.getCategory().toString() : "Non définie";
        JLabel lblQuickCategory = new JLabel("Catégorie: " + categoryInfo);
        lblQuickCategory.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblQuickCategory.setForeground(new Color(255, 140, 0));
        lblQuickCategory.setBounds(30, 90, 400, 25);
        contentPane.add(lblQuickCategory);
        
        JButton btnViewInfo = new JButton("Voir mes informations");
        btnViewInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openUserInfo();
            }
        });
        btnViewInfo.setBounds(30, 130, 200, 35);
        contentPane.add(btnViewInfo);
        
        JLabel lblFeatures = new JLabel("Fonctionnalités de gestion:");
        lblFeatures.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblFeatures.setBounds(30, 190, 250, 25);
        contentPane.add(lblFeatures);
        
        JButton btnPublishCalendar = new JButton("Publier le calendrier");
        btnPublishCalendar.setBounds(50, 230, 180, 35);
        contentPane.add(btnPublishCalendar);
        
        JButton btnManageRides = new JButton("Gérer les sorties");
        btnManageRides.setBounds(250, 230, 180, 35);
        contentPane.add(btnManageRides);
        
        JButton btnCalculateBudget = new JButton("Calculer les budgets");
        btnCalculateBudget.setBounds(450, 230, 180, 35);
        contentPane.add(btnCalculateBudget);
        
        JButton btnViewMembers = new JButton("Voir les membres");
        btnViewMembers.setBounds(50, 280, 180, 35);
        contentPane.add(btnViewMembers);
        
        JButton btnStatistics = new JButton("Statistiques");
        btnStatistics.setBounds(250, 280, 180, 35);
        contentPane.add(btnStatistics);
        
        JButton btnLogout = new JButton("Se déconnecter");
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
        btnLogout.setBounds(280, 400, 140, 35);
        contentPane.add(btnLogout);
        
        setLocationRelativeTo(null);
    }
    
    private void openUserInfo() {
        UserInfoFrame userInfoFrame = new UserInfoFrame(manager);
        userInfoFrame.setVisible(true);
    }
    
    private void handleLogout() {
        mainFrame.onLogout();
        this.dispose();
    }
}