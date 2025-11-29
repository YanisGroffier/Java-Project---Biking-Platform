package be.groffier.uitest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import be.groffier.models.Member;
import be.groffier.models.Bike;
import be.groffier.dao.BikeDAO;

public class BikesListFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Member member;
    private JPanel bikesListPanel;
    private BikeDAO bikeDAO;

    public BikesListFrame(Member member) {
        this.member = member;
        this.bikeDAO = new BikeDAO();
        
        setTitle("Mes Vélos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 650);
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
        
        JLabel lblTitle = new JLabel("Mes Vélos");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitle.setBounds(0, 10, 300, 35);
        headerPanel.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Gérez vos vélos");
        lblSubtitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(100, 100, 100));
        lblSubtitle.setBounds(0, 45, 300, 25);
        headerPanel.add(lblSubtitle);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        bikesListPanel = new JPanel();
        bikesListPanel.setBackground(Color.WHITE);
        bikesListPanel.setLayout(new BoxLayout(bikesListPanel, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(bikesListPanel);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(245, 245, 250));
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        
        JButton btnAddBike = new JButton("Ajouter un vélo");
        btnAddBike.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAddBike.setBackground(new Color(144, 238, 144));
        btnAddBike.setForeground(Color.BLACK);
        btnAddBike.setFocusPainted(false);
        btnAddBike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddBikeFrame addBikeFrame = new AddBikeFrame(member);
                addBikeFrame.setVisible(true);
                addBikeFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent windowEvent) {
                        loadBikes();
                    }
                });
            }
        });
        bottomPanel.add(btnAddBike);
        
        JButton btnRefresh = new JButton("Actualiser");
        btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadBikes();
            }
        });
        bottomPanel.add(btnRefresh);
        
        loadBikes();
        
        setLocationRelativeTo(null);
    }
    
    private void loadBikes() {
        List<Bike> bikes = bikeDAO.getBikesByMember(member.getId());
        displayBikes(bikes);
    }
    
    private void displayBikes(List<Bike> bikes) {
        bikesListPanel.removeAll();
        
        if (bikes.isEmpty()) {
            JLabel lblNoBikes = new JLabel("Aucun vélo enregistré.");
            lblNoBikes.setFont(new Font("Tahoma", Font.ITALIC, 14));
            lblNoBikes.setForeground(Color.GRAY);
            lblNoBikes.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblNoBikes.setBorder(new EmptyBorder(50, 0, 0, 0));
            bikesListPanel.add(lblNoBikes);
        } else {
            for (Bike bike : bikes) {
                JPanel bikePanel = createBikePanel(bike);
                bikesListPanel.add(bikePanel);
                bikesListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        
        bikesListPanel.revalidate();
        bikesListPanel.repaint();
    }
    
    private JPanel createBikePanel(Bike bike) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        int panelWidth = 390;
        int panelHeight = 140;
        
        panel.setMaximumSize(new Dimension(panelWidth, panelHeight));
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        panel.setMinimumSize(new Dimension(panelWidth, panelHeight));
        
        JLabel lblIdBadge = new JLabel("#" + bike.getId());
        lblIdBadge.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblIdBadge.setForeground(Color.WHITE);
        lblIdBadge.setOpaque(true);
        lblIdBadge.setBackground(new Color(70, 130, 180));
        lblIdBadge.setHorizontalAlignment(JLabel.CENTER);
        lblIdBadge.setBounds(10, 10, 50, 25);
        panel.add(lblIdBadge);
        
        JLabel lblName = new JLabel(bike.getName());
        lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblName.setBounds(75, 10, 250, 25);
        panel.add(lblName);
        
        JLabel lblTypeLabel = new JLabel("Type:");
        lblTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTypeLabel.setForeground(new Color(80, 80, 80));
        lblTypeLabel.setBounds(10, 50, 60, 20);
        panel.add(lblTypeLabel);
        
        JLabel lblType = new JLabel(bike.getType());
        lblType.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblType.setForeground(new Color(100, 100, 100));
        lblType.setBounds(70, 50, 150, 20);
        panel.add(lblType);
        
        JLabel lblLengthLabel = new JLabel("Longueur:");
        lblLengthLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblLengthLabel.setForeground(new Color(80, 80, 80));
        lblLengthLabel.setBounds(10, 75, 80, 20);
        panel.add(lblLengthLabel);
        
        JLabel lblLength = new JLabel(String.format("%.0f cm", bike.getLength()));
        lblLength.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblLength.setForeground(new Color(100, 100, 100));
        lblLength.setBounds(95, 75, 100, 20);
        panel.add(lblLength);
        
        JLabel lblWeightLabel = new JLabel("Poids:");
        lblWeightLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblWeightLabel.setForeground(new Color(80, 80, 80));
        lblWeightLabel.setBounds(220, 75, 60, 20);
        panel.add(lblWeightLabel);
        
        JLabel lblWeight = new JLabel(String.format("%.2f kg", bike.getWeight()));
        lblWeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblWeight.setForeground(new Color(100, 100, 100));
        lblWeight.setBounds(280, 75, 80, 20);
        panel.add(lblWeight);
        
        JButton btnDelete = new JButton("Supprimer");
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnDelete.setBackground(new Color(255, 99, 71));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setBounds(260, 100, 120, 30);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteBike(bike);
            }
        });
        panel.add(btnDelete);
        
        return panel;
    }
    
    private void handleDeleteBike(Bike bike) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Êtes-vous sûr de vouloir supprimer le vélo \"" + bike.getName() + "\" ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = bikeDAO.deleteBike(bike.getId());
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Vélo supprimé avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
                loadBikes();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de la suppression du vélo.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}