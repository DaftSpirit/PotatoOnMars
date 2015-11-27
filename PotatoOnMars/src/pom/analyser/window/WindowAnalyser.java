package pom.analyser.window;

import java.io.IOException;
import java.util.Set;

import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;
import pom.app.*;

public class WindowAnalyser {

	public static void main(String[] args) {
		NormalAlphabet na = new NormalAlphabet();
		SAXProcessor sp = new SAXProcessor();
		Analyser anal = new Analyser();
		Sorter so = new Sorter();
		
		try {
			
			double[] ts = TSProcessor.readFileColumn(SaxParameters.dataFName, 0, 0);
			double[] tsBad = TSProcessor.readFileColumn("data/learning/bad patterns.txt", 0, 0);
			
			SAXRecords resBad = sp.ts2saxViaWindow(tsBad, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
				    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);
			
			SAXRecords res = sp.ts2saxViaWindow(ts, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
				    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);
			
			Set<Integer> indexBad = resBad.getIndexes();
			Set<Integer> index = res.getIndexes();
			
			PatternList plBad = anal.makeRanksByHour(resBad, indexBad); 
			PatternList pl = anal.makeRanksByHour(res, index);
			
			DoublePatternList convertedBad = anal.convertRanks(plBad);
			DoublePatternList converted = anal.convertRanks(pl);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

}
