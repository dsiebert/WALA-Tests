package errors.cleanup;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReturnInFinal {

	public void doSomething(String path) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			byte b[] = new byte[is.available()];
			is.read(b);

		} catch (Throwable t) {
			System.err.println(t);
		} finally {
			is.close();
			return;
		}
	}
}
