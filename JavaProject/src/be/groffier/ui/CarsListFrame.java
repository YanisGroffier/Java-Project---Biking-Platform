package be.groffier.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import be.groffier.models.Vehicle;
import be.groffier.models.Member;

public class CarsListFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private Member member;
    private JPanel vehiclesContainer;

    public CarsListFrame(Member member) {
        this.member = member;

        setTitle("Mes Véhicules");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(780, 640);
        setMinimumSize(new Dimension(650, 500));
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 25, 25, 25));
        contentPane.setBackground(new Color(245, 245, 250));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Mes Véhicules");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
        lblTitle.setForeground(new Color(70, 130, 180));
        lblTitle.setBorder(new EmptyBorder(0, 0, 25, 0));
        contentPane.add(lblTitle, BorderLayout.NORTH);

        vehiclesContainer = new JPanel();
        vehiclesContainer.setLayout(new BoxLayout(vehiclesContainer, BoxLayout.Y_AXIS));
        vehiclesContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(vehiclesContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(245, 245, 250));
        JButton btnRefresh = new JButton("Actualiser");
        btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRefresh.setBackground(new Color(70, 130, 180));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> loadVehicles());
        bottomPanel.add(btnRefresh);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        loadVehicles();
    }

    private void loadVehicles() {
        vehiclesContainer.removeAll();

        List<Vehicle> vehicles = Vehicle.loadByMemberId(member.getId());

        if (vehicles.isEmpty()) {
            JLabel lblEmpty = new JLabel("Aucun véhicule enregistré pour le moment");
            lblEmpty.setFont(new Font("Tahoma", Font.ITALIC, 18));
            lblEmpty.setForeground(Color.GRAY);
            lblEmpty.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblEmpty.setBorder(new EmptyBorder(100, 0, 0, 0));
            vehiclesContainer.add(lblEmpty);
        } else {
            for (Vehicle v : vehicles) {
                vehiclesContainer.add(createVehicleCard(v));
                vehiclesContainer.add(Box.createRigidArea(new Dimension(0, 16)));
            }
        }

        vehiclesContainer.revalidate();
        vehiclesContainer.repaint();
    }

    private JPanel createVehicleCard(Vehicle vehicle) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(20, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            new EmptyBorder(18, 25, 18, 25)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));


        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);

        JLabel lblSeats = new JLabel(vehicle.getSeatNumber() + " places assises");
        lblSeats.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblSeats.setForeground(new Color(30, 30, 30));
        center.add(lblSeats);

        JLabel lblBikes = new JLabel(vehicle.getBikeSpotNumber() + " emplacement(s) pour vélo");
        lblBikes.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblBikes.setForeground(new Color(80, 80, 80));
        lblBikes.setBorder(new EmptyBorder(8, 0, 0, 0));
        center.add(lblBikes);

        card.add(center, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JLabel lblId = new JLabel("#" + vehicle.getId());
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblId.setForeground(Color.LIGHT_GRAY);
        lblId.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightPanel.add(lblId);
        rightPanel.add(Box.createVerticalGlue());

        JButton btnDelete = new JButton("Supprimer");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.setPreferredSize(new Dimension(110, 36));
        btnDelete.setMaximumSize(new Dimension(110, 36));
        btnDelete.setAlignmentX(Component.RIGHT_ALIGNMENT);

        btnDelete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "<html><b>Supprimer ce véhicule ?</b><br>" +
                vehicle.getSeatNumber() + " places • " +
                vehicle.getBikeSpotNumber() + " emplacements vélo</html>",
                "Confirmer la suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = vehicle.deleteFromDatabase();
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Véhicule supprimé !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadVehicles();
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible de supprimer le véhicule.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rightPanel.add(btnDelete);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }
}