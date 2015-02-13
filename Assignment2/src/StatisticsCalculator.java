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
     * Constructor for the calculator.
     * 
     * @param list1
     *            a list to be calculated
     * @param list2
     *            a list to be calculated
     * @param list3
     *            a list to be calculated
     * @param list4
     *            a list to be calculated
     * @param list5
     *            a list to be calculated
     */
    public static String Calculator(ArrayList<Process> list1,
	    ArrayList<Process> list2, ArrayList<Process> list3,
	    ArrayList<Process> list4, ArrayList<Process> list5) {
	ArrayList<Process> l1 = list1;
	ArrayList<Process> l2 = list2;
	ArrayList<Process> l3 = list3;
	ArrayList<Process> l4 = list4;
	ArrayList<Process> l5 = list5;
	AvgerageTurnaround();
	AverageWaiting();
	AverageResponse();
	return print();
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