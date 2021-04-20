import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 
 */

/**
 * 
 * @author Justin Yedinak
 * @version 2021-02-25
 */
class ElNinoTest {

	private Path getPath(String filename) throws URISyntaxException {
		URL url = getClass().getResource(filename);
		return url == null ? null : Path.of(url.toURI());
	}

	private String filename = "mei.ext_index.txt";

	private File file;

	@Test
	void processTest() {

		new ElNino();

		try {
			file = getPath(filename).toFile();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
//		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		int[] yearsToTest = { 1880, 1897, 1927, 1948, 1996, 1991 };
		String[] classesExpected = { "La Nina", "El Nino", "Neither", "La Nina", "El Nino", "El Nino" };
		String[] intensExpected = { "moderate", "strong", "none", "weak", "very strong", "very strong" };
		double[] meiExpected = { -1.069, 1.543, 0, -0.957, 3.214, 2.404 };

		for (int i = 0; i < yearsToTest.length; i++) {
			Result R = ElNino.process(file, yearsToTest[i]);
			assertEquals(classesExpected[i], R.classification);
			assertEquals(intensExpected[i], R.intensity);
			assertEquals(meiExpected[i], R.mei);

		}

	}

	@Test
	void processExceptionTest() {
		try {
			file = getPath(filename).toFile();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		int year = -1;

		Throwable exception1 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ElNino.process(file, year);
		});
		assertEquals("invalid year: " + year, exception1.getMessage());

		Throwable exception2 = Assertions.assertThrows(Exception.class, () -> {

			ElNino.process(null, year);
		});
		exception2.getStackTrace();
	}

}
