import java.util.ArrayList;

public class Swapping {
    private static final int MAINMEM = 100;
	
	public static void main(String[] args) {
		ArrayList<String> mainMem = new ArrayList<String>();
		
		//create a 5 array of process (100)
		ArrayList<Process> p1 = new ArrayList<Process>();
		ArrayList<Process> p2 = new ArrayList<Process>();
		ArrayList<Process> p3 = new ArrayList<Process>();
		ArrayList<Process> p4 = new ArrayList<Process>();
		ArrayList<Process> p5 = new ArrayList<Process>();
		
		//Creates processes and random values to them
		for(int i=1; i<=500; i++)
		{
		    Process n = new Process(i);
		    if (i < 30)
		    {   p1.add(n); }
		    else if (i>=30 && i<60)
		    {	p2.add(n);	}
		    else if (i>=60 && i<90)
		    {	p3.add(n);	}
		    else if (i>=90 && i<120)
		    {	p4.add(n);	}
		    else
		    {	p5.add(n);	}
		}
		
		
		//run 5 times
		BestFit best1 = new BestFit(p1, MAINMEM);
		BestFit best2 = new BestFit(p2, MAINMEM);
		BestFit best3 = new BestFit(p3, MAINMEM);
		BestFit best4 = new BestFit(p4, MAINMEM);
		BestFit best5 = new BestFit(p5, MAINMEM);
		
		int numOfProcess = best1.run();//returns the number of processes that occured
		
		//get the average and print
		

	}

}
