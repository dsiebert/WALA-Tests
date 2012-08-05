package calls.switchStatement;

/**
 * Missing break in switch statement
 * 
 * That may lead to an execution on multiple switch statements one after
 * another.
 * 
 * @author Dennis Siebert
 * 
 */
public class BreakStatement {

	public void name(int i) {
		switch (i) {
		case 1:

		case 2:
			break;
		default:
			break;
		}
	}
}
