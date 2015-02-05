
import java.util.Random;

/*
 */

/**
 *
 * @author David-Eric Thorpe
 */
public class Process {
    private float timeArrived;
    private float runTime;
    private int priority;

    
    /**
    *
    * 
    */
    public Process(int n){
        Random rn = new Random(n);
        timeArrived = rn.nextFloat();
        runTime = rn.nextFloat();
        priority = rn.nextInt();
    }

    
    public float getTimeArrival(){
        return timeArrived;
    }
    /**
    *
    * 
    */
    public float getRunTime(){
        return runTime;
    }
    /**
    *
    * 
    */
    public int getPriority(){
        return priority;
    }
}
