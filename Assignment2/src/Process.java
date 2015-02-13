import java.util.Random;

/***********************************************
 * A simulated process with random characteristics
 * @author David-Eric Thorpe
 * CS149
 * Team: Inception
 ***********************************************/
public class Process implements Cloneable {
    private static final int QUANTUM_WAIT_AMOUNT_MAX = 5;
    private static final int PRIORITY_HIGHEST = 1;
    private float arrivalTime;
    private float runTime;
    private int priority;
    private String name;
    private int actualStartTime;
    private int turnAroundTime;
    private int quantumWaitAmount;

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

        quantumWaitAmount = 0; // how long a process has been waiting in a queue

        actualStartTime = -1; // the first time a process starts running
        turnAroundTime = -1; // the last time a process runs
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
     * Increments the quantumWaitAmount by one..
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
    public Object clone() throws CloneNotSupportedException {
        Process objClone= new Process();

        objClone.setArrivalTime(arrivalTime);
        objClone.setRunTime(runTime);
        objClone.setName(name);
        objClone.setPriority(priority);

        return objClone;
    }
}
