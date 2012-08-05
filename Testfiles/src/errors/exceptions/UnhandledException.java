package errors.exceptions;

/**
 * Unhandled errors
 * 
 * The software catches potential errors but does not handle the specific error
 * to just let it compile. Errors or intrusions maybe unseen.
 * 
 * @author Dennis Siebert
 * 
 */
public class UnhandledException {

	public void error() {

		try {
			doSomething();
		} catch (IllegalArgumentException e) {

		}
	}

	private void doSomething() throws IllegalArgumentException {
		// TODO Auto-generated method stub
	}
}
