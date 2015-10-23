package pom.app;

public class WordConverter {

	public WordConverter() {
		super();
	}
	
	public double[] converter(String word){
		double[] res = new double[word.length()];
		
		for(int i = 0; i < word.length(); i++) {
			switch (word.charAt(i)) {
			case 'a':
				res[i] = 1;
				break;
			case 'b':
				res[i] = 2;
				break;
			case 'c':
				res[i] = 3;
				break;
			case 'd':
				res[i] = 4;
				break;
			case 'e':
				res[i] = 5;
				break;
			case 'f':
				res[i] = 6;
				break;
			case 'g':
				res[i] = 7;
				break;
			case 'h':
				res[i] = 8;
				break;
			case 'i':
				res[i] = 9;
				break;
			case 'j':
				res[i] = 10;
				break;
			case 'k':
				res[i] = 11;
				break;
			case 'l':
				res[i] = 12;
				break;
			case 'm':
				res[i] = 13;
				break;
			case 'n':
				res[i] = 14;
				break;
			case 'o':
				res[i] = 15;
				break;
			case 'p':
				res[i] = 16;
				break;
			case 'q':
				res[i] = 17;
				break;
			case 'r':
				res[i] = 18;
				break;
			case 's':
				res[i] = 19;
				break;
			case 't':
				res[i] = 20;
				break;
			case 'u':
				res[i] = 21;
				break;
			case 'v':
				res[i] = 22;
				break;
			case 'w':
				res[i] = 23;
				break;
			case 'x':
				res[i] = 24;
				break;
			case 'y':
				res[i] = 25;
				break;
			case 'z':
				res[i] = 26;
				break;
			}
		}
		
		return res;
	}
	
}
