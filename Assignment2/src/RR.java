import java.util.ArrayList;

/**********************************************
 * Round Robin (preemptive)
 * 
 * Each process is assigned a quantum (time interval)
 * The process runs in its quantum
 * When the ends the next process is started
 * 
 * @author Team: Inception
 * CS149
 * 
 *********************************************/
public class RR {
    private ArrayList<Process> processList;
    private ArrayList<Process> processesRunning;
    private ArrayList<String> rr;
    private float quantaPointer;
    
    public RR(ArrayList<Process> p) {
	this.processList = (ArrayList<Process>) p.clone();
    	this.processesRunning = new ArrayList<Process>();
    	this.rr = new ArrayList<String>();
    	this.quantaPointer = 0;
    	createList();
    }
    
    /**
     * Creates the list for the processes
     * puts them in their quantum 
     */
    public void createList() {
	int processListPointer = 0;
	while(quantaPointer<100 && !processList.isEmpty()) {
	    //idling
	    while(processList.get(0).getArrivalTime() > quantaPointer) {
                rr.add("   ");
                quantaPointer++;
            }
	    //moves the newest arrival onto the RR queue
	    processesRunning.add(processList.remove(0));
	    //appends name to history
	    rr.add(processesRunning.get(0).getName());
	    //reduces remaining runtime
	    processesRunning.get(0).decrementRunTime();
	    //if runtime remains, appends the process to the back of the RR queue
	    if(processesRunning.get(0).getRunTime()>0)processesRunning.add(processesRunning.remove(0));
	    //else removes it completely
	    else processesRunning.remove(0);
	    quantaPointer++;
	}
    }
    
    /**
     * Gets the Array of processes
     * in their quantum
     * @param an ArrayListof Strings representing the run order
     */
    public ArrayList<String> getrr() {
    	return rr;
    }

    
    /**
     * Adds the Processes
     * at their interval time
     * 
     */
    public void runtimeProcesses() {
 
    }
    

    
    /**
     * Removes processes that are completed
     */
    public void removeProcess() {

    }   
}
