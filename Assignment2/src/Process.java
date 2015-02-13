import java.util.Random;

/***********************************************
 * A simulated process with random characteristics
 * @author David-Eric Thorpe
 * CS149
 * Team: Inception
 ***********************************************/
public class Process implements Cloneable {
    private float arrivalTime;
    private float runTime;
    private int priority;
    private String name;
    private int actualStartTime;
    private int turnAroundTime;
    private int quantumWait;

    public void setRunTime(float runTime) {
        this.runTime = runTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Reduces the priority value. Increase the priority.
     * 1 = highest priority  4 = lowest priority
     * @return true if successful and false if already highest priority
     */
    public boolean deprecatePriority(){
        if(priority>1){
            priority--;
            return true;
        } else return false;    

    }
    
    /*
     *  Creates a clone of the process object with unique variables
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     * @return the cloned object
     */
    public Object clone() throws CloneNotSupportedException{
	Process objClone= new Process();
	objClone.setArrivalTime(this.arrivalTime);
	objClone.setRunTime(this.runTime);
	objClone.setName(this.name);
	objClone.setPriority(this.priority);
	return objClone;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(float arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Default Constructor
     * Creates a constructor that should never
     * be first for any of the scheduling algorithms
     */
    public Process() {
        name = "N/A";

        arrivalTime = 200;
        runTime = 1000;
        priority = -1;

        quantumWait = 0;

        actualStartTime = -1;
        turnAroundTime = -1;
    }

    /**
    * Constructor for the process. Takes a unique integer for use in random seeding and naming.
    * @param n is the seed to be used. Also a unique identifier for the process ID.
    */
    public Process(int n) {
        name = n + "";

        // random stats
        Random random = new Random();
        arrivalTime = random.nextFloat() * 99.0f; // gets a random float from 0.0 to 99.0
        runTime = random.nextFloat() * 10.0f; // gets a random float from 0.0 to 10.0
        priority = random.nextInt(4) + 1; // gets a random integer from 1 to 4

        quantumWait = 0; // how long a process has been waiting in a queue

        actualStartTime = -1; // the first time a process starts running
        turnAroundTime = -1; // the last time a process runs
    }

    /**
     * Gets the actual actualStartTime.
     * @return the actualStartTime
     */
    public int getActualStartTime() {
        return actualStartTime;
    }

    /**
     * Returns the arrival time of the float.
     * @return a float representing the arrival time for the process.
     */
    public float getArrivalTime(){
        return arrivalTime;
    }

    /**
     * A unique string to distinguish the process.
     * @return a string that represents a unique identifier of the process.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the priority of the process where the larger number represents the higher relative priority.
     * @return an integer representing the priority of the processes
     */
    public int getPriority(){
        return priority;
    }

    /**
     * Gets the quantumWait.
     * @return the quantumWait
     */
    public int getQuantumWait(){
    	return quantumWait;
    }

    /**
     * Gets the responseTime.
     * @return the responseTime
     */
    public int getResponseTime() {
        return Math.round(arrivalTime + 0.5f) - actualStartTime;
    }
    
    /**
    * Returns the average run time of this process in quantum.
    * @return a float representing the average run time for the process in quantum.
    */
    public float getRunTime(){
        return runTime;
    }

    /**
     * Gets the turnAroundTime.
     * @return the turnAroundTime
     */
    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    /**
     * Gets the waitingTime.
     * @return the waitingTime.
     */
    public int getWaitingTime() {
        return turnAroundTime - Math.round(runTime + 0.5f);
    }

    /**
     * Sets the actual actualStartTime.
     * @param actualStartTime the actualStartTime
     */
    public void setActualStartTime(int actualStartTime) {
        if(this.actualStartTime < 0) {
            this.actualStartTime = actualStartTime;
        }
    }

    /**
     * Sets the actual turnAroundTime which is the last time the process ran.
     * @param turnAroundTime the turnAroundTime
     */
    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    /**
     * Decrements the runTime by one.
     */
    public void decrementRunTime() {
        runTime--;
    }

	/**
	 * Increments the quantumWait by one..
	 */
	public void incrementQuantumWait(){
		quantumWait++;
	}

	/**
	 * Increments the priority by one.
	 */
	public void incrementPriority() {
		priority++;
	}
}
