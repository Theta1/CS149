import java.util.ArrayList;

/**
 * Calculates statistics from the provided lists for analysing process
 * scheduling algorithm schemes
 * 
 * @author Team Inception
 *
 */
public class Stats {

    /**
     * @param list
     * @return
     */
    public static String CalculateStats(ArrayList<Process> list) {
	return "Average Turnaround: " + AverageTurnaround(list) +
	    	"\nAverage Waiting: " + AverageWaiting(list) +
	    	"\nAverage Response: " + AverageResponse(list);
    }

    /**
     * Calculates the average turn around time of the provided lists.
     * From submission to completion.
     * @param list 
     * @return a float of the average turn around time.
     */
    private static float AverageTurnaround(ArrayList<Process> list) {
    	float turnTime = 0.0f;
    	for(Process p: list)
    	{	turnTime += ((float)p.getTurnAroundTime()) - p.getArrivalTime() + 1;	}
    	return (turnTime / list.size());
    }

    /**
     * Calculates the average waiting time of the provided lists.
     * From submission until it becomes active
     * @param list 
     * 
     * 
     * @return a float of the average waiting time.
     */
    private static float AverageWaiting(ArrayList<Process> list) {
    	float waiting = 0.0f;
    	for(Process p: list)
    	{	waiting += ((float)p.getTurnAroundTime()) - p.getArrivalTime() - p.getQuantaTime();	}
    	return (waiting / list.size());
    }

    /**
     * Calculates the average response time of the provided lists.
     * From first active until finished
     * @param list 
     * @return a float of the average response time.
     */
    private static float AverageResponse(ArrayList<Process> list) {
    	float response = 0.0f;
    	for(Process p: list)
    	{
    		response += ((float)p.getActualStartTime()) - p.getArrivalTime();
    	}
    	return (response / list.size());
    }

    /**
     * @param fCFSclone
     * @return
     */
    public static float CalculateThroughput(
	    ArrayList<ArrayList<Process>> fCFSclone) {
	float count = 0;
	for(ArrayList<Process> a: fCFSclone)
    	{
	    for(Process p: a){
		if(p.getActualStartTime()+p.getTurnAroundTime()>100) count++;
	    }
    	}
	return count/5;
    }
}