import java.util.ArrayList;
import java.util.List;


/**
 * Runs the different scheduling algorithms
 * for processes. Calculates statistics for
 * the scheduling
 * @author David-Eric Thorpe
 */
public class Assignment2 {

    /**
     * Creates 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	
<<<<<<< HEAD
		List<Process> list1 = new ArrayList <Process>();
		List<Process> list2 = new ArrayList <Process>();
		List<Process> list3 = new ArrayList <Process>();
		List<Process> list4 = new ArrayList <Process>();
		List<Process> list5 = new ArrayList <Process>();
		
		
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
		
		
		
		
=======
	List <Process>l1 = new ArrayList <Process>();
	
	for(int i=0;i<30;i++){
	    Process n = new Process(i);
	    l1.add(n);
	}
	
	SJF sjf = new SJF(l1);
	
>>>>>>> SJF sorts by arrival time
    }    
}
