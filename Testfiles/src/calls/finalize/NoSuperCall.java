package calls.finalize;

/**
 * ﬁnalize method does not call super.ﬁnalize()
 * 
 * The Java Language Specification states that it is a good practice for a
 * ﬁnalize() method to call super.ﬁnalize().
 * 
 * @author Dennis Siebert
 * 
 */
public class NoSuperCall {

	@Override
	protected void finalize() throws Throwable {

	}
}
