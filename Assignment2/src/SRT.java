import java.util.ArrayList;
import java.util.List;

/**********************************************
 * Shortest Remaining time
 * 
 * The process which is currently in execution runs
 * until it is complete or a new process is added in
 * the CPU scheduler  that requires smaller amount
 * of time for execution
 * 
 * @author Nate Kong
 * CS149
 * Team: Inception
 *********************************************/

public class SRT {
    private ArrayList<Process> processData; //the 
    private ArrayList<Process> srt;
    
    public SRT(ArrayList<Process> p) {
    	this.processData = (ArrayList<Process>) p.clone();
    	this.srt = new ArrayList<Process>();
    	
    	createList();
    }
    
    /*
     * Gets the Array of processes
     * in their quantum
     */
    public List<Process> getSRT() {
    	return srt;
    }
    
    /*
     * Creates the list for the processes
     * puts them in their quantum 
     */
    public void createList() {
    	while( processData != null )
    	{
    		
    	}
    	
    }
    
    
    
}
