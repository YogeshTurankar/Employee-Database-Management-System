import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javafaker.Faker;

public class Utils extends Employee {

	static Connection con = null;
	static PreparedStatement pst = null;
	static ResultSet rs = null;

	public static int empID() {
		con = EmployeeData.connectDB();

		String sql = "SELECT EmployeeID FROM EmployeeDetails order by EmployeeID desc limit 1";

		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			String ID1 = rs.getString("EmployeeID");
			ID1 = ID1.substring(2);
			int ID = Integer.parseInt(ID1);
			rs.close();
			pst.close();
			return ID;

		} catch (Exception e) {
			return 0;
		}

	}

	public static int NIID() {
		con = EmployeeData.connectDB();

		String sql = "SELECT NINumber FROM EmployeeDetails order by NINumber desc limit 1";

		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			String ID1 = rs.getString("NINumber");
			ID1 = ID1.substring(2);
			int ID = Integer.parseInt(ID1);
			rs.close();
			pst.close();
			return ID;

		} catch (Exception e) {
			return 0;
		}

	}

	public static String age() {

		String age1 = Employee.birth;
		LocalDate dob = LocalDate.parse(age1);

		LocalDate curDate = LocalDate.now();

		if ((dob != null) && (curDate != null)) {
			int years1 = Period.between(dob, curDate).getYears();
			String years = Integer.toString(years1);
			return years;
		} else {
			return null;
		}
	}

	public static Matcher name(String name) {
		Pattern p = Pattern.compile("[A-Z]?[a-z]*");
		Matcher m = p.matcher(name);
		return m;
	}

	public static boolean dateOfBirth() {
		String date = Employee.DOB;
		String format = "[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}";
		boolean D = date.matches(format);
		return D;
	}

}
