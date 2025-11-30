package be.groffier.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import be.groffier.models.Ride;
import be.groffier.models.Manager;

public class ManagerCalendarFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Manager manager;
    private JPanel ridesListPanel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private boolean filterByCategory;
    private Integer categoryFilter;

    public ManagerCalendarFrame(Manager manager) {
        this(manager, false, null);
    }
    
    /**
     * @wbp.parser.constructor
     */
    public ManagerCalendarFrame(Manager manager, boolean filterByCategory, Integer categoryId) {
        setResizable(false);
        setSize(460, 650);
        this.manager = manager;
        this.filterByCategory = filterByCategory;
        this.categoryFilter = categoryId;
        
        String titleText = filterByCategory ? 
            "Événements - " + manager.getCategory().toString() : 
            "Tous les événements";
        
        setTitle(titleText);
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
        
        JLabel lblTitle = new JLabel(titleText);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitle.setBounds(0, 10, 400, 35);
        headerPanel.add(lblTitle);
        
        String subtitleText = filterByCategory ? 
            "Sorties de votre catégorie" : 
            "Vue d'ensemble des sorties";
        
        JLabel lblSubtitle = new JLabel(subtitleText);
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
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1, 0, 5));
        bottomPanel.setBackground(new Color(245, 245, 250));
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        
        JButton btnRefresh = new JButton("Actualiser");
        btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadRides();
            }
        });
        bottomPanel.add(btnRefresh);
        
        JButton btnCreateEvent = new JButton("Créer un événement");
        btnCreateEvent.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCreateEvent.setBackground(new Color(144, 238, 144));
        btnCreateEvent.setForeground(Color.BLACK);
        btnCreateEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCreateRide();
            }
        });
        bottomPanel.add(btnCreateEvent);
        
        loadRides();
        
        setLocationRelativeTo(null);
    }
    
    private void openCreateRide() {
        CreateRideFrame createRideFrame = new CreateRideFrame(manager);
        createRideFrame.setVisible(true);
    }
    
    private void loadRides() {
        List<Ride> rides = Ride.loadAll();
        
        if (filterByCategory && categoryFilter != null) {
            rides = rides.stream()
                .filter(ride -> ride.getNum() == categoryFilter)
                .collect(Collectors.toList());
        }
        
        displayRides(rides);
    }
    
    private void displayRides(List<Ride> rides) {
        ridesListPanel.removeAll();
        
        if (rides.isEmpty()) {
            String noRidesMessage = filterByCategory ? 
                "Aucun événement disponible pour votre catégorie." :
                "Aucun événement disponible pour le moment.";
            
            JLabel lblNoRides = new JLabel(noRidesMessage);
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
        int panelHeight = 200;

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

        JLabel lblInscriptionsLabel = new JLabel("Inscrits:");
        lblInscriptionsLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblInscriptionsLabel.setForeground(new Color(80, 80, 80));
        lblInscriptionsLabel.setBounds(10, 140, 100, 25);
        panel.add(lblInscriptionsLabel);

        int inscriptionCount = ride.getTotalInscriptionNumber();
        JLabel lblInscriptions = new JLabel(inscriptionCount + " personne(s)");
        lblInscriptions.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblInscriptions.setForeground(new Color(100, 100, 100));
        lblInscriptions.setBounds(95, 140, 150, 25);
        panel.add(lblInscriptions);

        return panel;
    }
}