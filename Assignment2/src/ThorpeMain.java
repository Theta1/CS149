import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Runs the different scheduling algorithms for processes. Calculates statistics
 * for the scheduling
 * 
 * @author Team Inception: David Thorpe, Nathan Kong, Luke Sieben, Dennis Hsu CS
 *         149
 */
public class ThorpeMain {

    ArrayList<ArrayList> listSet = new ArrayList<ArrayList>();

    /**
     * Creates
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

	ArrayList<Process> list1 = new ArrayList<Process>();
	ArrayList<Process> list2 = new ArrayList<Process>();
	ArrayList<Process> list3 = new ArrayList<Process>();
	ArrayList<Process> list4 = new ArrayList<Process>();
	ArrayList<Process> list5 = new ArrayList<Process>();

	loadLists(list1, list2, list3, list4, list5, 30);
	sortLists(list1, list2, list3, list4, list5);

	System.out.println("List 1 data:\n" + printProcessList(list1));
	System.out.println("List 2 data:\n" + printProcessList(list2));
	System.out.println("List 3 data:\n" + printProcessList(list3));
	System.out.println("List 4 data:\n" + printProcessList(list4));
	System.out.println("List 5 data:\n" + printProcessList(list5));

	// runProcessScheduler(list1, list2, list3, list4, list5, "FCFS");
	// runProcessScheduler(list1, list2, list3, list4, list5, "SJF");
	// runProcessScheduler(list1, list2, list3, list4, list5, "SRT");
	runProcessScheduler(list1, list2, list3, list4, list5, "RR");
	// runProcessScheduler(list1, list2, list3, list4, list5, "HPF");

    }

    /**
     * Runs a process scheduler based on the string argument or prints error
     * 
     * @param list1
     * @param list2
     * @param list3
     * @param list4
     * @param list5
     * @param string
     */
    private static void runProcessScheduler(ArrayList<Process> list1,
	    ArrayList<Process> list2, ArrayList<Process> list3,
	    ArrayList<Process> list4, ArrayList<Process> list5, String string) {
	switch (string) {
	case "FCFS":
	    FCFS fcfs1 = new FCFS(list1);
	    printStringList(fcfs1.getStringList());

	    FCFS fcfs2 = new FCFS(list2);
	    printStringList(fcfs2.getStringList());

	    FCFS fcfs3 = new FCFS(list3);
	    printStringList(fcfs3.getStringList());

	    FCFS fcfs4 = new FCFS(list4);
	    printStringList(fcfs4.getStringList());

	    FCFS fcfs5 = new FCFS(list5);
	    printStringList(fcfs5.getStringList());

	    System.out.println(StatisticsCalculator.Calculator(list1));
	    break;
	case "SJF":
	    SJF sjf1 = new SJF(list1);
	    printStringList(sjf1.getStringList());

	    SJF sjf2 = new SJF(list2);
	    printStringList(sjf2.getStringList());

	    SJF sjf3 = new SJF(list3);
	    printStringList(sjf3.getStringList());

	    SJF sjf4 = new SJF(list4);
	    printStringList(sjf4.getStringList());

	    SJF sjf5 = new SJF(list5);
	    printStringList(sjf5.getStringList());

	    System.out.println(StatisticsCalculator.Calculator(list1));
	    break;
	case "SRT":
	    SRT srt1 = new SRT(list1);
	    printStringList(srt1.getStringList());

	    SRT srt2 = new SRT(list2);
	    printStringList(srt2.getStringList());

	    SRT srt3 = new SRT(list3);
	    printStringList(srt3.getStringList());

	    SRT srt4 = new SRT(list4);
	    printStringList(srt4.getStringList());

	    SRT srt5 = new SRT(list5);
	    printStringList(srt5.getStringList());

	    System.out.println(StatisticsCalculator.Calculator(list1));
	    break;
	case "RR":
	    RR rr1 = new RR(list1);
	    printStringList(rr1.getStringList());

	    RR rr2 = new RR(list2);
	    printStringList(rr2.getStringList());

	    RR rr3 = new RR(list3);
	    printStringList(rr3.getStringList());

	    RR rr4 = new RR(list4);
	    printStringList(rr4.getStringList());

	    RR rr5 = new RR(list5);
	    printStringList(rr5.getStringList());

	    System.out.println(StatisticsCalculator.Calculator(rr1.getList()));
	    break;
	case "HPF":
	    HPF hpf1 = new HPF(list1);
	    printStringList(hpf1.getStringList());

	    HPF hpf2 = new HPF(list2);
	    printStringList(hpf2.getStringList());

	    HPF hpf3 = new HPF(list3);
	    printStringList(hpf3.getStringList());

	    HPF hpf4 = new HPF(list4);
	    printStringList(hpf4.getStringList());

	    HPF hpf5 = new HPF(list5);
	    printStringList(hpf5.getStringList());

	    System.out.println(StatisticsCalculator.Calculator(list1));
	    break;
	case "HPFP":
	    HPFP hpfp1 = new HPFP(list1);
	    printStringList(hpfp1.getStringList());

	    HPFP hpfp2 = new HPFP(list2);
	    printStringList(hpfp2.getStringList());

	    HPFP hpfp3 = new HPFP(list3);
	    printStringList(hpfp3.getStringList());

	    HPFP hpfp4 = new HPFP(list4);
	    printStringList(hpfp4.getStringList());

	    HPFP hpfp5 = new HPFP(list5);
	    printStringList(hpfp5.getStringList());

	    System.out.println(StatisticsCalculator.Calculator(list1));
	    break;
	default:
	    System.out.println("Error " + string
		    + " not available for testing.");
	}

    }

