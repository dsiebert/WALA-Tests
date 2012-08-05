package aa.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * A software de-serializes data without checking that the resulting data is
 * valid. Data or code could be modiﬁed without provided functions.
 * 
 * @author Dennis Siebert
 * 
 */
public class DeserializeWithoutCheck {

	public void deserialize() {

		try {
			File file = new File("object.obj");
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					file));
			javax.swing.JButton button = (javax.swing.JButton) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
