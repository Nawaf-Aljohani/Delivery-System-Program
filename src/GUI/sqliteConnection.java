package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import org.sqlite.SQLiteConfig;

public class sqliteConnection {
	Connection conn = null;

	public static Connection dbConnector() {

		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();  
	        config.enforceForeignKeys(true);
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Comff\\Desktop\\Ics321Project\\JavaDB.db", config.toProperties() ); // this is the path to the sqlite database  make sure to include "jdbc:sqlite:xxxxxxxxxxxxxx"
			//JOptionPane.showMessageDialog(null, "Connectedok"); //don't forget to import all java swing stuff
			return conn;
		} catch (Exception error) {
			JOptionPane.showMessageDialog(null, error);
			return null;

		}

	}
}

