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
public class UnhandledException2 {

	private void error() {
		try {
			doSomething();
		} catch (IllegalArgumentException e) {

		} catch (Exception ex) {

		}
		
		
	}

	private void doSomething() throws IllegalArgumentException {
		System.out.print("test");
	}
}
