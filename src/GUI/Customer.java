package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.regex.Pattern;

public class Customer extends JFrame {
	private ArrayList<String> list;
	private JPanel contentPane;
	private JTextField txtReceiverPackageLength;
	private JTextField txtReceiverPackageWeight;
	private JTextField txtCardholderName;
	private JTextField txtCardNumber;
	private JTextField txtExpireDate;
	private JTextField txtCCV;
	private JTextField txtRecieverID;
	private String id;
	private JList<String> listOfAddresses;
	static JComboBox<String> comboBoxAddress;
	static JComboBox<String> comboBoxPackageType;
	static JComboBox<String> comboBoxCustomerPackages;
	static JComboBox<String> comboBoxSearchingMethod;
	static JTable tableTraceMyPackages;
	static JPanel customerCards;
	static JScrollPane scrollPane;
	static Label lblName;
	static String anotherPackage = "";
	static String anotherPackageValidate = "";
	static JTable tableAdvancedSearch;
	static JTextField txtSearchConstraints;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	Connection connection = null;
	private JTextField txtProfileFirstName;
	private JTextField txtProfileMiddleName;
	private JTextField txtProfilePhoneNumber;
	private JTextField txtnewAddress;
	private JTextField textFieldLastName;
	private JTextField textFieldEmailx;
	private JTextField txtReceiverPackageWidth;
	private JTextField txtReceiverPackageHeight;

