import java.util.Random;


/**
 * Called with an int and returns an int based on the assignment specifications.
 * @author Theta(1)
 *
 */
public class RandomPick{
	private final static int VMEM = 10;
	private final static int LOCALITY_PROBABILITY = 7;
	public static int pickAPage(int i){
		Random random = new Random();
		int probability = random.nextInt(VMEM);
		int locality;
		if (probability < LOCALITY_PROBABILITY)
			locality = random.nextInt((1 - -1) + 1) + 1;
		else 
			locality = random.nextInt((8 - 2) + 1) + 2;
		return (i+locality)%VMEM;
	}
	

}


