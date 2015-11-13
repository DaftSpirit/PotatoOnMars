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
			res.add(new ArrayList<double[]>());
		}

		for(int i = 0; i < pl.size(); i++) {
			for(int j = 0; j < pl.get(i).size(); j++) {
				double[] tmp;
				//System.out.println(pl.get(i).get(j));
				tmp = wc.converter(pl.get(i).get(j));
				res.get(i).add(tmp);
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
	
	// SUPERPOSITION DE COURBES + VARIANCE + ECART TYPE
	public double[][] courbAnalyser (double[] data)
	{
		// INITIALISATION
		double[][] res = new double[SaxParameters.steps][5];
		for (int i = 0 ; i < SaxParameters.steps ; i++)
		{
			// Min / Max
			for(int j=0 ; j < 2 ; j++)
			{
				res[i][j] = data[i];
			}
		}
		
		// DONNEES QUI SUIVENT
		for (int i = SaxParameters.steps ; i < data.length ; i++)
		{
			// Min
			if(res[i%SaxParameters.steps][0] > data[i]) {res[i%SaxParameters.steps][0] = data[i];}
				
			// Max
			if(res[i%SaxParameters.steps][1] < data[i]){res[i%SaxParameters.steps][1] = data[i];}
			
		}
		
		double moy =  0;
		int tmp = 1;
		int cnt = 0;
		for (int i = 0 ; i < SaxParameters.steps ; i++)
		{
			moy = 0;
			cnt = 0;
			//System.out.println("----");
			for(int j = 0 ; j < (int)(data.length/SaxParameters.steps) + tmp ; j++)
			{
				if( i >= (int)(data.length%SaxParameters.steps) )
				{
					tmp = 0;
				}
				//System.out.println(i+j*SaxParameters.steps);
				//System.out.println(data[i+j*SaxParameters.steps]);
				moy += data[i+j*SaxParameters.steps];
				cnt++;
			}
			res[i%SaxParameters.steps][2] = moy / cnt;
			
			
		}
		
		// VARIANCE : somme (x-xm)^2 / n // EC
		double xi = 0;
		tmp = 1;
		double variance = 0;
		for (int i = 0 ; i < SaxParameters.steps ; i++)
		{
			xi = 0;
			cnt = 0;
			for(int j = 0 ; j < (int)(data.length/SaxParameters.steps) + tmp ; j++)
			{
				if( i >= (int)(data.length%SaxParameters.steps) )
				{
					tmp = 0;
				}
				
				xi += Math.pow(data[i+j*SaxParameters.steps] - res[i%SaxParameters.steps][2], 2);
				//System.out.println(xi);
				cnt++;
			}
			variance = xi / cnt;
			res[i%SaxParameters.steps][3] = variance;
			res[i%SaxParameters.steps][4] = Math.sqrt(variance);
		}

		// PRINT
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
	
	
}
