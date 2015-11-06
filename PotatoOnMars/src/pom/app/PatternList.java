package pom.app;

import java.util.ArrayList;

public class PatternList extends ArrayList< ArrayList<String> >{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PatternList() {
		super();
	}
	
	public PatternList(PatternList pl) {
		super(pl);
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
