import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/******************************************
 * Highest Priority First (Preemptive)
 * 
 * The highest priority runs until
 * completion unless a higher priority
 * process enters
 *
 * @author Team: Inception
 * CS149
 * 
 *****************************************/
public class HPFP {
	private ArrayList<Process> processData;
    private ArrayList<Process> runnableData;
    private ArrayList<String> hpfp;
    private float cnt;
    
    public HPFP(ArrayList<Process> p) {
    	this.processData = (ArrayList<Process>) p.clone();
    	this.runnableData = new ArrayList<Process>();
    	this.hpfp = new ArrayList<String>();
    	this.cnt = 0;

    	createList();
    }
    
    /**
     * Gets the Array of processes
     * in their quantum
     */
    public ArrayList<String> gethpfp() {
    	return hpfp;
    }
       
    
    /**
     * Creates the list for the processes
     * puts them in their quantum 
     */
    public void createList() { 	    	
    	while( runnableData != null && cnt < 100 )
    	{	   		
    		runtimeProcesses();
        	
        	Process run = highPriority();
    		hpfp.add( run.getName() );
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
     * Finds the highest priority
     * process in the runnable processes
     * @return A process with the highest
     * priority
     */
    public Process highPriority() {
    	Process highP = new Process();
    	
    	for(Process P: runnableData)
    	{
    		if(P.getPriority() > highP.getPriority())
    		{	highP = P;	}
    	}
    	
    	return highP;
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
