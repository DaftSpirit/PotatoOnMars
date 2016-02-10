package pom.app;

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
	
	public double[] patternTest(double[] learning, Analyser anal, Sorter so,
			SAXAnalyser sax, double data, PatternList learn) throws IOException, SAXException {
		
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR);
		
		double thresold = 0.9;
		double copy[] = new double[learning.length + 1];
		
		for(int i = 0; i < copy.length-1; ++i){
			copy[i] = learning[i];
		}
		copy[copy.length-1] = data;
		PatternList pl = sax.process(copy);
		
		String patternToTest = pl.get(hour).get(pl.get(hour).size()-1);
		
		double res = so.stringPlacedGood(learn, hour, patternToTest, thresold);
		System.out.println(res);
		
		return copy;
	}

	public static void main(String[] args) {

		// instantiate classes
		Analyser anal = new Analyser();
		Sorter so = new Sorter();
		LearningTest lt = new LearningTest(so);
		SAXAnalyser sax = new SAXAnalyser(anal, new SAXProcessor(),
				new NormalAlphabet());

		try {

			double[] ts = Dolphin.flipper(SaxParameters.dataFName);
			
			PatternList pl = sax.process(ts);
			//System.out
			//		.println("\n------------------courbAnalyser-------------------\n");

			double[][] data = anal.courbAnalyser(ts);

			/*System.out
					.println("\n---------------Learning & Percentages-------------\n");

			System.out.println("\n avec la patternList de base\n");*/
			//lt.testForPatternList(pl);

			System.out.println("ANALYSE DE TOUTE LA DATA");
			anal.analyseAlldata(ts);

			//System.out.println("ANALYSE D'UNE DATA");
			//double error = anal.checkAnomaly(2.50056124562001, 16);
			//System.out.println(error);

		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
	}
}