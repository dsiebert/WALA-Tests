package input;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SQLInjection {

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {

			Connection conn = DriverManager.getConnection("url", "scott",
					"tiger");
			String item = request.getParameter("item");
			String query = "SELECT  FORM  records  WHERE  item=’" + item + "’";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doPos2(HttpServletRequest request, HttpServletResponse response) {
		try {

			Connection conn = DriverManager.getConnection("url", "scott",
					"tiger");
			String item = request.getParameter("item");
			PreparedStatement stmt = conn.prepareStatement(item);
			ResultSet rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doPost3(HttpServletRequest request, HttpServletResponse response) {
		try {

			Connection conn = DriverManager.getConnection("url", "scott",
					"tiger");
			String item = request.getParameter("item");
			String query = "SELECT  FORM  records  WHERE  item=’" + item + "’";
			PreparedStatement stmt = conn.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
