package aa.parameter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class UndefinedParameter extends HttpServlet  {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String  address  =  req.getParameter("address").trim();
	}

}
