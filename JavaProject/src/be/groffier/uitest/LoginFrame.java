package be.groffier.uitest;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import be.groffier.models.Person;
import be.groffier.dao.PersonDAO;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtName;
    private JPasswordField txtPassword;
    private MainFrame mainFrame;
    private PersonDAO personDAO;

    public LoginFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.personDAO = new PersonDAO();
        
        setTitle("Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitle = new JLabel("Formulaire de Connexion");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(120, 30, 220, 30);
        contentPane.add(lblTitle);
        
        JLabel lblName = new JLabel("Nom:");
        lblName.setBounds(50, 90, 120, 20);
        contentPane.add(lblName);
        
        txtName = new JTextField();
        txtName.setBounds(180, 90, 200, 25);
        contentPane.add(txtName);
        txtName.setColumns(10);
        
        JLabel lblPassword = new JLabel("Mot de passe:");
        lblPassword.setBounds(50, 130, 120, 20);
        contentPane.add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setBounds(180, 130, 200, 25);
        contentPane.add(txtPassword);
        
        JButton btnLogin = new JButton("Se connecter");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        btnLogin.setBounds(160, 190, 130, 30);
        contentPane.add(btnLogin);
        
        setLocationRelativeTo(null);
    }
    
    private void handleLogin() {
        String name = txtName.getText();
        String password = new String(txtPassword.getPassword());
        
        if (name.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez remplir tous les champs!", 
                "Erreur", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Person person = personDAO.authenticate(name, password);
        
        if (person != null) {
            JOptionPane.showMessageDialog(this, 
                "Connexion réussie! Bienvenue " + person.getFirstname() + "!", 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
            
            mainFrame.onLoginSuccess(person);
            this.dispose();
            
        } else {
            JOptionPane.showMessageDialog(this, 
                "Nom ou mot de passe incorrect!", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }
}