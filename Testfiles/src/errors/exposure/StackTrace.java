package errors.exposure;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Error handling / Exposure of System Data
 * 
 * Weakness in improperly handling errors and may return a full stack trace to a
 * user or potential attacker.
 * 
 * @author Dennis Siebert
 * 
 */
public class StackTrace {

	public void doSomething() {
		try {

		} catch (IllegalArgumentException ase) {
			Logger.getGlobal().log(Level.INFO, "Caught:  " + ase.toString());
		}
	}

}
