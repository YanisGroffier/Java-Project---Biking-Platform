package be.groffier.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import be.groffier.models.Member;

public class MemberDashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private MainFrame mainFrame;
    private Member member;
    private JLabel lblBalance;

    public MemberDashboard(MainFrame mainFrame, Member member) {
        this.mainFrame = mainFrame;
        this.member = member;
        
        setTitle("Bienvenue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 920, 626);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton btnViewInfo = new JButton(member.getFirstname() + " " + member.getName());
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
        
        JButton btnLogout = new JButton("Se déconnecter");
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnLogout.setForeground(new Color(200, 50, 50));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
        btnLogout.setBounds(720, 20, 160, 35);
        contentPane.add(btnLogout);
        
        JLabel lblWelcome = new JLabel("Bienvenue, " + member.getFirstname() + " " + member.getName() + " !");
        lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblWelcome.setBounds(20, 75, 430, 30);
        contentPane.add(lblWelcome);

        JButton btnCategories = new JButton("Voir les catégories");
        btnCategories.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCategories.setBounds(720, 78, 160, 35);
        btnCategories.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		openCategoriesCatalog();
        	}
        });
        contentPane.add(btnCategories);
        
        JPanel eventsPanel = new JPanel();
        eventsPanel.setBackground(new Color(240, 240, 240));
        eventsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        eventsPanel.setBounds(20, 130, 420, 240);
        eventsPanel.setLayout(null);
        contentPane.add(eventsPanel);
        
        JLabel lblEventsTitle = new JLabel("Événements");
        lblEventsTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEventsTitle.setBounds(10, 10, 200, 25);
        eventsPanel.add(lblEventsTitle);
        
        JButton btnViewAllEvents = new JButton("Consulter tous les events");
        btnViewAllEvents.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnViewAllEvents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCalendar();
            }
        });
        btnViewAllEvents.setBounds(10, 45, 390, 35);
        eventsPanel.add(btnViewAllEvents);
        
        JPanel separator = new JPanel();
        separator.setBackground(new Color(180, 180, 180));
        separator.setBounds(10, 95, 390, 1);
        eventsPanel.add(separator);
        
        JLabel lblMyInscriptions = new JLabel("Mes inscriptions");
        lblMyInscriptions.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblMyInscriptions.setBounds(10, 105, 200, 25);
        eventsPanel.add(lblMyInscriptions);
        
        JButton btnMyInscriptionsCyclist = new JButton("En tant que cycliste");
        btnMyInscriptionsCyclist.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnMyInscriptionsCyclist.setBounds(10, 140, 185, 35);
        btnMyInscriptionsCyclist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMyInscriptionsCyclist();
            }
        });
        eventsPanel.add(btnMyInscriptionsCyclist);
        
        JButton btnMyInscriptionsDriver = new JButton("En tant que conducteur");
        btnMyInscriptionsDriver.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnMyInscriptionsDriver.setBounds(205, 140, 195, 35);
        btnMyInscriptionsDriver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMyInscriptionsDriver();
            }
        });
        eventsPanel.add(btnMyInscriptionsDriver);

        JPanel paymentPanel = new JPanel();
        paymentPanel.setBackground(new Color(240, 240, 240));
        paymentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        paymentPanel.setBounds(460, 130, 420, 240);
        paymentPanel.setLayout(null);
        contentPane.add(paymentPanel);
        
        JLabel lblPaymentTitle = new JLabel("Paiements");
        lblPaymentTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPaymentTitle.setBounds(10, 10, 200, 25);
        paymentPanel.add(lblPaymentTitle);
        
        lblBalance = new JLabel("Solde actuel : " + String.format("%.2f€", member.getBalance()));
        lblBalance.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblBalance.setBounds(10, 45, 300, 20);
        paymentPanel.add(lblBalance);
        
        JLabel lblAmount = new JLabel("Montant");
        lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAmount.setBounds(10, 80, 100, 25);
        paymentPanel.add(lblAmount);
        
        JTextField txtAmount = new JTextField("");
        txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtAmount.setBounds(120, 80, 150, 30);
        paymentPanel.add(txtAmount);
        
        JButton btnPaySubscription = new JButton("Ajouter fonds");
        btnPaySubscription.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnPaySubscription.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAddFunds(txtAmount);
            }
        });
        btnPaySubscription.setBounds(10, 125, 390, 40);
        paymentPanel.add(btnPaySubscription);
        
        JButton btnPaySubscriptionFee = new JButton("Payer cotisations");
        btnPaySubscriptionFee.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnPaySubscriptionFee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleFeePayment();
            }
        });
        btnPaySubscriptionFee.setBounds(10, 180, 390, 40);
        paymentPanel.add(btnPaySubscriptionFee);
        
        JPanel bikesPanel = new JPanel();
        bikesPanel.setBackground(new Color(240, 240, 240));
        bikesPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        bikesPanel.setBounds(20, 390, 420, 180);
        bikesPanel.setLayout(null);
        contentPane.add(bikesPanel);
        
        JLabel lblBikesTitle = new JLabel("Vélos");
        lblBikesTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblBikesTitle.setBounds(10, 10, 200, 25);
        bikesPanel.add(lblBikesTitle);
        
        JButton btnMyBikes = new JButton("Mes vélos");
        btnMyBikes.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnMyBikes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openBikesList();
            }
        });
        btnMyBikes.setBounds(10, 55, 185, 35);
        bikesPanel.add(btnMyBikes);
        
        JButton btnAddBike = new JButton("Ajouter un vélo");
        btnAddBike.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnAddBike.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddBike();
            }
        });
        btnAddBike.setBounds(205, 55, 195, 35);
        bikesPanel.add(btnAddBike);
        
        JPanel vehiclesPanel = new JPanel();
        vehiclesPanel.setBackground(new Color(240, 240, 240));
        vehiclesPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        vehiclesPanel.setBounds(460, 390, 420, 180);
        vehiclesPanel.setLayout(null);
        contentPane.add(vehiclesPanel);
        
        JLabel lblVehiclesTitle = new JLabel("Véhicules");
        lblVehiclesTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblVehiclesTitle.setBounds(10, 10, 200, 25);
        vehiclesPanel.add(lblVehiclesTitle);
        
        JButton btnMyVehicles = new JButton("Mes véhicules");
        btnMyVehicles.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnMyVehicles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCarsList();
            }
        });
        btnMyVehicles.setBounds(10, 55, 185, 35);
        vehiclesPanel.add(btnMyVehicles);
        
        JButton btnAddVehicle = new JButton("Ajouter un véhicule");
        btnAddVehicle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnAddVehicle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddCar();
            }
        });
        btnAddVehicle.setBounds(205, 55, 195, 35);
        vehiclesPanel.add(btnAddVehicle);
    }
    
    private void openUserInfo() {
        UserInfoFrame userInfoFrame = new UserInfoFrame(member);
        userInfoFrame.setVisible(true);
    }
    
    private void openCalendar() {
        CalendarFrame calendarFrame = new CalendarFrame(member);
        calendarFrame.setVisible(true);
    }
    
    private void handleAddFunds(JTextField txtAmount) {
        try {
            String amountStr = txtAmount.getText().trim().replace("€", "").replace(",", ".");
            if (amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Veuillez entrer un montant.", 
                    "Champ requis", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Le montant doit être supérieur à 0€.", 
                    "Montant invalide", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Confirmer l'ajout de " + String.format("%.2f €", amount) + " à votre solde ?", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = member.addFunds(amount);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Ajout de " + String.format("%.2f €", amount) + " effectué avec succès.\n" +
                        "Nouveau solde : " + String.format("%.2f €", member.getBalance()), 
                        "Succès", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    txtAmount.setText(""); 
                    refreshDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erreur lors de l'ajout des fonds. Veuillez réessayer.", 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez entrer un montant valide (ex: 10 ou 3.50).", 
                "Format invalide", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleFeePayment() {
        double subscriptionFee = member.calculateFeeAmount();
        String message = "Confirmer le paiement de la cotisation de " + String.format("%.2f €", subscriptionFee) + " ?\n" +
                      "Votre solde actuel : " + String.format("%.2f €", member.getBalance());
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            message,
            "Confirmation de paiement", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (member.hasSufficientBalance(subscriptionFee)) {
                boolean success = member.paySubscriptionFee();
                
                if (success) {
                    LocalDate dateExpiration = LocalDate.now().plusYears(1);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    JOptionPane.showMessageDialog(this,
                        "Paiement des cotisations effectué avec succès.\n" +
                        "Abonnement valide jusqu'au " + dateExpiration.format(formatter) + "\n" +
                        "Montant payé : " + String.format("%.2f €", subscriptionFee) + "\n" +
                        "Nouveau solde : " + String.format("%.2f €", member.getBalance()),
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);	                
                    refreshDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erreur lors du paiement. Veuillez réessayer.", 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Solde insuffisant pour effectuer ce paiement.\n" +
                    "Solde actuel : " + String.format("%.2f €", member.getBalance()) + "\n" +
                    "Montant requis : " + String.format("%.2f €", subscriptionFee),
                    "Solde insuffisant", 
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void handleLogout() {
        mainFrame.onLogout();
        this.dispose();
    }
    
    private void openCategoriesCatalog() {
        CategoriesCatalogFrame categoryCatalogFrame = new CategoriesCatalogFrame(member);
        categoryCatalogFrame.setVisible(true);
    }
    
    private void openBikesList() {
        BikesListFrame bikesListFrame = new BikesListFrame(member);
        bikesListFrame.setVisible(true);
    }
    
    private void openAddBike() {
        AddBikeFrame addBikeFrame = new AddBikeFrame(member);
        addBikeFrame.setVisible(true);
    }
    
    private void openCarsList() {
        CarsListFrame carsListFrame = new CarsListFrame(member);
        carsListFrame.setVisible(true);
    }
    
    private void openAddCar() {
        AddCarFrame addCarFrame = new AddCarFrame(member);
        addCarFrame.setVisible(true);
    }
    
    private void openMyInscriptionsCyclist() {
        MySubscriptionsFrame subscriptionsFrame = new MySubscriptionsFrame(member, true);
        subscriptionsFrame.setVisible(true);
    }

    private void openMyInscriptionsDriver() {
        MySubscriptionsFrame subscriptionsFrame = new MySubscriptionsFrame(member, false);
        subscriptionsFrame.setVisible(true);
    }

    private void refreshDashboard() {
        lblBalance.setText("Solde actuel : " + String.format("%.2f€", member.getBalance()));
    }
}