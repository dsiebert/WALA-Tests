package calls.switchStatement;

/**
 * Missing default case in switch statement
 * 
 * The default case of a switch statement is missing. Could return an error if
 * no cases matches the input.
 * 
 * @author Dennis Siebert
 * 
 */
public class DefaultCaseStatement {

	public void name(int i) {
		switch (i) {
		case 1:
			break;
		case 2:
			break;
		}
	}
}
