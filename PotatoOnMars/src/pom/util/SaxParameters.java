package pom.util;

import net.seninp.jmotif.sax.NumerosityReductionStrategy;

public class SaxParameters {
	
	public final static String dataFName = "data/learning/clear data.txt";
	public final static int slidingWindowSize = 24;
	public final static int paaSize = 24;
	public final static int alphabetSize = 12;
	public final static int steps = 24;
	public final static NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.fromValue(0);
	public final static double nThreshold = 0.01;
	
}
