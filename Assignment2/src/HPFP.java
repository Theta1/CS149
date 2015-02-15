
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**********************************************
 * Highest Priority First (preemptive)
 *
 * The highest priority process runs
 * until it is complete
 *
 * @author Team: Inception
 * CS149
 *
 **********************************************/
public class HPFP {
    private static final int QUANTUM_MAX = 100;
    private ArrayList<Process> processList;
    private ArrayList<String> stringList;
    private ArrayList<Process> stats;

    public HPFP(ArrayList<Process> processList) {
        this.processList = (ArrayList<Process>) processList.clone();
        stringList = new ArrayList<String>();
        stats = new ArrayList<Process>();

        run();
    }

    /**
     * Runs the HPFP algorithm.
     */
    private void run() {
        // sorts Process by priority, then arrival time
        Comparator<Process> comparator = new Comparator<Process>() {
            public int compare(Process process1, Process process2) {
                int priorityDifference = process1.getPriority() - process2.getPriority();

                if(priorityDifference < 0) { // process1 has higher priority than process2
                    return -1;
                }
                else if(priorityDifference > 0) { // process2 has higher priority than process1
                    return 1;
                }
                else {
                    return new Float(process1.getArrivalTime()).compareTo(new Float(process2.getArrivalTime()));
                }
            }
        };

        ArrayList<Process> priorityProcessList = new ArrayList<>();
        priorityProcessList.add(processList.remove(0));
        int quantum = 0;

        while(!processList.isEmpty() && quantum <= QUANTUM_MAX) {
            Process process = priorityProcessList.get(0);

            // idle time
            while(process.getArrivalTime() > quantum) {
                stringList.add("");

                quantum++;
            }

            // process time
            while(process.getRunTime() > 0) {
                //System.out.print("Current process: " + process.getName() + "." + process.getPriority());
                stringList.add(process.getName());

                // update process stats
                process.decrementRunTime();
                process.incrementQuantaTime();
                process.setTurnAroundTime(quantum);
                if(process.getActualStartTime() == -1)
                {  	process.setActualStartTime(quantum);	}
                
                quantum++;

                // add new process into a the priorityList as a quantum has passed
                for(Process temp : processList) {
                    if(temp.getArrivalTime() < quantum) {
                        priorityProcessList.add(temp);
                    }
                }

                // remove all the elements in the priorityList from the processList
                processList.removeAll(priorityProcessList);

                // increased quantumWaitAmounts
                //System.out.print(" --> Increased quantumWaitAmount for: ");
                for(Process temp : priorityProcessList) {
                    // do not increase quantumWaitAmount for currently running process
                    if(temp != process) {
                        temp.incrementQuantumWaitAmount();
                        //System.out.print(temp.getName() + "." + temp.getPriority() + " ");
                    }
                }
                //System.out.println();

                // sort the priorityProcessList by priority, then arrivalTime
                Collections.sort(priorityProcessList, comparator);

                // get the new highest priority process
                process = priorityProcessList.get(0);
            }

            // remove the completed highest priority process
            stats.add(priorityProcessList.get(0));
            priorityProcessList.remove(0);

            // add new process into a the priorityList as a runTime has passed
            for(Process temp : processList) {
                if(temp.getArrivalTime() < quantum) {
                    priorityProcessList.add(temp);
                }
            }

            // make sure priorityProcessList has something in it
            if(priorityProcessList.isEmpty()) {
                priorityProcessList.add(processList.remove(0));
            }

            // remove all the elements in the priorityList from the processList
            processList.removeAll(priorityProcessList);

            // sort the priorityProcessList by priority, then arrivalTime
            Collections.sort(priorityProcessList, comparator);
        }
        //System.out.println();
    }

    /**
     * Gets the string list.
     * @return the string list
     */
    public ArrayList<String> getStringList() {
        return stringList;
    }
    
	/**
	 * Returns a list of processes with
	 * data to calculate statistics
	 * @return stats an ArrayList
	 */
	public ArrayList<Process> getStats() {
		return stats;
	}
}
