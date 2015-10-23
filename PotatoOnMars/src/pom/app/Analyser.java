package pom.app;

import java.util.ArrayList;
import java.util.Set;

import net.seninp.jmotif.sax.datastructure.SAXRecord;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class Analyser {

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
	
	
	public PatternList makeRanksByHour( SAXRecords res, Set<Integer> index ){
		PatternList ranks = new PatternList();
		for(int i=0; i < SaxParameters.steps ; i++)
		{
			ranks.add(new ArrayList<String>());
		}
		for (Integer idx : index) {
			ranks.get(idx%SaxParameters.steps).add(String.valueOf(res.getByIndex(idx).getPayload()));
		}
    	System.out.println(ranks);
    	return ranks;
	}
	
	public PatternList convertRanks(PatternList pl) {
		WordConverter wc = new WordConverter();
		PatternList res = pl;
		for(int i = 0; i < res.size(); i++) {
			for(int j = 0; j < res.get(i).size(); j++) {
				double[] tmp = wc.converter(res.get(i).get(j));
				res.get(i).get(j).replace(res.get(i).get(j), wc.valueOfTab(tmp));
			}
		}
		return res;
	}
	
}
