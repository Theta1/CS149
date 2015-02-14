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
	    //if the processList has items, moves the newest arrival onto the RR queue
	    if(!processList.isEmpty()) roundRobbin.add(processList.remove(0));
	    
	    //appends name of the running process to history
	    rr.add(roundRobbin.get(0).getName());
	    
	    //if it's the first run, marks time in the Process (for response time)
	    if(roundRobbin.get(0).getActualStartTime()==-1)roundRobbin.get(0).setActualStartTime((int) quantaPointer);
	    
	    //reduces remaining runtime
	    roundRobbin.get(0).decrementRunTime();
	    
	    //(for wait time)
	    waitNonActiveProcesses();
	    
	    //if runtime remains, appends the process to the back of the RR queue
	    if(roundRobbin.get(0).getRunTime() > 0) roundRobbin.add(roundRobbin.remove(0));
	    
	    //else removes it completely
	    else{
		//sets the last time the quanta is run
		roundRobbin.get(0).setTurnAroundTime((int) quantaPointer);
		roundRobbin.remove(0);
	    }
	    quantaPointer++;
	    
	}
    }
    
    /**
     * Waits all but the active process
     */
    public void waitNonActiveProcesses(){
	for(int i = 1; i<roundRobbin.size(); i++){
	    roundRobbin.get(i).incrementQuantumWaitAmount();
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