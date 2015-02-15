
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
        
        //Creates processes and random values to them
        for(int i=0; i<150; i++)
        {
            Process n = new Process(i);
            if (i < 30)
            {   list1.add(n); }
            else if (i>=30 && i<60)
            {   list2.add(n);   }
            else if (i>=60 && i<90)
            {   list3.add(n);   }
            else if (i>=90 && i<120)
            {   list4.add(n);   }
            else
            {   list5.add(n);   }
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
        System.out.println("------------------------------------------------------------------------------");
        printProcessList(list2);
        System.out.println("------------------------------------------------------------------------------");
        printProcessList(list3);
        System.out.println("------------------------------------------------------------------------------");
        printProcessList(list4);
        System.out.println("------------------------------------------------------------------------------");
        printProcessList(list5);
        System.out.println("------------------------------------------------------------------------------");
        
        // FCFS results
        System.out.println("FCFS");        
        ArrayList<ArrayList<Process>> FCFSclone = deepCopy(list1, list2, list3, list4, list5);
        for (ArrayList<Process> list: FCFSclone)
        {
            FCFS fcfs = new FCFS(list);
            printTimeline();
            printStringList( fcfs.getStringList() );
            new Stats(fcfs.getStats(), fcfs.getStringList());
        }
        System.out.println("\n\n");
        
        //SJF results
        System.out.println("SJF");
        ArrayList<ArrayList<Process>> SJFclone = deepCopy(list1, list2, list3, list4, list5);
        for (ArrayList<Process> list: SJFclone)
        {
            SJF sjf = new SJF(list);
            printTimeline();
            printStringList( sjf.getStringList() );
            new Stats(sjf.getStats(), sjf.getStringList());
        } 
        System.out.println("\n\n");
        
        // SRT results
        System.out.println("SRT");
        ArrayList<ArrayList<Process>> SRTclone = deepCopy(list1, list2, list3, list4, list5);
        for (ArrayList<Process> list: SRTclone)
        {
            SRT srt = new SRT(list);
            printTimeline();
            printStringList( srt.getStringList() );
            new Stats(srt.getStats(), srt.getStringList());
        }
        System.out.println("\n\n");
        
        //RR results
        System.out.println("RR");
        ArrayList<ArrayList<Process>> RRclone = deepCopy(list1, list2, list3, list4, list5);
        for (ArrayList<Process> list: RRclone)
        {
            RR rr = new RR(list);
            printTimeline();
            printStringList( rr.getStringList() );
            new Stats(rr.getStats(), rr.getStringList());
        }
        System.out.println("\n\n");
        
        //HPF results
        System.out.println("HPF");
        ArrayList<ArrayList<Process>> HPFclone = deepCopy(list1, list2, list3, list4, list5);
        for (ArrayList<Process> list: HPFclone)
        {
            HPF hpf = new HPF(list);
            printTimeline();
            printStringList( hpf.getStringList() );
            new Stats(hpf.getStats(), hpf.getStringList());
        }
        System.out.println("\n\n");
        
        //HPFP results
        System.out.println("HPFP");
        ArrayList<ArrayList<Process>> HPFPclone = deepCopy(list1, list2, list3, list4, list5);
        for (ArrayList<Process> list: HPFPclone)
        {
            HPFP hpfp = new HPFP(list);
            printTimeline();
            printStringList( hpfp.getStringList() );
            new Stats(hpfp.getStats(), hpfp.getStringList());
        }     
        System.out.println("\n\n");
        
    }

    /**
     * Prints out info about a process list.
     * @param processList
     */
    public static void printProcessList(List<Process> processList) {
        for(Process process : processList) {
            System.out.println("[Name: " + String.format("%3s", process.getName()) +
                    " --> Arrival Time: " + String.format("%10f", process.getArrivalTime()) + 
                    ", Run Time: " + String.format("%9f", process.getRunTime()) + 
                    ", Priority: " + process.getPriority() + "]   ");
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
     * @param stringList the string list
     */
    public static void printTimeline() {

        String output = "[";
        for(int i = 1; i<=100; i++) {
                output += String.format("%3d",i) + "|";
        }
        output = output.substring(0, output.length() - 1); // remove last |
        output += "]";

        System.out.println(output);
    }
    
    /**
     * Takes 5 lists and makes clones of them
     * @param list1, list2, list3, list4, list5
     * @return clone is an arraylist of 5 arraylist of processes
     */
    public static ArrayList<ArrayList<Process>> deepCopy( ArrayList<Process> list1, ArrayList<Process> list2,
            ArrayList<Process> list3, ArrayList<Process> list4, ArrayList<Process> list5) {
        ArrayList<Process> list1clone = new ArrayList<Process>();
        ArrayList<Process> list2clone = new ArrayList<Process>();
        ArrayList<Process> list3clone = new ArrayList<Process>();
        ArrayList<Process> list4clone = new ArrayList<Process>();
        ArrayList<Process> list5clone = new ArrayList<Process>();
        ArrayList<ArrayList<Process>> clone = new ArrayList<ArrayList<Process>>();
        
        for(Process p: list1)
        {   list1clone.add(p.clone());   }
        clone.add(list1clone);

        for(Process p: list2)
        {   list2clone.add(p.clone());   }
        clone.add(list2clone);
        
        for(Process p: list3)
        {   list3clone.add(p.clone());   }
        clone.add(list3clone);
        
        for(Process p: list4)
        {   list4clone.add(p.clone());   }
        clone.add(list4clone);
        
        for(Process p: list5)
        {   list5clone.add(p.clone());   }
        clone.add(list5clone);
        
        return clone;
    }
}