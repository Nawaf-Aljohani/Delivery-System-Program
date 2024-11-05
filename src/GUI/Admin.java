package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Label;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

public class Admin extends JFrame {

	private JPanel contentPane;
	private JTextField txtReport_package_ID;
	private JTextField txtReport_package_StartDate;
	private JTextField txtReport_package_EndDate;
	private JTextField txtEnterCustomerIdForNotification;
	private JTextField txt_customer_ID;
	private JTable tableTracePackages;
	private JTable tableShowData;

	private String error = "Somthing went wrong 404";
	/**
	 * Create the frame.
	 */

	Connection connection = null;
	private JTextField txt_package_ID;
	private JTextField txt_customer_Fname;
	private JTextField txt_customer_Mname;
	private JTextField txt_customer_Lname;
	private JTextField txt_customer_Phone;
	private JTextField txt_customer_Email;
	private JTextField txt_package_Destination;
	private JTextField txt_package_DimensionsL;
	private JTextField txt_package_Weight;
	private JTextField txt_package_RetailCenter_ID;
	private JTextField txt_package_Final_DeliveryDate;
	private JTextField txt_package_Customer_ID;
	private JTextField txtReport_package_Destination;
	private JEditorPane notificationDetials;
	private JCheckBox CBReport_package_StatusDelivered;
	private JCheckBox CBReport_package_StatusDelayed;
	private JCheckBox CBReport_package_StatusLost;
	private JCheckBox CBReport_package_StatusIntransit;

	private JCheckBox CBReport_package_TypeRegular;
	private JCheckBox CBReport_package_TypeFragile;
	private JCheckBox CBReport_package_TypeLiquid;
	private JCheckBox CBReport_package_TypeChemical;

	private JRadioButton RB_package_ShowTable;
	private JRadioButton RB_customer_ShowTable;

	private JCheckBox CBReport_package_Paid;
	private JCheckBox CBReport_package_Unpaid;


	JComboBox<String> CB_package_Status;
	JComboBox<String> CB_package_Package_Type;
	JComboBox<String> CB_package_Payment;
	JComboBox<String> CB_customer_Type;

	private JTextField txt_package_DimensionsW;
	private JTextField txt_package_DimensionsH;
	private JCheckBox CBReport_package_StatusDamaged;


	public Admin(long ID) {

		setLocationRelativeTo(null);
		setResizable(false);

		connection = sqliteConnection.dbConnector();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1123, 710);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSmaDeliverySystem_2_1 = new JLabel("SMA DELIVERY SYSTEM");
		lblSmaDeliverySystem_2_1.setForeground(Color.BLACK);
		lblSmaDeliverySystem_2_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblSmaDeliverySystem_2_1.setBounds(10, 11, 441, 57);
		contentPane.add(lblSmaDeliverySystem_2_1);

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				contentPane.setVisible(false);
				dispose();
				Main.main(null);
			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnLogout.setBounds(950, 11, 132, 57);
		contentPane.add(btnLogout);

		JPanel adminCards = new JPanel();
		adminCards.setBackground(Color.LIGHT_GRAY);
		adminCards.setBounds(20, 79, 1062, 581);
		contentPane.add(adminCards);
		adminCards.setLayout(new CardLayout(0, 0));

		JPanel mainAdminPanel = new JPanel();
		adminCards.add(mainAdminPanel, "mainAdminPanel");
		mainAdminPanel.setLayout(null);

		Label label_2 = new Label("Send a notifcation:");
		label_2.setFont(new Font("Dialog", Font.BOLD, 25));
		label_2.setBounds(10, 467, 258, 39);
		mainAdminPanel.add(label_2);

