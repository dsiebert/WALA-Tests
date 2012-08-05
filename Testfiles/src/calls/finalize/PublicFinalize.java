package calls.finalize;

/**
 * Finalize declared public
 * 
 * The software declares a ﬁnalize method public and thou is accessible can
 * explicitly be called.
 * 
 * @author Dennis Siebert
 * 
 */
public class PublicFinalize {

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}
}
