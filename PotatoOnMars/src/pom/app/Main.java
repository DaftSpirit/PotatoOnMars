package pom.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecord;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class Main {
	
	private final static String dataFName = "D:\\Karus's world\\Cours\\3A\\+ Projet 3A\\Donnees\\48 donnees.txt";
	private final static int slidingWindowSize = 48;
	private final static int paaSize = 16;
	private final static int alphabetSize = 4;
	private final static NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.fromValue(0);
	private final static double nThreshold = 0.01;
	
	private static ArrayList< ArrayList<String> > ranks = new ArrayList< ArrayList<String> >();

	
	public static void main(String[] args) throws IOException, SAXException {
		
		// instantiate classes
		NormalAlphabet na = new NormalAlphabet();
		SAXProcessor sp = new SAXProcessor();
		//System.out.println(sp);

		// read the input file
		double[] ts = TSProcessor.readFileColumn(dataFName, 0, 0);

		// perform the discretization ( 240 motifs != pour chaque fenetre de 24 points)
		SAXRecords res = sp.ts2saxViaWindow(ts, slidingWindowSize, paaSize, 
		    na.getCuts(alphabetSize), nrStrategy, nThreshold);

		// print the output
		Set<Integer> index = res.getIndexes();
		System.out.println(index.size());
		for (Integer idx : index) {
		    System.out.println(idx + ", " + String.valueOf(res.getByIndex(idx).getPayload()));
		}

		
		System.out.println("\n------------------------------------------------\n");
		
		// Motifs r�currents
		
		// get the list of 10 most frequent SAX words
		ArrayList<SAXRecord> motifs = res.getMotifs(10);


		// print best 10 motifs
		for ( int i = 1; i <= 10; i++ )
		{
			SAXRecord topMotif = motifs.get(i-1);
			System.out.println("top " + i + " motif "+ String.valueOf(topMotif.getPayload()) + " seen " + 
			            topMotif.getIndexes().size() + " times.");
		}
		
		System.out.println("\n------------------------------------------------\n");
		
		// rangement des donn�es
		for(int i=0; i < slidingWindowSize ; i++)
		{
			ranks.add(new ArrayList<String>());
		}
		
		System.out.println(ranks);

		for (Integer idx : index) {
			//System.out.println(idx);
			//System.out.println("Je remplis le tableau " + idx%slidingWindowSize);
			ranks.get(idx%slidingWindowSize).add(String.valueOf(res.getByIndex(idx).getPayload()));
		}
		
    	System.out.println(ranks);
    	
	}

}
