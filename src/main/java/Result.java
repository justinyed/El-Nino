/**
 * 
 */

/**
 * @author Justin Yedinak
 * @version 2021-02-23
 */
public class Result {
	public final String classification;
	public final String intensity;
	public final double mei;

	public Result(String classification, String intensity, double mei) {
		this.classification = classification;
		this.intensity = intensity;
		this.mei = mei;
	}
}