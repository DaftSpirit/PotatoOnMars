package pom.app;

import java.util.ArrayList;

/**
 * takes one element from the ranks and tries to sort it at the good place
 * 
 * @author joris
 */
public class Sorter {

	private PatternList pl2;

	/**
	 * @author joris Constructor
	 */
	public Sorter() {
		//CARPE DIEM
	}

	/**
	 * takes one pattern out of the list and tries to reput it at the same
	 * place. prints ont sysou the result
	 * @param pl
	 *            patternList of sorted patterns
	 * @param idxToSort
	 *            : index of the element to take from the patternList and to
	 *            sort
	 * @param hour
	 *            : hour to take the tested pattern from
	 * @author joris
	 */
	public void sortAndPrint(PatternList pl, int hour, int idxToSort) {
		this.pl2 = pl;
		String patternToTest = this.pl2.get(hour).get(idxToSort);
		this.pl2.get(hour).remove(idxToSort);
		double[] distances = new double[SaxParameters.steps];
		
		/* computes the distances between every hour */
		for (int i = 0; i < patternToTest.length(); ++i) {
			double distTmp = 0.0;
			for (ArrayList<String> hours : pl) {
				int idx = 0;
				int numberOfWords = 0;
				for (String pattern : hours) {
					char currentChar = patternToTest.charAt(i);
					distTmp += Distances.distanceToLetter(currentChar,
							pattern.charAt(i));
					numberOfWords++;
				}
				distances[idx] = distTmp/numberOfWords;
				idx++;
			}
		}
		
		/* takes the smallest distance and put the patterToTest at the good place */
		int hourSorted = -1;
		for(int j = 0; j < distances.length; ++j) {
			double min = Double.MAX_VALUE;
			if(distances[j] < min) {
				min = distances[j];
				hourSorted = j;
			}
		}
		System.out.println("hour found : " + hourSorted + "\n");
		System.out.println("real hour : " + hour + "\n");
		if(hourSorted == hour) {
			System.out.println("GG WP !!!");
		}
		else {
			System.out.println("FAK !! on n'a pas reussi");
		}
				
	}

}
