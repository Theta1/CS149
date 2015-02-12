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
public class Assignment2 {

    /**
     * Creates
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
	
		ArrayList<Process> list1 = new ArrayList <Process>();
		ArrayList<Process> list2 = new ArrayList <Process>();
		ArrayList<Process> list3 = new ArrayList <Process>();
		ArrayList<Process> list4 = new ArrayList <Process>();
		ArrayList<Process> list5 = new ArrayList <Process>();
		
		int processNum = 30;
		for(int i=0; i<600; i++)
		{
		    Process n = new Process(i);
		    if (i < processNum )
		    {   list1.add(n); }
		    else if (i<processNum )
		    {	list2.add(n);	}
		    else if (i<processNum )
		    {	list3.add(n);	}
		    else if (i<processNum )
		    {	list4.add(n);	}
		    else
		    {	list5.add(n);	}
		}

        // sorts the Process by arrival time
        Comparator<Process> comparator = new Comparator<Process>() {
            public int compare(Process process1, Process process2) {
                return new Float(process1.getArrivalTime()).compareTo(new Float( process2.getArrivalTime()));
            }
        };

        Collections.sort(list1, comparator);
//        Collections.sort(list2, comparator);
//        Collections.sort(list3, comparator);
//        Collections.sort(list4, comparator);
//        Collections.sort(list5, comparator);
		
		//SRT srt1 = new SRT(list1);
		 /*
		SRT srt2 = new SRT(list2);
		SRT srt3 = new SRT(list3);
		SRT srt4 = new SRT(list4);
		SRT srt5 = new SRT(list5);*/

        // print out lists and FCFS results
//        printProcessList(list1);
        FCFS fcfs1 = new FCFS(list1);
        printStringList(fcfs1.getStringList());

//        printProcessList(list2);
        FCFS fcfs2 = new FCFS(list2);
        printStringList(fcfs2.getStringList());

//        printProcessList(list3);
        FCFS fcfs3 = new FCFS(list3);
        printStringList(fcfs3.getStringList());

//        printProcessList(list4);
        FCFS fcfs4 = new FCFS(list4);
        printStringList(fcfs4.getStringList());

//        printProcessList(list5);
        FCFS fcfs5 = new FCFS(list5);
        printStringList(fcfs5.getStringList());
		
    }

    /**
     * Prints out info about a string list separating different strings with []s.
     * @param stringList the string list
     */
    public static void printStringList(List<String> stringList) {
        String previousString = stringList.get(0);

        String output = "[";
        for(String string : stringList) {
            if(string.equals(previousString)) {
                output += String.format("%3s", string) + "|";
            }
            else {
                output = output.substring(0, output.length() - 1); // remove last |
                output += "][" + String.format("%3s", string) + "|";
                previousString = string;
            }
        }
        output = output.substring(0, output.length() - 1); // remove last |
        output += "]";
        System.out.println(printQuantaTimeline());
        System.out.println(output);
        System.out.println();

    }
    /**
     * Builds a timetime for each Quanta
     * @return a String representing the timeline
     */
    public static String printQuantaTimeline(){
	String s="[";
	for(int i = 1; i<101; i++){
	    s+=String.format("%1$3d", i)+"|";
	}
        s = s.substring(0, s.length() - 1); // remove last |
        s += "]";
	return s;
    }
}
