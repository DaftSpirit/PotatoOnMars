package pom.app;

import net.seninp.jmotif.sax.NumerosityReductionStrategy;

public class SaxParameters {
	
	final static String dataFName = "data/48 donnees.txt";
	final static int slidingWindowSize = 23;
	final static int paaSize = 8;
	final static int alphabetSize = 4;
	final static int steps = 24;
	final static NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.fromValue(0);
	final static double nThreshold = 0.01;
	
}
