package pom.core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import pom.util.DoublePatternList;
import pom.util.PatternList;
import pom.util.SaxParameters;
import pom.util.WordConverter;
import net.seninp.jmotif.sax.datastructure.SAXRecord;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

/**
 * Class that analyse stuff for you, use this as a tool
 * @author Karuskrokro
 *
 */
public class Analyser {
	
	/**
	 * Print best nb patterns
	 * @param rec : SAXRecord object to use
	 * @param nb : how many patterns
	 */
	public void printRecurrentPatterns( SAXRecords rec , int nb ){
		ArrayList<SAXRecord> motifs = rec.getMotifs(nb);
		for ( int i = 1; i <= nb; i++ )
		{
			SAXRecord topMotif = motifs.get(i-1);
			System.out.println("top " + i + " motif "+ String.valueOf(topMotif.getPayload()) + " seen " + 
			            topMotif.getIndexes().size() + " times.");
		}
	}
	
	/**
	 * Returns a PatternList with the different payloads data in it. The PatternList is sorted by hour.
	 * @param rec : SAXRecord object to use
	 * @param index : The SAXRecord's index
	 * @return
	 */
	public PatternList makeRanksByHour( SAXRecords rec, Set<Integer> index ){
		PatternList ranks = new PatternList();
		for(int i=0; i < SaxParameters.steps ; i++)
		{
			ranks.add(new ArrayList<String>());
		}
		for (Integer idx : index) {
			ranks.get(idx%SaxParameters.steps).add(String.valueOf(rec.getByIndex(idx).getPayload()));
		}
    	System.out.println(ranks);
    	return ranks;
	}
	
	/**
	 * Convert a PatternList in a DoublePatternList
	 * @param pl : PatternList to use
	 * @return : DoublePatternList, same as a PatternList but with doubles in it instead of Strings
	 */
	public DoublePatternList convertRanks(PatternList pl) {
		WordConverter wc = new WordConverter();
		DoublePatternList res = new DoublePatternList();
		
		for(int i=0; i < SaxParameters.steps ; i++)
		{
			res.add(new ArrayList<double[]>());
		}

		for(int i = 0; i < pl.size(); i++) {
			for(int j = 0; j < pl.get(i).size(); j++) {
				double[] tmp;
				tmp = wc.converter(pl.get(i).get(j));
				res.get(i).add(tmp);
			}
		}		
		return res;
	}
	
	/**
	 * Simple 5 points median straghtener
	 * @param data : data to straighten
	 * @return data straightened
	 */
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
	
	/**
	 * Writes a double's array into a file.
	 * @param data data to read
	 */
	public void dataWritter(double[] data,String filename)
	{
		try
		{
		    FileWriter fw = new FileWriter (filename);
		    for (double d : data)
		    {
		        fw.write (String.valueOf (d));
		        fw.write ("\n");
		    }
		    fw.close();
		}
		catch (IOException exception)
		{
		    System.out.println ("Data Error");
		}
	}
	
	/**
	 * Calculate the minimum / maximum / med / variance / EC of an double's array. The size of the array depends of the SaxParameters's Steps
	 * and all the results are calculated for each hour depending also on the sax params's steps
	 * @param data
	 * @return double[SawParameters.Steps][5]. double[x][0] -> minimums. double[x][1] -> maximums
	 * double[x][2] -> all the medians. double[x][3] -> variance. double[x][4] -> EC 
	 */
	public double[][] courbAnalyser (double[] data)
	{
		double[][] res = new double[SaxParameters.steps][5];
		double moy , xi, variance =  0;
		int tmp = 1; // Astuce de n+1
		int cnt = 0;

		for (int i = 0 ; i < SaxParameters.steps ; i++)
		{
			for(int j=0 ; j < 2 ; j++) {res[i][j] = data[i];}
		}
		
		for (int i = SaxParameters.steps ; i < data.length ; i++)
		{
			// Min
			if(res[i%SaxParameters.steps][0] > data[i]) {res[i%SaxParameters.steps][0] = data[i];}
			// Max
			if(res[i%SaxParameters.steps][1] < data[i]){res[i%SaxParameters.steps][1] = data[i];}
		}
		
		for (int i = 0 ; i < SaxParameters.steps ; i++)
		{
			moy = 0;
			cnt = 0;
			for(int j = 0 ; j < (int)(data.length/SaxParameters.steps) + tmp ; j++)
			{
				if( i >= (int)(data.length%SaxParameters.steps) ){tmp = 0;} // Astuce pour les moyennes a n+1 éléments
				moy += data[i+j*SaxParameters.steps];
				cnt++;
			}
			res[i%SaxParameters.steps][2] = moy / cnt;
		}
		
		tmp = 1;
		for (int i = 0 ; i < SaxParameters.steps ; i++)
		{
			xi = 0;
			cnt = 0;
			for(int j = 0 ; j < (int)(data.length/SaxParameters.steps) + tmp ; j++)
			{
				if( i >= (int)(data.length%SaxParameters.steps) ){tmp = 0;}
				xi += Math.pow(data[i+j*SaxParameters.steps] - res[i%SaxParameters.steps][2], 2);
				cnt++;
			}
			variance = xi / cnt;
			res[i%SaxParameters.steps][3] = variance;
			res[i%SaxParameters.steps][4] = Math.sqrt(variance);
		}

		System.out.println("		MIN		MAX			MOY		VARIANCE		EC");
		for(int k =0 ; k < res.length ; k++)
		{
			System.out.print("h : " + k + " [ ");
			System.out.print(res[k][0]);
			System.out.print("  ");
			System.out.print(res[k][1]);
			System.out.print("  ");
			System.out.print(res[k][2]);
			System.out.print("  ");
			System.out.print(res[k][3]);
			System.out.print("  ");
			System.out.print(res[k][4]);
			System.out.println(" ]");
		}
		return res;
	}
	
	public double normalLaw (double value, double ec, double moy)
	{
		double res = 0;
		res = ( 1 / (ec * Math.sqrt(2*Math.PI))) * Math.exp( (-1.0/2.0) * Math.pow(((value - moy)/ec), 2)); // loi normale ?
		return res;
	}
	
	
	
	

}
