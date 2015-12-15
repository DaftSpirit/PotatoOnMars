package pom.app;

import java.io.IOException;
import java.util.Set;

import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;
import pom.core.Analyser;
import pom.core.Sorter;
import pom.util.DoublePatternList;
import pom.util.PatternList;
import pom.util.SaxParameters;

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
			
			for(int i = 0; i < plBad.size(); ++i) {
				for(String patternToTest : plBad.get(i)) {
					double color = so.stringPlacedGood(pl, i, patternToTest, 0.9);
					if (color == 0.0){
						System.out.println("No anomalies");
					}
					else if (color == Double.MAX_VALUE) {
						System.out.println("/!\\ There is a big anomaly /!\\");
					}
					else {
						System.out.println("There is a small anomaly");
					}
				}
			}
			
//			for(int i = 0; i < convertedBad.size(); ++i) {
//				for(double[] patternToTest : convertedBad.get(i)) {
//					double color = so.doublePlacedGood(converted, i, patternToTest, 1.3);
//					if (color == 0.0){
//						System.out.println("green");
//					}
//					else if (color == Double.MAX_VALUE) {
//						System.out.println("/!\\ RED ALERT /!\\");
//					}
//					else {
//						System.out.println("ORANGE");
//					}
//				}
//			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

}
