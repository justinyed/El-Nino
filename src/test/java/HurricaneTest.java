import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Objects;

import org.junit.jupiter.api.Test;

/**
 * 
 */

/**
 * @author Justin Yedinak
 * @version 2021-02-23
 */
class HurricaneTest {

	@Test
	public void GettersTest() {
		Hurricane h = new Hurricane("test");
		assertEquals("test", h.getName());
		assertEquals(1, h.getCategory());
	}

	@Test
	public void ConstructorTest() {

		Throwable e1 = assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			Hurricane h = new Hurricane(null);
		});
		assertEquals("Hurricane name cannot be null", e1.getMessage());

		Throwable e2 = assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			Hurricane h = new Hurricane("");
		});
		assertEquals("Hurricane name cannot be empty", e2.getMessage());

		Hurricane h = new Hurricane("test");
		assertEquals("test", h.getName());
		assertEquals(1, h.getCategory());
	}

	@Test
	public void IncrementDecrementTest() {
		Hurricane h = new Hurricane("test");
		int[] possibles = { 0, 1, 2, 3, 4, 5 };

		// Check Decrement
		h.decrement();
		h.decrement();
		assertEquals(0, h.getCategory());
		// Check Increment
		for (int p : possibles) {
			assertEquals(p, h.getCategory());
			h.increment();
		}
	}

	@Test
	public void toStringTest() {
		Hurricane h = new Hurricane("Nina");
		h.increment();
		assertEquals("Hurricane [name=" + "Nina" + ", category=" + 2 + "]", h.toString());
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void EqualsTest() {
		Hurricane a = new Hurricane("Nina");
		a.increment();
		Hurricane b = new Hurricane("Pinta");
		assertEquals(false, a.equals(b));
		Hurricane c = new Hurricane("Nina");
		assertEquals(false, a.equals(c));
		Hurricane d = new Hurricane("Nina");
		d.increment();
		assertEquals(true, a.equals(d));
		assertEquals(true, a.equals(a));
		assertEquals(false, a.equals(1));
		assertEquals(false, a.equals(null));
	}

	@Test
	public void hashCodeTest() {
		Hurricane a = new Hurricane("Nina");
		a.increment();
		assertEquals(Objects.hash(a.getCategory(), a.getName()), a.hashCode());
	}

	@Test
	public void compareToTest() {
		Hurricane a = new Hurricane("Nina");
		Hurricane b = new Hurricane("Nina");
		a.compareTo(b);
		assertEquals(0, a.compareTo(b));
		assertEquals(0, b.compareTo(a));
		a.increment();
		assertEquals(-1, a.compareTo(b));
		assertEquals(1, b.compareTo(a));

	}

}