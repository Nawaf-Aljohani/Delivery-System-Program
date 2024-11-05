package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Main {
	private ArrayList<String> list;
	private JFrame frame;
	private JPasswordField txtLogInPass;
	private JTextField txtEnterEmail;
	private JTextField txtRegEmail;
	private JPasswordField txtRegPass;
	private JTextField textFieldFname;
	private JTextField textFieldLname;
	private JTextField textFieldPhone;
	private JTextField textFieldID;
	private JTextField textFieldMname;
	public long ID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		connection = sqliteConnection.dbConnector();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	Connection connection = null;

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		// -------------------------------- start of main panel
		// --------------------------------

		JPanel mainCards = new JPanel();
		mainCards.setLayout(new CardLayout());
		frame.getContentPane().add(mainCards);

		JPanel main = new JPanel();
		mainCards.add(main, "main");
		main.setLayout(null);

		JLabel lblSmaDeliverySystem = new JLabel("SMA DELIVERY SYSTEM");
		lblSmaDeliverySystem.setForeground(UIManager.getColor("Button.foreground"));
		lblSmaDeliverySystem.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblSmaDeliverySystem.setBounds(392, 24, 359, 37);
		main.add(lblSmaDeliverySystem);

		JButton btnContactUs = new JButton("Contact us");
		btnContactUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(
							"https://teams.microsoft.com/l/team/19%3auRNUU4iU3EYsfLhsfwGrzsyv1VH19lhSdbdm5QNiVII1%40thread.tacv2/conversations?groupId=c73e34a6-0b98-45b3-a531-2dcf19f9f59ftenantId=29b4b088-d27d-4129-b9f9-8637b59ea4b3"));
				} catch (java.io.IOException ea) {
					System.out.println(ea.getMessage());
				}
			}
		});
		btnContactUs.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnContactUs.setBounds(648, 444, 194, 56);
		main.add(btnContactUs);

		JButton btnAboutUs = new JButton("About us");
		btnAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CardLayout ca = (CardLayout) mainCards.getLayout();
				ca.show(mainCards, "aboutUs");
			}
		});
		btnAboutUs.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAboutUs.setBounds(271, 444, 194, 57);
		main.add(btnAboutUs);

		JPanel regLogPanel = new JPanel();
		regLogPanel.setBounds(271, 104, 571, 289);
		main.add(regLogPanel);
		regLogPanel.setLayout(new CardLayout(0, 0));

		Panel logInPanel = new Panel();
		logInPanel.setLayout(null);
		logInPanel.setBackground(Color.LIGHT_GRAY);
		regLogPanel.add(logInPanel, "logInPanel");

		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(98, 91, 79, 21);
		logInPanel.add(lblNewLabel_1);

		txtLogInPass = new JPasswordField();
		txtLogInPass.setBounds(187, 142, 279, 23);
		logInPanel.add(txtLogInPass);

		txtEnterEmail = new JTextField();
		txtEnterEmail.setText("");
		txtEnterEmail.setColumns(10);
		txtEnterEmail.setBounds(187, 92, 279, 23);
		logInPanel.add(txtEnterEmail);

		JLabel lblLoginText = new JLabel("");
		lblLoginText.setBounds(10, 157, 227, 60);
		logInPanel.add(lblLoginText);

		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setID();
				String email = txtEnterEmail.getText();
				ResultSet result;
				// customer and admin should have password and email
				String sql = "SELECT * from Person WHERE Email = ? and Password = ?";
				PreparedStatement pst;
				try {
					if ((email.contains("@employee") || email.contains("@admin"))) {
						pst = connection.prepareStatement(sql);
						pst.setString(1, txtEnterEmail.getText());
						pst.setString(2, txtLogInPass.getText());
						result = pst.executeQuery();
						if (result.next()) {
							connection.close();
							frame.dispose();
							Admin admin = new Admin(ID);
							admin.setVisible(true);
						} else {
							lblLoginText.setText("Email or passward are incorrect");

						}
					} else {
						pst = connection.prepareStatement(sql);
						pst.setString(1, txtEnterEmail.getText());
						pst.setString(2, txtLogInPass.getText());
						result = pst.executeQuery();
						if (result.next()) {
							connection.close();
							frame.dispose();
							Customer customer = new Customer(txtEnterEmail.getText());
							customer.setVisible(true);
						} else {
							lblLoginText.setText("Email or passward are incorrect");
						}
					}
				} catch (Exception e1) {
					System.out.print("heyo");
				}

			}
		});
		btnLogIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogIn.setBounds(379, 203, 87, 35);
		logInPanel.add(btnLogIn);

		JLabel lblNewLabel_1_1 = new JLabel("Passward");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(98, 141, 67, 21);
		logInPanel.add(lblNewLabel_1_1);

		JLabel lblLoginForm = new JLabel("Login Form");
		lblLoginForm.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblLoginForm.setBounds(196, 11, 170, 37);
		logInPanel.add(lblLoginForm);

		JButton btnDontHaveAn = new JButton("Don't have an account?");
		btnDontHaveAn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) regLogPanel.getLayout();
				ca.show(regLogPanel, "registrationPanel");
			}
		});
		btnDontHaveAn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDontHaveAn.setBounds(150, 203, 204, 35);
		logInPanel.add(btnDontHaveAn);

		JPanel registrationPanel = new JPanel();
		registrationPanel.setBackground(Color.LIGHT_GRAY);
		regLogPanel.add(registrationPanel, "registrationPanel");
		registrationPanel.setLayout(null);

		JLabel lblRegistrationForm = new JLabel("Registration form");
		lblRegistrationForm.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblRegistrationForm.setBounds(25, 12, 311, 37);
		registrationPanel.add(lblRegistrationForm);

		txtRegEmail = new JTextField();
		txtRegEmail.setColumns(10);
		txtRegEmail.setBounds(125, 93, 173, 20);
		registrationPanel.add(txtRegEmail);

		txtRegPass = new JPasswordField();
		txtRegPass.setBounds(125, 138, 173, 20);
		registrationPanel.add(txtRegPass);

		JButton btnCreateANew = new JButton("Create a new account");

		btnCreateANew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textFieldID.getText().length() != 12) {
					JOptionPane.showMessageDialog(null, "ID must be 12 digits");
				}
				else if (textFieldFname.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "First name must not exceed 20 characters");
				}

				else if (textFieldMname.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "Middle name must not exceed 20 characters");
				}

				else if (textFieldLname.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "Last name must not exceed 20 characters");
				}

				else if (textFieldPhone.getText().length() != 10) {
					JOptionPane.showMessageDialog(null, "Phone number must be 10 digits\nFormat: 05********");
				}

				else if (txtRegEmail.getText().length() > 32) {
					JOptionPane.showMessageDialog(null, "Email must not exceed 32 characters");
				}

				else if (txtRegPass.getText().length() > 32) {
					JOptionPane.showMessageDialog(null, "Password must not exceed 32 characters");
				}

				else if (txtRegEmail.getText().contains("@employee") || txtRegEmail.getText().contains("@admin")) {
					JOptionPane.showMessageDialog(null, "Admin sign up not possible from application");
				} else {
					try {
						String sql = "INSERT INTO Person(ID,Fname,Mname,Lname,Phone_Number,Email,Password) VALUES(?,?,?,?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(sql);
						pst.setString(1, textFieldID.getText());
						pst.setString(2, textFieldFname.getText());
						pst.setString(3, textFieldMname.getText());
						pst.setString(4, textFieldLname.getText());
						pst.setString(5, textFieldPhone.getText());
						pst.setString(6, txtRegEmail.getText());
						pst.setString(7, txtRegPass.getText());
						pst.executeUpdate();

						String sql2 = "INSERT INTO Customer(ID,Customer_Type) VALUES(?,?)";
						PreparedStatement pst2 = connection.prepareStatement(sql2);
						pst2.setString(1, textFieldID.getText());
						pst2.setString(2, "Main");
						pst2.executeUpdate();

						CardLayout ca = (CardLayout) regLogPanel.getLayout();
						ca.show(regLogPanel, "logInPanel");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Account may already exists, please check all information");
					}

				}
			}
		});

		btnCreateANew.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreateANew.setBounds(350, 243, 201, 35);
		registrationPanel.add(btnCreateANew);

		JButton btnCanelReg = new JButton("Cancel");
		btnCanelReg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCanelReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) regLogPanel.getLayout();
				ca.show(regLogPanel, "logInPanel");
			}
		});
		btnCanelReg.setBounds(12, 243, 94, 35);
		registrationPanel.add(btnCanelReg);

		JLabel lblNewLabel_1_1_1 = new JLabel("Passward");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(12, 139, 67, 14);
		registrationPanel.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Email");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(12, 94, 67, 14);
		registrationPanel.add(lblNewLabel_1_2);

		JLabel lblNewLabel = new JLabel("Profile info");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(413, 26, 148, 20);
		registrationPanel.add(lblNewLabel);

		JLabel lblNewLabel_4_4 = new JLabel("First name");
		lblNewLabel_4_4.setBounds(323, 58, 112, 20);
		registrationPanel.add(lblNewLabel_4_4);

		textFieldFname = new JTextField();
		textFieldFname.setColumns(10);
		textFieldFname.setBounds(410, 58, 141, 20);
		registrationPanel.add(textFieldFname);

		JLabel lblNewLabel_4_1_2 = new JLabel("Last name");
		lblNewLabel_4_1_2.setBounds(323, 127, 112, 17);
		registrationPanel.add(lblNewLabel_4_1_2);

		textFieldLname = new JTextField();
		textFieldLname.setColumns(10);
		textFieldLname.setBounds(410, 125, 141, 20);
		registrationPanel.add(textFieldLname);

		JLabel lblNewLabel_4_2_2 = new JLabel("Phone number");
		lblNewLabel_4_2_2.setBounds(323, 159, 112, 21);
		registrationPanel.add(lblNewLabel_4_2_2);

		textFieldPhone = new JTextField();
		textFieldPhone.setColumns(10);
		textFieldPhone.setBounds(410, 160, 141, 20);
		registrationPanel.add(textFieldPhone);

		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		textFieldID.setBounds(410, 199, 141, 20);
		registrationPanel.add(textFieldID);

		JLabel lblNewLabel_4_2_2_1 = new JLabel("ID");
		lblNewLabel_4_2_2_1.setBounds(323, 201, 112, 20);
		registrationPanel.add(lblNewLabel_4_2_2_1);

		JLabel lblNewLabel_4_4_1 = new JLabel("Middle name");
		lblNewLabel_4_4_1.setBounds(323, 93, 112, 20);
		registrationPanel.add(lblNewLabel_4_4_1);

		textFieldMname = new JTextField();
		textFieldMname.setColumns(10);
		textFieldMname.setBounds(410, 92, 141, 20);
		registrationPanel.add(textFieldMname);
		// -------------------------------- end of main panel
		// --------------------------------

		// -------------------------------- start of about us panel
		// --------------------------------
		JPanel aboutUs = new JPanel();
		mainCards.add(aboutUs, "aboutUs");
		aboutUs.setLayout(null);

		JButton btnGoToMain = new JButton("Back to main");
		btnGoToMain.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnGoToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout ca = (CardLayout) mainCards.getLayout();
				ca.show(mainCards, "main");
				frame.setBounds(100, 100, 1200, 600);
			}
		});
		btnGoToMain.setBounds(10, 462, 269, 88);
		aboutUs.add(btnGoToMain);

		JLabel lblSmaDeliverySystem_1 = new JLabel("SMA DELIVERY");
		lblSmaDeliverySystem_1.setForeground(Color.BLACK);
		lblSmaDeliverySystem_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblSmaDeliverySystem_1.setBounds(430, 13, 441, 57);
		aboutUs.add(lblSmaDeliverySystem_1);

		JTextArea txtAboutUs = new JTextArea();
		txtAboutUs.setBackground(new Color(255, 255, 255));
		txtAboutUs.setFont(new Font("Arial", Font.PLAIN, 20));
		txtAboutUs.setText(
				"At SMA, we are a team of dedicated professionals who are committed to providing exceptional service to our customers. Our company was founded on the principle of delivering the highest quality products and services, and we have been doing so for many years. We are constantly striving to innovate and improve, and we are proud of the strong reputation we have built in the industry. Whether you are a long-time customer or a new one, we are here to help you with all of your needs. Thank you for choosing SMA. We look forward to working with you.");
		txtAboutUs.setLineWrap(true);
		txtAboutUs.setBounds(321, 81, 504, 257);
		aboutUs.add(txtAboutUs);
		// -------------------------------- start of about us panel
		// --------------------------------

	}

	public void setID() {
		try {
			String sql = "Select ID from Person where Email = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, txtEnterEmail.getText());
			ResultSet rs = pstmt.executeQuery();
			this.ID = Long.parseLong(rs.getString(1));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incorrect information");
		}
	}

}
