package pom.app;

import java.util.ArrayList;

public class DoublePatternList extends ArrayList<ArrayList<double[]>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DoublePatternList() {
		super();
	}
	
	public DoublePatternList(DoublePatternList dpl){
		for(int i = 0 ; i < dpl.size() ; i++)
		{
			this.add(new ArrayList<double[]>());
		}
		for(int k = 0; k < this.size(); k++){
			for(int j = 0; j < dpl.get(k).size(); ++j) {
				this.get(k).add(dpl.get(k).get(j));
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
			for(int j = 0; j < this.get(i).size(); ++j) {
				tmp.append("[ ");
				for(int k = 0; k < this.get(i).get(j).length; ++k) {
					tmp.append(this.get(i).get(j)[k] + " ");
				}
				tmp.append("]\n");
			}
		}	
		return tmp.toString();
	}

}
