package pom.app;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import net.seninp.jmotif.sax.datastructure.SAXRecord;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

public class Analyser {

	public void printRecurrentPatterns( SAXRecords res , int nb ){
		// print best "nb" motifs
		ArrayList<SAXRecord> motifs = res.getMotifs(nb);
		for ( int i = 1; i <= nb; i++ )
		{
			SAXRecord topMotif = motifs.get(i-1);
			System.out.println("top " + i + " motif "+ String.valueOf(topMotif.getPayload()) + " seen " + 
			            topMotif.getIndexes().size() + " times.");
		}
	}
	
	
	public PatternList makeRanksByHour( SAXRecords res, Set<Integer> index ){
		PatternList ranks = new PatternList();
		for(int i=0; i < SaxParameters.steps ; i++)
		{
			ranks.add(new ArrayList<String>());
		}
		for (Integer idx : index) {
			ranks.get(idx%SaxParameters.steps).add(String.valueOf(res.getByIndex(idx).getPayload()));
		}
    	System.out.println(ranks);
    	return ranks;
	}
	
	public DoublePatternList convertRanks(PatternList pl) {
		WordConverter wc = new WordConverter();
		DoublePatternList res = new DoublePatternList();
		
		for(int i=0; i < SaxParameters.steps ; i++)
		{
			double[] e = new double[SaxParameters.paaSize];
			res.add(new ArrayList<double[]>());
			for(int j = 0; j < pl.get(i).size(); ++j) {
				res.get(i).add(e);
			}
		}

		for(int i = 0; i < pl.size(); i++) {
			for(int j = 0; j < pl.get(i).size(); j++) {
				double[] tmp;
				//System.out.println(pl.get(i).get(j));
				tmp = wc.converter(pl.get(i).get(j));
				for(int k = 0; k < tmp.length; ++k){
					res.get(i).get(j)[k] = tmp[k];
				}
			}
		}		
		return res;
	}
	
	public double[] medianStraightener5Points(double[] data)
	{
		double [] res = new double[data.length];
		res[0] = (data[0]+data[1]+data[2])/3;
		res[1] = (data[0]+data[1]+data[2]+data[3])/4;
		for(int i=2; i < data.length-2; i++)
		{
			res[i] = ( data[i-2] + data[i-1] + data[i] + data[i+1] + data[i+2] ) / 5;
		}
		res[data.length-2] = (data[data.length-1]+data[data.length-2]+data[data.length-3]+data[data.length-4])/4;
		res[data.length-1] = (data[data.length-1]+data[data.length-2]+data[data.length-3])/3;
		return res;
	}
	
	public void dataWritter(double[] data)
	{
		try
		{
		    FileWriter fw = new FileWriter ("data/48 donnees lissses.txt");
		 
		    for (double d : data)
		    {
		        fw.write (String.valueOf (d));
		        fw.write ("\n");
		    }
		    fw.close();
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
	
}
