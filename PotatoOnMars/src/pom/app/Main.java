package pom.app;

import java.io.IOException;
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
		Sorter so = new Sorter();
		LearningTest lt = new LearningTest(so);
		
		// read the input file
		double[] ts = TSProcessor.readFileColumn(SaxParameters.dataFName, 0, 0);

		// perform the discretization ( 240 motifs != pour chaque fenetre de 24 points)
		SAXRecords res = sp.ts2saxViaWindow(ts, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
		    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);

		// print the output
		Set<Integer> index = res.getIndexes();
		
		// 10 motifs les plus recurrents
		anal.printRecurrentPatterns(res, 10);
		
		System.out.println("\n------------------------------------------------\n");
    	
		// Rangement dans le tableau
		PatternList pl = anal.makeRanksByHour(res, index); 
		
		//patterns convertis en doubles
		DoublePatternList converted = anal.convertRanks(pl);
		
		System.out.println("\n------------------------------------------------\n");
			
		// LISSAGE
		double[] ts_lisse = anal.medianStraightener5Points(ts);
		
		anal.dataWritter(ts_lisse);
		
		SAXRecords res_lisse = sp.ts2saxViaWindow(ts_lisse, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
			    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);
		Set<Integer> index_lisse = res.getIndexes();
		
		PatternList pl_lisse = anal.makeRanksByHour(res_lisse, index_lisse); 
		
		System.out.println("\n-----------------------------------------------\n");
		//TRI / SORTING
		
		
		if(so.sortAndPrint(pl, 0, 0)) {
			System.out.println("GG WP !!!\n");
		}
		else {
			System.out.println("FAK !! on n'a pas reussi\n");
		}
		if(so.sortAndPrint(pl_lisse, 6, 0)) {
			System.out.println("GG WP !!!\n");
		}
		else{
			System.out.println("FAK !! on n'a pas reussi\n");
		}
		
		System.out.println("\n------------------courbAnalyser-------------------\n");
		
		anal.courbAnalyser(pl);
		
		System.out.println("\n---------------Learning & Percentages-------------\n");
		
		System.out.println("\n avec la patternList de base\n");
		lt.testForPatternList(pl);
		
		System.out.println("\n avec la patternList lisse");
		lt.testForPatternList(pl_lisse);
    	
	}

}
