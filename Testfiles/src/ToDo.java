

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDo {

	public static String part;
	
	public void doSomething(String arg) {
		try {
			Connection conn = DriverManager.getConnection("url", "scott",
					"tiger");
			String query = "SELECT  FORM  records  WHERE  item=" + arg + part;
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
