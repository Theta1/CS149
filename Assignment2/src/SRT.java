import java.util.ArrayList;
/**********************************************
 * Shortest Remaining time
 * 
 * The process which is currently in execution runs
 * until it is complete or a new process is added in
 * the CPU scheduler  that requires smaller amount
 * of time for execution
 * 
 * @author Team: Inception
 * CS149
 * 
 *********************************************/

public class SRT {
    private ArrayList<Process> processData;
    private ArrayList<Process> runnableData;
    private ArrayList<String> srt;
    private float cnt;
    
    public SRT(ArrayList<Process> p) {
    	this.processData = (ArrayList<Process>) p.clone();
    	this.runnableData = new ArrayList<Process>();
    	this.srt = new ArrayList<String>();
    	this.cnt = 0;
    	System.out.println("hello");
    	createList();
        }
    
    /**
     * Gets the Array of processes
     * in their quantum
     */
    public ArrayList<String> getSRT() {
    	return srt;
    }
    
    
    /**
     * Creates the list for the processes
     * puts them in their quantum 
     */
    public void createList() { 	
    	while( runnableData != null && cnt < 100 )
    	{	
    		System.out.println("hello");
        	runtimeProcesses();
        	Process addProcess = findShortTime();
    		
    		srt.add( addProcess.getName() );
    		addProcess.decrementRunTime();
    		cnt++;
    		
    		removeProcess();
    	}
    	
    }
    
    /**
     * Adds the Processes
     * at their time interval
     * to the arraylist runnableData
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
     * finds the shortest remaining time in
     * ArrayList of Processes
     * @return the process
     */
    public Process findShortTime() {
    	Process shortTime = runnableData.get(0);  	
    	
    	for( Process p : runnableData)
		{
    		if( p.getRunTime() < shortTime.getRunTime() )
			{	
    			shortTime = p;
			}
		}
    	return shortTime;
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
