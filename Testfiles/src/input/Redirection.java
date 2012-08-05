package input;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Redirection {

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			String nextPage = request.getParameter("next");

			response.sendRedirect(nextPage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String returnUrl = request.getParameter("returnUrl");
			if (returnUrl == null)
				returnUrl = "http://kunagi.org/message.html#{message}";
			returnUrl = returnUrl.replace("{message}", "");

			response.sendRedirect(returnUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
