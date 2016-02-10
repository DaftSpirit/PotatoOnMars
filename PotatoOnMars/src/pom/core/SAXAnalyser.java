package pom.core;

import java.io.IOException;
import java.util.Set;

import pom.util.PatternList;
import pom.util.SaxParameters;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class SAXAnalyser {
	
	private Analyser anal;
	private SAXProcessor sp;
	private NormalAlphabet na;
	
	public SAXAnalyser(Analyser anal, SAXProcessor sp, NormalAlphabet na) {
		this.anal = anal;
		this.sp = sp;
		this.na = na;
	}
	
	public PatternList process(double[] datas) throws IOException, SAXException{
		
		//System.out.println("\n------------------discretisation-------------------\n");

		// perform the discretization ( 240 motifs != pour chaque fenetre de 24 points)
		SAXRecords res = sp.ts2saxViaWindow(datas, SaxParameters.slidingWindowSize, SaxParameters.paaSize, 
		    na.getCuts(SaxParameters.alphabetSize), SaxParameters.nrStrategy, SaxParameters.nThreshold);

		// print the output
		Set<Integer> index = res.getIndexes();
		
		PatternList pl = anal.makeRanksByHour(res, index);
		
		return pl;
	}
}
