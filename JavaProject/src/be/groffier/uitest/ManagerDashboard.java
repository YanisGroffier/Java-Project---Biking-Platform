package be.groffier.uitest;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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
    	setResizable(false);
        this.mainFrame = mainFrame;
        this.manager = manager;
        
        setTitle("Dashboard Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 920, 626);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(new Color(255, 248, 240));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton btnViewInfo = new JButton(manager.getFirstname() + " " + manager.getName());
        btnViewInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnViewInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openUserInfo();
            }
        });
        btnViewInfo.setBounds(20, 20, 250, 35);
        contentPane.add(btnViewInfo);
        
        JLabel lblTitle = new JLabel("ClubVelo - Manager");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setBounds(300, 20, 320, 35);
        contentPane.add(lblTitle);
        
        JButton btnLogout = new JButton("Se déconnecter");
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnLogout.setForeground(new Color(200, 50, 50));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
        btnLogout.setBounds(720, 20, 160, 35);
        contentPane.add(btnLogout);
        
        JLabel lblWelcome = new JLabel("Bienvenue, " + manager.getFirstname() + " " + manager.getName() + " !");
        lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblWelcome.setBounds(20, 75, 430, 30);
        contentPane.add(lblWelcome);
        
        String categoryInfo = (manager.getCategory() != null) ? 
            manager.getCategory().toString() : "Non définie";
        JLabel lblQuickCategory = new JLabel("Catégorie: " + categoryInfo);
        lblQuickCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblQuickCategory.setBounds(20, 110, 400, 25);
        contentPane.add(lblQuickCategory);
        
        JPanel eventsPanel = new JPanel();
        eventsPanel.setBackground(new Color(240, 240, 240));
        eventsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        eventsPanel.setBounds(20, 160, 860, 200);
        eventsPanel.setLayout(null);
        contentPane.add(eventsPanel);
        
        JLabel lblEventsTitle = new JLabel("Événements");
        lblEventsTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEventsTitle.setBounds(10, 10, 200, 25);
        eventsPanel.add(lblEventsTitle);
        
        JButton btnConsultEvents = new JButton("Tous les événements");
        btnConsultEvents.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnConsultEvents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openManagerCalendar();
            }
        });
        btnConsultEvents.setBounds(170, 46, 250, 40);
        eventsPanel.add(btnConsultEvents);
        
        JButton btnConsultCategoryEvents = new JButton("Mes événements");
        btnConsultCategoryEvents.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnConsultCategoryEvents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMyCategoryEvents();
            }
        });
        btnConsultCategoryEvents.setBounds(440, 46, 250, 40);
        eventsPanel.add(btnConsultCategoryEvents);
        
        JButton btnCreateEvent = new JButton("Créer un événement");
        btnCreateEvent.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCreateEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCreateRide();
            }
        });
        btnCreateEvent.setBounds(170, 106, 520, 40);
        eventsPanel.add(btnCreateEvent);
        
        setLocationRelativeTo(null);
    }
    
    private void openUserInfo() {
        UserInfoFrame userInfoFrame = new UserInfoFrame(manager);
        userInfoFrame.setVisible(true);
    }
    
    private void openManagerCalendar() {
        ManagerCalendarFrame managerCalendarFrame = new ManagerCalendarFrame(manager);
        managerCalendarFrame.setVisible(true);
    }
    
    private void openMyCategoryEvents() {
        if (manager.getCategory() != null) {
            ManagerCalendarFrame managerCalendarFrame = new ManagerCalendarFrame(manager, true, manager.getCategory().getNum());
            managerCalendarFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Aucune catégorie assignée à votre compte.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openCreateRide() {
        CreateRideFrame createRideFrame = new CreateRideFrame(manager);
        createRideFrame.setVisible(true);
    }
    
    private void handleLogout() {
        mainFrame.onLogout();
        this.dispose();
    }
}