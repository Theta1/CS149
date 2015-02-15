
import java.util.ArrayList;

/**********************************************
 * First Come First Serve
 * 
 * Each process is ran as in the
 * order it comes in at
 * 
 * @author Team: Inception
 * CS149
 * 
 *********************************************/
public class FCFS {
    private static final int QUANTUM_MAX = 100;
    private ArrayList<Process> processList;
    private ArrayList<String> stringList;
    private ArrayList<Process> stats;

    /**
     * Creates a First Come First Serve schedule.
     * @param processList the process list
     */
    public FCFS(ArrayList<Process> processList){
        this.processList = (ArrayList<Process>) processList.clone();
        stringList = new ArrayList<String>();
        stats = new ArrayList<Process>();

        run();
    }

    /**
     * Runs the FCFS algorithm.
     */
    private void run() {
        int quantum = 0;

        while(!processList.isEmpty() && quantum <= QUANTUM_MAX) {
            Process process = processList.remove(0);

            // idle time
            while(process.getArrivalTime() > quantum) {
                stringList.add("");

                quantum++;
            }

            // process time
            process.setActualStartTime(quantum);
            while(process.getRunTime() > 0) {
                stringList.add(process.getName());

                // update process stats
                process.decrementRunTime();
                process.incrementQuantaTime();
                process.setTurnAroundTime(quantum);

                quantum++;
            }
            stats.add(process);
        }
    }

    /**
     * Gets the string list.
     * @return the string list
     */
    public ArrayList<String> getStringList() {
        return stringList;
    }
    
    /**
     * Returns a list of processes
     * each process maintains it's own info
     * @return stats a list of processes
     */
    public ArrayList<Process> getStats() {
        return stats;
    }
}
