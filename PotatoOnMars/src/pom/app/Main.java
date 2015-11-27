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
		System.out.println(converted);
		System.out.println("\n------------------------------------------------\n");
			
		// LISSAGE
		double[] ts_lisse = anal.medianStraightener5Points(ts);
		anal.dataWritter(ts_lisse,"data/48 donnees lissses.txt");
		
		SAXRecords res_lisse = sp.ts2saxViaWindow(ts_lisse, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
			    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);
		Set<Integer> index_lisse = res.getIndexes();
		
		PatternList pl_lisse = anal.makeRanksByHour(res_lisse, index_lisse); 
		
		System.out.println("\n-----------------------------------------------\n");
		
		//TRI / SORTING
		if(so.sortAndPrint(pl, 0, 0)) {
			System.out.println("Success !!!\n");
		}
		else {
			System.out.println("Failure !!\n");
		}
		if(so.sortAndPrint(pl_lisse, 6, 0)) {
			System.out.println("Success !!!\n");
		}
		else{
			System.out.println("Failure !!\n");
		}
		
		System.out.println("\n------------------courbAnalyser-------------------\n");
		
		double[][] data = anal.courbAnalyser(ts);
		
		System.out.println("\n---------------Learning & Percentages-------------\n");
		
		System.out.println("\n avec la patternList de base\n");
		lt.testForPatternList(pl);
		
		System.out.println("\n avec la patternList lisse");
		lt.testForPatternList(pl_lisse);
		
		System.out.println("\n avec une DoublePatternList");
		lt.testForDoublePatternList(converted);
    	// loi normale de probabilité euclidienne (utilisation de la moyenne et écart type pour trouver la probabilité d'appartenance au paquet 18h)
		// apprentissage sur des données propres
		// courbe appliquant la loi de probabilité de chaque point (pic sur un donnée anormale)
		// fenetre pour le comportement
		// idem pour les fenetres, analyser l'écart type (meme si elle est bien classée) car il peut etre trop grand quand meme)
		// courbe de résultats, avec un seuil qui dit ce qu'es une anomalie ou non (fenetrée)
		
		System.out.println("\n---------------Gaussian distribution-------------\n");
		
		double test = 1.5;
		double tab[] = new double[20];
		double tests[] = {1.8,1.9,1.8,1.8,1.7,1.9,1.8,2.0,2.1,2.3,2.4,2.1,2.0,2.3,2.2,2.1,4.0,1.9,1.8,2.3}; // H = 6
		System.out.println("\nLoi de probabilité de l'heure 6\n");
		
		for(int i = 0; i < tests.length ; i++)
		{
			System.out.println(anal.normalLaw(test, 0.1730, 1.9923)); // loi gaussienne
			test = test + 0.1;
		}
		
		System.out.println("\nRépartition du jeu de données\n");
		for(int i = 0; i < tests.length ; i++)
		{
			tab[i] = anal.normalLaw(tests[i], 0.1730, 1.9923);
			System.out.println(tab[i]);
		}
		
		System.out.println("\n% de chance d'anomalie ?\n");
		for(int i = 0; i < tests.length ; i++)
		{
			if (tab[i] > 1) tab[i] = 1;
			tab[i] = (1 - tab[i])*100;
			System.out.println(tab[i]);
		}
		
	}

}
