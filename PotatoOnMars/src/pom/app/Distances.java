package pom.app;

/**
 * computes different distances
 * @author joris
 *
 */
public class Distances {

	/**
	 * computes the distance between two letters
	 * @param c1 the first char
	 * @param c2 the second char
	 * @return the distance motherf***
	 * @author joris
	 */
	public static double distanceToLetter(char c1, char c2) {
		double res = 0.0;
		res = Math.abs((c1-96)-(c2-96));		
		return res;
	}
	
	/**
	 * computes the distance between two doubles
	 * @param d1 the first double
	 * @param d2 the second double
	 * @return the distance (abs(d1 - d2)) between d1&d2
	 * @author joris
	 */
	public static double distanceToDouble(double d1, double d2) {
		return Math.abs(d1 - d2);
	}
	
}
