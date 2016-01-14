package pom.debugSerializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import pom.core.Analyser;
import pom.util.PatternList;
import pom.util.SaxParameters;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class TestMain {
	public static void main(String[] args) throws IOException, SAXException {

		// instantiate classes
		NormalAlphabet na = new NormalAlphabet();
		SAXProcessor sp = new SAXProcessor();
		Analyser anal = new Analyser();

		// read the input file
		double[] ts = TSProcessor.readFileColumn(SaxParameters.dataFName, 0, 0);

		// perform the discretization ( 240 motifs != pour chaque fenetre de 24
		// points)
		SAXRecords res = sp.ts2saxViaWindow(ts,
				SaxParameters.slidingWindowSize, SaxParameters.paaSize,
				na.getCuts(SaxParameters.alphabetSize),
				SaxParameters.nrStrategy, SaxParameters.nThreshold);

		// print the output
		Set<Integer> index = res.getIndexes();

		// 10 motifs les plus recurrents
		anal.printRecurrentPatterns(res, 10);

		System.out
				.println("\n------------------------------------------------\n");

		// Rangement dans le tableau
		PatternList pl = anal.makeRanksByHour(res, index);
		FileOutputStream fos = new FileOutputStream("data/learning/patterns.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		try {
			oos.writeObject(pl);			
			oos.flush();
		}finally {
			try {
				oos.close();
			}finally {
				fos.close();
			}
		}
		
		FileInputStream fis = new FileInputStream("data/learning/patterns.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		try {
			PatternList read = (PatternList) ois.readObject();
			System.out.println(read.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
			ois.close();
			} finally {
				fis.close();
			}
		} 
	}
}
