package input;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Command {

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			String btype = request.getParameter("backuptype");
			String cmd = new String("cmd.exe  /K  \" c:\\util\\rmanDB.bat  "
					+ btype + "&&c:\\utl\\cleanup.bat\"");
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost2(HttpServletRequest request, HttpServletResponse response) {

		String btype = request.getParameter("backuptype");
		String cmd = new String("cmd.exe  /K  \" c:\\util\\rmanDB.bat  "
				+ btype + "&&c:\\utl\\cleanup.bat\"");
	}

	public void doPost3(HttpServletRequest request, HttpServletResponse response) {
		try {
			String btype = request.getParameter("backuptype");

			Runtime.getRuntime().exec(btype);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
