import java.util.Objects;

/**
 * 
 */

/**
 * 
 * @author Justin Yedinak
 * @version 2021-02-23
 */
public class Hurricane {
	private String name;
	private int category;

	/**
	 * @param name of hurricane
	 * @throws IllegalArgumentException if string is null or empty
	 */
	public Hurricane(String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("Hurricane name cannot be null");
		}
		if (name.equals("")) {
			throw new IllegalArgumentException("Hurricane name cannot be empty");
		}
		this.name = name;
		this.category = 1;
	}

	/**
	 * getter for the name of the hurricane
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter for the category of the hurricane
	 * 
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * Increments category by 1 but not beyond 5.
	 */
	public void increment() {
		if (this.category < 5) {
			this.category += 1;
		}
	}

	/**
	 * Decrements category by 1 but not below 0.
	 */
	public void decrement() {
		if (this.category > 0) {
			this.category -= 1;
		}
	}

	/**
	 * hash code Method
	 */
	@Override
	public int hashCode() {
		return Objects.hash(category, name);
	}

	/**
	 * Equals Method
	 * 
	 * @param obj to compare
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if ((getClass() != obj.getClass())) {
			return false;
		}
		Hurricane other = (Hurricane) obj;
		return category == other.category && Objects.equals(name, other.name);
	}

	/**
	 * Compares hurricanes by name and (if equal) by category (returns -1, 0 or 1
	 * only)
	 * 
	 * @param obj to compare
	 */
	public int compareTo(Object obj) {
		if ((getClass() == obj.getClass())) {
			Hurricane o = (Hurricane) obj;
			int c = this.toString().compareTo(o.toString());

			if (c == 0) {
				return 0;
			}

			if (c < 0) {
				return 1;
			}

		}
		return -1;
	}

	/**
	 * returns “Hurricane [name=n, category=c]” and where n and c are the values of
	 * name and category.
	 */
	@Override
	public String toString() {
		return "Hurricane [name=" + name + ", category=" + category + "]";
	}

}
