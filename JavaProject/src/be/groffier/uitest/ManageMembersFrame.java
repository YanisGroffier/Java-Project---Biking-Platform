package be.groffier.uitest;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import be.groffier.dao.PersonDAO;
import be.groffier.models.Member;

public class ManageMembersFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableMembres;
	private DefaultTableModel tableModel;
	private PersonDAO personDAO;

	public ManageMembersFrame() {
		personDAO = new PersonDAO();
		
		setTitle("Gestion des Membres");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Liste des Membres");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitle.setBounds(20, 20, 300, 30);
		contentPane.add(lblTitle);
		
		JButton btnRefresh = new JButton("Actualiser");
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadMembers();
			}
		});
		btnRefresh.setBounds(620, 20, 140, 30);
		contentPane.add(btnRefresh);
		
		String[] columnNames = {"ID", "Nom", "Dernier paiement"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tableMembres = new JTable(tableModel);
		tableMembres.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tableMembres.setRowHeight(25);
		tableMembres.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		
		tableMembres.getColumnModel().getColumn(0);
		tableMembres.getColumnModel().getColumn(1);
		tableMembres.getColumnModel().getColumn(2);
		
		JScrollPane scrollPane = new JScrollPane(tableMembres);
		scrollPane.setBounds(20, 70, 740, 330);
		contentPane.add(scrollPane);
		

		JLabel lblInfo = new JLabel("Total: 0 membre(s)");
		lblInfo.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblInfo.setForeground(Color.GRAY);
		lblInfo.setBounds(20, 415, 300, 20);
		contentPane.add(lblInfo);
		
		JButton btnNotify = new JButton("Notifier les impayés");
		btnNotify.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNotify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ManageMembersFrame.this, 
					"Les membres dont les abonnements sont expirés ont été notifiés.", 
					"Notification", 
					JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNotify.setBounds(310, 410, 180, 30);
		contentPane.add(btnNotify);
		
		JButton btnClose = new JButton("Fermer");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(620, 410, 140, 30);
		contentPane.add(btnClose);
		
		loadMembers();
		updateInfoLabel(lblInfo);
		
		setLocationRelativeTo(null);
	}
	
	private void loadMembers() {
		tableModel.setRowCount(0);
		
		List<Member> members = personDAO.getAllMembersWithHistory();
		
		for (Member member : members) {
			Object[] row = {
				member.getId(),
				member.getName(),
				//member.getFirstname(),
				//member.getTel(),
				//String.format("%.2f", member.getBalance()),
				member.getLastPaymentDate()
			};
			tableModel.addRow(row);
		}
		
		updateInfoLabel(null);
		
		System.out.println("Table mise à jour avec " + members.size() + " membre(s)");
	}
	
	private void updateInfoLabel(JLabel lblInfo) {
		if (lblInfo == null) {
			for (Component comp : contentPane.getComponents()) {
				if (comp instanceof JLabel) {
					JLabel label = (JLabel) comp;
					if (label.getText().startsWith("Total:")) {
						lblInfo = label;
						break;
					}
				}
			}
		}
		if (lblInfo != null) {
			int count = tableModel.getRowCount();
			lblInfo.setText("Total: " + count + " membre(s)");
		}
	}
}