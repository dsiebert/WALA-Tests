package aa.compare;

/**
 * The software does not correctly compares two objects based on their conceptual design.
 * In this case it compares for make and model but not years, maybe a flaw.
 * 
 * @author Dennis Siebert
 *
 */
public class SematicComparisonRight {

	private String make;
	private String model;
	private int year;

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof SematicComparisonRight))
			return false;

		SematicComparisonRight t = (SematicComparisonRight) o;

		return (this.make.equals(t.getMake()) && this.model
				.equals(t.getModel()) && this.year == t.getYear());
	}
}