		JButton btnGoToSendNotification = new JButton("Send");
		btnGoToSendNotification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// do stuff
				PreparedStatement pst;
				String allPackages = "SELECT email FROM Person Where ID=? ";
				ResultSet result;
				try {
					pst = connection.prepareStatement(allPackages);
					pst.setString(1, txtEnterCustomerIdForNotification.getText());
					result = pst.executeQuery();
					if (result.next()) {
						Desktop desktop = Desktop.getDesktop();
						String x = notificationDetials.getText();
						String x1 = x.replaceAll("\\s", "%20");
						String message = "mailto:" + result.getString(1) + "?subject=Delivery%20Notice&body=" + x1;
						URI uri = URI.create(message);
						try {
							desktop.mail(uri);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				} catch (Exception ex) {
				}

			}

		});
		btnGoToSendNotification.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnGoToSendNotification.setBounds(938, 542, 97, 28);
		mainAdminPanel.add(btnGoToSendNotification);

		notificationDetials = new JEditorPane();
		notificationDetials.setFont(new Font("Tahoma", Font.PLAIN, 13));
		notificationDetials.setBounds(239, 512, 495, 58);
		mainAdminPanel.add(notificationDetials);

		JLabel lblNewLabel = new JLabel("Enter \r\nnotifcation \r\ndetails:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 508, 219, 58);
		mainAdminPanel.add(lblNewLabel);

		txtEnterCustomerIdForNotification = new JTextField();
		txtEnterCustomerIdForNotification.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtEnterCustomerIdForNotification.setText("Enter customer ID");
		txtEnterCustomerIdForNotification.setBounds(744, 542, 183, 28);
		mainAdminPanel.add(txtEnterCustomerIdForNotification);
		txtEnterCustomerIdForNotification.setColumns(10);

		Label label_2_1 = new Label("Send notifcation to who?");
		label_2_1.setFont(new Font("Dialog", Font.BOLD, 20));
		label_2_1.setBounds(771, 505, 244, 31);
		mainAdminPanel.add(label_2_1);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 63, 244, 207);
		mainAdminPanel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_11 = new JLabel("Enter customer ID");
		lblNewLabel_11.setBounds(10, 45, 203, 14);
		panel.add(lblNewLabel_11);

		txt_customer_ID = new JTextField();
		txt_customer_ID.setBounds(10, 70, 143, 20);
		panel.add(txt_customer_ID);
		txt_customer_ID.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Manage customer");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(50, 0, 141, 34);
		panel.add(lblNewLabel_4);

		JButton btn_Customer_Add = new JButton("Add");
		btn_Customer_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer_Insert();
			}
		});
		btn_Customer_Add.setBounds(10, 99, 224, 34);
		panel.add(btn_Customer_Add);

		JButton btn_Customer_Modify = new JButton("Modify");
		btn_Customer_Modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer_Update();
			}
		});
		btn_Customer_Modify.setBounds(10, 134, 224, 34);
		panel.add(btn_Customer_Modify);

		JButton btn_Customer_Delete = new JButton("Delete");
		btn_Customer_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer_Delete();
			}
		});
		btn_Customer_Delete.setBounds(10, 170, 224, 34);
		panel.add(btn_Customer_Delete);

		JButton btn_customer_Show = new JButton("Show");
		btn_customer_Show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customer_Select();
			}
		});
		btn_customer_Show.setBounds(154, 69, 80, 20);
		panel.add(btn_customer_Show);
		String name = "";
		try {
			String sql = "Select Fname, Lname from Person where ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, ID);

			ResultSet rs = pstmt.executeQuery();
			name = rs.getString(1) + " " + rs.getString(2);
			;
		} catch (Exception e) {

		}

		JLabel lblNewLabel_3 = new JLabel("Hello " + name);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_3.setBounds(10, 0, 326, 52);
		mainAdminPanel.add(lblNewLabel_3);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(515, 9, 537, 492);
		mainAdminPanel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lbl_Trace = new JLabel("Trace packages");
		lbl_Trace.setFont(new Font("Tahoma", Font.BOLD, 30));
		lbl_Trace.setBounds(147, 5, 248, 41);
		panel_1.add(lbl_Trace);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 517, 429);
		panel_1.add(scrollPane);

		tableTracePackages = new JTable();
		tableTracePackages.setEnabled(false);
		tableTracePackages.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));

		package_ShowAll();

		scrollPane.setViewportView(tableTracePackages);

		// Turn off JTable's auto resize so that JScrollPane will show a horizontal
		// scroll bar.
		tableTracePackages.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		RB_package_ShowTable = new JRadioButton("Packages");
		RB_package_ShowTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				package_ShowAll();
				lbl_Trace.setText("Trace packages");
				RB_customer_ShowTable.setSelected(false);
			}
		});
		RB_package_ShowTable.setSelected(true);
		RB_package_ShowTable.setBackground(Color.LIGHT_GRAY);
		RB_package_ShowTable.setBounds(10, 5, 109, 23);

		RB_customer_ShowTable = new JRadioButton("Customers");
		RB_customer_ShowTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer_ShowAll();
				lbl_Trace.setText("Trace customers");
				RB_package_ShowTable.setSelected(false);
				;
				;
			}
		});
		RB_customer_ShowTable.setBackground(Color.LIGHT_GRAY);
		RB_customer_ShowTable.setBounds(10, 26, 109, 23);

		panel_1.add(RB_package_ShowTable);
		panel_1.add(RB_customer_ShowTable);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(264, 63, 244, 207);
		mainAdminPanel.add(panel_2);

		JLabel lblNewLabel_11_1 = new JLabel("Enter package ID");
		lblNewLabel_11_1.setBounds(10, 45, 203, 14);
		panel_2.add(lblNewLabel_11_1);

		txt_package_ID = new JTextField();
		txt_package_ID.setColumns(10);
		txt_package_ID.setBounds(10, 70, 143, 20);
		panel_2.add(txt_package_ID);

		JLabel lblNewLabel_4_1 = new JLabel("Manage package");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4_1.setBounds(50, 0, 143, 34);
		panel_2.add(lblNewLabel_4_1);

		JButton btn_package_Add = new JButton("Add");
		btn_package_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				package_Insert();
			}
		});
		btn_package_Add.setBounds(10, 99, 224, 34);
		panel_2.add(btn_package_Add);

		JButton btn_package_Modify = new JButton("Modify");
		btn_package_Modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				package_Update();
			}
		});
		btn_package_Modify.setBounds(10, 134, 224, 34);
		panel_2.add(btn_package_Modify);

		JButton btn_package_Delete = new JButton("Delete");
		btn_package_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				package_Delete();
			}
		});
		btn_package_Delete.setBounds(10, 170, 224, 34);
		panel_2.add(btn_package_Delete);

		JButton btn_package_Show = new JButton("Show");
		btn_package_Show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				package_Select();

			}
		});
		btn_package_Show.setBounds(154, 70, 80, 20);
		panel_2.add(btn_package_Show);

		txt_customer_Fname = new JTextField();
		txt_customer_Fname.setBounds(62, 276, 120, 20);
		mainAdminPanel.add(txt_customer_Fname);
		txt_customer_Fname.setColumns(10);

		JLabel lblFname = new JLabel("Fname:");
		lblFname.setBounds(20, 282, 46, 14);
		mainAdminPanel.add(lblFname);

		JLabel lblMname = new JLabel("Mname:");
		lblMname.setBounds(20, 307, 46, 14);
		mainAdminPanel.add(lblMname);

		JLabel lblLname = new JLabel("Lname:");
		lblLname.setBounds(20, 332, 46, 14);
		mainAdminPanel.add(lblLname);

		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(20, 357, 46, 14);
		mainAdminPanel.add(lblPhone);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(20, 382, 46, 14);
		mainAdminPanel.add(lblEmail);

		txt_customer_Mname = new JTextField();
		txt_customer_Mname.setColumns(10);
		txt_customer_Mname.setBounds(62, 301, 120, 20);
		mainAdminPanel.add(txt_customer_Mname);

		txt_customer_Lname = new JTextField();
		txt_customer_Lname.setColumns(10);
		txt_customer_Lname.setBounds(62, 326, 120, 20);
		mainAdminPanel.add(txt_customer_Lname);

		txt_customer_Phone = new JTextField();
		txt_customer_Phone.setColumns(10);
		txt_customer_Phone.setBounds(62, 351, 120, 20);
		mainAdminPanel.add(txt_customer_Phone);

		txt_customer_Email = new JTextField();
		txt_customer_Email.setColumns(10);
		txt_customer_Email.setBounds(62, 376, 120, 20);
		mainAdminPanel.add(txt_customer_Email);

		JLabel lblFname_1 = new JLabel("Status:");
		lblFname_1.setBounds(274, 282, 103, 14);
		mainAdminPanel.add(lblFname_1);

		JLabel lblMname_1 = new JLabel("Dimensions(L,W,H):");
		lblMname_1.setBounds(274, 307, 132, 14);
		mainAdminPanel.add(lblMname_1);

		txt_package_Destination = new JTextField();
		txt_package_Destination.setColumns(10);
		txt_package_Destination.setBounds(391, 331, 120, 20);
		mainAdminPanel.add(txt_package_Destination);

		JLabel lblLname_1 = new JLabel("Destination:");
		lblLname_1.setBounds(274, 332, 109, 14);
		mainAdminPanel.add(lblLname_1);

		txt_package_DimensionsL = new JTextField();
		txt_package_DimensionsL.setColumns(10);
		txt_package_DimensionsL.setBounds(391, 306, 36, 20);
		mainAdminPanel.add(txt_package_DimensionsL);

		txt_package_Weight = new JTextField();
		txt_package_Weight.setColumns(10);
		txt_package_Weight.setBounds(391, 356, 120, 20);
		mainAdminPanel.add(txt_package_Weight);

		txt_package_RetailCenter_ID = new JTextField();
		txt_package_RetailCenter_ID.setColumns(10);
		txt_package_RetailCenter_ID.setBounds(391, 381, 120, 20);
		mainAdminPanel.add(txt_package_RetailCenter_ID);

		JLabel lblEmail_1 = new JLabel("Weight:");
		lblEmail_1.setBounds(274, 357, 95, 14);
		mainAdminPanel.add(lblEmail_1);

		JLabel lblEmail_1_1 = new JLabel("Final_DeliveryDate:");
		lblEmail_1_1.setBounds(274, 432, 109, 14);
		mainAdminPanel.add(lblEmail_1_1);

		txt_package_Final_DeliveryDate = new JTextField();
		txt_package_Final_DeliveryDate.setColumns(10);
		txt_package_Final_DeliveryDate.setBounds(391, 431, 120, 20);
		mainAdminPanel.add(txt_package_Final_DeliveryDate);

		JLabel lblPhone_1_1 = new JLabel("Customer_ID:");
		lblPhone_1_1.setBounds(274, 407, 95, 14);
		mainAdminPanel.add(lblPhone_1_1);

		JLabel lblLname_1_1 = new JLabel("RetailCenter_ID:");
		lblLname_1_1.setBounds(274, 382, 95, 14);
		mainAdminPanel.add(lblLname_1_1);

		txt_package_Customer_ID = new JTextField();
		txt_package_Customer_ID.setColumns(10);
		txt_package_Customer_ID.setBounds(391, 406, 120, 20);
		mainAdminPanel.add(txt_package_Customer_ID);

		JLabel lblEmail_1_1_1 = new JLabel("Package_Type:");
		lblEmail_1_1_1.setBounds(274, 456, 95, 14);
		mainAdminPanel.add(lblEmail_1_1_1);

		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(20, 407, 46, 14);
		mainAdminPanel.add(lblType);

		JLabel lblEmail_1_1_1_2 = new JLabel("Payment:");
		lblEmail_1_1_1_2.setBounds(274, 483, 95, 14);
		mainAdminPanel.add(lblEmail_1_1_1_2);

		CB_package_Status = new JComboBox<String>();
		CB_package_Status.setBounds(391, 278, 120, 22);
		CB_package_Status.addItem("Deleiverd");
		CB_package_Status.addItem("In transit");
		CB_package_Status.addItem("Delayed");
		CB_package_Status.addItem("Damaged");
		CB_package_Status.addItem("Lost");
		mainAdminPanel.add(CB_package_Status);

		CB_package_Package_Type = new JComboBox<String>();
		CB_package_Package_Type.setBounds(391, 454, 120, 22);
		CB_package_Package_Type.addItem("Regular");
		CB_package_Package_Type.addItem("Fragile");
		CB_package_Package_Type.addItem("Liquid");
		CB_package_Package_Type.addItem("Chemical");
		mainAdminPanel.add(CB_package_Package_Type);

		CB_package_Payment = new JComboBox<String>();
		CB_package_Payment.setBounds(391, 481, 120, 22);
		CB_package_Payment.addItem("Paid");
		CB_package_Payment.addItem("Unpaid");
		mainAdminPanel.add(CB_package_Payment);

		txt_package_DimensionsW = new JTextField();
		txt_package_DimensionsW.setColumns(10);
		txt_package_DimensionsW.setBounds(433, 306, 36, 20);
		mainAdminPanel.add(txt_package_DimensionsW);

		txt_package_DimensionsH = new JTextField();
		txt_package_DimensionsH.setColumns(10);
		txt_package_DimensionsH.setBounds(475, 306, 36, 20);
		mainAdminPanel.add(txt_package_DimensionsH);

		CB_customer_Type = new JComboBox<String>();
		CB_customer_Type.setBounds(62, 403, 120, 22);
		CB_customer_Type.addItem("Sender");
		CB_customer_Type.addItem("Reciver");
		mainAdminPanel.add(CB_customer_Type);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.BLACK);
		panel_3.setBounds(236, 509, 501, 64);
		mainAdminPanel.add(panel_3);

		JPanel reportsPanel = new JPanel();
		adminCards.add(reportsPanel, "reportsPanel");
		reportsPanel.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Find a package by ID:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(10, 8, 177, 29);
		reportsPanel.add(lblNewLabel_2);

		txtReport_package_ID = new JTextField();
		txtReport_package_ID.setColumns(10);
		txtReport_package_ID.setBounds(197, 15, 155, 20);
		reportsPanel.add(txtReport_package_ID);

		JLabel lblNewLabel_12_2 = new JLabel("Enter start date");
		lblNewLabel_12_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_12_2.setBounds(10, 114, 177, 29);
		reportsPanel.add(lblNewLabel_12_2);

		JLabel lblNewLabel_12_3 = new JLabel("Enter end date");
		lblNewLabel_12_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_12_3.setBounds(176, 112, 177, 32);
		reportsPanel.add(lblNewLabel_12_3);

		txtReport_package_StartDate = new JTextField();
		txtReport_package_StartDate.setColumns(10);
		txtReport_package_StartDate.setBounds(10, 154, 155, 20);
		reportsPanel.add(txtReport_package_StartDate);

		txtReport_package_EndDate = new JTextField();
		txtReport_package_EndDate.setColumns(10);
		txtReport_package_EndDate.setBounds(176, 154, 155, 20);
		reportsPanel.add(txtReport_package_EndDate);

		JButton btnShowPackageStatus = new JButton("Show");
		btnShowPackageStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showReport();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnShowPackageStatus.setBounds(20, 434, 311, 23);
		reportsPanel.add(btnShowPackageStatus);

		JButton btnGoToAdminPanel = new JButton("Back to admin panel");
		btnGoToAdminPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) adminCards.getLayout();
				ca.show(adminCards, "mainAdminPanel");
			}
		});
		btnGoToAdminPanel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnGoToAdminPanel.setBounds(10, 494, 300, 54);
		reportsPanel.add(btnGoToAdminPanel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(555, 11, 497, 537);
		reportsPanel.add(scrollPane_1);

		tableShowData = new JTable();
		tableShowData.setEnabled(false);
		tableShowData.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));

		// Turn off JTable's auto resize so that JScrollPane will show a horizontal
		// scroll bar.
		tableShowData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_1.setViewportView(tableShowData);

		JLabel lblFname_1_1 = new JLabel("Status:");
		lblFname_1_1.setBounds(20, 191, 95, 14);
		reportsPanel.add(lblFname_1_1);

		JLabel lblLname_1_2 = new JLabel("Destination:");
		lblLname_1_2.setBounds(20, 262, 95, 14);
		reportsPanel.add(lblLname_1_2);

		txtReport_package_Destination = new JTextField();
		txtReport_package_Destination.setColumns(10);
		txtReport_package_Destination.setBounds(82, 259, 125, 20);
		reportsPanel.add(txtReport_package_Destination);

		JLabel lblEmail_1_1_1_1 = new JLabel("Package_Type:");
		lblEmail_1_1_1_1.setBounds(20, 308, 95, 14);
		reportsPanel.add(lblEmail_1_1_1_1);

		CBReport_package_StatusDelivered = new JCheckBox("Delivered");
		CBReport_package_StatusDelivered.setBounds(30, 212, 80, 23);
		reportsPanel.add(CBReport_package_StatusDelivered);

		CBReport_package_StatusDelayed = new JCheckBox("Delayed");
		CBReport_package_StatusDelayed.setBounds(180, 212, 73, 23);
		reportsPanel.add(CBReport_package_StatusDelayed);

		CBReport_package_StatusLost = new JCheckBox("Lost");
		CBReport_package_StatusLost.setBounds(330, 212, 80, 23);
		reportsPanel.add(CBReport_package_StatusLost);

		CBReport_package_TypeRegular = new JCheckBox("Regular");
		CBReport_package_TypeRegular.setBounds(30, 334, 80, 23);
		reportsPanel.add(CBReport_package_TypeRegular);

		CBReport_package_TypeFragile = new JCheckBox("Fragile");
		CBReport_package_TypeFragile.setBounds(112, 334, 80, 23);
		reportsPanel.add(CBReport_package_TypeFragile);

		CBReport_package_TypeLiquid = new JCheckBox("Liquid");
		CBReport_package_TypeLiquid.setBounds(193, 334, 80, 23);
		reportsPanel.add(CBReport_package_TypeLiquid);

		CBReport_package_TypeChemical = new JCheckBox("Chemical");
		CBReport_package_TypeChemical.setBounds(272, 334, 80, 23);
		reportsPanel.add(CBReport_package_TypeChemical);

		CBReport_package_StatusIntransit = new JCheckBox("In transit");
		CBReport_package_StatusIntransit.setBounds(107, 212, 73, 23);
		reportsPanel.add(CBReport_package_StatusIntransit);

		JLabel lblEmail_1_1_1_1_1 = new JLabel("Payment:");
		lblEmail_1_1_1_1_1.setBounds(20, 375, 95, 14);
		reportsPanel.add(lblEmail_1_1_1_1_1);

		CBReport_package_Paid = new JCheckBox("Paid");
		CBReport_package_Paid.setBounds(30, 396, 80, 23);
		reportsPanel.add(CBReport_package_Paid);

		CBReport_package_Unpaid = new JCheckBox("Unpaid");
		CBReport_package_Unpaid.setBounds(112, 396, 80, 23);
		reportsPanel.add(CBReport_package_Unpaid);

		CBReport_package_StatusDamaged = new JCheckBox("Damaged");
		CBReport_package_StatusDamaged.setBounds(250, 212, 80, 23);
		reportsPanel.add(CBReport_package_StatusDamaged);

		JButton btnGoToGenerateReports = new JButton("Generate reports");
		btnGoToGenerateReports.setBounds(734, 11, 206, 57);
		contentPane.add(btnGoToGenerateReports);
		btnGoToGenerateReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) adminCards.getLayout();
				ca.show(adminCards, "reportsPanel");
			}
		});
		btnGoToGenerateReports.setFont(new Font("Tahoma", Font.PLAIN, 20));


		JButton btnSendIn = new JButton("Send Insurance");
		btnSendIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendInsuranceINFO();
			}
		});
		btnSendIn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSendIn.setBounds(518, 11, 206, 57);
		contentPane.add(btnSendIn);
	}

	private void sendInsuranceINFO() {

		PreparedStatement pst;
		String allPackages = "SELECT Person.Email, Person.Fname, Person.Lname, Package.Weight, Package.Status, Package.Package_ID FROM Person NATURAL JOIN Package Where (Status = 'Lost') OR (Status ='Damaged') OR (Status = 'Delayed')";
		ResultSet result;
		try {
			pst = connection.prepareStatement(allPackages); 
			result = pst.executeQuery();
			while (result.next()) {
				Desktop desktop = Desktop.getDesktop();
				String x = "Hi " + result.getString(2) + " " + result.getString(3) + "."
						+ "\nWe are Sorry for the " + result.getString(5) + " of your package number " + result.getString(6) + "."
						+ "\nWe will give you an insurance of " + (Float.parseFloat(result.getString(4)) * 10) + "SAR " ;
				String x1 = x.replaceAll("\\s", "%20");
				String message = "mailto:" + result.getString(1) + "?subject=Delivery%20Notice&body=" + x1;
				URI uri = URI.create(message);
				try {
					desktop.mail(uri);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} catch (Exception ex) {
			System.out.print(ex);
		}
	}

	//////////////////////////////////
	// Database //
	//////////////////////////////////


	private void package_Format() throws Exception {

		txt_package_ID.setBackground(Color.RED);
		error = "The ID should be 12 length";
		if( txt_package_ID.getText().length() != 12){
			throw new SQLException();
		}
		txt_package_ID.setBackground(Color.WHITE);



		txt_package_Weight.setBackground(Color.RED);
		error = "The weight should be between 0 and 100";
		if((Float.parseFloat(txt_package_Weight.getText()) <= 0.0)  
				|| (Float.parseFloat(txt_package_Weight.getText()) >= 100.0)){
			throw new SQLException();
		}
		txt_package_Weight.setBackground(Color.WHITE);



		txt_package_DimensionsL.setBackground(Color.RED);
		error = "The lenght should be between 0 and 240";
		if((Float.parseFloat(txt_package_DimensionsL.getText()) <= 0.0)  
				|| (Float.parseFloat(txt_package_DimensionsL.getText()) >= 240.0)){
			throw new SQLException();
		}
		txt_package_DimensionsL.setBackground(Color.WHITE);

		txt_package_DimensionsW.setBackground(Color.RED);
		error = "The width should be between 0 and 240";
		if((Float.parseFloat(txt_package_DimensionsW.getText()) <= 0.0)  
				|| (Float.parseFloat(txt_package_DimensionsW.getText()) >= 120.0)){
			throw new SQLException();
		}
		txt_package_DimensionsW.setBackground(Color.WHITE);

		txt_package_DimensionsH.setBackground(Color.RED);
		error = "The height should be between 0 and 240";
		if((Float.parseFloat(txt_package_DimensionsH.getText()) <= 0.0)  
				|| (Float.parseFloat(txt_package_DimensionsH.getText()) >= 220.0)){
			throw new SQLException();
		}
		txt_package_DimensionsH.setBackground(Color.WHITE);
	}

	private void customer_Format() throws Exception {

		txt_customer_ID.setBackground(Color.RED);
		error = "The ID should be 12 length";
		if( txt_customer_ID.getText().length() != 12){
			throw new SQLException();
		}
		txt_customer_ID.setBackground(Color.WHITE);

	}

	private void package_Insert() {
		try {
			package_Format();
			String sql = "INSERT INTO Package VALUES(?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, txt_package_ID.getText());
			pstmt.setString(2, CB_package_Status.getSelectedItem().toString());

			String length = txt_package_DimensionsL.getText();
			String width = txt_package_DimensionsW.getText();
			String height = txt_package_DimensionsH.getText();
			String dimensions=length+"Lx"+width+"Wx"+height+"H";

			pstmt.setString(3, dimensions);

			pstmt.setString(4, txt_package_Destination.getText());
			pstmt.setString(5, txt_package_Weight.getText());
			pstmt.setString(6, txt_package_RetailCenter_ID.getText());
			pstmt.setString(7, txt_package_Customer_ID.getText());
			pstmt.setString(8, txt_package_Final_DeliveryDate.getText());
			pstmt.setString(9, CB_package_Package_Type.getSelectedItem().toString());
			pstmt.setString(10, CB_package_Payment.getSelectedItem().toString());


			pstmt.executeUpdate();
		}catch(SQLException e) {
		JOptionPane.showMessageDialog(null, "Somthing went wrong 404");
		} catch(Exception e) {
		JOptionPane.showMessageDialog(null, error);
	}
	if (RB_package_ShowTable.isSelected()) {
		package_ShowAll();
	}
}

private void package_Update() {
	try {
		package_Format();
		String sql = "UPDATE Package set Status = ?, Dimensions = ?, Destination = ?, Weight = ?, RetailCenter_ID = ?, Customer_ID = ?, Final_DeliveryDate = ?, Package_Type = ?, Payment = ? Where Package_ID = ?";

		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, CB_package_Status.getSelectedItem().toString());

		String length = txt_package_DimensionsL.getText();
		String width = txt_package_DimensionsW.getText();
		String height = txt_package_DimensionsH.getText();
		String dimensions=length+"Lx"+width+"Wx"+height+"H";

		pstmt.setString(2, dimensions);

		pstmt.setString(3, txt_package_Destination.getText());

		pstmt.setString(4, txt_package_Weight.getText());
		pstmt.setString(5, txt_package_RetailCenter_ID.getText());
		pstmt.setString(6, txt_package_Customer_ID.getText());
		pstmt.setString(7, txt_package_Final_DeliveryDate.getText());
		pstmt.setString(8, CB_package_Package_Type.getSelectedItem().toString());
		pstmt.setString(9, CB_package_Payment.getSelectedItem().toString());
		pstmt.setString(10, txt_package_ID.getText());


		pstmt.executeUpdate();
	}catch(SQLException e) {
		JOptionPane.showMessageDialog(null, "Somthing went wrong 404");
	} catch(Exception e) {
		JOptionPane.showMessageDialog(null, error);
	}
	if (RB_package_ShowTable.isSelected()) {
		package_ShowAll();
	}
}

private void package_Delete() {
	try {
		String sql = "DELETE FROM Package Where Package_ID = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setLong(1, Long.parseLong(txt_package_ID.getText()));
		pstmt.executeUpdate();

		CB_package_Status.setSelectedIndex(0);
		txt_package_Destination.setText("");

		txt_package_DimensionsL.setText("");
		txt_package_DimensionsW.setText("");
		txt_package_DimensionsH.setText("");

		txt_package_Weight.setText("");
		txt_package_RetailCenter_ID.setText("");
		txt_package_Customer_ID.setText("");
		txt_package_Final_DeliveryDate.setText("");
		CB_package_Package_Type.setSelectedIndex(0);
		CB_package_Payment.setSelectedIndex(0);

	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
	if (RB_package_ShowTable.isSelected()) {
		package_ShowAll();
	}
}

private void package_ShowAll() {
	try {
		tableTracePackages.setModel(new DefaultTableModel());
		String sql = "Select * from Package";
		PreparedStatement pstmt = connection.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		DefaultTableModel model = (DefaultTableModel) tableTracePackages.getModel();

		int cols = rsmd.getColumnCount();
		String[] colName = new String[cols];
		for (int i = 0; i < cols; i++) {
			colName[i] = rsmd.getColumnName(i + 1);
			model.setColumnIdentifiers(colName);
		}

		tableTracePackages.getColumnModel().getColumn(0).setPreferredWidth(90);
		tableTracePackages.getColumnModel().getColumn(1).setPreferredWidth(60);
		tableTracePackages.getColumnModel().getColumn(2).setPreferredWidth(110);
		tableTracePackages.getColumnModel().getColumn(3).setPreferredWidth(80);
		tableTracePackages.getColumnModel().getColumn(4).setPreferredWidth(60);
		tableTracePackages.getColumnModel().getColumn(5).setPreferredWidth(95);
		tableTracePackages.getColumnModel().getColumn(6).setPreferredWidth(130);
		tableTracePackages.getColumnModel().getColumn(7).setPreferredWidth(120);
		tableTracePackages.getColumnModel().getColumn(8).setPreferredWidth(100);
		tableTracePackages.getColumnModel().getColumn(9).setPreferredWidth(100);

		while (rs.next()) {
			String[] row = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10) };
			model.addRow(row);
		}
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
}

private void package_Select() {
	String errorLocal = "Somthing went wrong 404";
	try {

		txt_package_ID.setBackground(Color.RED);
		errorLocal = "The ID should be 12 length";
		if( txt_package_ID.getText().length() != 12){
			throw new SQLException();
		}
		txt_package_ID.setBackground(Color.WHITE);

		String sql = "Select * from Package where Package_ID = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);

		pstmt.setString(1, txt_package_ID.getText());
		ResultSet rs = pstmt.executeQuery();

		int index = 0;
		for(int i=0; i<CB_package_Status.getItemCount(); i++) {
			if (CB_package_Status.getItemAt(i).toString().equalsIgnoreCase(rs.getString(2))){
				index = i;
			}
		}
		CB_package_Status.setSelectedIndex(index);

		String[] d = rs.getString(3).split("x");
		for(int i=0; i< d.length; i++) {
			d[i] = d[i].substring(0, d[i].length()-1);
		}
		txt_package_DimensionsL.setText(d[0]);
		txt_package_DimensionsW.setText(d[1]);
		txt_package_DimensionsH.setText(d[2]);



		txt_package_Destination.setText(rs.getString(4));
		txt_package_Weight.setText(rs.getString(5));
		txt_package_RetailCenter_ID.setText(rs.getString(6));
		txt_package_Customer_ID.setText(rs.getString(7));
		txt_package_Final_DeliveryDate.setText(rs.getString(8));

		index = 0;
		for(int i=0; i<CB_package_Package_Type.getItemCount(); i++) {
			if (CB_package_Package_Type.getItemAt(i).toString().equalsIgnoreCase(rs.getString(9))){
				index = i;
			}
		}
		CB_package_Package_Type.setSelectedIndex(index);

		index = 0;
		for(int i=0; i<CB_package_Payment.getItemCount(); i++) {
			if (CB_package_Payment.getItemAt(i).toString().equalsIgnoreCase(rs.getString(10))){
				index = i;
			}
		}
		CB_package_Payment.setSelectedIndex(index);



	} catch (SQLException e) {
		JOptionPane.showMessageDialog(null, errorLocal);		
	}
}

private void customer_ShowAll() {
	try {
		tableTracePackages.setModel(new DefaultTableModel());
		String sql = "Select ID, Fname, Mname, Lname, Phone_Number, Email, Customer_Type from Person NATURAL JOIN Customer";

		PreparedStatement pstmt = connection.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();

		ResultSetMetaData rsmd = rs.getMetaData();

		DefaultTableModel model = (DefaultTableModel) tableTracePackages.getModel();

		int cols = rsmd.getColumnCount() ;
		String[] colName = new String[cols];
		for (int i = 0; i < cols; i++) {
			colName[i] = rsmd.getColumnName(i + 1);
			model.setColumnIdentifiers(colName);				
		}

		tableTracePackages.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableTracePackages.getColumnModel().getColumn(1).setPreferredWidth(80);
		tableTracePackages.getColumnModel().getColumn(2).setPreferredWidth(80);
		tableTracePackages.getColumnModel().getColumn(3).setPreferredWidth(80);
		tableTracePackages.getColumnModel().getColumn(4).setPreferredWidth(100);
		tableTracePackages.getColumnModel().getColumn(5).setPreferredWidth(170);
		tableTracePackages.getColumnModel().getColumn(6).setPreferredWidth(120);

		while (rs.next()) {
			String[] row = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7) };
			model.addRow(row);
		}
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
}

