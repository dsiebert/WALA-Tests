package errors.exposure;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Neutralization of log entries ,
 * 
 * The software does not neutralize/check output that is written to log ﬁles. An
 * attacker could forge log ﬁles and inject own code into log ﬁles.
 * 
 * If an attacker submits the string
 * "twenty-one%0a%0aINFO:+User+logged+out%3dbadguy", the following entry is
 * logged
 * 
 * INFO: Failed to parse val=twenty-one INFO: User logged out=badguy
 * 
 * @author Dennis Siebert
 * 
 */
public class LogFiles {

	protected void doSomething(String val) {

		try {
			int value = Integer.parseInt(val);
		} catch (NumberFormatException nfe) {
			Logger.getGlobal().log(Level.INFO,
					"Failed  to  parse  val  =  " + val);
		}
	}
}
