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
public class ClassByName2 {

	public boolean compareByName(Object inputClass) {
		if (inputClass.getClass().getName().equals("TrustedClassName")) {

			// Do something assuming you trust inputClass
			// ...
			return true;
		}
		return false;
	}
}
