import java.awt.List;
import java.util.ArrayList;


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
		
		list1 = sort(list1);
		list2 = sort(list2);
		list3 = sort(list3);
		list4 = sort(list4);
		list5 = sort(list5);
		
		SRT srt1 = new SRT(list1);
		SRT srt2 = new SRT(list2);
		SRT srt3 = new SRT(list3);
		SRT srt4 = new SRT(list4);
		SRT srt5 = new SRT(list5);
		
    }  
    
    /**
     * Sorts the process data
     * by arrival time
     */
    public static ArrayList<Process> sort(ArrayList<Process> processData) {
    	for (int i = 0; i < processData.size()-1; i++)
    	{
    		for (int j = 1; j < processData.size(); j++ )
    		{
    			if (processData.get(j).getArrivalTime() < processData.get(i).getArrivalTime())
    			{
    				Process k = processData.get(i);
    				processData.set(i, processData.get(j));
    				processData.set(j, k);
    			}
    		}
    	}
    	return processData;
    }
}
