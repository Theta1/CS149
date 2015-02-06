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
<<<<<<< HEAD
    private ArrayList<Process> processData;
    private ArrayList<Process> runnableData;
    private ArrayList<Character> srt;
    private float cnt;
    
    public SRT(ArrayList<Process> p) {
    	this.processData = (ArrayList<Process>) p.clone();
    	this.runnableData = new ArrayList<Process>();
    	this.srt = new ArrayList<Character>();
    	this.cnt = 0;

    	sort();
    	createList();
    }
    
    /**
     * Gets the Array of processes
     * in their quantum
     */
    public ArrayList<Character> getSRT() {
    	return srt;
    }
    
    /**
     * Sorts the process data
     * by arrival time
     */
    public void sort() {
    	for (int i = 0; i < processData.size()-1; i++)
    	{
    		for (int j = 1; j < processData.size(); j++ )
    		{
    			if (processData.get(j).getArrivalTime() < processData.get(i).getArrivalTime())
    			{
    				Process k = processData.get(i);
    				processData.set(i, processData.get(j));
    				processData.set(j, k);
    			}
    		}
    	}
    }
    
    /**
     * Creates the list for the processes
     * puts them in their quantum 
     */
    public void createList() { 	
    	while( (processData != null && runnableData != null) || cnt < 100 )
    	{	
        	runtimeProcesses();
        	Process addProcess = findShortTime();
    		
    		srt.add( addProcess.getName() );
    		addProcess.setRunTime( addProcess.getRunTime() - 1 );
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
   
=======
    public SRT(){
	
    }
>>>>>>> SJF sorts by arrival time
}
