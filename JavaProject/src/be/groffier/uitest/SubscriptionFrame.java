package be.groffier.uitest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import be.groffier.dao.BikeDAO;
import be.groffier.dao.InscriptionDAO;
import be.groffier.dao.VehicleDAO;
import be.groffier.models.Bike;
import be.groffier.models.Vehicle;
import be.groffier.models.Member;
import be.groffier.models.Ride;

public class SubscriptionFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Member member;
    private Ride ride;
    private JComboBox<String> comboRole;
    private JComboBox<Bike> comboBike;
    private JComboBox<Vehicle> comboVehicle;

    private BikeDAO bikeDAO = new BikeDAO();
    private VehicleDAO vehicleDAO = new VehicleDAO();

    public SubscriptionFrame(JFrame parent, Ride ride, Member member) {
        this.member = member;
        this.ride = ride;

        setTitle("S'inscrire");
        setSize(440, 580);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(35, 40, 40, 40));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        getContentPane().add(mainPanel);

        JLabel titleLabel = new JLabel("S'inscrire");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 40, 0));
        mainPanel.add(titleLabel);

        JLabel lblRole = new JLabel("En tant que");
        lblRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRole.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(lblRole);
        
        comboRole = new JComboBox<>(new String[]{"Cycliste", "Conducteur"});
        comboRole.setFont(new Font("Tahoma", Font.PLAIN, 15));
        comboRole.setMaximumSize(new Dimension(320, 44));
        comboRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(comboRole);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        JLabel lblBike = new JLabel("Choisissez un vélo");
        lblBike.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblBike.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBike.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(lblBike);

        comboBike = new JComboBox<>();
        comboBike.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBike.setMaximumSize(new Dimension(320, 44));
        comboBike.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(comboBike);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        JLabel lblVehicle = new JLabel("Choisissez un véhicule");
        lblVehicle.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblVehicle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblVehicle.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(lblVehicle);

        comboVehicle = new JComboBox<>();
        comboVehicle.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboVehicle.setMaximumSize(new Dimension(320, 44));
        comboVehicle.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboVehicle.setEnabled(false);
        mainPanel.add(comboVehicle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        JButton btnSubmit = new JButton("S'inscrire");
        btnSubmit.setBackground(new Color(144, 238, 144));
        btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnSubmit.setFocusPainted(false);
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSubmit.setMaximumSize(new Dimension(280, 60));
        btnSubmit.setPreferredSize(new Dimension(280, 60));

        btnSubmit.addActionListener(e -> handleSubscription());

        mainPanel.add(btnSubmit);

        loadBikesAndVehicles();

        comboRole.addActionListener(e -> {
            String role = (String) comboRole.getSelectedItem();
            boolean isDriver = "Conducteur".equals(role);

            comboVehicle.setEnabled(isDriver);

            if (!isDriver) comboVehicle.setSelectedItem(null);
            
        });

        // Par défaut j'affiche le cycliste
        comboRole.setSelectedIndex(0);
        comboVehicle.setEnabled(false);
        comboBike.setEnabled(true);
    }

    private void loadBikesAndVehicles() {
        List<Bike> bikes = bikeDAO.getBikesByMember(member.getId());
        comboBike.removeAllItems();
        if (bikes.isEmpty()) {
            comboBike.addItem(null);
            comboBike.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setText("Aucun vélo enregistré");
                    setForeground(Color.GRAY);
                    setEnabled(false);
                    return this;
                }
            });
        } else {
            for (Bike bike : bikes) {
                comboBike.addItem(bike);
            }
        }

        comboBike.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Bike) {
                    Bike b = (Bike) value;
                    setText(b.getType() + " – " + b.getWeight() + " kg");
                } else if (value == null) {
                    setText("Aucun vélo");
                }
                return this;
            }
        });

        List<Vehicle> vehicles = vehicleDAO.getAllVehicles(member.getId());
        comboVehicle.removeAllItems();
        if (vehicles.isEmpty()) {
            comboVehicle.addItem(null);
            comboVehicle.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setText("Aucun véhicule enregistré");
                    setForeground(Color.GRAY);
                    setEnabled(false);
                    return this;
                }
            });
        } else {
            for (Vehicle v : vehicles) {
                comboVehicle.addItem(v);
            }
        }

        comboVehicle.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Vehicle) {
                    Vehicle v = (Vehicle) value;
                    setText(v.getSeatNumber() + " places • " + v.getBikeSpotNumber() + " vélos");
                } else if (value == null) {
                    setText("Aucun véhicule");
                }
                return this;
            }
        });
    }

    private void handleSubscription() {
        String role = (String) comboRole.getSelectedItem();
        Bike selectedBike = (Bike) comboBike.getSelectedItem();
        Vehicle selectedVehicle = (Vehicle) comboVehicle.getSelectedItem();

        boolean isPassenger = "Cycliste".equals(role);
        boolean hasBike = true;
        
        if (isPassenger && selectedBike == null) {
            JOptionPane.showMessageDialog(this, "En tant que cycliste, vous devez sélectionner un vélo.", 
                "Vélo requis", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!isPassenger && selectedVehicle == null) {
            JOptionPane.showMessageDialog(this, "En tant que conducteur, vous devez sélectionner un véhicule.", 
                "Véhicule requis", JOptionPane.WARNING_MESSAGE);
            return;
        }

        InscriptionDAO inscriptionDAO = new InscriptionDAO();
        if (inscriptionDAO.isAlreadyRegistered(member.getId(), ride.getNum())) {
            JOptionPane.showMessageDialog(this, "Vous êtes déjà inscrit à cette sortie !", 
                "Déjà inscrit", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = inscriptionDAO.createInscription(
            member,
            ride,
            isPassenger,
            hasBike
        );

        if (success) {
            String recap = "<html><b>Inscription confirmée !</b><br><br>" +
                "Sortie : #" + ride.getNum() + " – " + ride.getName() + "<br>" +
                "Rôle : <b>" + role + "</b><br>" +
                (isPassenger 
                    ? "Vélo : " + selectedBike.getType() + " (" + selectedBike.getWeight() + " kg)"
                    : "Véhicule : " + selectedVehicle.getSeatNumber() + " places • " + 
                      selectedVehicle.getBikeSpotNumber() + " vélos" +
                      (selectedBike != null ? "<br>Vélo emmené : " + selectedBike.getType() : "")) +
                "</html>";

            JOptionPane.showMessageDialog(this, recap, "Inscription réussie", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
            if (getOwner() instanceof CalendarFrame) {
                ((CalendarFrame) getOwner()).refreshCalendar();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.", 
                "Échec", JOptionPane.ERROR_MESSAGE);
        }
    }
}