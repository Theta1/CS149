import java.util.Random;

/**
 * Called with an int representing the reference page and returns an int
 * representing the next page with 70% probability the page returned is self or
 * neighbour.
 * 
 * @author Theta(1)
 *
 */
public class RandomPick {
	private final static int VIRTUAL_MEM_SIZE = 10;
	private final static int LOCALITY_PROBABILITY = 7;
	static Random random = new Random();
	/**
	 * There is a 70% probability that the next reference will be to page i,
	 * i-1, or i+1. Otherwise, the return should be larger. Page references
	 * should wrap from 9 back to 0.
	 * 
	 * @param i
	 *            is an int representing the reference page number
	 * @return an int representing the new page
	 */
	public static int pickAPage(int i) {
		
		int probability = random.nextInt(VIRTUAL_MEM_SIZE);
		int locality;
		if (probability < LOCALITY_PROBABILITY){
			locality = random.nextInt((1 - -1) + 1) + -1;
		}
		else{
			locality = random.nextInt((8 - 2) + 1) + 2;
		}
		return (i + locality) % VIRTUAL_MEM_SIZE;
	}
}
