import java.util.ArrayList;

/**
 * 
 */

/**
 * Main Method for testing Process Object
 * 
 * @author Standard
 *
 */
public class TestProcess {

    /**
     * 
     * @param args
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
	for(int i=0; i<30; i++)
	{
	System.out.println(list1.get(i).getArrivalTime());
	}
    }

}
