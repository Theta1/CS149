import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Runs the different scheduling algorithms
 * for processes. Calculates statistics for
 * the scheduling
 * @author Team Inception: David Thorpe, Nathan Kong, Luke Sieben, Dennis Hsu
 * CS 149
 */
public class Assignment2 {

    /**
     * Creates 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	
		ArrayList<Process> list1 = new ArrayList <Process>();
		ArrayList<Process> list2 = new ArrayList <Process>();
		ArrayList<Process> list3 = new ArrayList <Process>();
		ArrayList<Process> list4 = new ArrayList <Process>();
		ArrayList<Process> list5 = new ArrayList <Process>();
		
		int numberOfProcesses = 30;
		for(int i=0; i<numberOfProcesses*5; i++)
		{
		    Process n = new Process(i);
		    if (i < numberOfProcesses)
		    {   list1.add(n); }
		    else if (i < numberOfProcesses * 2)
		    {	list2.add(n);	}
		    else if (i < numberOfProcesses * 3)
		    {	list3.add(n);	}
		    else if (i < numberOfProcesses * 4)
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
        Collections.sort(list2, comparator);
        Collections.sort(list3, comparator);
        Collections.sort(list4, comparator);
        Collections.sort(list5, comparator);
		
		//SRT srt1 = new SRT(list1);
		 /*
		SRT srt2 = new SRT(list2);
		SRT srt3 = new SRT(list3);
		SRT srt4 = new SRT(list4);
		SRT srt5 = new SRT(list5);*/

        // print out lists and FCFS results
        FCFS fcfs1 = new FCFS(list1);
        FCFS fcfs2 = new FCFS(list2);
        FCFS fcfs3 = new FCFS(list3);
        FCFS fcfs4 = new FCFS(list4);
        FCFS fcfs5 = new FCFS(list5);
        WorkthroughList(fcfs1, list1);
        WorkthroughList(fcfs2, list2);
        WorkthroughList(fcfs3, list3);
        WorkthroughList(fcfs4, list4);
        WorkthroughList(fcfs5, list5);
        System.out.println("\n"+StatisticsCalculator.calculateThroughput(list1, list2, list3, list4, list5));
/*
        printProcessList(list2);
        FCFS fcfs2 = new FCFS(list2);
        printStringList(fcfs2.getStringList());

        printProcessList(list3);
        FCFS fcfs3 = new FCFS(list3);
        printStringList(fcfs3.getStringList());

        printProcessList(list4);
        FCFS fcfs4 = new FCFS(list4);
        printStringList(fcfs4.getStringList());

        printProcessList(list5);
        FCFS fcfs5 = new FCFS(list5);
        printStringList(fcfs5.getStringList());

        printProcessList(list1);
        //printStringList(new HPF(list1).getStringList());
        printStringList(new HPFP(list1).getStringList());
        */
    }

    private static void WorkthroughList(FCFS fcfs1, ArrayList<Process> list1) {
        System.out.println("Origonal list of processes:");
        printProcessList(list1);
        System.out.println();
        System.out.println("Process Order:");
        printQuantaList();
        printStringList(fcfs1.getStringList());
        System.out.println("\nProcess details:");
        printProcessList(fcfs1.getProcessedProcessList());
        System.out.println();
        System.out.println(StatisticsCalculator.RunStatistics(fcfs1.getProcessedProcessList())+"\n");
    }

    /**
     * Prints out info about a process list.
     * @param processList
     */
    public static void printProcessList(List<Process> processList) {
        for(Process process : processList) {
            System.out.println("[Name: " + String.format("%3s", process.getName()) 
        	    + " --> Arrival Time: " + String.format("%10f", process.getArrivalTime()) 
        	    + ", Run Time: " + String.format("%9f", process.getRunTime()) 
        	    + ", Priority: " + process.getPriority() 
        	    + ", Final runtime: " + String.format("%9f", process.getRunTime())
        	    + ", Time started: " + String.format("%9d", process.getActualStartTime())
        	    + ", Turn Around time: " + String.format("%9d", process.getTurnAroundTime())
        	    + ", Waiting time: " + String.format("%9d", process.getWaitingTime())
        	    + ", Responce time: " + String.format("%9d", process.getResponseTime())
        	    + "]   ");
        }
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
                output += "|" + String.format("%3s", string) + "|";
                previousString = string;
            }
        }
        output = output.substring(0, output.length() - 1); // remove last |
        output += "]";

        System.out.println(output);
    }
    
    /**
     * Prints out info about a string list separating different strings with []s.
     */
    public static void printQuantaList() {

        String output = "[";
        for(int i = 0; i < 100; i++) {
            output += String.format("%3s", i) + "|";
        }
            output = output.substring(0, output.length() - 1); // remove last |

        output += "]";

        System.out.println(output);
    }
}
