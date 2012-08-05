package input;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PathTraversal {

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		 String  rName  =  request.getParameter("reportName");
		 File  rFile  =  new  File("/urs/local/apfr/reports/"  +  rName);
		 rFile.delete();
	}
	
	public void doPost2(HttpServletRequest request, HttpServletResponse response) {
		 String  rName  =  request.getParameter("reportName");
	}
	
	public void doPost3(HttpServletRequest request, HttpServletResponse response) {
		 String  rName  =  request.getParameter("reportName");
		 File  rFile  =  new  File(rName);
		 rFile.delete();
	}

}
