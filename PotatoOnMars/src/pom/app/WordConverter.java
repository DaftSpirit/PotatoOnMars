package pom.app;

public class WordConverter {

	public WordConverter() {
		super();
	}
	
	/**
	 * converts to a double[] a word with alphabet correlation
	 * @param word : the word to convert in a series of double
	 * @return the double[] corresponding to the word
	 */
	public double[] converter(String word){
		double[] res = new double[word.length()];
		for(int i = 0; i < word.length(); i++) {
			res[i] = (double)word.charAt(i) - 96.0;
			//System.out.println(res[i]);
		}
		//System.out.println(res);
		return res;
	}
	
	public String valueOfTab(double[] tab) {
		StringBuffer res = new StringBuffer();
		for(int i = 0; i < tab.length; i++){
			res.append(String.valueOf(tab[i]));
		}
		return res.toString();
	}
	
	
	
}
