package pom.app;

import java.util.ArrayList;
import java.util.Set;

import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.datastructure.SAXRecord;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class Analyser {

	final static String dataFName = "data/48 donnees.txt";
	final static int slidingWindowSize = 24;
	final static int paaSize = 8;
	final static int alphabetSize = 4;
	final static NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.fromValue(0);
	final static double nThreshold = 0.01;
	static ArrayList< ArrayList<String> > ranks = new ArrayList< ArrayList<String> >();
	
	public void printRecurrentPatterns( ArrayList<SAXRecord> motifs , int nb ){
		// print best 10 motifs
		for ( int i = 1; i <= nb; i++ )
		{
			SAXRecord topMotif = motifs.get(i-1);
			System.out.println("top " + i + " motif "+ String.valueOf(topMotif.getPayload()) + " seen " + 
			            topMotif.getIndexes().size() + " times.");
		}
	}
	
	
	public ArrayList< ArrayList<String> > makeRanksByHour( SAXRecords res, Set<Integer> index ){
		for(int i=0; i < slidingWindowSize ; i++)
		{
			ranks.add(new ArrayList<String>());
		}

		for (Integer idx : index) {
			//System.out.println(idx);
			//System.out.println("Je remplis le tableau " + idx%slidingWindowSize);
			ranks.get(idx%slidingWindowSize).add(String.valueOf(res.getByIndex(idx).getPayload()));
		}
		
    	System.out.println(ranks);
    	return ranks;
	}
	
	
	
}
