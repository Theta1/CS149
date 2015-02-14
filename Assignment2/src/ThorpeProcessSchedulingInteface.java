import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Standard
 *
 */
public interface ThorpeProcessSchedulingInteface {
    /**
     * Returns the list of processes after being processed.
     * @return the list of processed processes.
     */
    public ArrayList<Process> getList();
    /**
     * Returns the average turn-around time for a processe under this scheduling scheme. 
     * @return float representing the average turn-around time.
     */
    public float getAverageTurnAroundTime();
    /**
     * Returns the average waiting time for a process under this scheduling scheme.
     * @return a float representing the average waiting time.
     */
    public float getAverageWaitingTime();
    /**
     * Returns the average response time for a process under this scheduling scheme.
     * @return a float representing the average response time.
     */
    public float getAverageResponseTime();
    /**
     * Returns the total throughput for this scheduling scheme within 100 quanta.
     * @return an int representing the total throughput.
     */
    public int getThroughput();
    /**
     * Depricated. Returns a string list of the processes.
     * @return
     */
    public ArrayList<String> getStringList();
    /**
     * Replaces the default toString behaviour.
     * @return
     */
    public String toString();
}
