import java.util.Random;

/***********************************************
 * A simulated process with random characteristics
 * @author David-Eric Thorpe
 * CS149
 * Team: Inception
 ***********************************************/
public class Process  {
    private float arrivalTime;
    private float runTime;
    private int priority;
    public char name;
    
    /**
     * Default Constructor
     * Creates a constructor that should never
     * be first for any of the scheduling algorithms
     */
    public Process() {
    	this.name = '0';
    	this.arrivalTime = 200;
    	this.runTime = 1000;
    	this.priority = -1;
    }
    
    /**
    * Constructor for the process. Takes a unique integer for use in random seeding and naming.
    * @param n- is the seed to be used. Also a unique identifier for the process ID.
    */
    public Process(int n){
    	name = (char)n;
        Random rn = new Random(n);
        arrivalTime = rn.nextFloat()*99;
        runTime = rn.nextFloat()*10;
        priority = rn.nextInt();
    }

    /**
     * A unique character to distinguish the process. 
     * @return a character that represents a unique identifier of the process.
     */
    public char getName() {
        return name;
    }
    
    /**
     * Returns the arrival time of the float. 
     * @return a float representing the arrival time for the process.
     */
    public float getArrivalTime(){
        return arrivalTime;
    }
    
    /**
    * Returns the average run time of this process in quantum.
    * @return a float representing the average run time for the process in quantum.
    */
    public float getRunTime(){
        return runTime;
    }
    
    /**
    * Returns the priority of the process where the larger number represents the higher relative priority.
    * @return an integer representing the priority of the processes  
    */
    public int getPriority(){
        return priority;
    }

	/**
	 * Sets the arrival time of the process
	 * @param arrivalTime the arrivalTime as a float
	 */
	public void setArrivalTime(float arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Sets the run time of the process
	 * @param runTime the runTime as a float
	 */
	public void setRunTime(float runTime) {
		this.runTime = runTime;
	}

	/**
	 * Sets the priority of the process
	 * @param priority the priority as an int
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * sets the name of the process
	 * @param name the name as a char
	 */
	public void setName(char name) {
		this.name = name;
	}
    
}
