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
    private float quantum;
    
    public SRT(ArrayList<Process> p) {
    	this.processData = (ArrayList<Process>) p.clone();
    	this.runnableData = new ArrayList<Process>();
    	this.srt = new ArrayList<String>();
    	this.quantum = 0;

    	run();
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
    public void run() { 	
    	while( quantum < 100 || !runnableData.isEmpty() )
    	{	
    		runtimeProcesses();
            
    		if(runnableData.isEmpty())
    		{	srt.add("");	}
    		else
    		{
    			Process addProcess = findShortTime();
    			if (addProcess.getActualStartTime() < 0)
    			{	/*addProcess.setActualStartTime(quantum);*/	}
	    		srt.add( addProcess.getName() );
	    		addProcess.decrementRunTime();
	    		if(addProcess.getRunTime() < 0)
	    		{
	        		//addProcess.setTurnAroundTime(quantum);
	        		runnableData.remove(addProcess);
	    		}
    		}
    		quantum++;
    	}
    	
    }
    
    /**
     * Adds the Processes
     * at their time interval
     * to the ArrayList runnableData
     */
    public void runtimeProcesses() {
    	for(Process p: processData)
    	{
    		if( p.getArrivalTime() <= quantum ) 
    		{	runnableData.add( processData.get(processData.indexOf(p)) );	}
    	}
    	for (Process q: runnableData)
    	{	processData.remove(q); 	}
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
			{	shortTime = p;	}
		}
    	return shortTime;
    }
   
}
