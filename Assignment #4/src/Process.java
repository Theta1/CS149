import java.util.Random;

/************************************
 * Creates a Process
 * @author Theta1
 * CS149
 ************************************/

public class Process {
	private int size;
	private int duration;
	private int name;
	
	Process(int i) {
		size = getSize();
		duration = getDuration();
		name = i;
	}
	
	/**
	 * creates a random size of the Process
	 * @return the size of the Process
	 */
	int getSize() {
		Random rand = new Random();
		int[] array = {5, 11, 17, 31};
		int num = rand.nextInt(4);
		return array[num];		
	}
	
	/**
	 * Creates a random duration for the Process
	 * @return the duration
	 */
	int getDuration() {
		Random rand = new Random();
		int[] array = {1, 2, 3, 4, 5};
		int num = rand.nextInt(5);
		return array[num];
	}
	
	
}
