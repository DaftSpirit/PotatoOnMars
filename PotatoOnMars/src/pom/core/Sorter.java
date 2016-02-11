package pom.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import net.seninp.jmotif.sax.SAXException;
import pom.util.Distances;
import pom.util.DoublePatternList;
import pom.util.PatternList;
import pom.util.SaxParameters;

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
	 * place.
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
		// System.out.println(patternToTest);
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

		if (hourSorted == hour) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * tries to places an unknown pattern (a string) at the good place
	 * 
	 * @param pl
	 *            : the list of patterns learned (strings)
	 * @param hour
	 *            : the hour of the pattern to test (for comparison)
	 * @param pattern
	 *            : the pattern to test (a string)
	 * @param thresold
	 *            : the limit of the distance to be good even if the pattern is
	 *            well placed
	 * @return true if the pattern is well placed
	 * @author joris
	 */
	public double stringPlacedGood(PatternList pl, int hour,
			String patternToTest, double thresold) {
		double[] distances = new double[SaxParameters.steps];

		/* computes the distances between every hour */
		int idx = 0;
		double distTmp = 0.0;
		for (ArrayList<String> hours : pl) {
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
		 * takes the smallest distance and put the patternToTest at the good
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

		if ((hourSorted == hour)) {
			if (min > thresold) {
				return min;
			} else {
				return 0.0;
			}
		} else {
			return Double.MAX_VALUE;
		}
	}

	/**
	 * tries to places an unknown pattern (an array of doubles) at the good
	 * place
	 * 
	 * @param dpl
	 *            : the list of patterns learned (arrays of doubles)
	 * @param hour
	 *            : the hour of the pattern to test (for comparison)
	 * @param pattern
	 *            : the pattern to test (an array of doubles)
	 * @param thresold
	 *            : the limit of the distance to be good even if the pattern is
	 *            well placed
	 * @return true if the pattern is well placed
	 * @author joris
	 */
	public double doublePlacedGood(DoublePatternList dpl, int hour,
			double[] patternToTest, double thresold) {
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

		if ((hourSorted == hour)) {
			if (min > thresold) {
				return min;
			} else {
				return 0.0;
			}
		} else {
			return Double.MAX_VALUE;
		}
	}

	/**
	 * The main function for patterns. Takes a new double, the array of learning
	 * and a thresold to tell if the data is an anomaly or not
	 * 
	 * @param learning : the array of doubles which represents the data for all the hours
	 * @param anal : the analyser to make a patternList
	 * @param sax : the object that makes the patterns
	 * @param data : the new double that has arrived
	 * @param learn : the learning PatternList
	 * @param hour : the hour at which the data has arrived
	 * @param thresold : the thresold of distance to tell if a pattern is an anomaly or not
	 * @return : the new array of double updated with data
	 * @throws IOException
	 * @throws SAXException
	 */
	public double[] patternTest(double[] learning, Analyser anal,
			SAXAnalyser sax, double data, PatternList learn, int hour, double thresold)
			throws IOException, SAXException {

		// Calendar cal = Calendar.getInstance();
		// int hour = cal.get(Calendar.HOUR);

		double copy[] = new double[learning.length + 1];

		for (int i = 0; i < copy.length - 1; ++i) {
			copy[i] = learning[i];
		}
		copy[copy.length - 1] = data;
		PatternList pl = sax.process(copy);

		String patternToTest = pl.get(hour).get(pl.get(hour).size() - 1);

		double res = this
				.stringPlacedGood(learn, hour, patternToTest, thresold);
		if (res > thresold) {
			if (res != Double.MAX_VALUE) {
				System.out.println("Bien Place mais au dessus du seuil de : "
						+ (res - thresold));
			} else {
				System.out.println("PAS BIEN CLASSE !");
			}
		} else {
			System.out.println("BIEN PLACE !");
		}
		return copy;
	}

}
