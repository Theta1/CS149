import java.util.ArrayList;

/**
 * Calculates statistics from the provided lists for analysing process
 * scheduling algorithm schemes
 * 
 * @author Team Inception
 *
 */
public class StatisticsCalculator {
    private ArrayList<Process> l1;
    private ArrayList<Process> l2;
    private ArrayList<Process> l3;
    private ArrayList<Process> l4;
    private ArrayList<Process> l5;
        
    /**
     * Constructor for the calculator.
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
	    AverageResponseTime();
	    return print();
    }
    
    /**
     * Calculates the average turn around time of the provided lists.
     */
    private static void AvgerageTurnaround(){
	
    }
    
    /**
     * Calculates the average waiting time of the provided lists.
     */
    private static void AverageWaiting(){
	
    }
    
    /**
     * Calculates the average response time of the provided lists.
     */
    private static void AverageResponseTime(){
	
    }
    private static String print(){
	return "";
    }
}