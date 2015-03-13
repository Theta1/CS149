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
		size = setSize();
		duration = setDuration();
		name = i;
	}
	
	public int getSize() {
		return size;
	}

	public int getDuration() {
		return duration;
	}

	/**
	 * creates a random size of the Process
	 * @return the size of the Process
	 */
	int setSize() {
		Random rand = new Random();
		int[] array = {5, 11, 17, 31};
		int num = rand.nextInt(4);
		return array[num];		
	}
	
	/**
	 * Creates a random duration for the Process
	 * @return the duration
	 */
	int setDuration() {
		Random rand = new Random();
		int[] array = {1, 2, 3, 4, 5};
		int num = rand.nextInt(5);
		return array[num];
	}

	/**
	 * Gets the name of the process.
	 * @return
	 */
	public int getName() {
		return name;
	}


	
}