private void customer_Insert(){
	try {			
		customer_Format();
		String sqlPerson = "INSERT INTO Person VALUES(?,?,?,?,?,?)";
		String sqlCustomer = "INSERT INTO Customer VALUES(?,?)";

		PreparedStatement pstmtPerson = connection.prepareStatement(sqlPerson);
		PreparedStatement pstmtCustomer = connection.prepareStatement(sqlCustomer);

		pstmtPerson.setString(1, txt_customer_ID.getText());
		pstmtPerson.setString(2, txt_customer_Fname.getText());
		pstmtPerson.setString(3, txt_customer_Mname.getText());
		pstmtPerson.setString(4, txt_customer_Lname.getText());
		pstmtPerson.setString(5, txt_customer_Phone.getText());
		pstmtPerson.setString(6, txt_customer_Email.getText());

		pstmtCustomer.setString(1, txt_customer_ID.getText());
		pstmtCustomer.setString(2, CB_customer_Type.getSelectedItem().toString());

		pstmtPerson.executeUpdate();
		pstmtCustomer.executeUpdate();

	}catch(SQLException e) {
		JOptionPane.showMessageDialog(null, "Somthing went wrong 404");
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, error);
	}

	if (RB_customer_ShowTable.isSelected()) {
		customer_ShowAll();
	}
}

