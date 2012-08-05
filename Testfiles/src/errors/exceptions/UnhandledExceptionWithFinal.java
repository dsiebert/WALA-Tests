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

public class UnhandledExceptionWithFinal {

	private void error() {
		try {
			doSomething();
		} catch (IllegalArgumentException e) {
			
		} catch (Exception e) {
			String ex = "exception";
		}finally{
			String cleanup = "cleanup";
		}
	}

	private void doSomething() throws IllegalArgumentException {
		System.out.print("test");
	}
}
