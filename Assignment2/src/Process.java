import java.util.Random;

/***********************************************
 * A simulated process with random characteristics
 * and runtime information
 * @author Team: Inception
 * CS149
 * 
 ***********************************************/
public class Process {
    private static final int QUANTUM_WAIT_AMOUNT_MAX = 5;
    private static final int PRIORITY_HIGHEST = 1;
    private float arrivalTime;
    private float runTime;
    private int priority;
    private String name;
    private int actualStartTime;
    private int endTime;
    private int quantumWaitAmount;
    private float quantaTime;

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

        quantumWaitAmount = 0;
        
        actualStartTime = -1;
        endTime = -1;
        quantaTime = 0.0f;
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

        quantumWaitAmount = 0;
        
        actualStartTime = -1; // the first time a process starts running
        endTime = -1; // the last time a process runs
        
        quantaTime = 0.0f; // the run time
    }

    /**
     * Gets the quanta at which the process first run.
     * @return an int representing the quanta start time.
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
     * Gets the quantumWaitAmount.
     * @return the quantumWaitAmount
     */
    public int getQuantumWait(){
        return quantumWaitAmount;
    }
    
    /**
     * Gets the responseTime.
     * @return the responseTime
     */
    public float getResponseTime() {
        return actualStartTime - arrivalTime;
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
    public float getTurnAroundTime() {
        return endTime - arrivalTime + 1;
    }

    /**
     * Gets the waitingTime.
     * @return the waitingTime.
     */
    public float getWaitingTime() {
        return endTime - arrivalTime - quantaTime;
    }

    /**
     * Gets the number of quanta the process filled
     * @return quantaTime the run time
     */
	public float getQuantaTime() {
		return quantaTime;
	}

	public void incrementQuantaTime() {
		this.quantaTime = quantaTime++;
	}

	/**
     * Sets the runTime.
     * @param runTime the runTime
     */
    public void setRunTime(float runTime) {
        this.runTime = runTime;
    }

    /**
     * Sets the priority.
     * @param priority the priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Sets the arrivalTime.
     * @param arrivalTime the arrivalTime
     */
    public void setArrivalTime(float arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Sets the name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * Sets the actual endTime which is the last time the process ran.
     * @param endTime the endTime
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    /**
     * Decrements the runTime by one.
     */
    public void decrementRunTime() {
        runTime--;
    }

    /**
     * Decrements the time waited by one.
     * Useful for when the process is running but has otherwise had wait incremented.
     */
    public void decrementQuantumWaitTimeAmount() {
	quantumWaitAmount--;
    }
    
    /**
     * Increments the quantumWaitAmount by one..
     * THIS max maybe problematic for statistic calculations
     */
    public void incrementQuantumWaitAmount() {
        quantumWaitAmount++;

        if(quantumWaitAmount == QUANTUM_WAIT_AMOUNT_MAX) {
            quantumWaitAmount = 0;

            increasePriority();
        }
    }
    
    /**
     * Increases the priority by one (1 = highest priority).
     */
    public void increasePriority() {
        if(priority > PRIORITY_HIGHEST) {
            priority--;
        }
    }

    /**
     *  Creates a clone of the process object with unique variables
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     * @return the cloned object
     */
    public Process clone() {
        Process objClone= new Process();

        objClone.setArrivalTime(arrivalTime);
        objClone.setRunTime(runTime);
        objClone.setName(name);
        objClone.setPriority(priority);

        return objClone;
    }
}
