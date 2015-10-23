package pom.app;

import java.util.ArrayList;

public class PatternList extends ArrayList< ArrayList<String> >{
	
	private ArrayList< ArrayList<String> > ranks = new ArrayList< ArrayList<String> >();

	public ArrayList< ArrayList<String> > getRanks() {
		return ranks;
	}

	public void setRanks(ArrayList< ArrayList<String> > ranks) {
		this.ranks = ranks;
	}
	
//	@Override
//	public String toString ()
//	{
//		StringBuffer tmp = new StringBuffer();
//		for(int i = 0; i < this.ranks.size(); i++)
//		{
//			tmp.append(i+"h: ");
//			tmp.append(ranks.get(i) + "\n");
//		}	
//		return tmp.toString();
//	}

}
