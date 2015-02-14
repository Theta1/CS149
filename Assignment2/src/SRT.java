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
    public ArrayList<String> getStringList() {
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

            if (runnableData.size() != 0)
            {
                Process addProcess = findShortTime();
                srt.add( addProcess.getName());
                addProcess.decrementRunTime();
            }
            else
            {	srt.add("");	}
            quantum++;

            removeProcess();
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
            if( p.getArrivalTime() < quantum )
            {
                runnableData.add( processData.remove(processData.indexOf(p)) );
            }
        }
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
