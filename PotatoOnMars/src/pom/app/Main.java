package pom.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import pom.core.Analyser;
import pom.core.LearningTest;
import pom.core.SAXAnalyser;
import pom.core.Sorter;
import pom.util.Dolphin;
import pom.util.PatternList;
import pom.util.SaxParameters;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;

public class Main {

	private static String file = "data/test/result.txt";

	public static void main(String[] args) {

		// instantiate classes
		Analyser anal = new Analyser();
		Sorter so = new Sorter();
		LearningTest lt = new LearningTest(so);
		SAXAnalyser sax = new SAXAnalyser(anal, new SAXProcessor(),
				new NormalAlphabet());

		try {

			double[] ts = Dolphin.flipper(SaxParameters.dataFName);
			double[] tests = Dolphin.flipper("data/test/test.txt");

			PatternList pl = sax.process(ts);
			// System.out
			// .println("\n------------------courbAnalyser-------------------\n");

			double[][] data = anal.courbAnalyser(ts);
			FileWriter fw = new FileWriter(new File("data/test/result.txt"));
			for(int i = 0; i < 20; ++i){
				System.out.println(tests[i]);
				ts = so.patternTest(ts, anal, sax, tests[i], pl, (i+3) % SaxParameters.steps, 1.1);
				//double tmp = anal.checkAnomaly(tests[i], (i+3) % SaxParameters.steps);
				
				//fw.write(Double.toString(tmp) + "\n");
				//System.out.println(tmp);
			}
			fw.close();


			//System.out.println("\n avec la patternList de base\n");
			//so.patternTest(ts, anal, sax, 0.823 , pl, 3, 0.9);
			// lt.testForPatternList(pl);

			System.out.println("ANALYSE DE TOUTE LA DATA");
			//anal.analyseAlldata(ts);

			// System.out.println("ANALYSE D'UNE DATA");
			// double error = anal.checkAnomaly(2.50056124562001, 16);
			// System.out.println(error);

		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
	}
}