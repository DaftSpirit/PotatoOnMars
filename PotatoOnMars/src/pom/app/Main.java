package pom.app;

import java.io.IOException;
import java.util.Set;

import pom.core.Analyser;
import pom.core.LearningTest;
import pom.core.Sorter;
import pom.util.PatternList;
import pom.util.SaxParameters;
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
		double[] corrupted_data = TSProcessor.readFileColumn("data/learning/ROC_pic.txt", 0, 0);

		System.out.println("\n------------------discretisation-------------------\n");

		// perform the discretization ( 240 motifs != pour chaque fenetre de 24 points)
		SAXRecords res = sp.ts2saxViaWindow(ts, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
		    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);

		// print the output
		Set<Integer> index = res.getIndexes();
		    	
		// Rangement dans le tableau (patterns)
		PatternList pl = anal.makeRanksByHour(res, index); 
		
		//patterns convertis en doubles
		//DoublePatternList converted = anal.convertRanks(pl); BUGGED
		//System.out.println("\n------------------------------------------------\n");
			
		// LISSAGE
		double[] ts_lisse = anal.medianStraightener5Points(ts);
		anal.dataWritter(ts_lisse,"data/48 donnees lissses.txt");
		
		System.out.println("\n------------------discretisation lissée-------------------\n");

		// Discrétisation sur la liste lisse
		SAXRecords res_lisse = sp.ts2saxViaWindow(ts_lisse, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
			    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);
		Set<Integer> index_lisse = res.getIndexes();
		
		// Tableau lisse
		PatternList pl_lisse = anal.makeRanksByHour(res_lisse, index_lisse); 		
		
		System.out.println("\n------------------courbAnalyser-------------------\n");
		
		double[][] data = anal.courbAnalyser(ts);
		
		System.out.println("\n---------------Learning & Percentages-------------\n");
		
		System.out.println("\n avec la patternList de base\n");
		lt.testForPatternList(pl);
		
		System.out.println("\n avec la patternList lisse");
		lt.testForPatternList(pl_lisse);


		System.out.println("ANALYSE DE TOUTE LA DATA");
		anal.analyseAlldata(corrupted_data);

		System.out.println("ANALYSE D'UNE DATA");
		double error = anal.checkAnomaly(2.50056124562001, 16);
		System.out.println(error);
		
		
	}
	
	

}
