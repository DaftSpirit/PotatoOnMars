package pom.app;

/**
 * percentage of good sorting
 * @author joris
 *
 */
public class LearningTest {

	private Sorter so;
	
	/**
	 * constructs the tester
	 * @param so the sorter who sorts a pattern
	 */
	public LearningTest(Sorter so){
		//CARPE DIEM
		this.so = so;
	}
	
	/**
	 * Tests the sorting w/ partternLists
	 * @param pl the patternList to test
	 * prints the percentage of good sorting
	 */
	public void testForPatternList(PatternList pl) {
		double timesExec = 0.0;
		double timesWorked = 0.0;
		for(int i = 0; i < pl.size(); ++i) {
			for(int j = 0; j < pl.get(i).size(); ++j) {
				boolean test = this.so.sortAndPrint(pl, i, j);
				if(test) {
					timesWorked++;
				}
				timesExec++;
			}
		}
		double percentage = (timesWorked / timesExec) * 100.0;
		System.out.println("times worked : " + percentage);
	}
	
	/**
	 * Tests the sorting w/ DoublePatternList
	 * @param dpl the DoublePatternList to use
	 * prints the percentage of good sorting
	 * @author joris
	 */
	public void testForDoublePatternList(DoublePatternList dpl) {
		double timesExec = 0.0;
		double timesWorked = 0.0;
		for(int i = 0; i < dpl.size(); i++){
			for(int j = 0; j < dpl.get(i).size(); j++){
				boolean test = this.so.sortDoubles(dpl, i, j);
				if(test){
					timesWorked++;
				}
				timesExec++;
			}
		}
		double percentage = (timesWorked / timesExec) * 100.0;
		System.out.println("times worked : " + percentage);
	}
	
	
	
}
