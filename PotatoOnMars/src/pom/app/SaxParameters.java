package pom.app;

import net.seninp.jmotif.sax.NumerosityReductionStrategy;

public class SaxParameters {
	
	final static String dataFName = "data/learning/clear data.txt";
	final static int slidingWindowSize = 24;
	final static int paaSize = 24;
	final static int alphabetSize = 12;
	final static int steps = 24;
	final static NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.fromValue(0);
	final static double nThreshold = 0.01;
	
}
