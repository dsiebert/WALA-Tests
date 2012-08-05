package calls.clone;

/**
 * clone method does not call super.clone()
 * 
 * An overwritten clone method does not uses super.clone(). A potential subclass
 * will not clone itself. Instead it gets an object of the superclass with a
 * potential other behaviour
 * 
 * @author Dennis Siebert
 * 
 */
public class SubClone extends SuperClone {

	public final Object clone() throws CloneNotSupportedException {

		Object returnMe = super.clone();
		return returnMe;

	}
}
