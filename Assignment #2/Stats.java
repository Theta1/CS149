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
     * @param list of processes.
     * @return a formatted string of the calculated stats.
     */
    public static String CalculateStats(ArrayList<Process> list) {
	return "Average Turnaround: " + AverageTurnaround(list) +
	    	"\nAverage Waiting: " + AverageWaiting(list) +
	    	"\nAverage Response: " + AverageResponse(list);
    }

    /**
     * Calculates the average turn around time of the provided lists.
     * From submission to completion.
     * @param list of processes
     * @return a float of the average turn around time.
     */
    private static float AverageTurnaround(ArrayList<Process> list) {
    	float turnTime = 0.0f;
    	for(Process p: list)
    	{	turnTime += p.getTurnAroundTime();	}
    	return (turnTime / list.size());
    }

    /**
     * Calculates the average waiting time of the provided lists.
     * From submission until it becomes active
     * @param list of processes
     * @return a float of the average waiting time.
     */
    private static float AverageWaiting(ArrayList<Process> list) {
    	float waiting = 0.0f;
    	for(Process p: list)
    	{	waiting += p.getWaitingTime();	}
    	return (waiting / list.size());
    }

    /**
     * Calculates the average response time of the provided lists.
     * From first active until finished
     * @param list of processes
     * @return a float of the average response time.
     */
    private static float AverageResponse(ArrayList<Process> list) {
    	float response = 0.0f;
    	for(Process p: list){
    		response += p.getResponseTime();
    	}
    	return (response / list.size());
    }
    
    /**
     * Sums the number of processes that finished running within 100 quanta.
     * @param a is a list or process
     * @return a float preresenting the total number of processes that finished running
     */
    public static float CalculaleThroughput(ArrayList<Process> a) {
	float count = 0;
	for(Process p: a){
	    if(p.getEndTime()<100) count++;
	}
	return count;
    }

    /**
     * Calculates the average throughput over the list of lists of processes. 
     * @param listOfLists the list of lists of processes
     * @return a float representing the average
     */
    public static float CalculateAverageThroughput(
	    ArrayList<ArrayList<Process>> listOfLists) {
	float count = 0;
	for(ArrayList<Process> a: listOfLists)
    	{
	    count = CalculaleThroughput(a);
    	}
	return count/listOfLists.size();
    }
}