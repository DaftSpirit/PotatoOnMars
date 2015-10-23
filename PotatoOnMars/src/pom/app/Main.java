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
	
	
	
	private static ArrayList< ArrayList<String> > ranks = new ArrayList< ArrayList<String> >();

	
	public static void main(String[] args) throws IOException, SAXException {
		
		// instantiate classes
		NormalAlphabet na = new NormalAlphabet();
		SAXProcessor sp = new SAXProcessor();
		Analyser anal = new Analyser();

		// read the input file
		double[] ts = TSProcessor.readFileColumn(Analyser.dataFName, 0, 0);

		// perform the discretization ( 240 motifs != pour chaque fenetre de 24 points)
		SAXRecords res = sp.ts2saxViaWindow(ts, Analyser.slidingWindowSize, Analyser.paaSize, 
		    na.getCuts(Analyser.alphabetSize), Analyser.nrStrategy, Analyser.nThreshold);

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


//		// print best 10 motifs
//		for ( int i = 1; i <= 10; i++ )
//		{
//			SAXRecord topMotif = motifs.get(i-1);
//			System.out.println("top " + i + " motif "+ String.valueOf(topMotif.getPayload()) + " seen " + 
//			            topMotif.getIndexes().size() + " times.");
//		}
		
		anal.printRecurrentPatterns(motifs, 10);
		
		
		System.out.println("\n------------------------------------------------\n");
		
//		// rangement des donn�es
//		for(int i=0; i < Analyser.slidingWindowSize ; i++)
//		{
//			ranks.add(new ArrayList<String>());
//		}
//		for (Integer idx : index) {
//			//System.out.println(idx);
//			//System.out.println("Je remplis le tableau " + idx%slidingWindowSize);
//			ranks.get(idx%Analyser.slidingWindowSize).add(String.valueOf(res.getByIndex(idx).getPayload()));
//		}
//		
//    	System.out.println(ranks);
//    	
    	
    	anal.makeRanksByHour(res, index);
    	
	}

}
