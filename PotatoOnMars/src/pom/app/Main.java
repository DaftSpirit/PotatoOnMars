package pom.app;

import java.io.IOException;
import java.util.Set;

import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class Main {
	
	private static PatternList ranks = new PatternList();
	
	public static void main(String[] args) throws IOException, SAXException {
		
		// instantiate classes
		NormalAlphabet na = new NormalAlphabet();
		SAXProcessor sp = new SAXProcessor();
		Analyser anal = new Analyser();

		// read the input file
		double[] ts = TSProcessor.readFileColumn(SaxParameters.dataFName, 0, 0);

		// perform the discretization ( 240 motifs != pour chaque fenetre de 24 points)
		SAXRecords res = sp.ts2saxViaWindow(ts, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
		    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);

		// print the output
		Set<Integer> index = res.getIndexes();
//		System.out.println(index.size());
//		for (Integer idx : index) {
//		    System.out.println(idx + ", " + String.valueOf(res.getByIndex(idx).getPayload()));
//		}
//		System.out.println("\n------------------------------------------------\n");
		
		
		// 10 motifs les plus recurrents
		anal.printRecurrentPatterns(res, 10);
		
		System.out.println("\n------------------------------------------------\n");
    	
		// Rangement dans le tableau
    	ranks = anal.makeRanksByHour(res, index);
    	
    	DataOracle oracle = new DataOracle(ts);
    	double future = oracle.prediction();
    	System.out.println(future + "the prediction");
    	
    	System.out.println("\n------------------------------------------------\n");
    	
    	System.out.println(ts[ts.length-1] + "the real double");
    	
    	System.out.println("\n-------------------another method for prediction----------------------------\n");
    	
    	future = oracle.betterPrediction(0.1);
    	System.out.println(future + "the prediction");
    	
    	System.out.println("\n------------------------------------------------\n");
    	
    	System.out.println(ts[ts.length-1] + "the real double");
    	
	}

}
