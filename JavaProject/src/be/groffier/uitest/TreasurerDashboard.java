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
import be.groffier.models.Treasurer;

public class TreasurerDashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private MainFrame mainFrame;
    private Treasurer treasurer;

    public TreasurerDashboard(MainFrame mainFrame, Treasurer treasurer) {
        this.mainFrame = mainFrame;
        this.treasurer = treasurer;
        
        setTitle("Dashboard Trésorier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 920, 680);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(new Color(240, 255, 240));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton btnViewInfo = new JButton(treasurer.getFirstname() + " " + treasurer.getName());
        btnViewInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnViewInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openUserInfo();
            }
        });
        btnViewInfo.setBounds(20, 20, 250, 35);
        contentPane.add(btnViewInfo);
        
        JLabel lblTitle = new JLabel("ClubVelo");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setBounds(300, 20, 320, 35);
        contentPane.add(lblTitle);
        
        JButton btnLogout = new JButton("Disconnect");
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnLogout.setForeground(new Color(200, 50, 50));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
        btnLogout.setBounds(720, 20, 160, 35);
        contentPane.add(btnLogout);
        
        JLabel lblWelcome = new JLabel("Bienvenue, " + treasurer.getFirstname() + " " + treasurer.getName() + " !");
        lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblWelcome.setBounds(20, 75, 860, 30);
        contentPane.add(lblWelcome);
        
        JLabel lblQuickNote = new JLabel("Accès complet aux fonctionnalités financières");
        lblQuickNote.setFont(new Font("Tahoma", Font.ITALIC, 12));
        lblQuickNote.setForeground(Color.GRAY);
        lblQuickNote.setBounds(20, 115, 400, 25);
        contentPane.add(lblQuickNote);
        
        JButton btnManageMembers = new JButton("Gérer les membres");
        btnManageMembers.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnManageMembers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageMembersFrame manageMembersFrame = new ManageMembersFrame();
                manageMembersFrame.setVisible(true);
            }
        });
        btnManageMembers.setBounds(20, 160, 200, 35);
        contentPane.add(btnManageMembers);
        
        setLocationRelativeTo(null);
    }
    
    private void openUserInfo() {
        UserInfoFrame userInfoFrame = new UserInfoFrame(treasurer);
        userInfoFrame.setVisible(true);
    }
    
    private void handleLogout() {
        mainFrame.onLogout();
        this.dispose();
    }
}