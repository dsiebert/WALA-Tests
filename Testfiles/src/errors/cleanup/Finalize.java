package errors.cleanup;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Improper cleanup after thrown exceptions
 * 
 * The software does not clean up / release resources after an exception has
 * been caught. In case of an error the is.close() may not been executed at all.
 * 
 * @author Dennis Siebert
 * 
 */
public class Finalize {

	public void doSomething(String path) {
		try {
			InputStream is = new FileInputStream(path);
			byte b[] = new byte[is.available()];
			is.read(b);
			is.close();
		} catch (Throwable t) {
			System.err.println(t);
		}
	}
}
