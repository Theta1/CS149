import java.util.Random;

/************************************
 * Creates a Process and its attributes
 * 
 * @author Theta1
 * CS149
 * Assignment #4 Swapping
 ************************************/

public class Process {
	private int size;
	private int duration;
	private int name;
	
	Process() {
		size = 0;
		duration = 0;
		name = 0;
	}
	
	Process(int i) {
		size = size();
		duration = duration();
		name = i;
	}
	
	public int getSize() {
		return size;
	}

	public int getDuration() {
		return duration;
	}
	

	public void setSize(int size) {
		this.size = size;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setName(int name) {
		this.name = name;
	}
	
	public void decrementDuration() {
		duration--;
	}
	/**
	 * creates a random size of the Process
	 * @return the size of the Process
	 */
	int size() {
		Random rand = new Random();
		int[] array = {5, 11, 17, 31};
		int num = rand.nextInt(4);
		return array[num];		
	}
	
	/**
	 * Creates a random duration for the Process
	 * @return the duration
	 */
	int duration() {
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

	public Process clone() {
		Process newClone = new Process();
		newClone.setSize(size);
		newClone.setDuration(duration);
		newClone.setName(name);
		return newClone;
	}
	
}
