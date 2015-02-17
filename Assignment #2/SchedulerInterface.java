import java.util.ArrayList;

/**********************************************
 * First Come First Serve
 * 
 * Each process is ran as in the
 * order it comes in at
 * 
 * @author Team: Theta 1
 * CS149
 * 
 *********************************************/
public interface SchedulerInterface {

    /**
     * Gets the string list, an in order process history
     * @return the string list
     */
    public ArrayList<String> getStringList();
    
    /**
     * Returns a list of the processes acted on
     * each process maintains it's own info
     * @return stats a list of processes
     */
    public ArrayList<Process> getStats();
}