private void customer_Update() {
	try {
		customer_Format();
		String sqlPerson = "UPDATE Person set Fname = ?, Mname = ?, Lname = ?, Phone_Number = ?, Email = ? Where ID = ?";
		String sqlCustomer = "UPDATE Customer set Customer_Type = ? Where ID = ?";

		PreparedStatement pstmtPerson = connection.prepareStatement(sqlPerson);
		PreparedStatement pstmtCustomer = connection.prepareStatement(sqlCustomer);

		pstmtPerson.setString(1, txt_customer_Fname.getText());
		pstmtPerson.setString(2, txt_customer_Mname.getText());
		pstmtPerson.setString(3, txt_customer_Lname.getText());
		pstmtPerson.setString(4, txt_customer_Phone.getText());
		pstmtPerson.setString(5, txt_customer_Email.getText());
		pstmtPerson.setString(6, txt_customer_ID.getText());

		pstmtCustomer.setString(1, CB_customer_Type.getSelectedItem().toString());
		pstmtCustomer.setString(2, txt_customer_ID.getText());

		pstmtPerson.executeUpdate();
		pstmtCustomer.executeUpdate();

	}catch(SQLException e) {
		JOptionPane.showMessageDialog(null, "Somthing went wrong 404");
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, error);
	}

	if (RB_customer_ShowTable.isSelected()) {
		customer_ShowAll();
	}
}

