package aa.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Plaintext storage and retrieval of a password. The software stores sensible
 * password information in plaintext. The software could get compromised.
 * 
 * @author Dennis Siebert
 * 
 */
public class PropertyPlainText {

	public void load() {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("config.properties"));
			String password = prop.getProperty("password");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void save(String pw) {
		try {
			Properties prop = new Properties();
			prop.store(new FileOutputStream("config.properties"), pw);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
