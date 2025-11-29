package be.groffier.uitest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import be.groffier.models.Category;
import be.groffier.models.Manager;
import be.groffier.models.Ride;
import be.groffier.dao.RideDAO;

public class CreateRideFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Manager manager;
    private JTextField txtName;
    private JTextField txtCategory;
    private JTextField txtStartPlace;
    private JTextField txtDate;
    private JTextField txtFee;

    public CreateRideFrame(Manager manager) {
    	setResizable(false);
        this.manager = manager;

        setTitle("Créer un événement");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 425);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Créer une sortie");
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

        JLabel lblStartPlace = new JLabel("Point de RDV");
        lblStartPlace.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblStartPlace.setBounds(20, 115, 120, 25);
        contentPane.add(lblStartPlace);

        txtStartPlace = new JTextField("ASBL ClubVelo");
        txtStartPlace.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtStartPlace.setBounds(150, 115, 200, 30);
        txtStartPlace.setEditable(false);
        txtStartPlace.setBackground(new Color(240, 240, 240));
        contentPane.add(txtStartPlace);

        JLabel lblDate = new JLabel("Date");
        lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDate.setBounds(20, 160, 120, 25);
        contentPane.add(lblDate);

        txtDate = new JTextField();
        txtDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtDate.setBounds(150, 160, 200, 30);
        contentPane.add(txtDate);

        JLabel lblDateFormat = new JLabel("Format: JJ/MM/AAAA");
        lblDateFormat.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblDateFormat.setForeground(Color.GRAY);
        lblDateFormat.setBounds(150, 192, 200, 15);
        contentPane.add(lblDateFormat);

        JLabel lblFee = new JLabel("Cotisation (€)");
        lblFee.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFee.setBounds(20, 215, 120, 25);
        contentPane.add(lblFee);

        txtFee = new JTextField();
        txtFee.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtFee.setBounds(150, 215, 200, 30);
        contentPane.add(txtFee);

        JButton btnCreate = new JButton("Créer la balade");
        btnCreate.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCreate.setBackground(new Color(144, 238, 144));
        btnCreate.setForeground(Color.BLACK);
        btnCreate.addActionListener(e -> handleCreateRide(manager));
        btnCreate.setBounds(20, 275, 330, 40);
        contentPane.add(btnCreate);

        JButton btnCancel = new JButton("Annuler");
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCancel.setBackground(new Color(240, 240, 240));
        btnCancel.addActionListener(e -> this.dispose());
        btnCancel.setBounds(20, 325, 330, 35);
        contentPane.add(btnCancel);

        setLocationRelativeTo(null);
    }

    private void handleCreateRide(Manager manager) {
        try {
            String name = txtName.getText().trim();
            int category = manager.getCategory().getNum();
            String startPlace = txtStartPlace.getText().trim();
            String dateStr = txtDate.getText().trim();
            String feeStr = txtFee.getText().trim().replace("€", "").replace(",", ".");

            if (name.isEmpty() || dateStr.isEmpty() || feeStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs.",
                    "Champs requis",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            //dateFormat.setLenient(false); //Utile?
            Date startDate = dateFormat.parse(dateStr);

            double fee = Double.parseDouble(feeStr);

            if (fee < 0) {
                JOptionPane.showMessageDialog(this,
                    "La cotisation doit être positive ou nulle.",
                    "Valeur invalide",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Ride ride = new Ride(name, startPlace, startDate, fee);
            
            RideDAO rideDAO = new RideDAO();
            boolean success = rideDAO.createRide(ride, category);

            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Sortie créée avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);

                txtName.setText("");
                txtDate.setText("");
                txtFee.setText("");

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de la création de la sortie.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this,
                "Format de date invalide. Utilisez: jj/mm/aaaa\nExemple: 09/09/2006",
                "Format invalide",
                JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Veuillez entrer une valeur valide pour la cotisation.",
                "Format invalide",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}