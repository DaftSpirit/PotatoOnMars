package pom.app;

import java.io.IOException;
import pom.core.Analyser;
import pom.core.LearningTest;
import pom.core.SAXAnalyser;
import pom.core.Sorter;
import pom.util.PatternList;
import pom.util.SaxParameters;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;

public class Main {

	public static void main(String[] args) {

		// instantiate classes
		Analyser anal = new Analyser();
		Sorter so = new Sorter();
		LearningTest lt = new LearningTest(so);
		SAXAnalyser sax = new SAXAnalyser(anal, new SAXProcessor(),
				new NormalAlphabet());

		System.out
				.println("\n------------------discretisation-------------------\n");
		try {
			double[] corrupted_data;
			PatternList pl;
			corrupted_data = TSProcessor.readFileColumn(
					"data/learning/ROC_pic.txt", 0, 0);
			double[] ts = TSProcessor.readFileColumn(
					SaxParameters.dataFName, 0, 0);
			
			
			pl = sax.process(ts);

			// LISSAGE
			double[] ts_lisse = anal.medianStraightener5Points(ts);
			anal.dataWritter(ts_lisse, "data/48 donnees lissses.txt");

			System.out
					.println("\n------------------discretisation lissée-------------------\n");

			// Tableau lisse
			PatternList pl_lisse;
			double[] data_lisses = TSProcessor.readFileColumn(
					"data/48 donnees lissses.txt", 0, 0);
			pl_lisse = sax.process(data_lisses);

			System.out
					.println("\n------------------courbAnalyser-------------------\n");

			double[][] data = anal.courbAnalyser(ts);

			System.out
					.println("\n---------------Learning & Percentages-------------\n");

			System.out.println("\n avec la patternList de base\n");
			lt.testForPatternList(pl);

			System.out.println("\n avec la patternList lisse");
			lt.testForPatternList(pl_lisse);

			System.out.println("ANALYSE DE TOUTE LA DATA");
			anal.analyseAlldata(corrupted_data);

			System.out.println("ANALYSE D'UNE DATA");
			double error = anal.checkAnomaly(2.50056124562001, 16);
			System.out.println(error);

		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}

	}

}