	public Customer(String email) {

		connection = sqliteConnection.dbConnector();

//
		id = getCustomerID(email);
		showUserName(email);

		// getting user information
		String profilesql = "SELECT ID,Fname, Mname, Lname, Phone_Number FROM Person WHERE email=?";
		ResultSet result;
		PreparedStatement pst;
		String FnameOfCustomer = null;
		String MnameOfCustomer = null;
		String LnameOfCustomer = null;
		String PhoneOfCustomer = null;
		String EmailOfCustomer = null;
		try {
			pst = connection.prepareStatement(profilesql);
			pst.setString(1, email);
			result = pst.executeQuery();
			id = result.getString("ID");
			FnameOfCustomer = result.getString("Fname");
			MnameOfCustomer = result.getString("Mname");
			LnameOfCustomer = result.getString("Lname");
			PhoneOfCustomer = result.getString("Phone_Number");
			EmailOfCustomer = email;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 845, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null); // so that the frame can be on the center of the screen
		setResizable(false); // so that the frame cannot be full screen or resized

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(695, 11, 124, 45);
		contentPane.add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				Main.main(null);

			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 25));

		JLabel lblSmaDeliverySystem_2 = new JLabel("SMA DELIVERY SYSTEM");
		lblSmaDeliverySystem_2.setBounds(10, 11, 441, 57);
		contentPane.add(lblSmaDeliverySystem_2);
		lblSmaDeliverySystem_2.setForeground(Color.BLACK);
		lblSmaDeliverySystem_2.setFont(new Font("Tahoma", Font.BOLD, 30));

		customerCards = new JPanel();
		customerCards.setBounds(12, 67, 807, 483);
		contentPane.add(customerCards);
		customerCards.setLayout(new CardLayout(0, 0));

		// -------------------------------- start of main customer panel
		// --------------------------------
		JPanel mainCustomerPanel = new JPanel();
		mainCustomerPanel.setLayout(null);
		mainCustomerPanel.setBackground(Color.LIGHT_GRAY);
		customerCards.add(mainCustomerPanel, "mainCustomerPanel");

		JLabel lblNewLabel_3 = new JLabel("Track my packages");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_3.setBounds(409, 10, 283, 42);
		mainCustomerPanel.add(lblNewLabel_3);

		JButton btnGoToUpdateProfile = new JButton("Create/Update profile");
		btnGoToUpdateProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) customerCards.getLayout();
				ca.show(customerCards, "updateProfilePanel");

				DefaultListModel<String> dlm1 = new DefaultListModel<String>();
				// person addresses
				String addressList = "SELECT Person_Address FROM Person_Address WHERE ID=?";
				ResultSet setList;

				PreparedStatement pstList;
				try {
					pstList = connection.prepareStatement(addressList);
					pstList.setString(1, id);
					setList = pstList.executeQuery();
					while (setList.next()) {
						dlm1.addElement(setList.getString("Person_Address"));
					}
				} catch (Exception e1) {
					System.out.print(e1);
				}
				listOfAddresses.setModel(dlm1);

			}
		});
		btnGoToUpdateProfile.setBounds(10, 67, 193, 23);
		mainCustomerPanel.add(btnGoToUpdateProfile);

		JLabel lblNewLabel = new JLabel("Traceback package locations");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 188, 238, 23);
		mainCustomerPanel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Send a new package");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) customerCards.getLayout();
				ca.show(customerCards, "sendPackagePanel");
			}
		});
		btnNewButton.setBounds(10, 421, 181, 51);
		mainCustomerPanel.add(btnNewButton);

		lblName = new Label(showUserName(email));

		lblName.setFont(new Font("Dialog", Font.PLAIN, 28));
		lblName.setBounds(10, 10, 393, 51);
		mainCustomerPanel.add(lblName);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(258, 63, 539, 409);
		mainCustomerPanel.add(scrollPane);

		tableTraceMyPackages = new JTable();
		tableTraceMyPackages.setEnabled(false);
		tableTraceMyPackages.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));

		// Turn off JTable's auto resize so that JScrollPane will show a horizontal
		// scroll bar.
		tableTraceMyPackages.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tableTraceMyPackages);

		JButton btnNewButton_2 = new JButton("Traceback package");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String value = (String) comboBoxCustomerPackages.getSelectedItem();
				showPackageHistory(value);
				// insertIntoPackagesComboBox(id);

			}
		});
		btnNewButton_2.setBounds(10, 242, 150, 23);
		mainCustomerPanel.add(btnNewButton_2);

		JButton btnNewButton_4 = new JButton("Show all packages");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAllPackagesInTable(id);
				insertIntoPackagesComboBox(id);
			}
		});
		btnNewButton_4.setBounds(10, 135, 150, 32);
		mainCustomerPanel.add(btnNewButton_4);

		comboBoxCustomerPackages = new JComboBox<String>();
		comboBoxCustomerPackages.setBounds(10, 211, 150, 22);
		insertIntoPackagesComboBox(id);
		mainCustomerPanel.add(comboBoxCustomerPackages);

		JButton btnNewButton_5 = new JButton("pay for package");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				anotherPackage = (String) comboBoxCustomerPackages.getSelectedItem();
				anotherPackageValidate = anotherPackage;
				if (checkIfPackageIsPaid(anotherPackageValidate) == true) {
					JOptionPane.showMessageDialog(null, "Package is already paid");
				} else {
					CardLayout ca = (CardLayout) customerCards.getLayout();
					ca.show(customerCards, "paymentPanel");
				}
			}
		});
		btnNewButton_5.setBounds(10, 277, 134, 23);
		mainCustomerPanel.add(btnNewButton_5);

		JButton btnNewButton_7 = new JButton("Advanced Search");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) customerCards.getLayout();
				ca.show(customerCards, "searchForPackageInfoPanel");
			}
		});
		btnNewButton_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_7.setBounds(10, 346, 193, 23);
		mainCustomerPanel.add(btnNewButton_7);

		JButton button = new JButton("New button");
		button.setBounds(269, 11, 89, 23);
		mainCustomerPanel.add(button);

		// comboBoxCustomerPackages = new JComboBox<String>();
		// comboBoxCustomerPackages.setBounds(10, 290, 150, 22);
		// mainCustomerPanel.add(comboBoxCustomerPackages);
		// -------------------------------- end of main customer panel
		// --------------------------------

		// -------------------------------- start of send package panel
		// --------------------------------

		JPanel sendPackagePanel = new JPanel();
		sendPackagePanel.setLayout(null);
		sendPackagePanel.setBackground(Color.LIGHT_GRAY);
		customerCards.add(sendPackagePanel, "sendPackagePanel");

		JLabel lblNewLabel_5 = new JLabel("Receiver details");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_5.setBounds(305, 11, 179, 23);
		sendPackagePanel.add(lblNewLabel_5);

		JLabel lblNewLabel_4_3 = new JLabel("Destination");
		lblNewLabel_4_3.setBounds(239, 94, 106, 14);
		sendPackagePanel.add(lblNewLabel_4_3);

		JLabel lblNewLabel_4_1_1 = new JLabel("Package length");
		lblNewLabel_4_1_1.setBounds(239, 137, 131, 14);
		sendPackagePanel.add(lblNewLabel_4_1_1);

		txtReceiverPackageLength = new JTextField();
		txtReceiverPackageLength.setColumns(10);
		txtReceiverPackageLength.setBounds(346, 134, 106, 20);
		sendPackagePanel.add(txtReceiverPackageLength);

		JLabel lblNewLabel_4_2_1 = new JLabel("Package weight in kg");
		lblNewLabel_4_2_1.setBounds(239, 227, 131, 14);
		sendPackagePanel.add(lblNewLabel_4_2_1);

		txtReceiverPackageWeight = new JTextField();
		txtReceiverPackageWeight.setColumns(10);
		txtReceiverPackageWeight.setBounds(359, 224, 106, 20);
		sendPackagePanel.add(txtReceiverPackageWeight);

		JLabel lblNewLabel_6 = new JLabel("What is package type?");
		lblNewLabel_6.setBounds(239, 255, 145, 14);
		sendPackagePanel.add(lblNewLabel_6);

		comboBoxPackageType = new JComboBox<String>();
		comboBoxPackageType.setBounds(239, 280, 106, 22);
		comboBoxPackageType.addItem("Regular");
		comboBoxPackageType.addItem("Fragile");
		comboBoxPackageType.addItem("Liquid");
		comboBoxPackageType.addItem("Chemical");
		sendPackagePanel.add(comboBoxPackageType);

		JComboBox<String> comboBoxInsurance = new JComboBox<String>();
		comboBoxInsurance.setBounds(239, 332, 106, 22);
		comboBoxInsurance.addItem("Yes");
		comboBoxInsurance.addItem("No");
		sendPackagePanel.add(comboBoxInsurance);

		JLabel lblNewLabel_7 = new JLabel("Do you want to insure the package?");
		lblNewLabel_7.setBounds(239, 313, 226, 14);
		sendPackagePanel.add(lblNewLabel_7);

		JButton btn1 = new JButton("later");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) customerCards.getLayout();
				ca.show(customerCards, "mainCustomerPanel");
			}
		});

		JLabel lblSendDeliveryErorr = new JLabel("");
		lblSendDeliveryErorr.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSendDeliveryErorr.setBounds(10, 429, 440, 43);
		sendPackagePanel.add(lblSendDeliveryErorr);

		JButton btnSubmitPackageDetails = new JButton("Submit");
		btnSubmitPackageDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String randomNum = randomNumber();

				if (txtRecieverID.getText().length() < 12) {
					JOptionPane.showMessageDialog(null, "Reciver Id must be 12 digits");
				}

				else if (comboBoxAddress.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "address cannot be null");
				}

				// Length
				else if (txtReceiverPackageLength.getText().length() < 1
						|| Integer.parseInt(txtReceiverPackageLength.getText()) < 1
						|| Integer.parseInt(txtReceiverPackageLength.getText()) > 240) {
					JOptionPane.showMessageDialog(null, "Package length must be between 1cm and 240cm");
				}

				// width
				else if (txtReceiverPackageWidth.getText().length() < 1
						|| Integer.parseInt(txtReceiverPackageWidth.getText()) < 1
						|| Integer.parseInt(txtReceiverPackageWidth.getText()) > 120) { // height
					JOptionPane.showMessageDialog(null, "Package width must be between 1cm and 120cm");
				}

				// height
				else if (txtReceiverPackageHeight.getText().length() < 1
						|| Integer.parseInt(txtReceiverPackageHeight.getText()) < 1
						|| Integer.parseInt(txtReceiverPackageHeight.getText()) > 220) { // height
					JOptionPane.showMessageDialog(null, "Package height must be between 1cm and 220cm");
				}
				// weight
				else if (txtReceiverPackageWeight.getText().length() < 1
						|| Integer.parseInt(txtReceiverPackageWeight.getText()) < 1
						|| Integer.parseInt(txtReceiverPackageWeight.getText()) > 100) { // height
					JOptionPane.showMessageDialog(null, "Package height must be between 1 and 100kg");
				}

				else {
					int result = JOptionPane.showConfirmDialog(null, "Do you want pay for the package now?");
					switch (result) {
					case JOptionPane.YES_OPTION:
						CardLayout ca = (CardLayout) customerCards.getLayout();
						ca.show(customerCards, "paymentPanel");
						break;
					case JOptionPane.NO_OPTION:
						CardLayout ca1 = (CardLayout) customerCards.getLayout();
						ca1.show(customerCards, "mainCustomerPanel");
						insertIntoPackageTable(randomNum);
						insertIntoPackagesComboBox(id);

						txtRecieverID.setText("");
						comboBoxAddress.removeAllItems();
						txtReceiverPackageLength.setText("");
						txtReceiverPackageWidth.setText("");
						txtReceiverPackageHeight.setText("");
						txtReceiverPackageWeight.setText("");
						break;
					}
				}

			}
		});
		btnSubmitPackageDetails.setBounds(239, 365, 131, 23);
		sendPackagePanel.add(btnSubmitPackageDetails);

		txtRecieverID = new JTextField();
		txtRecieverID.setColumns(10);
		txtRecieverID.setBounds(316, 50, 106, 20);

		sendPackagePanel.add(txtRecieverID);

		comboBoxAddress = new JComboBox<String>();

		if (txtRecieverID.getText().length() == 5) {
		}

		comboBoxAddress.setBounds(316, 90, 106, 22);
		sendPackagePanel.add(comboBoxAddress);

		JLabel lblNewLabel_4_1_1_1 = new JLabel("Reciever ID");
		lblNewLabel_4_1_1_1.setBounds(239, 50, 131, 14);
		sendPackagePanel.add(lblNewLabel_4_1_1_1);

		JButton btnNewButton_3 = new JButton("Show addresses");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertIntoAddressComboBox();
			}
		});
		btnNewButton_3.setBounds(432, 49, 131, 23);
		sendPackagePanel.add(btnNewButton_3);

		JButton btnCancelPackage = new JButton("Cancel");
		btnCancelPackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) customerCards.getLayout();
				ca.show(customerCards, "mainCustomerPanel");

				txtRecieverID.setText("");
				comboBoxAddress.removeAllItems();
				txtReceiverPackageLength.setText("");
				txtReceiverPackageWeight.setText("");
				lblSendDeliveryErorr.setText("");
			}
		});
		btnCancelPackage.setBounds(395, 365, 89, 23);
		sendPackagePanel.add(btnCancelPackage);

		txtReceiverPackageWidth = new JTextField();
		txtReceiverPackageWidth.setColumns(10);
		txtReceiverPackageWidth.setBounds(346, 162, 106, 20);
		sendPackagePanel.add(txtReceiverPackageWidth);

		JLabel lblNewLabel_4_1_1_2 = new JLabel("Package width");
		lblNewLabel_4_1_1_2.setBounds(239, 165, 131, 14);
		sendPackagePanel.add(lblNewLabel_4_1_1_2);

		txtReceiverPackageHeight = new JTextField();
		txtReceiverPackageHeight.setColumns(10);
		txtReceiverPackageHeight.setBounds(346, 193, 106, 20);
		sendPackagePanel.add(txtReceiverPackageHeight);

		JLabel lblNewLabel_4_1_1_3 = new JLabel("Package height");
		lblNewLabel_4_1_1_3.setBounds(239, 193, 131, 14);
		sendPackagePanel.add(lblNewLabel_4_1_1_3);

		// -------------------------------- end of send package panel
		// --------------------------------

		// -------------------------------- start of payment panel
		// --------------------------------

		JPanel paymentPanel = new JPanel();
		paymentPanel.setBackground(Color.LIGHT_GRAY);
		customerCards.add(paymentPanel, "paymentPanel");
		paymentPanel.setLayout(null);
		JPanel Ppanel = new JPanel();
		Ppanel.setBackground(new Color(173, 216, 230));
		Ppanel.setBounds(234, 112, 292, 261);
		paymentPanel.add(Ppanel);
		Ppanel.setLayout(null);

		JLabel lblPaymentStatus = new JLabel("");
		lblPaymentStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPaymentStatus.setBounds(10, 203, 272, 47);
		Ppanel.add(lblPaymentStatus);

		txtCardholderName = new JTextField();
		txtCardholderName.setBounds(10, 28, 162, 20);
		Ppanel.add(txtCardholderName);
		txtCardholderName.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("Cardholder name");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10.setBounds(10, 11, 188, 14);
		Ppanel.add(lblNewLabel_10);

		JLabel lblNewLabel_10_1 = new JLabel("Card number");
		lblNewLabel_10_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10_1.setBounds(10, 59, 188, 14);
		Ppanel.add(lblNewLabel_10_1);

		txtCardNumber = new JTextField();
		txtCardNumber.setColumns(10);
		txtCardNumber.setBounds(10, 76, 162, 20);

		Ppanel.add(txtCardNumber);

		txtExpireDate = new JTextField();
		txtExpireDate.setColumns(10);
		txtExpireDate.setBounds(10, 124, 104, 20);
		Ppanel.add(txtExpireDate);

		JLabel lblNewLabel_10_1_1 = new JLabel("Expire date MM/YY");
		lblNewLabel_10_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10_1_1.setBounds(10, 107, 144, 14);
		Ppanel.add(lblNewLabel_10_1_1);

		txtCCV = new JTextField();
		txtCCV.setColumns(10);
		txtCCV.setBounds(140, 124, 38, 20);
		Ppanel.add(txtCCV);

		JLabel lblNewLabel_10_1_1_1 = new JLabel("CCV");
		lblNewLabel_10_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10_1_1_1.setBounds(147, 107, 86, 14);
		Ppanel.add(lblNewLabel_10_1_1_1);

		JButton btnValidatePayment = new JButton("Validate payment");
		btnValidatePayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String cardholder = txtCardholderName.getText();
				String cardNumber = txtCardNumber.getText();
				String expireDate = txtExpireDate.getText();
				String ccv = txtCCV.getText();

				String randomNum = randomNumber();

				if (!Pattern.matches("[a-zA-Z]+", cardholder)) {
					JOptionPane.showMessageDialog(null, "Cardholder name must contain only characters");
				} else if (!cardNumber.chars().allMatch(Character::isDigit) || cardNumber.length() != 16) { // 1234567891234567
					JOptionPane.showMessageDialog(null, "Card number must be 16 digit");
				} else if (!expireDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}")) {
					JOptionPane.showMessageDialog(null, "Expire date layout is MM/YY");
				} else if (!ccv.chars().allMatch(Character::isDigit) || ccv.length() != 3) {
					lblPaymentStatus.setText("ccv must be 3 digits");
				} else if (anotherPackage != "") {
					JOptionPane.showMessageDialog(null, "payment status: Validated");
					CardLayout ca = (CardLayout) customerCards.getLayout();
					ca.show(customerCards, "mainCustomerPanel");

					validatePayment(anotherPackage);

					txtCardholderName.setText("");
					txtCardNumber.setText("");
					txtExpireDate.setText("");
					txtCCV.setText("");
					lblPaymentStatus.setText("");
					anotherPackage = "";

					showAllPackagesInTable(id);
				}

				else {

					insertIntoPackageTable(randomNum);
					JOptionPane.showMessageDialog(null, "payment status: Validated");

					CardLayout ca = (CardLayout) customerCards.getLayout();
					ca.show(customerCards, "mainCustomerPanel");

					validatePayment(randomNum);

					// reset everything to "" so that the text fields be empty
					txtCardholderName.setText("");
					txtCardNumber.setText("");
					txtExpireDate.setText("");
					txtCCV.setText("");
					lblPaymentStatus.setText("");

					showAllPackagesInTable(id);
				}
			}
		});
		btnValidatePayment.setBounds(10, 155, 144, 23);
		Ppanel.add(btnValidatePayment);

		JLabel lblNewLabel_9 = new JLabel("Validate card information");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_9.setBounds(259, 76, 229, 25);
		paymentPanel.add(lblNewLabel_9);

		JButton btnBackToPaymentChoice = new JButton("Cancel");
		btnBackToPaymentChoice.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnBackToPaymentChoice.setBounds(10, 409, 177, 63);
		paymentPanel.add(btnBackToPaymentChoice);

		JPanel searchForPackageInfoPanel = new JPanel();
		searchForPackageInfoPanel.setBackground(Color.LIGHT_GRAY);
		customerCards.add(searchForPackageInfoPanel, "searchForPackageInfoPanel");
		searchForPackageInfoPanel.setLayout(null);

		JButton btnNewButton_6 = new JButton("Back");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) customerCards.getLayout();
				ca.show(customerCards, "mainCustomerPanel");
			}
		});
		btnNewButton_6.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnNewButton_6.setBounds(10, 433, 107, 39);
		searchForPackageInfoPanel.add(btnNewButton_6);

		JLabel lblNewLabel_2 = new JLabel("Search constraints:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(10, 139, 260, 27);
		searchForPackageInfoPanel.add(lblNewLabel_2);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(262, 42, 535, 430);
		searchForPackageInfoPanel.add(scrollPane_2);

		tableAdvancedSearch = new JTable();
		scrollPane_2.setViewportView(tableAdvancedSearch);

		// Turn off JTable's auto resize so that JScrollPane will show a horizontal
		// scroll bar.
		tableAdvancedSearch.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_2.setViewportView(tableAdvancedSearch);

		JLabel lblNewLabel_2_1 = new JLabel("ADVANCED TRACKING");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_2_1.setBounds(262, 0, 529, 38);
		searchForPackageInfoPanel.add(lblNewLabel_2_1);

		comboBoxSearchingMethod = new JComboBox<String>();
		comboBoxSearchingMethod.setBounds(10, 94, 148, 22);
		comboBoxSearchingMethod.addItem("ID");
		comboBoxSearchingMethod.addItem("Catagory");
		comboBoxSearchingMethod.addItem("Destination");
		comboBoxSearchingMethod.addItem("Final DeliveryDate");
		searchForPackageInfoPanel.add(comboBoxSearchingMethod);

		JLabel lblNewLabel_2_2 = new JLabel("Searching method:");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2_2.setBounds(10, 56, 260, 27);
		searchForPackageInfoPanel.add(lblNewLabel_2_2);

		JButton btnNewButton_8 = new JButton("Start searching");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String method = comboBoxSearchingMethod.getSelectedItem().toString();
				String details = txtSearchConstraints.getText();

				searchPackageCustom(id, method, details);
			}
		});
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_8.setBounds(10, 208, 214, 59);
		searchForPackageInfoPanel.add(btnNewButton_8);

		txtSearchConstraints = new JTextField();
		txtSearchConstraints.setBounds(10, 177, 190, 20);
		searchForPackageInfoPanel.add(txtSearchConstraints);
		txtSearchConstraints.setColumns(10);

		JPanel updateProfilePanel = new JPanel();
		updateProfilePanel.setLayout(null);
		customerCards.add(updateProfilePanel, "updateProfilePanel");

		JLabel lblNewLabel_4_4 = new JLabel("First Name");
		lblNewLabel_4_4.setBounds(10, 36, 106, 14);
		updateProfilePanel.add(lblNewLabel_4_4);

		txtProfileFirstName = new JTextField();
		txtProfileFirstName.setText(FnameOfCustomer);
		txtProfileFirstName.setColumns(10);
		txtProfileFirstName.setBounds(126, 30, 153, 26);
		updateProfilePanel.add(txtProfileFirstName);

		JLabel lblNewLabel_4_1_2 = new JLabel("Middle Name");
		lblNewLabel_4_1_2.setBounds(10, 75, 106, 14);
		updateProfilePanel.add(lblNewLabel_4_1_2);

		txtProfileMiddleName = new JTextField();
		txtProfileMiddleName.setText(MnameOfCustomer);
		txtProfileMiddleName.setColumns(10);
		txtProfileMiddleName.setBounds(126, 67, 153, 30);
		updateProfilePanel.add(txtProfileMiddleName);

		JLabel lblNewLabel_4_2_2 = new JLabel("Phone number");
		lblNewLabel_4_2_2.setBounds(10, 157, 106, 14);
		updateProfilePanel.add(lblNewLabel_4_2_2);

		txtProfilePhoneNumber = new JTextField();
		txtProfilePhoneNumber.setText(PhoneOfCustomer);
		txtProfilePhoneNumber.setColumns(10);
		txtProfilePhoneNumber.setBounds(126, 149, 153, 30);
		updateProfilePanel.add(txtProfilePhoneNumber);

		JButton btnSubmitProfileInfo = new JButton("Update profile");
		btnSubmitProfileInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtProfileFirstName.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "First name must not exceed 20 characters");
				}

				else if (txtProfileMiddleName.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "Middle name must not exceed 20 characters");
				}

				else if (textFieldLastName.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "Last name must not exceed 20 characters");
				}

				else if (txtProfilePhoneNumber.getText().length() != 10) {
					JOptionPane.showMessageDialog(null, "Phone number must be 10 digits\nFormat: 05********");
				}

				else if (textFieldEmailx.getText().length() > 32) {
					JOptionPane.showMessageDialog(null, "Email must not exceed 32 characters");
				}

				else if (textFieldEmailx.getText().contains("@employee")
						|| textFieldEmailx.getText().contains("@admin")) {
					JOptionPane.showMessageDialog(null, "Admin sign up not possible from application");
				} else {

					PreparedStatement pst;

					try {
						String UpdateSqlProfile = "UPDATE Person SET Fname= ?, Mname= ?, Lname= ?,Phone_Number= ?, Email= ? WHERE ID=?";
						pst = connection.prepareStatement(UpdateSqlProfile);
						pst.setString(1, txtProfileFirstName.getText());
						pst.setString(2, txtProfileMiddleName.getText());
						pst.setString(3, textFieldLastName.getText());
						pst.setString(4, txtProfilePhoneNumber.getText());
						pst.setString(5, textFieldEmailx.getText());
						pst.setString(6, id);
						pst.executeUpdate();

					} catch (Exception e1) {
						System.out.print(e1);
					}

					CardLayout ca = (CardLayout) customerCards.getLayout();
					ca.show(customerCards, "mainCustomerPanel");

				}
			}
		});
		btnSubmitProfileInfo.setBounds(10, 399, 164, 73);
		updateProfilePanel.add(btnSubmitProfileInfo);

		JButton btnAddNewAdress = new JButton("Add new address");
		btnAddNewAdress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PreparedStatement pst;
				String insertID;
				try {
					insertID = "INSERT INTO Person_Address(ID, Person_Address) VALUES(?,?)";
					pst = connection.prepareStatement(insertID);
					pst.setString(1, id);
					pst.setString(2, txtnewAddress.getText());
					pst.executeUpdate();

					DefaultListModel<String> dlm1 = new DefaultListModel<String>();
					// person addresses
					String addressList = "SELECT Person_Address FROM Person_Address WHERE ID=?";
					ResultSet setList;

					PreparedStatement pstList;
					try {
						pstList = connection.prepareStatement(addressList);
						pstList.setString(1, id);
						setList = pstList.executeQuery();
						while (setList.next()) {
							dlm1.addElement(setList.getString("Person_Address"));

						}
					} catch (Exception e1) {
						System.out.print(e1);
					}
					listOfAddresses.setModel(dlm1);

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Address already exists");
					System.out.print(e1);
				}

			}
		});
		btnAddNewAdress.setBounds(10, 298, 262, 35);
		updateProfilePanel.add(btnAddNewAdress);

		JLabel lblNewLabel_1 = new JLabel("My adresses");
		lblNewLabel_1.setBounds(384, 11, 129, 14);
		updateProfilePanel.add(lblNewLabel_1);

		txtnewAddress = new JTextField();
		txtnewAddress.setText("txtnewAddress");
		txtnewAddress.setColumns(10);
		txtnewAddress.setBounds(10, 240, 269, 42);
		updateProfilePanel.add(txtnewAddress);

		JButton btnDeleteAddress = new JButton("Delete address");
		btnDeleteAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deleteSQL = "DELETE FROM Person_Address WHERE Person_Address=?";
				PreparedStatement pstDelete;
				try {
					pstDelete = connection.prepareStatement(deleteSQL);
					pstDelete.setString(1, listOfAddresses.getSelectedValue().toString());
					pstDelete.executeUpdate();

					DefaultListModel<String> dlm1 = new DefaultListModel<String>();
					String addressList = "SELECT Person_Address FROM Person_Address WHERE ID=?";
					ResultSet setList;
					PreparedStatement pstList;
					try {
						pstList = connection.prepareStatement(addressList);
						pstList.setString(1, id);
						setList = pstList.executeQuery();
						while (setList.next()) {
							dlm1.addElement(setList.getString("Person_Address"));
						}
					} catch (Exception e1) {
						System.out.print(e1);
					}
					listOfAddresses.setModel(dlm1);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Address already exists");
					System.out.print(e1);

				}
			}
		});
		btnDeleteAddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDeleteAddress.setBounds(499, 399, 176, 73);
		updateProfilePanel.add(btnDeleteAddress);

		JLabel lblNewLabel_4_1_2_1 = new JLabel("Last name");
		lblNewLabel_4_1_2_1.setBounds(10, 116, 106, 14);
		updateProfilePanel.add(lblNewLabel_4_1_2_1);

		textFieldLastName = new JTextField();
		textFieldLastName.setText(LnameOfCustomer);
		textFieldLastName.setColumns(10);
		textFieldLastName.setBounds(126, 108, 153, 30);
		updateProfilePanel.add(textFieldLastName);

		JLabel lblNewLabel_4_2_2_2 = new JLabel("Email");
		lblNewLabel_4_2_2_2.setBounds(10, 196, 106, 14);
		updateProfilePanel.add(lblNewLabel_4_2_2_2);

		textFieldEmailx = new JTextField();
		textFieldEmailx.setText(email);
		textFieldEmailx.setColumns(10);
		textFieldEmailx.setBounds(126, 190, 153, 26);
		updateProfilePanel.add(textFieldEmailx);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(399, 54, 269, 329);
		updateProfilePanel.add(scrollPane_1);

		listOfAddresses = new JList<String>();
		scrollPane_1.setViewportView(listOfAddresses);

		btnBackToPaymentChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) customerCards.getLayout();
				ca.show(customerCards, "mainCustomerPanel");
			}
		});

		// -------------------------------- end of update profile panel
		// --------------------------------

	}
	// -------------------------------- start of functions
	// --------------------------------

	// done
	public String randomNumber() {
		long min = 100000000000L; // 12 digits inclusive
		long max = 1000000000000L; // 13 digits exclusive
		Random random = new Random();
		long number = min + ((long) (random.nextDouble() * (max - min)));
		return Long.toString(number);
	}

	// done
	public void insertIntoPackageTable(String id) {
		try {

			String length = txtReceiverPackageLength.getText();
			String width = txtReceiverPackageWidth.getText();
			String height = txtReceiverPackageHeight.getText();

			String dimensions = length + "Lx" + width + "Wx" + height + "H";

			String packageId = id;
			String status = "In transit";

			String destination = (String) comboBoxAddress.getSelectedItem();
			String weight = txtReceiverPackageWeight.getText();
			String retailCenter = "Dammam";
			String cusotmerID = txtRecieverID.getText();
			String finalDeliveryDate = "12/21/2022";
			String packageType = (String) comboBoxPackageType.getSelectedItem();
			String paymentStatus = "Unpaid";

			String sql = "INSERT INTO Package" + "(Package_ID,Status,Dimensions,"// (Package Id auto generated,Status
																					// "in transit",Dimensions ?)
					+ "Destination,Weight," // (Destination ?, Weight ?)
					+ "RetailCenter_ID,Customer_ID," // (Retail center id is fixed, customer id ?)
					+ "Final_DeliveryDate,Package_Type,Payment)" // (final delivery date 3 days , package type ?)
					+ " VALUES(?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, packageId);
			pst.setString(2, status);
			pst.setString(3, dimensions);
			pst.setString(4, destination);
			pst.setString(5, weight);
			pst.setString(6, retailCenter);
			pst.setString(7, cusotmerID);
			pst.setString(8, finalDeliveryDate);
			pst.setString(9, packageType);
			pst.setString(10, paymentStatus);

			pst.executeUpdate();
			pst.close();
		} catch (SQLException e1) {
			System.out.print(e1);
		}

	}

	// done
	public void insertIntoAddressComboBox() {

		comboBoxAddress.removeAllItems();
		String cID = txtRecieverID.getText();
		try {

			String sql = "SELECT Person_Address from Person_Address where ID=?";

			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, cID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				comboBoxAddress.addItem(rs.getString("Person_Address"));
			}

			pst.close();

		} catch (SQLException e1) {
			System.out.print(e1);
		}

	}

	// done
	public void validatePayment(String id) {
		try {

			// INSERT INTO Package table that the package has been paid
			String sql = "UPDATE Package SET Payment=? Where Package_ID=? ";

			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, "Paid");
			pst.setString(2, id);// make it point to package ID

			pst.executeUpdate();
			pst.close();
		} catch (SQLException e1) {
			System.out.print(e1);
		}

	}

	// done
	public void showPackageHistory(String s) {

		tableTraceMyPackages.setModel(new DefaultTableModel());

		String id = s;
		try {

			String sql = "SELECT Package_Location,History" + " From Package_Location" + " Where Package_ID=?"
					+ "Order by History";
			PreparedStatement pst = connection.prepareStatement(sql);

			pst.setString(1, id);

			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel) tableTraceMyPackages.getModel();

			int cols = rsmd.getColumnCount();
			String[] colName = new String[cols];
			for (int i = 0; i < cols; i++) {
				colName[i] = rsmd.getColumnName(i + 1);
				model.setColumnIdentifiers(colName);
			}

			tableTraceMyPackages.getColumnModel().getColumn(0).setPreferredWidth(110);
			tableTraceMyPackages.getColumnModel().getColumn(1).setPreferredWidth(83);

			String loca, hist;
			while (rs.next()) {
				loca = rs.getString(1);
				hist = rs.getString(2);
				String[] row = { loca, hist };
				model.addRow(row);
			}
			pst.close();

		} catch (SQLException e1) {
			System.out.print(e1);
		}

	}

	// done
	public void showAllPackagesInTable(String s) {

		tableTraceMyPackages.setModel(new DefaultTableModel());
		try {

			String sql = "SELECT * From Package Where Customer_Id=? Order by Package_ID";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, s);

			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel) tableTraceMyPackages.getModel();

			int cols = rsmd.getColumnCount();
			String[] colName = new String[cols];
			for (int i = 0; i < cols; i++) {
				colName[i] = rsmd.getColumnName(i + 1);
				model.setColumnIdentifiers(colName);
			}

			String packageID, status, dimension, distination, weight, retailCenter, Customer_ID, finalDeliveryDate,
					package_type, payment;
			while (rs.next()) {
				packageID = rs.getString(1);
				status = rs.getString(2);
				dimension = rs.getString(3);
				distination = rs.getString(4);
				weight = rs.getString(5);
				retailCenter = rs.getString(6);
				Customer_ID = rs.getString(7);
				finalDeliveryDate = rs.getString(8);
				package_type = rs.getString(9);
				payment = rs.getString(10);
				String[] row = { packageID, status, dimension, distination, weight, retailCenter, Customer_ID,
						finalDeliveryDate, package_type, payment };
				model.addRow(row);
			}
			tableTraceMyPackages.getColumnModel().getColumn(0).setPreferredWidth(110);
			tableTraceMyPackages.getColumnModel().getColumn(2).setPreferredWidth(110);

		} catch (SQLException e1) {
			System.out.print(e1);
		}

	}

	// done
	public String getCustomerID(String s) {

		try {
			String sql = "SELECT ID FROM Person WHERE Email=?";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, s);
			ResultSet rs = pst.executeQuery();
			String ID = rs.getString(1);
			return ID;
		} catch (SQLException e1) {
			System.out.print(e1);
		}
		return "";
	}

	// done
	public void insertIntoPackagesComboBox(String id) {
		//
		comboBoxCustomerPackages.removeAllItems();
		//
		String cID = id;
		try {

			String sql = "SELECT Package_ID from Package where Customer_ID=? ORDER BY Package_ID";

			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, cID);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				comboBoxCustomerPackages.addItem(rs.getString("Package_ID"));
			}
			pst.close();

		} catch (SQLException e1) {
			System.out.print(e1);
		}

	}

	// done
	public String showUserName(String s) {

		try {

			String sql = "Select Fname,Lname From Person where Email=? ";

			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, s);

			ResultSet rs = pst.executeQuery();
			String ss = "Hello " + rs.getString("Fname") + " " + rs.getString("Lname");
			return ss;

		} catch (SQLException e1) {
			System.out.print(e1);
		}
		return "";

	}

	// done
	public boolean checkIfPackageIsPaid(String s) {

		try {

			String sql = "Select Payment From Package where Package_ID=? ";

			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, s);

			ResultSet rs = pst.executeQuery();
			if (rs.getString("Payment").equals("Paid")) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e1) {
			System.out.print(e1);
		}
		return rootPaneCheckingEnabled;

	}

	// done
	public void searchPackageCustom(String id, String method, String details) {

		tableAdvancedSearch.setModel(new DefaultTableModel());

		if (method.equals("ID")) {
			// in transit- deliveried - lost -
			try {

				String sql = "Select * FROM Package WHERE Package_ID=? AND Customer_ID=? ";

				PreparedStatement pst = connection.prepareStatement(sql);
				pst.setString(1, details);
				pst.setString(2, id);

				ResultSet rs = pst.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				DefaultTableModel model = (DefaultTableModel) tableAdvancedSearch.getModel();

				int cols = rsmd.getColumnCount();
				String[] colName = new String[cols];
				for (int i = 0; i < cols; i++) {
					colName[i] = rsmd.getColumnName(i + 1);
					model.setColumnIdentifiers(colName);
				}
				String packageID, status, dimension, distination, weight, retailCenter, Customer_ID, finalDeliveryDate,
						package_type, payment;
				while (rs.next()) {
					packageID = rs.getString(1);
					status = rs.getString(2);
					dimension = rs.getString(3);
					distination = rs.getString(4);
					weight = rs.getString(5);
					retailCenter = rs.getString(6);
					Customer_ID = rs.getString(7);
					finalDeliveryDate = rs.getString(8);
					package_type = rs.getString(9);
					payment = rs.getString(10);
					String[] row = { packageID, status, dimension, distination, weight, retailCenter, Customer_ID,
							finalDeliveryDate, package_type, payment };
					model.addRow(row);
				}

			} catch (SQLException e1) {
				System.out.print(e1);
			}

		} else if (method.equals("Catagory")) {
			// regualr- fragile- chemical
			try {

				String sql = "Select * FROM Package WHERE Package_Type=? AND Customer_ID=? ";

				PreparedStatement pst = connection.prepareStatement(sql);
				pst.setString(1, details);
				pst.setString(2, id);

				ResultSet rs = pst.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				DefaultTableModel model = (DefaultTableModel) tableAdvancedSearch.getModel();

				int cols = rsmd.getColumnCount();
				String[] colName = new String[cols];
				for (int i = 0; i < cols; i++) {
					colName[i] = rsmd.getColumnName(i + 1);
					model.setColumnIdentifiers(colName);
				}
				String packageID, status, dimension, distination, weight, retailCenter, Customer_ID, finalDeliveryDate,
						package_type, payment;
				while (rs.next()) {
					packageID = rs.getString(1);
					status = rs.getString(2);
					dimension = rs.getString(3);
					distination = rs.getString(4);
					weight = rs.getString(5);
					retailCenter = rs.getString(6);
					Customer_ID = rs.getString(7);
					finalDeliveryDate = rs.getString(8);
					package_type = rs.getString(9);
					payment = rs.getString(10);
					String[] row = { packageID, status, dimension, distination, weight, retailCenter, Customer_ID,
							finalDeliveryDate, package_type, payment };
					model.addRow(row);
				}

			} catch (SQLException e1) {
				System.out.print(e1);
			}
		} else if (method.equals("Destination")) {
			// dammam - dahran
			try {

				String sql = "Select * FROM Package WHERE Destination=? AND Customer_ID=? ";

				PreparedStatement pst = connection.prepareStatement(sql);
				pst.setString(1, details);
				pst.setString(2, id);

				ResultSet rs = pst.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				DefaultTableModel model = (DefaultTableModel) tableAdvancedSearch.getModel();

				int cols = rsmd.getColumnCount();
				String[] colName = new String[cols];
				for (int i = 0; i < cols; i++) {
					colName[i] = rsmd.getColumnName(i + 1);
					model.setColumnIdentifiers(colName);
				}
				String packageID, status, dimension, distination, weight, retailCenter, Customer_ID, finalDeliveryDate,
						package_type, payment;
				while (rs.next()) {
					packageID = rs.getString(1);
					status = rs.getString(2);
					dimension = rs.getString(3);
					distination = rs.getString(4);
					weight = rs.getString(5);
					retailCenter = rs.getString(6);
					Customer_ID = rs.getString(7);
					finalDeliveryDate = rs.getString(8);
					package_type = rs.getString(9);
					payment = rs.getString(10);
					String[] row = { packageID, status, dimension, distination, weight, retailCenter, Customer_ID,
							finalDeliveryDate, package_type, payment };
					model.addRow(row);
				}

			} catch (SQLException e1) {
				System.out.print(e1);
			}
		} else if (method.equals("Final DeliveryDate")) {
			// 12/15/2022 -- 12/21-2022 Final DeliveryDate

			try {
				String sql = "Select * FROM Package WHERE Final_DeliveryDate=? AND Customer_ID=? ";

				PreparedStatement pst = connection.prepareStatement(sql);
				pst.setString(1, details);
				pst.setString(2, id);

				ResultSet rs = pst.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				DefaultTableModel model = (DefaultTableModel) tableAdvancedSearch.getModel();

				int cols = rsmd.getColumnCount();
				String[] colName = new String[cols];
				for (int i = 0; i < cols; i++) {
					colName[i] = rsmd.getColumnName(i + 1);
					model.setColumnIdentifiers(colName);
				}
				String packageID, status, dimension, distination, weight, retailCenter, Customer_ID, finalDeliveryDate,
						package_type, payment;
				while (rs.next()) {
					packageID = rs.getString(1);
					status = rs.getString(2);
					dimension = rs.getString(3);
					distination = rs.getString(4);
					weight = rs.getString(5);
					retailCenter = rs.getString(6);
					Customer_ID = rs.getString(7);
					finalDeliveryDate = rs.getString(8);
					package_type = rs.getString(9);
					payment = rs.getString(10);
					String[] row = { packageID, status, dimension, distination, weight, retailCenter, Customer_ID,
							finalDeliveryDate, package_type, payment };
					model.addRow(row);
				}

			} catch (SQLException e1) {
				System.out.print(e1);
			}
		}

	}
}

/*
 * 
 * 
 * 
 * to do list:
 * 
 * 
 * make sure that when submitting the send request the address cannot be null --
 * done
 * 
 * make sure that the card holder name is only alphabetical characters -- done
 * 
 * make sure that the card number is 16 digits -- done
 * 
 * make sure that the card expire date contains / at the third position or at
 * least put .contains("/") -- done
 * 
 * add checks for
 * 
 * 1 weight between 1kg-100kg -- done 4 Length between 1cm-240cm -- done 2 width
 * between 1cm-120cm -- done 3 height between 1cm-220cm -- done
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
