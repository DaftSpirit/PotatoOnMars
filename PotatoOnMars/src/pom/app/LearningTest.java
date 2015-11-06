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
		int timesExec = 0;
		int timesWorked = 0;
		for(int i = 0; i < pl.size(); ++i) {
			for(int j = 0; j < pl.get(i).size(); ++j) {
				int test = this.so.sortAndPrint(pl, i, j);
				if(test == 0) {
					timesWorked++;
				}
				timesExec++;
			}
		}
		double percentage = (timesWorked / timesExec) * 100;
		System.out.println("times worked : " + percentage);
	}
	
	
	
}
