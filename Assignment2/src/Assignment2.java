import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*********************************************
 * Runs the different scheduling algorithms
 * for processes. Calculates statistics for
 * the scheduling
 * @author Team Inception: David Thorpe, Nathan Kong, Luke Sieben, Dennis Hsu
 * CS 149
 **********************************************/
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
		
		
		for(int i=0; i<150; i++)
		{
		    Process n = new Process(i);
		    if (i < 30)
		    {   list1.add(n); }
		    else if (i>=30 && i<60)
		    {	list2.add(n);	}
		    else if (i>=60 && i<90)
		    {	list3.add(n);	}
		    else if (i>=90 && i<120)
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
		
        // print out lists
        printProcessList(list1);
        printProcessList(list2);
        printProcessList(list3);
        printProcessList(list4);
        printProcessList(list5);
        
        // FCFS results
        printStringList(new FCFS(list1).getStringList());
        printStringList(new FCFS(list2).getStringList());
        printStringList(new FCFS(list3).getStringList());
        printStringList(new FCFS(list4).getStringList());
        printStringList(new FCFS(list5).getStringList());

        // SRT results
        printStringList(new SRT(list1).getSRT());
        printStringList(new SRT(list2).getSRT());
        printStringList(new SRT(list3).getSRT());
        printStringList(new SRT(list4).getSRT());
        printStringList(new SRT(list5).getSRT());
        
        //SJF results
        /*printStringList(new SJF(list1).getStringList());
        printStringList(new SJF(list2).getStringList());
        printStringList(new SJF(list3).getStringList());
        printStringList(new SJF(list4).getStringList());
        printStringList(new SJF(list5).getStringList());
        */
                
        //HPF results
        printStringList(new HPF(list1).getStringList());
        printStringList(new HPF(list2).getStringList());
        printStringList(new HPF(list3).getStringList());
        printStringList(new HPF(list4).getStringList());
        printStringList(new HPF(list5).getStringList());
        
        //HPFP results
        printStringList(new HPFP(list1).getStringList());
        printStringList(new HPFP(list2).getStringList());
        printStringList(new HPFP(list3).getStringList());
        printStringList(new HPFP(list4).getStringList());
        printStringList(new HPFP(list5).getStringList());
        
      //RR results
        printStringList(new RR(list1).getStringList());
        printStringList(new RR(list2).getStringList());
        printStringList(new RR(list3).getStringList());
        printStringList(new RR(list4).getStringList());
        printStringList(new RR(list5).getStringList());
    }

    /**
     * Prints out info about a process list.
     * @param processList
     */
    public static void printProcessList(List<Process> processList) {
        for(Process process : processList) {
            System.out.println("[Name: " + String.format("%3s", process.getName()) + " --> Arrival Time: " + String.format("%10f", process.getArrivalTime()) + ", Run Time: " + String.format("%9f", process.getRunTime()) + ", Priority: " + process.getPriority() + "]   ");
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