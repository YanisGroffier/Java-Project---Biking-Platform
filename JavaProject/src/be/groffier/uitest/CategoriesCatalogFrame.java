package be.groffier.uitest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import be.groffier.dao.CategoryMemberDAO;
import be.groffier.models.Member;
import be.groffier.models.CategoryEnum;

public class CategoriesCatalogFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Member member;
    private CategoryMemberDAO categoryMemberDAO;

    public CategoriesCatalogFrame(Member member) {
        this.member = member;
        this.categoryMemberDAO = new CategoryMemberDAO();
        
        setTitle("Catalogue des Catégories");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Sélectionnez une catégorie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new GridLayout(4, 1, 10, 15));
        contentPane.add(categoriesPanel, BorderLayout.CENTER);

        createCategoryRow(categoriesPanel, CategoryEnum.MountainBike_trial, 1);
        createCategoryRow(categoriesPanel, CategoryEnum.MountainBike_Downhill, 2);
        createCategoryRow(categoriesPanel, CategoryEnum.MountainBike_Cross, 3);
        createCategoryRow(categoriesPanel, CategoryEnum.RoadBike, 4);
    }

    private void createCategoryRow(JPanel parent, CategoryEnum category, int categoryId) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout(10, 0));
        rowPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        String categoryName = formatCategoryName(category);
        JLabel categoryLabel = new JLabel(categoryName);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        rowPanel.add(categoryLabel, BorderLayout.CENTER);

        JButton subscribeButton = new JButton("S'inscrire");
        subscribeButton.setFont(new Font("Arial", Font.BOLD, 12));
        subscribeButton.addActionListener(e -> handleSubscription(categoryId, categoryName));
        rowPanel.add(subscribeButton, BorderLayout.EAST);

        parent.add(rowPanel);
    }

    private String formatCategoryName(CategoryEnum category) {
        switch (category) {
            case MountainBike_trial:
                return "Mountain Bike - Trial";
            case MountainBike_Downhill:
                return "Mountain Bike - Downhill";
            case MountainBike_Cross:
                return "Mountain Bike - Cross";
            case RoadBike:
                return "Road Bike";
            default:
                return category.toString();
        }
    }

    private void handleSubscription(int categoryId, String categoryName) {
        if (categoryMemberDAO.isMemberInCategory(member.getId(), categoryId)) {
            JOptionPane.showMessageDialog(
                this,
                "Vous êtes déjà inscrit dans cette catégorie.",
                "Inscription impossible",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Voulez-vous vous inscrire dans la catégorie:\n" + categoryName + " ?",
            "Confirmation d'inscription",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = categoryMemberDAO.addMemberToCategory(member.getId(), categoryId);
            
            if (success) {
                JOptionPane.showMessageDialog(
                    this,
                    "Inscription réussie à la catégorie " + categoryName + " !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Une erreur est survenue lors de l'inscription.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}