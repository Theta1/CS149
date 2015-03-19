import java.util.ArrayList;

/****************************************
 * Simulates swapping of processes
 * and different algorithms for memory allocation
 * 
 * @author Theta1
 * CS149
 * Assignment #4 Swapping
 ****************************************/

public class Swapping {
    private static final int MAINMEM = 100;
    private static final int TOTALPROCESSES = 60 * 5;
    private static final int TIME = 60;//runs for 60 seconds
	
	public static void main(String[] args) {
		ArrayList<String> mainMem = new ArrayList<String>();
		
		//create a 5 array of process (100)
		ArrayList<Process> p1 = new ArrayList<Process>();
		ArrayList<Process> p2 = new ArrayList<Process>();
		ArrayList<Process> p3 = new ArrayList<Process>();
		ArrayList<Process> p4 = new ArrayList<Process>();
		ArrayList<Process> p5 = new ArrayList<Process>();
		
		//Creates processes and random values to them
		for(int i=1; i<=TOTALPROCESSES; i++)
		{
		    Process n = new Process(i);
		    if (i < (TOTALPROCESSES * (1/5) ) )
		    {   p1.add(n); }
		    else if (i>= (TOTALPROCESSES * (1/5) ) && i<(TOTALPROCESSES * (2/5) ))
		    {	p2.add(n);	}
		    else if (i>=(TOTALPROCESSES * (2/5) ) && i<(TOTALPROCESSES * (3/5) ))
		    {	p3.add(n);	}
		    else if (i>=(TOTALPROCESSES * (3/5) ) && i<(TOTALPROCESSES * (4/5) ))
		    {	p4.add(n);	}
		    else
		    {	p5.add(n);	}
		}
		
		//FIRST FIT
		//run 5 times
		FirstFit ff1 = new FirstFit(deepCopy(p1), MAINMEM, TIME);
		FirstFit ff2 = new FirstFit(deepCopy(p2), MAINMEM, TIME);
		FirstFit ff3 = new FirstFit(deepCopy(p3), MAINMEM, TIME);
		FirstFit ff4 = new FirstFit(deepCopy(p4), MAINMEM, TIME);
		FirstFit ff5 = new FirstFit(deepCopy(p5), MAINMEM, TIME);
		
		//returns the number of processes that was swapped in
		int ffp1 = ff1.run();
		int ffp2 = ff2.run();
		int ffp3 = ff3.run();
		int ffp4 = ff4.run();
		int ffp5 = ff5.run();
		
		System.out.println( "Average number of process swapped for First fit is: " + (ffp1+ffp2+ffp3+ffp4+ffp5) / 5 + "\n\n");
		

		//NEXT FIT
		//run 5 times
		NextFit nf1 = new NextFit(p1, MAINMEM);
		NextFit nf2 = new NextFit(p2, MAINMEM);
		NextFit nf3 = new NextFit(p3, MAINMEM);
		NextFit nf4 = new NextFit(p4, MAINMEM);
		NextFit nf5 = new NextFit(p5, MAINMEM);
		
		//returns the number of processes that was swapped in
		int nfp1 = nf1.run();
		int nfp2 = nf2.run();
		int nfp3 = nf3.run();
		int nfp4 = nf4.run();
		int nfp5 = nf5.run();
		
		System.out.println( "Average number of process swapped for Next fit is: " + (nfp1+nfp2+nfp3+nfp4+nfp5) / 5 + "\n\n");
		
		//wfp FIT
		//run 5 times
		BestFit best1 = new BestFit(p1, MAINMEM);
		BestFit best2 = new BestFit(p2, MAINMEM);
		BestFit best3 = new BestFit(p3, MAINMEM);
		BestFit best4 = new BestFit(p4, MAINMEM);
		BestFit best5 = new BestFit(p5, MAINMEM);
		
		//returns the number of processes that was swapped in
		int BFprocess1 = best1.run();
		int BFprocess2 = best2.run();
		int BFprocess3 = best3.run();
		int BFprocess4 = best4.run();
		int BFprocess5 = best5.run();
		
		System.out.println( "Average number of process swapped for Best fit is: " + (BFprocess1+BFprocess2+BFprocess3+BFprocess4+BFprocess5) / 5 + "\n\n");
		
		//WORST FIT
		//run 5 times
		WorstFit wf1 = new WorstFit(p1, MAINMEM);
		WorstFit wf2 = new WorstFit(p2, MAINMEM);
		WorstFit wf3 = new WorstFit(p3, MAINMEM);
		WorstFit wf4 = new WorstFit(p4, MAINMEM);
		WorstFit wf5 = new WorstFit(p5, MAINMEM);
		
		//returns the number of processes that was swapped in
		int wfp1 = wf1.run();
		int wfp2 = wf2.run();
		int wfp3 = wf3.run();
		int wfp4 = wf4.run();
		int wfp5 = wf5.run();
		
		System.out.println( "Average number of process swapped for Worst fit is: " + (wfp1+wfp2+wfp3+wfp4+wfp5) / 5 + "\n\n");
	}
	
	   /**
     * Takes 5 lists and makes clones of them
     * @param list1, list2, list3, list4, list5
     * @return clone is an arraylist of 5 arraylist of processes
     */
    public static ArrayList<Process> deepCopy( ArrayList<Process> list) {
    	ArrayList<Process> clone = new ArrayList<Process>();
    	
    	for(Process p: list)
    	{   clone.add(p.clone());   }    	
    	return clone;
    }

}
