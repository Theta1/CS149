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

public class SRT implements SchedulerInterface{
    private ArrayList<Process> processData;
    private ArrayList<Process> runnableData;
    private ArrayList<Process> stats;
    private ArrayList<String> srt;
    private int quantum;

    public SRT(ArrayList<Process> processData) {
        this.processData = processData;
        this.runnableData = new ArrayList<Process>();
        this.stats = new ArrayList<Process>();
        this.srt = new ArrayList<String>();
        this.quantum = 0;

        run();
    }

    public ArrayList<String> getStringList() {
        return srt;
    }
    
    public ArrayList<Process> getStats() {
    	return stats;
    }


    /**
     * Creates the list for the processes
     * puts them in their quantum 
     */
    public void run() {
        while( quantum < 100 || !runnableData.isEmpty() )
        {
        	if (quantum < 100)
            {	runtimeProcesses(); }

            if (runnableData.isEmpty())
            {	srt.add("");	}
            else
            {	
                Process addProcess = findShortTime();
                if(addProcess != null)
                {
	                srt.add( addProcess.getName());
	                addProcess.decrementRunTime();
	                addProcess.incrementQuantaTime();
	                addProcess.setActualStartTime(quantum);
                }
                else
                {	runnableData.clear();	}
            }
            removeProcess();
            quantum++;
        }

    }

    /**
     * Adds the Processes
     * at their time interval
     * to the arraylist runnableData
     */
    public void runtimeProcesses() {
        for(Process p: processData)
        {
            if( p.getArrivalTime() <= quantum )
            {   runnableData.add(p);	}
        }
        processData.removeAll(runnableData);
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
            if( quantum < 100 && (p.getRunTime() < shortTime.getRunTime()) )
            {  shortTime = p;   }
            if( quantum >= 100 && (p.getRunTime() < shortTime.getRunTime()) && (p.getActualStartTime() > -1)  )
            {	shortTime = p;	}
        }
        
        if(quantum < 100)
        {	return shortTime;	}
        if(quantum >= 100 && shortTime.getActualStartTime() > -1)
        {	return shortTime;	}
        return null;
    }

    /**
     * Removes processes that are completed
     */
    public void removeProcess() { 	
        for( Process r: runnableData)
        {
            if( r.getRunTime() <= 0 )
            {
            	r.setEndTime(quantum);
            	stats.add(r);
            }
        }
        runnableData.removeAll(stats);
    }

}
