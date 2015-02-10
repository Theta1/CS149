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
	private ArrayList<Process> processData;
    private ArrayList<Process> runnableData;
    private ArrayList<Character> rr;
    private float cnt;
    
    public RR(ArrayList<Process> p) {
    	this.processData = (ArrayList<Process>) p.clone();
    	this.runnableData = new ArrayList<Process>();
    	this.rr = new ArrayList<Character>();
    	this.cnt = 0;

    	createList();
    }
    
    /**
     * Gets the Array of processes
     * in their quantum
     */
    public ArrayList<Character> getrr() {
    	return rr;
    }
        
    /**
     * Creates the list for the processes
     * puts them in their quantum 
     */
    public void createList() { 	
    	int dataCnt = 0;
    	
    	while( runnableData != null && cnt < 100 )
    	{	
        	if (dataCnt >= runnableData.size())
        	{	dataCnt = 0;	}
    		
    		runtimeProcesses();
        	
        	Process run = runnableData.get(dataCnt);
        	
    		rr.add( run.getName() );
    		dataCnt++;
    		cnt++;
    		run.setRunTime( run.getRunTime() - 1 );
    		
    		removeProcess();
    	}
    	
    }
    
    /**
     * Adds the Processes
     * at their interval time
     * 
     */
    public void runtimeProcesses() {
    	ArrayList<Integer> remove = new ArrayList<Integer>();
    	for(int i = 0; i < processData.size(); i++)
    	{
    		if( processData.get(i).getArrivalTime() < cnt ) 
    		{
    			runnableData.add(processData.get(i));
    			remove.add(i);
    		}
    	}
    	
    	for(int j = remove.size()-1; j >= 0; j--)
    	{	processData.remove(remove.get(j));	}
    }
    

    
    /**
     * Removes processes that are completed
     */
    public void removeProcess() {
		for( Process p: runnableData)
		{
			if( p.getRunTime() == 0 )
			{	runnableData.remove(p);	}
		}
    }
   
}
