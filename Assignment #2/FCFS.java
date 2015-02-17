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
public class FCFS implements SchedulerInterface{
    private static final int QUANTUM_MAX = 100;
    private ArrayList<Process> processList;
    private ArrayList<String> stringList;
    private ArrayList<Process> stats;

    /**
     * Creates a First Come First Serve schedule.
     * @param processList the process list
     */
    public FCFS(ArrayList<Process> processList){
        this.processList = processList;
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
                process.setEndTime(quantum);

                quantum++;
            }
            stats.add(process);
        }
    }

    public ArrayList<String> getStringList() {
        return stringList;
    }
    
    public ArrayList<Process> getStats() {
        return stats;
    }
}
