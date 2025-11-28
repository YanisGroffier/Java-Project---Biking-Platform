package be.groffier.uitest;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import be.groffier.models.Person;
import be.groffier.models.Member;
import be.groffier.models.Manager;
import be.groffier.models.Treasurer;

public class UserInfoFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Person user;

    public UserInfoFrame(Person user) {
        this.user = user;
        
        setTitle("Informations utilisateur");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(248, 248, 255));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitle = new JLabel("Mes Informations");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(30, 20, 300, 30);
        contentPane.add(lblTitle);
        
        String role = getRoleLabel();
        JLabel lblRole = new JLabel("Rôle: " + role);
        lblRole.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblRole.setForeground(getRoleColor());
        lblRole.setBounds(30, 120, 300, 25);
        contentPane.add(lblRole);
        
        JLabel lblSeparator1 = new JLabel("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"); //TODO changer en separator
        lblSeparator1.setFont(new Font("Dialog", Font.PLAIN, 12));
        lblSeparator1.setForeground(Color.LIGHT_GRAY);
        lblSeparator1.setBounds(30, 90, 420, 20);
        contentPane.add(lblSeparator1);
        
        JLabel lblBasicInfo = new JLabel("Informations personnelles");
        lblBasicInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBasicInfo.setBounds(30, 60, 250, 25);
        contentPane.add(lblBasicInfo);
        
        JLabel lblId = new JLabel("ID: " + user.getId());
        lblId.setBounds(50, 155, 400, 20);
        contentPane.add(lblId);
        
        JLabel lblName = new JLabel("Nom: " + user.getName());
        lblName.setBounds(50, 180, 400, 20);
        contentPane.add(lblName);
        
        JLabel lblFirstname = new JLabel("Prénom: " + user.getFirstname());
        lblFirstname.setBounds(50, 205, 400, 20);
        contentPane.add(lblFirstname);
        
        JLabel lblTel = new JLabel("Téléphone: " + user.getTel());
        lblTel.setBounds(50, 230, 400, 20);
        contentPane.add(lblTel);
        
        JLabel lblSeparator2 = new JLabel("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"); //TODO changer en separator
        lblSeparator2.setFont(new Font("Dialog", Font.PLAIN, 12));
        lblSeparator2.setForeground(Color.LIGHT_GRAY);
        lblSeparator2.setBounds(30, 260, 420, 20);
        contentPane.add(lblSeparator2);
        
        int yPosition = 290;
        
        if (user instanceof Member) {
            Member member = (Member) user;
            
            JLabel lblSpecificInfo = new JLabel("Informations Membre");
            lblSpecificInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
            lblSpecificInfo.setBounds(30, yPosition, 250, 25);
            contentPane.add(lblSpecificInfo);
            yPosition += 35;
            
            JLabel lblBalance = new JLabel("Solde: " + String.format("%.2f", member.getBalance()) + " €");
            lblBalance.setFont(new Font("Tahoma", Font.BOLD, 13));
            lblBalance.setForeground(new Color(34, 139, 34));
            lblBalance.setBounds(50, yPosition, 400, 25);
            contentPane.add(lblBalance);
            
        } else if (user instanceof Manager) {
            Manager manager = (Manager) user;
            
            JLabel lblSpecificInfo = new JLabel("Informations Manager");
            lblSpecificInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
            lblSpecificInfo.setBounds(30, yPosition, 250, 25);
            contentPane.add(lblSpecificInfo);
            yPosition += 35;
            
            String categoryInfo = (manager.getCategory() != null) ? 
                manager.getCategory().toString() : "Non définie";
            JLabel lblCategory = new JLabel("Catégorie: " + categoryInfo);
            lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lblCategory.setBounds(50, yPosition, 400, 25);
            contentPane.add(lblCategory);
            
        }
        
        JButton btnClose = new JButton("Fermer");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnClose.setBounds(180, 370, 120, 35);
        contentPane.add(btnClose);
        
        setLocationRelativeTo(null);
    }
    
    private String getRoleLabel() {
        if (user instanceof Treasurer) return "Trésorier";
         else if (user instanceof Manager) return "Manager";
         else if (user instanceof Member) return "Membre";
         else return "Inconnu";
    }
    
    private Color getRoleColor() {
        if (user instanceof Treasurer) return new Color(46, 139, 87);
        else if (user instanceof Manager) return new Color(255, 140, 0);
        else if (user instanceof Member) return new Color(70, 130, 180);
        else return Color.BLACK;
    }
}