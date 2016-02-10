package pom.util;

import java.io.IOException;

import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.TSProcessor;

public class Dolphin {
	
	public static double[] flipper(String fileName) throws IOException, SAXException{
		double[] ts = TSProcessor.readFileColumn(
				fileName, 0, 0);		
		
		for(int i = 0; i < ts.length; ++i){
			double temp = ts[i];
			ts[i] = ts[(ts.length-1)-i];
			ts[(ts.length-1)-i] = temp;
		}
		
		return ts;
	}
}
