package pom.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;

import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class Main {
	
	
	public static void main(String[] args) throws IOException, SAXException {
		
		// instantiate classes
		NormalAlphabet na = new NormalAlphabet();
		SAXProcessor sp = new SAXProcessor();
		Analyser anal = new Analyser();
		WordConverter wc = new WordConverter();
		
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

		PatternList pl = anal.makeRanksByHour(res, index); 
		
		//patterns convertis en doubles
		DoublePatternList converted = anal.convertRanks(pl);
		//System.out.println(converted);
		
		System.out.println("\n------------------------------------------------\n");
			
		// LISSAGE
		
		double[] ts_lisse = new double[ts.length];
		ts_lisse[0] = ts[0];
		ts_lisse[ts.length-1] = ts[ts.length-1];
		for(int i=1; i < ts.length-1; i++)
		{
			ts_lisse[i] = ( ts[i-1] + ts[i] + ts[i+1] ) / 3;
		}
		
		SAXRecords res_lisse = sp.ts2saxViaWindow(ts_lisse, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
			    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);
		Set<Integer> index_lisse = res.getIndexes();
		
		PatternList pl_lisse = anal.makeRanksByHour(res_lisse, index_lisse); 
		try
		{
		    FileWriter fw = new FileWriter ("data/48 donnees lissses.txt");
		 
		    for (double d : ts_lisse)
		    {
		        fw.write (String.valueOf (d));
		        fw.write ("\n");
		    }
		    fw.close();
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
    	
//    	
//    	//Deprecated : doesn't work
//    	DataOracle oracle = new DataOracle(ts);
//    	double future = oracle.prediction();
//    	System.out.println(future + "the prediction");
//    	
//    	System.out.println("\n------------------------------------------------\n");
//    	
//    	System.out.println(ts[ts.length-1] + "the real double");
//    	
//    	System.out.println("\n-------------------another method for prediction----------------------------\n");
//    	
//    	future = oracle.betterPrediction(0.03);
//    	System.out.println(future + "the prediction");
//    	
//    	System.out.println("\n------------------------------------------------\n");
//    	
//    	System.out.println(ts[ts.length-1] + "the real double");
    	
	}

}