private void customer_Delete() {
	try {
		String sqlCustomer = "DELETE FROM Customer Where ID = ?";
		String sqlPerson = "DELETE FROM Person Where ID = ?";
		PreparedStatement pstmtCustomer = connection.prepareStatement(sqlCustomer);
		PreparedStatement pstmtPerson = connection.prepareStatement(sqlPerson);

		pstmtCustomer.setString(1, txt_customer_ID.getText());
		pstmtPerson.setString(1, txt_customer_ID.getText());

		pstmtCustomer.executeUpdate();
		pstmtPerson.executeUpdate();

		txt_customer_Fname.setText("");
		txt_customer_Mname.setText("");
		txt_customer_Lname.setText("");
		txt_customer_Phone.setText("");
		txt_customer_Email.setText("");
		CB_customer_Type.setSelectedIndex(0);

	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}

	if (RB_customer_ShowTable.isSelected()) {
		customer_ShowAll();
	}
}

private void Customer_Select() {
	try {
		String sqlPerson = "Select * from Person where ID = ?";
		String sqlCustomer = "Select * from Customer where ID = ?";
		PreparedStatement pstmtPerson = connection.prepareStatement(sqlPerson);
		PreparedStatement pstmtCustomer = connection.prepareStatement(sqlCustomer);

		pstmtPerson.setString(1, txt_customer_ID.getText());
		pstmtCustomer.setString(1, txt_customer_ID.getText());

		ResultSet rsPerson = pstmtPerson.executeQuery();
		ResultSet rsCustomer = pstmtCustomer.executeQuery();


		txt_customer_Fname.setText(rsPerson.getString(2));
		txt_customer_Mname.setText(rsPerson.getString(3));
		txt_customer_Lname.setText(rsPerson.getString(4));
		txt_customer_Phone.setText(rsPerson.getString(5));
		txt_customer_Email.setText(rsPerson.getString(6));

		int index = 0;
		for(int i=0; i<CB_customer_Type.getItemCount(); i++) {
			if (CB_customer_Type.getItemAt(i).toString().equalsIgnoreCase(rsCustomer.getString(2))){
				index = i;
			}
		}
		CB_customer_Type.setSelectedIndex(index);

	} catch (SQLException e) {

	}
}

