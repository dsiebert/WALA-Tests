package aa.compare;

/**
 * Compares a class simply by its name, which could be problematic in the case
 * of multiple classes with the same name and following from that introduce
 * unwanted behaviour. Although attackers could send/use classes with the same
 * name and alter the behaviour.
 * 
 * @author Dennis Siebert
 * 
 */
public class ClassByName3 {

	public boolean compareByName(Object inputClass, String name) {
		if (inputClass.getClass().getName().equals(name)) {
			// Do something assuming you trust inputClass and name
			// ...
			return true;
		}
		return false;
	}

}
