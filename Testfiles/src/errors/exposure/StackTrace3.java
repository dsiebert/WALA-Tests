package errors.exposure;

/**
 * Error handling / Exposure of System Data
 * 
 * Weakness in improperly handling errors and may return a full stack trace to a
 * user or potential attacker.
 * 
 * @author Dennis Siebert
 * 
 */
public class StackTrace3 {

	public void error() {
		try {

		} catch (IllegalArgumentException ase) {
			ase.printStackTrace();
		}
	}

}
