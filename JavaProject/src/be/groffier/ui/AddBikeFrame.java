package be.groffier.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

import be.groffier.models.Member;
import be.groffier.models.Bike;

public class AddBikeFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Member member;
    private JTextField txtName;
    private JTextField txtLength;
    private JTextField txtWeight;
    private JComboBox<String> comboType;
    private final String[] bikeTypes = {
    	"CrossCountry", "CycloCross", "Endurance", "Freeride", "FullSuspension", "Hardtail", "Racing"
    };

    public AddBikeFrame(Member member) {
        this.member = member;

        setTitle("Ajouter un Vélo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Ajouter vélo");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(20, 20, 300, 30);
        contentPane.add(lblTitle);

        JLabel lblName = new JLabel("Nom");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblName.setBounds(20, 70, 120, 25);
        contentPane.add(lblName);

        txtName = new JTextField();
        txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtName.setBounds(150, 70, 200, 30);
        contentPane.add(txtName);

        JLabel lblLength = new JLabel("Longueur (cm)");
        lblLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblLength.setBounds(20, 115, 120, 25);
        contentPane.add(lblLength);

        txtLength = new JTextField();
        txtLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtLength.setBounds(150, 115, 200, 30);
        contentPane.add(txtLength);

        JLabel lblWeight = new JLabel("Poids (kg)");
        lblWeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblWeight.setBounds(20, 160, 120, 25);
        contentPane.add(lblWeight);

        txtWeight = new JTextField();
        txtWeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtWeight.setBounds(150, 160, 200, 30);
        contentPane.add(txtWeight);

        JLabel lblType = new JLabel("Type");
        lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblType.setBounds(20, 205, 120, 25);
        contentPane.add(lblType);

        comboType = new JComboBox<>(bikeTypes);
        comboType.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboType.setBounds(150, 205, 200, 30);
        comboType.setSelectedIndex(0);
        contentPane.add(comboType);

        JButton btnAdd = new JButton("Ajouter");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdd.setBackground(new Color(144, 238, 144));
        btnAdd.setForeground(Color.BLACK);
        btnAdd.addActionListener(e -> handleAddBike());
        btnAdd.setBounds(20, 260, 330, 40);
        contentPane.add(btnAdd);

        setLocationRelativeTo(null);
    }

    private void handleAddBike() {
        try {
            String name = txtName.getText().trim().replace(".", "");
            String lengthStr = txtLength.getText().trim().replace(".", "");
            String weightStr = txtWeight.getText().trim().replace(".", "");
            String type = (String) comboType.getSelectedItem();

            if (name.isEmpty() || lengthStr.isEmpty() || weightStr.isEmpty() || type == null) {
                JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs.",
                    "Champs requis",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            int length = Integer.parseInt(lengthStr);
            double weight = Double.parseDouble(weightStr);

            if (length <= 0 || weight <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Les valeurs doivent être positives.",
                    "Valeurs invalides",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Bike bike = new Bike(type, weight, length, member, null);
            bike.setName(name);
            boolean success = bike.saveToDatabase(member.getId());

            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Vélo ajouté avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);

                txtName.setText("");
                txtLength.setText("");
                txtWeight.setText("");
                comboType.setSelectedIndex(0);

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'ajout du vélo.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Veuillez entrer des valeurs valides pour la longueur et le poids.",
                "Format invalide",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}