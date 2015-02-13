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
    private ArrayList<Process> roundRobbin;
    private ArrayList<String> rr;
    private float quantaPointer;
    
    public RR(ArrayList<Process> p) {
	this.processList = (ArrayList<Process>)p.clone();
    	this.roundRobbin = new ArrayList<Process>();
    	this.rr = new ArrayList<String>();
    	this.quantaPointer = 0;
    	runRoundRobbin();
    }
    
    /**
     * Creates the list for the processes
     * puts them in their quantum 
     */
    public void runRoundRobbin() {
	//Will run if there is space left in history
	while(quantaPointer < 100 || !roundRobbin.isEmpty()) {
	    //idling if the RR is empty and no process has arrived
	    while(roundRobbin.isEmpty() && processList.get(0).getArrivalTime() > quantaPointer) {
                rr.add("   ");
                quantaPointer++;
            }
	    //if the processListmoves the newest arrival onto the RR queue
	    if(!processList.isEmpty()) roundRobbin.add(processList.remove(0));
	    //appends name to history
	    rr.add(roundRobbin.get(0).getName());
	    //reduces remaining runtime
	    roundRobbin.get(0).decrementRunTime();
	    //if runtime remains, appends the process to the back of the RR queue
	    if(roundRobbin.get(0).getRunTime() > 0) roundRobbin.add(roundRobbin.remove(0));
	    //else removes it completely
	    else roundRobbin.remove(0);
	    quantaPointer++;
	}
    }
    
    /**
     * Gets the Array of processes
     * in their quantum
     * @param an ArrayListof Strings representing the run order
     */
    public ArrayList<String> getStringList() {
    	return rr;
    }
}
