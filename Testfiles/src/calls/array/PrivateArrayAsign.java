package calls.array;

/**
 * Assign data to private array
 * 
 * Same like giving public access to an array. Allowing to modify the private
 * array.
 * 
 * @author Dennis Siebert
 * 
 */
public class PrivateArrayAsign {

	private String[] userRoles;

	public void setUserRoles(String[] userRoles) {
		this.userRoles = userRoles;
	}

}