    /**
     * Sorts the lists provided based on arrival time.
     * 
     * @param list1
     * @param list2
     * @param list3
     * @param list4
     * @param list5
     */
    private static void sortLists(ArrayList<Process> list1,
	    ArrayList<Process> list2, ArrayList<Process> list3,
	    ArrayList<Process> list4, ArrayList<Process> list5) {
	Comparator<Process> comparator = new Comparator<Process>() {
	    public int compare(Process process1, Process process2) {
		return new Float(process1.getArrivalTime())
			.compareTo(new Float(process2.getArrivalTime()));
	    }
	};
	Collections.sort(list1, comparator);
	Collections.sort(list2, comparator);
	Collections.sort(list3, comparator);
	Collections.sort(list4, comparator);
	Collections.sort(list5, comparator);

    }

    /**
     * Loads lists with random data of amounts specified.
     * 
     * @param list1
     * @param list2
     * @param list3
     * @param list4
     * @param list5
     * @param amount
     */
    private static void loadLists(ArrayList<Process> list1,
	    ArrayList<Process> list2, ArrayList<Process> list3,
	    ArrayList<Process> list4, ArrayList<Process> list5, int amount) {
	for (int i = 0; i < amount * 5; i++) {
	    Process n = new Process(i);
	    if (i < amount) {
		list1.add(n);
	    } else if (i < 2 * amount) {
		list2.add(n);
	    } else if (i < 3 * amount) {
		list3.add(n);
	    } else if (i < 4 * amount) {
		list4.add(n);
	    } else {
		list5.add(n);
	    }
	}

    }

    /**
     * Writes a string of info about a process list.
     * 
     * @param processList
     * @return a formatted string of information
     */
    public static String printProcessList(List<Process> processList) {
	String s = "";
	for (Process process : processList) {
	    s += "[Name: " + String.format("%3s", process.getName())
		    + " --> Arrival Time: "
		    + String.format("%10f", process.getArrivalTime())
		    + ", Run Time: "
		    + String.format("%9f", process.getRunTime())
		    + ", Priority: " + process.getPriority() + "]   \n";
	}
	return s;
    }

    /**
     * Prints out info about a string list separating different strings with
     * []s.
     * 
     * @param stringList
     *            the string list
     */
    public static void printStringList(List<String> stringList) {
	String previousString = stringList.get(0);

	String output = "[";
	for (String string : stringList) {
	    if (string.equals(previousString)) {
		output += String.format("%3s", string) + "|";
	    } else {
		output = output.substring(0, output.length() - 1); // remove
								   // last |
		output += "][" + String.format("%3s", string) + "|";
		previousString = string;
	    }
	}
	output = output.substring(0, output.length() - 1); // remove last |
	output += "]";

	System.out.println(output);
	System.out.println();
    }
}
