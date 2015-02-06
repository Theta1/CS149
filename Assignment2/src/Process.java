import java.util.Random;

/**
 * A simulated process with random characteristics
 * @author David-Eric Thorpe
 */
public class Process {
    private float arrivalTime;
    private float runTime;
    private int priority;
    public char name;
    
    /**
     * A unique character to distinguish the process. 
     * @return a character that represents a unique identifier of the process.
     */
    public char getName() {
        return name;
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
}
