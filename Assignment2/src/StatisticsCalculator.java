import java.util.ArrayList;

/**
 * Calculates statistics from the provided lists for analysing process
 * scheduling algorithm schemes
 * 
 * @author Team Inception
 *
 */
public class StatisticsCalculator {
    private static ArrayList<Process> l1;
    private ArrayList<Process> l2;
    private ArrayList<Process> l3;
    private ArrayList<Process> l4;
    private ArrayList<Process> l5;

    private float averageTurnaroundTime;
    private float averageWaitTime;
    private float averageResponseTime;
    
    /**
     * Calculates the average number of process that finnish over 100 across five sets of processes
     * @return
     */
    public static String calculateThroughput(ArrayList<Process> list1,
	    ArrayList<Process> list2, ArrayList<Process> list3,
	    ArrayList<Process> list4, ArrayList<Process> list5){
	int count = 0;
	for(Process process: list1){
	    if(process.getActualStartTime()+process.getTurnAroundTime()<100) count++;
	}
	for(Process process1: list2){
	    if(process1.getActualStartTime()+process1.getTurnAroundTime()<100) count++;	    
	}
	for(Process process1: list3){
	    if(process1.getActualStartTime()+process1.getTurnAroundTime()<100) count++;
	}
	for(Process process1: list4){
	    if(process1.getActualStartTime()+process1.getTurnAroundTime()<100) count++;
	}
	for(Process process1: list5){
	    if(process1.getActualStartTime()+process1.getTurnAroundTime()<100) count++;
	}
	return "The average algorythm throughput was: "+count/5;
    }
    
    /**
     * Calculates the statistics for the list;
     * @param list
     * @return
     */
    public static String RunStatistics(ArrayList<Process> list){
	int averageTurnAroundTime = 0;
	int averageResponceTime = 0;
	int averageWaitTime = 0;
	for (Process process: list){
	    averageWaitTime += process.getResponseTime();
	    averageTurnAroundTime += process.getTurnAroundTime();
	    averageResponceTime += process.getResponseTime(); 
	}
	int count = list.size();
	return "Statistics:\nThe turnaround time was: " + averageTurnAroundTime/count + 
		"\nThe average waiting time was: " + averageWaitTime/count +
		"\nThe average responce time was: " + averageResponceTime/count;
    }

    /**
     * Concatenates the results into a String
     * @return a formatted String of the results
     */
    private static String print() {
	return "Avgerage Turnaround: " + AvgerageTurnaround() + "\n"
		+ "Average Waiting: " + AverageWaiting() + "\n"
		+ "Average Response: " + AverageResponse();
    }

    /**
     * Calculates the average turn around time of the provided lists.
     * From submission to completion
     * @return a float of the average turn around time.
     */
    private static float AvgerageTurnaround() {
	return 0.0f;
    }

    /**
     * Calculates the average waiting time of the provided lists.
     * From submission until it becomes active
     * 
     * How do we know that it has been run and that it didn't fall off the list?
     * 
     * @return a float of the average waiting time.
     */
    private static float AverageWaiting() {
	int sum = 0;
	int i = 0;
	for(Process p:l1){
	    sum+=p.getActualStartTime();
	    i++;
	}
	return sum/i;
    }

    /**
     * Calculates the average response time of the provided lists.
     * From first active until finnished
     * @return a float of the average response time.
     */
    private static float AverageResponse() {
	return 0.0f;
    }
}