import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Justin Yedinak
 * @version 2021-02-23
 */
public class ElNino {

	/** ArrayList of events for a two-year period */
	private static ArrayList<Result> events;

	/** Array of meis for a two-year period */
	private static double[] meis;

	/**
	 * Return earliest event in a given two-year period and if Classification is
	 * equal, then return most severe Intensity.
	 * 
	 * @param file      with MEI data
	 * @param firstYear of two to check for event
	 * @return result containing event details
	 * @throws IllegalArgumentException "invalid year: y” where y is the year given
	 *                                  as input (firstYear) if y is not found.
	 */
	public static Result process(File file, int firstYear) throws IllegalArgumentException {
		try {
			Scanner fin = new Scanner(file);
			fin.nextLine(); // Skip Header

			// Acquire Data
			String[] splitLine1;
			int curYear;
			while (fin.hasNext()) {

				splitLine1 = fin.nextLine().split("\t");
				curYear = Integer.parseInt(splitLine1[0]);

				// Right Year?
				if (curYear == firstYear && fin.hasNext()) {

					String[] splitLine2 = fin.nextLine().split("\t");
					fin.close();

					// Create array of bi-monthly mei values for the two years
					combineArrays(splitLine1, splitLine2);

					return calcResult(meis);

				}
			}
			fin.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("invalid year: " + firstYear);

	}

	/**
	 * Set meis to double array with only MEI data points from Two Consec. Years
	 * 
	 * @param em1 string array 1
	 * @param em2 string array 2
	 */
	private static void combineArrays(String[] em1, String[] em2) {
		double[] elem = new double[(em1.length * 2) - 1];

		for (int i = 0; i < em1.length - 1; i++) {
			elem[i] = Double.parseDouble(em1[i + 1]);
			elem[i + em1.length - 1] = Double.parseDouble(em2[i + 1]);
		}

		meis = elem;

	}

	/**
	 * A warm El Niño event occurs when the MEI is at or above +0.5 for at least
	 * five consecutive bi-monthly periods.
	 * 
	 * A cold La Niña event occurs when the MEI is at or below -0.5 for at least
	 * five consecutive bi-monthly periods.
	 * 
	 * @param meis for a two year period
	 * @return Earliest event and if Classification is equal, then return most
	 *         severe Intensity.
	 */
	private static Result calcResult(double[] meis) {
		events = new ArrayList<>();
		ArrayList<Double> elNinoMarkers = new ArrayList<>();
		ArrayList<Double> laNinaMarkers = new ArrayList<>();

		// Stores values that may be a part of an event

		for (int i = 0; i < meis.length - 1; i++) {

			// Check for El Nino Event Marker (mei >= 0.5)
			// if marker found, then reset La Nina marker count and store El Nino Marker.
			if (meis[i] >= 0.5) {
				laNinaMarkers.clear();
				elNinoMarkers.add(meis[i]);
				checkForEvent(elNinoMarkers, i);
			}

			// Check for La Nina Event Marker (mei <= -0.5)
			// if marker found, then reset El Nino marker count and store La Nina Marker.
			else if (meis[i] <= -0.5) {
				elNinoMarkers.clear();
				laNinaMarkers.add(meis[i]);
				checkForEvent(laNinaMarkers, i);
			}

			// Reset Both (since not consecutive).
			else {
				elNinoMarkers.clear();
				laNinaMarkers.clear();
			}
		}

		// Add "Neither" Event in case events is empty.
		events.add(new Result(calcClassification(0), calcIntensity(0), 0));

		// Return earliest event and
		// if Classification is equal, then return most severe Intensity.
		sortEvents(events);
		return events.get(0);

	}

	/**
	 * Checks if meisList could be an event if so add Result to events ArrayList
	 * 
	 * @param meisList ArrayList of markers to check for event
	 * @param index    current index to check
	 */
	private static void checkForEvent(List<Double> meisList, int index) {
		if (Math.abs(meis[index + 1]) < 0.5 && meisList.size() >= 5) {
			meisList.sort((mei1, mei2) -> {
				mei1 = (Double) Math.abs(mei1);
				mei2 = (Double) Math.abs(mei2);
				return mei2.compareTo(mei1);
			});

			double mei = meisList.get(0);
			meisList.clear();
			events.add(new Result(calcClassification(mei), calcIntensity(mei), mei));
		}
	}

	/**
	 * 
	 * A warm El Niño event occurs when the MEI is at or above +0.5
	 * 
	 * A cold La Niña event occurs when the MEI is at or below -0.5
	 * 
	 * @param mei to use in calc
	 * @return Classification of event as a string
	 */
	private static String calcClassification(double mei) {
		String classif = "Neither";
		if (mei >= 0.5) {
			classif = "El Nino";
		} else if (mei <= -0.5) {
			classif = "La Nina";
		}
		return classif;
	}

	/**
	 * The threshold is further broken down into weak (with a 0.5 to 1.0 anomaly),
	 * moderate (1.0 to 1.5), strong (1.5 to 2.0) and very strong (>= 2.0) events.
	 *
	 * @param mei largest value for event
	 * @return proper intensity as string
	 */
	private static String calcIntensity(double mei) {
		mei = Math.abs(mei);
		String intensity = "none";
		if (mei >= 2.0) {
			intensity = "very strong";
		} else if (mei >= 1.5) {
			intensity = "strong";
		} else if (mei >= 1.0) {
			intensity = "moderate";
		} else if (mei >= 0.5) {
			intensity = "weak";
		}
		return intensity;
	}

	/**
	 * Custom Sort. Sorts by MEI value in descending order and if events
	 * classifications are not equal then the events are considered equal in sort
	 * priority
	 * 
	 * @param events to be sorted
	 */
	private static void sortEvents(List<Result> events) {

		events.sort((ev1, ev2) -> {
			Double mei1 = (Double) Math.abs(ev1.mei);
			Double mei2 = (Double) Math.abs(ev2.mei);

			// If events classifications are not equal,
			// then deem the events equal in sort priority
			if (!ev1.classification.equals(ev2.classification)) {
				return 0;
			}

			return mei2.compareTo(mei1);
		});

	}

}
