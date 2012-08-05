package calls.array;

/**
 * Private Array returned
 * 
 * A get-method returns a private marked array. Because arrays are mutable, the
 * content could be changed.
 * 
 * @author Dennis Siebert
 * 
 */
public class PrivateArrayReturn {
	private String[] colors;

	public String[] getColors() {
		return colors;
	}

}
