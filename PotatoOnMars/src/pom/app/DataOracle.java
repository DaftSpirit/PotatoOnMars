package pom.app;

/**
 * predicts the future data
 * @author joris
 *
 */
public class DataOracle {
	
	private double[] ts;

	/**
	 * 
	 * @param ts : the time series to predict
	 */
	public DataOracle(double[] ts) {
		this.ts = ts;
	}
	
	/**
	 * Uses classic mean to predict the future element of ts
	 * @return the prediction of the last element of ts
	 * for test purposes we try to predict an already existing element
	 */
	public double prediction() {
		
		double future = 0;
		int size = this.ts.length - 2;
		
		for(int i = 0; i < size; i++){
			future += this.ts[size - i];
		}
		future /= size;
		
		return future;
	}
	
	/**
	 * uses Exponentially Moving Weight Average to predict the future element of ts
	 * @param alpha weight to use for the mean alpha c ]0 , 1]
	 * @return the prediction of the last element of ts
	 * for test purposes we try to predict an already existing element
	 */
	public double betterPrediction(double alpha) {
		double future = 0;
		int size = this.ts.length - 2;
		
		for(int i = 0; i < size; i++) {
			future += this.ts[size - i] * Math.pow((1 - alpha), i);
		}
		
		future *= alpha;
		
		return future;
	}
	
}
