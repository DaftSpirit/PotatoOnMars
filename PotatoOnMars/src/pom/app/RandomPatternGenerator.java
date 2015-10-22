package pom.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RandomPatternGenerator {
	private final static int _PATTERN_SIZE_ = 24;
	private final static int _PATTERN_NUMBER_ = 10;
	
	private static File _FILE_=new File("data/generation.txt");
	private ArrayList<Double> array = new ArrayList<Double>();
	
	
	public void mainPattern(int width) {
		for(int i = 0 ; i < width-1 ; i++)
		{
			this.array.add(Math.random()*2);
		}
		this.array.add(Math.random()+5); // Ajout du pic
	}
	
	public void subPatterns(int ammount)
	{
		int size = this.array.size();
		for (int i = 0; i < ammount ; i++)
		{
			for(int j = 0 ; j < size ; j++)
			{
				this.array.add(this.array.get(j) + Math.random()/3); // Petites diff entres les semaines
			}
		}
	}
	
	public void noise(int nbPatterns){
		int target = (int)(1+ nbPatterns*Math.random());
		System.out.println("Semaine compliquée : " + target);
		for( int i = 1; i < _PATTERN_SIZE_ - 2 ; i++) // +1 / - 1 -> Les pics ne sont pas affectés
		{
			array.set(target * _PATTERN_SIZE_ + i, array.get(target * _PATTERN_SIZE_ + i) + Math.random()+1); // ajout du rdm
		}
	}
	
	public void write(ArrayList<Double> array) throws IOException{
		FileWriter fw = new FileWriter(_FILE_);
		for (int i = 0 ; i < this.array.size() ; i++)
		{
			fw.write(Double.toString(this.array.get(i)));  
			fw.write("\n"); 
		}
		fw.close();
	}
	
	public static void main(String[] args) {
		RandomPatternGenerator g = new RandomPatternGenerator();
		try {
			g.mainPattern(_PATTERN_SIZE_);
			g.subPatterns(_PATTERN_NUMBER_);
			g.noise(_PATTERN_NUMBER_);
			g.write(g.array);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
