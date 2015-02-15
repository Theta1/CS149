import java.util.ArrayList;

/**
 * Calculates statistics from the provided lists for analysing process
 * scheduling algorithm schemes
 * 
 * @author Team Inception
 *
 */
public class Stats {
    private static ArrayList<Process> list;
    private static int throughput;
    private static int numOfQuanta;

    public Stats(ArrayList<Process> list, ArrayList<String> stringList) {
    	this.list = list;
    	this.throughput = list.size();
    	this.numOfQuanta = stringList.size();

		print();
    }

    /**
     * Prints the statistics of the processes
     */
    private static void print() {
    	System.out.println("Average Turnaround: " + AverageTurnaround() );
    	System.out.println("Average Waiting: " + AverageWaiting() );
		System.out.println("Average Response: " + AverageResponse() );
		System.out.println("Throughput: " + throughput + " Processes out of " + numOfQuanta + " time slices");
		System.out.println();
    }

    /**
     * Calculates the average turn around time of the provided lists.
     * From submission to completion
     * @return a float of the average turn around time.
     */
    private static float AverageTurnaround() {
    	float turnTime = 0.0f;
    	for(Process p: list)
    	{	turnTime += ((float)p.getTurnAroundTime()) - p.getArrivalTime() + 1;	}
    	return (turnTime / throughput);
    }

    /**
     * Calculates the average waiting time of the provided lists.
     * From submission until it becomes active
     * 
     * 
     * @return a float of the average waiting time.
     */
    private static float AverageWaiting() {
    	float waiting = 0.0f;
    	for(Process p: list)
    	{	waiting += ((float)p.getTurnAroundTime()) - p.getArrivalTime() - p.getQuantaTime();	}
    	return (waiting / throughput);
    }

    /**
     * Calculates the average response time of the provided lists.
     * From first active until finished
     * @return a float of the average response time.
     */
    private static float AverageResponse() {
    	float response = 0.0f;
    	for(Process p: list)
    	{
    		response += ((float)p.getActualStartTime()) - p.getArrivalTime();
    	}
    	return (response / throughput);
    }
}