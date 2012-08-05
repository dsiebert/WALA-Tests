package aa.storage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The software stores sensitive data in a cookie, a ﬁle, an executable,
 * more..., which makes it easy accessible. An executable can be reverse
 * engineered.
 * 
 * @author Dennis Siebert
 * 
 */
public class CookiePlainText extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String acctID = "465813268";
		resp.addCookie(new Cookie("userAccountID", acctID));
	}
}