private void showReport() throws SQLException {
	tableShowData.setModel(new DefaultTableModel());
	String sql = "Select * From Package";

	String ID = "";
	String status = "";
	String type = "";
	String Destination = "";
	String date = "";
	String endDate = "";
	String payment = "";

	if (!txtReport_package_ID.getText().equalsIgnoreCase("")) {
		ID = " Where Package_ID = '" + txtReport_package_ID.getText() + "'";
	}

	if (CBReport_package_StatusDelivered.isSelected()) {
		if (ID.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
			status = status + " Where Status = '" + CBReport_package_StatusDelivered.getText() + "'";
		} else {
			status = status + " or Status = '" + CBReport_package_StatusDelivered.getText() + "'";
		}
	}

	if (CBReport_package_StatusIntransit.isSelected()) {
		if (ID.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
			status = status + " Where Status = '" + CBReport_package_StatusIntransit.getText() + "'";
		} else {
			status = status + " or Status = '" + CBReport_package_StatusIntransit.getText() + "'";
		}
	}

	if (CBReport_package_StatusDelayed.isSelected()) {
		if (ID.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
			status = status + " Where Status = '" + CBReport_package_StatusDelayed.getText() + "'";
		} else {
			status = status + " or Status = '" + CBReport_package_StatusDelayed.getText() + "'";
		}
	}
	if (CBReport_package_StatusLost.isSelected()) {
		if (ID.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
			status = status + " Where Status = '" + CBReport_package_StatusLost.getText() + "'";
		} else {
			status = status + " or Status = '" + CBReport_package_StatusLost.getText() + "'";
		}
	}
	if (CBReport_package_StatusDamaged.isSelected()) {
		if (ID.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
			status = status + " Where Status = '" + CBReport_package_StatusDamaged.getText() + "'";
		} else {
			status = status + " or Status = '" + CBReport_package_StatusDamaged.getText() + "'";
		}
	}

	if (CBReport_package_TypeRegular.isSelected()) {
		if (ID.equalsIgnoreCase("") && type.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
			type = type + " Where Package_Type = '" + CBReport_package_TypeRegular.getText() + "'";
		} else {
			type = type + " or Package_Type = '" + CBReport_package_TypeRegular.getText() + "'";
		}
	}
	if (CBReport_package_TypeFragile.isSelected()) {
		if (ID.equalsIgnoreCase("") && type.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
			type = type + " Where Package_Type = '" + CBReport_package_TypeFragile.getText() + "'";
		} else {
			type = type + " or Package_Type = '" + CBReport_package_TypeFragile.getText() + "'";
		}
	}
	if (CBReport_package_TypeLiquid.isSelected()) {
		if (ID.equalsIgnoreCase("") && type.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
			type = type + " Where Package_Type = '" + CBReport_package_TypeLiquid.getText() + "'";
		} else {
			type = type + " or Package_Type = '" + CBReport_package_TypeLiquid.getText() + "'";
		}
	}
	if (CBReport_package_TypeChemical.isSelected()) {
		if (ID.equalsIgnoreCase("") && type.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
			type = type + " Where Package_Type = '" + CBReport_package_TypeChemical.getText() + "'";
		} else {
			type = type + " or Package_Type = '" + CBReport_package_TypeChemical.getText() + "'";
		}
	}

	if (ID.equalsIgnoreCase("") && type.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
		if (!txtReport_package_Destination.getText().equalsIgnoreCase("")) {
			Destination = " Where Destination = '" + txtReport_package_Destination.getText() + "'";
		}
	} else {
		if (!txtReport_package_Destination.getText().equalsIgnoreCase("")) {
			Destination = " or Destination = '" + txtReport_package_Destination.getText() + "'";
		}
	}

	if (ID.equalsIgnoreCase("") && txtReport_package_Destination.getText().equalsIgnoreCase("")
			&& type.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
		if (!txtReport_package_StartDate.getText().equalsIgnoreCase("")
				&& txtReport_package_EndDate.getText().equalsIgnoreCase("")) {
			date = " Where Final_DeliveryDate >= '" + txtReport_package_StartDate.getText() + "'";
		}
	} else {
		if (!txtReport_package_StartDate.getText().equalsIgnoreCase("")
				&& txtReport_package_EndDate.getText().equalsIgnoreCase("")) {
			date = " or Final_DeliveryDate >= '" + txtReport_package_StartDate.getText() + "'";
		}
	}

	if (ID.equalsIgnoreCase("") && txtReport_package_Destination.getText().equalsIgnoreCase("")
			&& type.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
		if (txtReport_package_StartDate.getText().equalsIgnoreCase("")
				&& !txtReport_package_EndDate.getText().equalsIgnoreCase("")) {
			date = " Where Final_DeliveryDate <= '" + txtReport_package_EndDate.getText() + "'";
		}
	} else {
		if (txtReport_package_StartDate.getText().equalsIgnoreCase("")
				&& !txtReport_package_EndDate.getText().equalsIgnoreCase("")) {
			date = " or Final_DeliveryDate <= '" + txtReport_package_EndDate.getText() + "'";
		}
	}

	if (ID.equalsIgnoreCase("") && txtReport_package_Destination.getText().equalsIgnoreCase("")
			&& type.equalsIgnoreCase("") && status.equalsIgnoreCase("")) {
		if (!txtReport_package_StartDate.getText().equalsIgnoreCase("")
				&& !txtReport_package_EndDate.getText().equalsIgnoreCase("")) {
			date = " Where Final_DeliveryDate BETWEEN '" + txtReport_package_StartDate.getText() + "' AND '"
					+ txtReport_package_EndDate.getText() + "'";
		}
	} else {
		if (!txtReport_package_StartDate.getText().equalsIgnoreCase("")
				&& !txtReport_package_EndDate.getText().equalsIgnoreCase("")) {
			date = " or Final_DeliveryDate BETWEEN '" + txtReport_package_StartDate.getText() + "' AND '"
					+ txtReport_package_EndDate.getText() + "'";
		}
	}

	if (CBReport_package_Paid.isSelected()) {
		if (date.equalsIgnoreCase("") && ID.equalsIgnoreCase("")
				&& txtReport_package_Destination.getText().equalsIgnoreCase("") && type.equalsIgnoreCase("")
				&& status.equalsIgnoreCase("")) {
			status = status + " Where Payment = '" + CBReport_package_Paid.getText() + "'";
		} else {
			status = status + " or Payment = '" + CBReport_package_Paid.getText() + "'";
		}
	}

	if (CBReport_package_Unpaid.isSelected()) {
		if (date.equalsIgnoreCase("") && ID.equalsIgnoreCase("")
				&& txtReport_package_Destination.getText().equalsIgnoreCase("") && type.equalsIgnoreCase("")
				&& status.equalsIgnoreCase("")) {
			payment = payment + " Where Payment = '" + CBReport_package_Unpaid.getText() + "'";
		} else {
			payment = payment + " or Payment = '" + CBReport_package_Unpaid.getText() + "'";
		}
	}

	try {
		sql = sql + ID + status + type + Destination + date + endDate + payment;

		PreparedStatement pstmt = connection.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		DefaultTableModel model = (DefaultTableModel) tableShowData.getModel();

		int cols = rsmd.getColumnCount();
		String[] colName = new String[cols];
		for (int i = 0; i < cols; i++) {
			colName[i] = rsmd.getColumnName(i + 1);
			model.setColumnIdentifiers(colName);
		}

		tableShowData.getColumnModel().getColumn(0).setPreferredWidth(90);
		tableShowData.getColumnModel().getColumn(1).setPreferredWidth(60);
		tableShowData.getColumnModel().getColumn(2).setPreferredWidth(110);
		tableShowData.getColumnModel().getColumn(3).setPreferredWidth(80);
		tableShowData.getColumnModel().getColumn(4).setPreferredWidth(60);
		tableShowData.getColumnModel().getColumn(5).setPreferredWidth(95);
		tableShowData.getColumnModel().getColumn(6).setPreferredWidth(130);
		tableShowData.getColumnModel().getColumn(7).setPreferredWidth(120);
		tableShowData.getColumnModel().getColumn(8).setPreferredWidth(100);
		tableShowData.getColumnModel().getColumn(9).setPreferredWidth(100);

		while (rs.next()) {
			String[] row = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10) };
			model.addRow(row);
		}
	} catch (SQLException e) {
	}
}
}
