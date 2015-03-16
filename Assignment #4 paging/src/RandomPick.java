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
	private final static int VMEM = 10;
	private final static int LOCALITY_PROBABILITY = 7;

	/**
	 * * Called with an int representing the reference page and returns an int
	 * representing the next page with 70% probability the page returned is self
	 * or neighbour.
	 * 
	 * @param i
	 *            is an int representing the reference page number
	 * @return an int representing the new page
	 */
	public static int pickAPage(int i) {
		Random random = new Random();
		int probability = random.nextInt(VMEM);
		int locality;
		if (probability < LOCALITY_PROBABILITY)
			locality = random.nextInt((1 - -1) + 1) + 1;
		else
			locality = random.nextInt((8 - 2) + 1) + 2;
		return (i + locality) % VMEM;
	}

}
