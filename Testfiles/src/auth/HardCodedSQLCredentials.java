package auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HardCodedSQLCredentials {

	private String usr;
	private Connection conn;
	
	public void access(){
		try {
			DriverManager.getConnection("url",  "scott",  "tiger");

			conn = DriverManager.getConnection("jdbc:odbc:mydb;UID="+usr+";PWD=tiger");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
