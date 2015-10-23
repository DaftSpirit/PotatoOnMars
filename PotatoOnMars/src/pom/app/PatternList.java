package pom.app;

import java.util.ArrayList;

public class PatternList extends ArrayList< ArrayList<String> >{
	
	
	@Override
	public String toString ()
	{
		int h=0;
		StringBuffer tmp = new StringBuffer();
		for(int i = 0; i < this.size(); i++)
		{
			h = (i+23)%SaxParameters.slidingWindowSize;
			tmp.append(i+"h-"+h+"h: ");
			tmp.append(this.get(i) + "\n");
			//tmp.append("-" i);
		}	
		return tmp.toString();
	}

}
