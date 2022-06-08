import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class EmployeeData {

	
	public static Connection connectDB() {
		String dbLocation=System.getProperty("user.dir")+"\\src\\DataBase\\Employee.db";
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con=DriverManager.getConnection("jdbc:sqlite:"+dbLocation);
//			JOptionPane.showMessageDialog(null, "Connection made");
			return con;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Connection Error");
			System.exit(0);
			return null;
		}
	}	
	
}
