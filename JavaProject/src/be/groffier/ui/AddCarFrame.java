package be.groffier.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import be.groffier.models.Member;
import be.groffier.models.Vehicle;

public class AddCarFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Member member;
    private JTextField txtNbrPlaces;
    private JTextField txtNbrBikes;

    public AddCarFrame(Member member) {
        this.member = member;
        
        setTitle("Ajouter un Véhicule");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitle = new JLabel("Ajouter véhicule");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(20, 20, 300, 30);
        contentPane.add(lblTitle);
        
        JLabel lblNbrPlaces = new JLabel("Nbr places");
        lblNbrPlaces.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNbrPlaces.setBounds(20, 70, 120, 25);
        contentPane.add(lblNbrPlaces);
        
        txtNbrPlaces = new JTextField("");
        txtNbrPlaces.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNbrPlaces.setBounds(150, 70, 200, 30);
        contentPane.add(txtNbrPlaces);
        
        JLabel lblNbrBikes = new JLabel("Nbr vélos");
        lblNbrBikes.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNbrBikes.setBounds(20, 115, 120, 25);
        contentPane.add(lblNbrBikes);
        
        txtNbrBikes = new JTextField("");
        txtNbrBikes.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNbrBikes.setBounds(150, 115, 200, 30);
        contentPane.add(txtNbrBikes);
        
        JButton btnAdd = new JButton("Ajouter");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdd.setBackground(new Color(144, 238, 144));
        btnAdd.setForeground(Color.BLACK);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAddCar();
            }
        });
        btnAdd.setBounds(20, 170, 330, 40);
        contentPane.add(btnAdd);
        
        setLocationRelativeTo(null);
    }
    
    private void handleAddCar() {
        try {
            String placesStr = txtNbrPlaces.getText().trim();
            String bikesStr = txtNbrBikes.getText().trim();

            if (placesStr.isEmpty() || bikesStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int nbrPlaces = Integer.parseInt(placesStr);
            int nbrBikes = Integer.parseInt(bikesStr);

            if (nbrPlaces < 2 || nbrBikes < 0) {
                JOptionPane.showMessageDialog(this, "Places ≥ 2 et emplacements vélo ≥ 0", "Valeur invalide", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vehicle vehicle = new Vehicle(nbrPlaces, nbrBikes, member, null);
            if (vehicle.saveToDatabase(member.getId())) {
                JOptionPane.showMessageDialog(this, "Véhicule ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres valides.", "Format incorrect", JOptionPane.ERROR_MESSAGE);
        }
    }
}