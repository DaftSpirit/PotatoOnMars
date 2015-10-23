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
	 * @return the prediction of the last element of ts
	 * for test purposes we try to predict an already existing element
	 */
	public double prediction() {
		
		double future = 0;
		int size = this.ts.length -1;
		
		for(int i = 0; i < size; i++){
			future += this.ts[size - i];
		}
		future /= size;
		
		return future;
	}
	
}
