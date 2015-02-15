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
    private ArrayList<Process> roundrobin;
    private ArrayList<String> rr;
    private ArrayList<Process> stats;
    private int quantum;
    
    public RR(ArrayList<Process> processList) {
    	this.processList = processList;
    	this.roundrobin = new ArrayList<Process>();
    	this.rr = new ArrayList<String>();
    	this.stats = new ArrayList<Process>();
    	this.quantum = 0;
    	
    	run();
    }
    
    /**
     * Creates the list for the processes
     * puts them in their quantum 
     */
    public void run() {
    	//Will run if there is space left in history
    	while(quantum < 100 || !roundrobin.isEmpty()) {
    		//idling if the RR is empty and no process has arrived
    		while(roundrobin.isEmpty() && processList.get(0).getArrivalTime() > quantum) {
    			rr.add("");
    			quantum++;
    		}
    		
    		//if the processList has items, moves the newest arrival onto the RR queue
    		if(!processList.isEmpty() && quantum < 100) roundrobin.add(processList.remove(0));

    		//appends name of the running process to history
    		rr.add(roundrobin.get(0).getName());

    		//if it's the first run, marks time in the Process (for response time)
    		if(roundrobin.get(0).getActualStartTime()==-1)roundrobin.get(0).setActualStartTime(quantum);

    		//reduces remaining runtime
    		roundrobin.get(0).decrementRunTime();
    		roundrobin.get(0).incrementQuantaTime();

    		//(for wait time)
    		waitNonActiveProcesses();

    		//if runtime remains, appends the process to the back of the RR queue
    		if(roundrobin.get(0).getRunTime() > 0) roundrobin.add(roundrobin.remove(0));

    		//else removes it completely
    		else{
    			//sets the last time the quanta is run
    			roundrobin.get(0).setTurnAroundTime(quantum);
    			stats.add(roundrobin.remove(0));
    		}
    		quantum++;

    	}
    }

    /**
     * Waits all but the active process
     */
    public void waitNonActiveProcesses(){
    	for(int i = 1; i<roundrobin.size(); i++){
    		roundrobin.get(i).incrementQuantumWaitAmount();
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
    
    /**
     * Gets the Process details
     * @return stats a list of processes
     */
	public ArrayList<Process> getStats() {
		return stats;
	}
}
