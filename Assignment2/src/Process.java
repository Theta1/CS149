import java.util.Random;

/***********************************************
 * A simulated process with random characteristics
 * 
 * @author David-Eric Thorpe CS149 Team: Inception
 ***********************************************/
public class Process {
    private float arrivalTime;
    private float runTime;
    private int priority;
    private String name;

    /**
     * Default Constructor Creates a constructor that should never be first for
     * any of the scheduling algorithms
     */
    public Process() {
	this.name = "N/A";
	this.arrivalTime = 200;
	this.runTime = 1000;
	this.priority = -1;
    }

    /**
     * Constructor for the process. Takes a unique integer for use in random
     * seeding and naming.
     * 
     * @param n
     *            is the seed to be used. Also a unique identifier for the
     *            process ID.
     */
    public Process(int n) {
	name = String.format("%1$3d", n);
	
	Random rn = new Random(n);
	arrivalTime = rn.nextFloat() * 99.0f;
	runTime = rn.nextFloat() * 10.0f;
	priority = rn.nextInt(4) + 1; // gets a random number from 1 to 4
    }

    /**
     * A unique string to distinguish the process.
     * 
     * @return a string that represents a unique identifier of the process.
     */
    public String getName() {
	return name;
    }

    /**
     * Returns the arrival time of the float.
     * 
     * @return a float representing the arrival time for the process.
     */
    public float getArrivalTime() {
	return arrivalTime;
    }

    /**
     * Returns the average run time of this process in quantum.
     * 
     * @return a float representing the average run time for the process in
     *         quantum.
     */
    public float getRunTime() {
	return runTime;
    }

    /**
     * Returns the priority of the process where the larger number represents
     * the higher relative priority.
     * 
     * @return an integer representing the priority of the processes
     */
    public int getPriority() {
	return priority;
    }

    /**
     * Sets the arrival time of the process
     * 
     * @param arrivalTime
     *            the arrivalTime as a float
     */
    public void setArrivalTime(float arrivalTime) {
	this.arrivalTime = arrivalTime;
    }

    /**
     * Sets the run time of the process
     * 
     * @param runTime
     *            the runTime as a float
     */
    public void setRunTime(float runTime) {
	this.runTime = runTime;
    }

    /**
     * Sets the priority of the process
     * 
     * @param priority
     *            the priority as an int
     */
    public void setPriority(int priority) {
	this.priority = priority;
    }

    /**
     * Sets the name of the process
     * 
     * @param name
     *            the name
     */
    public void setName(String name) {
	this.name = name;
    }

    /*
     * (non-Javadoc) Returns a formatted string representing important Process
     * Object attributes
     * 
     * @see java.lang.Object#toString()
     * 
     * @return a String
     */
    public String toString() {
	return "[Name: " + this.getName() + " --> Arrival Time: "
		+ String.format("%1$9f", this.getArrivalTime())
		+ ", Run Time: " + String.format("%1$9f", this.getRunTime())
		+ ", Priority: " + this.getPriority() + "]   ";
    }
}
