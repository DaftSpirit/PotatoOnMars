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
	
}
