package calls.finalize;

/**
 * Explicit Call to Finalize()
 * 
 * The software makes a call to ﬁnalize from the outside of the ﬁnalize block
 * which typically results in at least two executions of the ﬁnalize statement.
 * 
 * @author Dennis Siebert
 * 
 */
public class ExplicitCall {

	public static void main(String[] args) throws Throwable {
		new PublicFinalize().finalize();
	}
}
