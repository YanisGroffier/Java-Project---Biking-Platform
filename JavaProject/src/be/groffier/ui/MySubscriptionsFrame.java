package be.groffier.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import be.groffier.models.Member;
import be.groffier.models.Ride;
import be.groffier.models.Inscription;

public class MySubscriptionsFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Member member;
    private boolean isCyclist;
    private JPanel ridesListPanel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public MySubscriptionsFrame(Member member, boolean isCyclist) {
        this.member = member;
        this.isCyclist = isCyclist;
        
        setResizable(false);
        setTitle(isCyclist ? "Mes inscriptions - Cycliste" : "Mes inscriptions - Conducteur");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 480, 650);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(new Color(245, 245, 250));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(245, 245, 250));
        headerPanel.setPreferredSize(new Dimension(410, 80));
        headerPanel.setLayout(null);
        contentPane.add(headerPanel, BorderLayout.NORTH);
        
        String title = isCyclist ? "Mes inscriptions - Cycliste" : "Mes inscriptions - Conducteur";
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(0, 10, 400, 35);
        headerPanel.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Liste de vos sorties");
        lblSubtitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(100, 100, 100));
        lblSubtitle.setBounds(0, 45, 300, 25);
        headerPanel.add(lblSubtitle);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        ridesListPanel = new JPanel();
        ridesListPanel.setBackground(Color.WHITE);
        ridesListPanel.setLayout(new BoxLayout(ridesListPanel, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(ridesListPanel);
        
        JButton btnRefresh = new JButton("Actualiser");
        btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnRefresh.addActionListener(e -> loadInscriptions());
        contentPane.add(btnRefresh, BorderLayout.SOUTH);
        
        loadInscriptions();
        
        setLocationRelativeTo(null);
    }
    
    private void loadInscriptions() {
        List<Ride> rides;
        
        if (isCyclist) {
            rides = Inscription.loadCyclistInscriptionsByMemberId(member.getId());
        } else {
            rides = Inscription.loadDriverInscriptionsByMemberId(member.getId());
        }
        
        displayRides(rides);
    }
    
    private void displayRides(List<Ride> rides) {
        ridesListPanel.removeAll();
        
        if (rides.isEmpty()) {
            String message = isCyclist 
                ? "Aucune inscription en tant que cycliste." 
                : "Aucune inscription en tant que conducteur.";
            
            JLabel lblNoRides = new JLabel(message);
            lblNoRides.setFont(new Font("Tahoma", Font.ITALIC, 14));
            lblNoRides.setForeground(Color.GRAY);
            lblNoRides.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblNoRides.setBorder(new EmptyBorder(50, 0, 0, 0));
            ridesListPanel.add(lblNoRides);
        } else {
            for (Ride ride : rides) {
                JPanel ridePanel = createRidePanel(ride);
                ridesListPanel.add(ridePanel);
                ridesListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        
        ridesListPanel.revalidate();
        ridesListPanel.repaint();
    }
    
    private JPanel createRidePanel(Ride ride) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            new EmptyBorder(20, 20, 20, 20)
        ));

        int panelWidth = 390;
        int panelHeight = 180;

        panel.setMaximumSize(new Dimension(panelWidth, panelHeight));
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        panel.setMinimumSize(new Dimension(panelWidth, panelHeight));

        JLabel lblIdBadge = new JLabel("#" + ride.getNum());
        lblIdBadge.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblIdBadge.setForeground(Color.WHITE);
        lblIdBadge.setOpaque(true);
        lblIdBadge.setBackground(new Color(70, 130, 180));
        lblIdBadge.setHorizontalAlignment(JLabel.CENTER);
        lblIdBadge.setBounds(10, 10, 60, 30);
        panel.add(lblIdBadge);

        JLabel lblName = new JLabel(ride.getName());
        lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblName.setBounds(85, 10, 290, 35);
        panel.add(lblName);

        JLabel lblPlace = new JLabel(ride.getStartPlace());
        lblPlace.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPlace.setForeground(new Color(100, 100, 100));
        lblPlace.setBounds(85, 45, 290, 20);
        panel.add(lblPlace);

        JLabel lblDateLabel = new JLabel("Date:");
        lblDateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDateLabel.setForeground(new Color(80, 80, 80));
        lblDateLabel.setBounds(10, 80, 60, 25);
        panel.add(lblDateLabel);

        JLabel lblDate = new JLabel(dateFormat.format(ride.getStartDate()));
        lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDate.setForeground(new Color(100, 100, 100));
        lblDate.setBounds(70, 80, 200, 25);
        panel.add(lblDate);

        JLabel lblFeeLabel = new JLabel("Cotisation:");
        lblFeeLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblFeeLabel.setForeground(new Color(80, 80, 80));
        lblFeeLabel.setBounds(10, 110, 80, 25);
        panel.add(lblFeeLabel);

        JLabel lblFee = new JLabel(String.format("%.2f €", ride.getFee()));
        lblFee.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblFee.setForeground(new Color(34, 139, 34));
        lblFee.setBounds(95, 110, 100, 25);
        panel.add(lblFee);

        String roleText = isCyclist ? "Cycliste" : "Conducteur";
        Color roleColor = isCyclist ? new Color(34, 139, 34) : new Color(70, 130, 180);
        
        JLabel lblRole = new JLabel(roleText);
        lblRole.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblRole.setForeground(Color.WHITE);
        lblRole.setOpaque(true);
        lblRole.setBackground(roleColor);
        lblRole.setHorizontalAlignment(JLabel.CENTER);
        lblRole.setBounds(250, 110, 130, 30);
        panel.add(lblRole);

        return panel;
    }
}