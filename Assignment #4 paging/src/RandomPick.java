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
	private final static int LOCALITY_PROBABILITY = 7;
	static int local;

	/**
	 * There is a 70% probability that the next reference will be to page i,
	 * i-1, or i+1. Otherwise, the return should be between 2 and 8.
	 * 
	 * @param i
	 *            is an int representing the reference page number
	 * @param virtualMemorySize
	 *            is an int representing the size of the virtual memeory
	 * @return an int representing the new page
	 */
	public static int pickAPage(int i, int virtualMemorySize) {
		Random random = new Random();
		int probability = random.nextInt(10);
		int locality;
		if (probability < LOCALITY_PROBABILITY) {
			locality = random.nextInt((1 - -1) + 1) + -1;
		} else {
			locality = random.nextInt((8 - 2) + 1) + 2;
		}
		//c derived languages: -1 % 9 = -1
		//this allows for -1 % 9 = 8
		return ((((i + locality) % virtualMemorySize) + virtualMemorySize) % virtualMemorySize);
	}
}
