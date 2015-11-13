package pom.app;

import java.util.ArrayList;

/**
 * takes one element from the ranks and tries to sort it at the good place
 * 
 * @author joris
 */
public class Sorter {

	private PatternList pl2;
	private DoublePatternList dpl2;

	/**
	 * @author joris Constructor
	 */
	public Sorter() {
		// CARPE DIEM
	}

	/**
	 * takes one pattern out of the list and tries to reput it at the same
	 * place. prints ont sysou the result
	 * 
	 * @param pl
	 *            patternList of sorted patterns
	 * @param idxToSort
	 *            : index of the element to take from the patternList and to
	 *            sort
	 * @param hour
	 *            : hour to take the tested pattern from
	 * @return true if the pattern is correctly sorted
	 * @author joris
	 */
	public boolean sortAndPrint(PatternList pl, int hour, int idxToSort) {
		this.pl2 = new PatternList(pl);
		String patternToTest = this.pl2.get(hour).get(idxToSort);
//		System.out.println(patternToTest);
		this.pl2.get(hour).remove(idxToSort);
		double[] distances = new double[SaxParameters.steps];

		/* computes the distances between every hour */
		int idx = 0;
		double distTmp = 0.0;
		for (ArrayList<String> hours : this.pl2) {
			int numberOfWords = 0;
			for (String pattern : hours) {
				for (int i = 0; i < patternToTest.length(); ++i) {
					char currentChar = patternToTest.charAt(i);
					distTmp += Distances.distanceToLetter(currentChar,
							pattern.charAt(i));
					numberOfWords++;
				}
			}
			distances[idx] = distTmp / numberOfWords;
			distTmp = 0.0;
			numberOfWords = 0;
			idx++;
		}

		/*
		 * takes the smallest distance and put the patterToTest at the good
		 * place
		 */
		int hourSorted = -1;
		double min = Double.MAX_VALUE;
		for (int j = 0; j < distances.length; ++j) {
			// System.out.println(distances[j]);
			if (distances[j] < min) {
				min = distances[j];
				hourSorted = j;
			}
		}
//		System.out.println("hour found : " + hourSorted + "\n");
//		System.out.println("real hour : " + hour + "\n");
		if (hourSorted == hour) {
			// System.out.println("GG WP !!!\n");
			return true;
		} else {
			// System.out.println("FAK !! on n'a pas reussi\n");
			return false;
		}

	}

	/**
	 * tries to sort one element of the patternlist
	 * 
	 * @param dpl
	 *            pattern list of double[]
	 * @param hour
	 *            hour to sort
	 * @param idxToSort
	 *            pattern at hour to sort
	 * @return true if the pattern is sorted correctly
	 * @author joris
	 */
	public boolean sortDoubles(DoublePatternList dpl, int hour, int idxToSort) {
		this.dpl2 = new DoublePatternList(dpl);
		double[] patternToTest = this.dpl2.get(hour).get(idxToSort);
		this.dpl2.get(hour).remove(idxToSort);
		double[] distances = new double[SaxParameters.steps];

		/*
		 * computes the distances between the pattern to test and all other
		 * words
		 */
		int idx = 0;
		double distTmp = 0.0;
		for (ArrayList<double[]> hours : dpl) {
			int numberOfWords = 0;
			for (double[] pattern : hours) {
				for (int i = 0; i < patternToTest.length; i++) {
					double currentDouble = patternToTest[i];
					distTmp += Distances.distanceToDouble(currentDouble,
							pattern[i]);
					numberOfWords++;
				}
			}
			distances[idx] = distTmp / numberOfWords;
			distTmp = 0.0;
			numberOfWords = 0;
			idx++;
		}

		/*
		 * takes the smallest distance and put the patterToTest at the good
		 * place
		 */
		int hourSorted = -1;
		double min = Double.MAX_VALUE;
		for (int j = 0; j < distances.length; ++j) {
			if (distances[j] < min) {
				min = distances[j];
				hourSorted = j;
			}
		}
//		System.out.println("hour found : " + hourSorted + "\n");
//		System.out.println("real hour : " + hour + "\n");
		if (hourSorted == hour) {
			return true;
		} else {
			return false;
		}
	}

}
