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
    private ArrayList<Process> processedList;
    private ArrayList<String> stringList;

    /**
     * Creates a First Come First Serve schedule.
     * @param processList the process list
     */
    public FCFS(ArrayList<Process> processList){
        this.processList = (ArrayList<Process>) processList.clone();
        stringList = new ArrayList<>();

        run();
    }

    /**
     * Runs the FCFS algorithm.
     */
    private void run() {
        int quantum = 0;

        while(!processList.isEmpty() && quantum <= QUANTUM_MAX) {
            Process process = processList.remove(0);
            processedList.add(process);
            
            // idle time
            while(process.getArrivalTime() > quantum) {
                stringList.add("");

                quantum++;
            }

            // process time
            while(process.getRunTime() > 0) {
                stringList.add(process.getName());

                // update process stats
                process.decrementRunTime();
                process.setActualStartTime(quantum);
                process.setTurnAroundTime(quantum);

                quantum++;
            }
        }
    }
    
    /**
     * Returns the list of processed processes.
     * @return
     */
    public ArrayList<Process> getProcessedProcessList(){
	return this.processedList;
    }

    /**
     * Gets the string list.
     * @return the string list
     */
    public ArrayList<String> getStringList() {
        return stringList;
    }
}
