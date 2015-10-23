package pom.app;

import java.util.ArrayList;
import java.util.Set;

import net.seninp.jmotif.sax.datastructure.SAXRecord;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class Analyser {

	static ArrayList< ArrayList<String> > ranks = new ArrayList< ArrayList<String> >();
	
	
	public void printRecurrentPatterns( SAXRecords res , int nb ){
		// print best "nb" motifs
		ArrayList<SAXRecord> motifs = res.getMotifs(nb);
		for ( int i = 1; i <= nb; i++ )
		{
			SAXRecord topMotif = motifs.get(i-1);
			System.out.println("top " + i + " motif "+ String.valueOf(topMotif.getPayload()) + " seen " + 
			            topMotif.getIndexes().size() + " times.");
		}
	}
	
	
	public ArrayList< ArrayList<String> > makeRanksByHour( SAXRecords res, Set<Integer> index ){
		for(int i=0; i < SaxParameters.slidingWindowSize ; i++)
		{
			ranks.add(new ArrayList<String>());
		}
		for (Integer idx : index) {
			ranks.get(idx%SaxParameters.slidingWindowSize).add(String.valueOf(res.getByIndex(idx).getPayload()));
		}
    	System.out.println(ranks);
    	return ranks;
	}
	
	
	
}
