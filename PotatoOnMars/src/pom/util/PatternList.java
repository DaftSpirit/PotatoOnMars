package pom.util;

import java.io.Serializable;
import java.util.ArrayList;

public class PatternList extends ArrayList< ArrayList<String> > implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PatternList() {
		super();
	}
	
	// DUNNO? STOP ME BAISER LA LISTE ORIGINALE PLZ
	public PatternList(PatternList pl) {
		for(int i = 0 ; i < pl.size() ; i++)
		{
			this.add(new ArrayList<String>());
		}
		for(int k = 0; k < this.size(); k++){
			for(int j = 0; j < pl.get(k).size(); ++j) {
				this.get(k).add(pl.get(k).get(j));
			}
		}
	}

	@Override
	public String toString ()
	{
		int h=0;
		StringBuffer tmp = new StringBuffer();
		for(int i = 0; i < this.size(); i++)
		{
			h = ((i+SaxParameters.slidingWindowSize)%SaxParameters.steps);
			tmp.append(i+"h-"+h+"h: ");
			tmp.append(this.get(i) + "\n");
		}	
		return tmp.toString();
	}

}
