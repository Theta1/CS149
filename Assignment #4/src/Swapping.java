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
    private static final int TOTALPROCESSES = 70;
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
		for(int i=1; i<=TOTALPROCESSES * 5; i++)
		{
		    Process n = new Process(i);
		    if (i < (TOTALPROCESSES) ) 
		    {   p1.add(n); }
		    else if (i<(TOTALPROCESSES * 2 ))
		    {	p2.add(n);	}
		    else if (i<(TOTALPROCESSES * 3 ))
		    {	p3.add(n);	}
		    else if (i<(TOTALPROCESSES * 4 ))
		    {	p4.add(n);	}
		    else
		    {	p5.add(n);	}
		}
		
		    
		//FIRST FIT
		//run 5 times
		FirstFit ff1 = new FirstFit(deepCopy(p1), MAINMEM, TIME, true);
		FirstFit ff2 = new FirstFit(deepCopy(p2), MAINMEM, TIME, true);
		FirstFit ff3 = new FirstFit(deepCopy(p3), MAINMEM, TIME, true);
		FirstFit ff4 = new FirstFit(deepCopy(p4), MAINMEM, TIME, true);
		FirstFit ff5 = new FirstFit(deepCopy(p5), MAINMEM, TIME, true);
		FirstFit ff10 = new FirstFit(deepCopy(p1), MAINMEM, TIME, false);
		FirstFit ff20 = new FirstFit(deepCopy(p2), MAINMEM, TIME, false);
		FirstFit ff30 = new FirstFit(deepCopy(p3), MAINMEM, TIME, false);
		FirstFit ff40 = new FirstFit(deepCopy(p4), MAINMEM, TIME, false);
		FirstFit ff50 = new FirstFit(deepCopy(p5), MAINMEM, TIME, false);
		
		//returns the number of processes that was swapped in
		int ffp1 = ff1.run(); 
		int ffp2 = ff2.run(); 
		int ffp3 = ff3.run(); 
		int ffp4 = ff4.run(); 
		int ffp5 = ff5.run();
		int ffp10 = ff10.run(); 
		int ffp20 = ff20.run(); 
		int ffp30 = ff30.run(); 
		int ffp40 = ff40.run(); 
		int ffp50 = ff50.run();
		
		System.out.println("First Fit");
		System.out.println( "Average number of process swapped with compaction for First fit is: " + (ffp1+ffp2+ffp3+ffp4+ffp5) / 5.0 );
		System.out.println( "Average number of MB compacted for Worst fit is: " + (ff1.getMemCompact()+ff2.getMemCompact()+ff3.getMemCompact()+ff4.getMemCompact()+ff5.getMemCompact()) / 5.0 );
		System.out.println( "Average number of process swapped without compaction for First fit is: " + (ffp10+ffp20+ffp30+ffp40+ffp50) / 5.0 + "\n" );
		
		//NEXT FIT
		//run 5 times
		NextFit nf1 = new NextFit(deepCopy(p1), MAINMEM, TIME, true);
		NextFit nf2 = new NextFit(deepCopy(p2), MAINMEM, TIME, true);
		NextFit nf3 = new NextFit(deepCopy(p3), MAINMEM, TIME, true);
		NextFit nf4 = new NextFit(deepCopy(p4), MAINMEM, TIME, true);
		NextFit nf5 = new NextFit(deepCopy(p5), MAINMEM, TIME, true);
		NextFit nf10 = new NextFit(deepCopy(p1), MAINMEM, TIME, false);
		NextFit nf20 = new NextFit(deepCopy(p2), MAINMEM, TIME, false);
		NextFit nf30 = new NextFit(deepCopy(p3), MAINMEM, TIME, false);
		NextFit nf40 = new NextFit(deepCopy(p4), MAINMEM, TIME, false);
		NextFit nf50 = new NextFit(deepCopy(p5), MAINMEM, TIME, false);
		
		//returns the number of processes that was swapped in
		int nfp1 = nf1.run(); 
		int nfp2 = nf2.run();
		int nfp3 = nf3.run();
		int nfp4 = nf4.run();
		int nfp5 = nf5.run();
		int nfp10 = nf10.run(); 
		int nfp20 = nf20.run();
		int nfp30 = nf30.run();
		int nfp40 = nf40.run();
		int nfp50 = nf50.run();
		
		System.out.println("Next Fit");
		System.out.println( "Average number of process swapped with compaction for Next fit is: " + (nfp1+nfp2+nfp3+nfp4+nfp5) / 5.0 );
		System.out.println( "Average number of MB compacted for Worst fit is: " + (nf1.getMemCompact()+nf2.getMemCompact()+nf3.getMemCompact()+nf4.getMemCompact()+nf5.getMemCompact()) / 5.0);
		System.out.println( "Average number of process swapped without compaction for Next fit is: " + (nfp10+nfp20+nfp30+nfp40+nfp50) / 5.0 + "\n");
		
		//wfp FIT
		//run 5 times
		BestFit best1 = new BestFit(deepCopy(p1), MAINMEM, TIME, true);
		BestFit best2 = new BestFit(deepCopy(p2), MAINMEM, TIME, true);
		BestFit best3 = new BestFit(deepCopy(p3), MAINMEM, TIME, true);
		BestFit best4 = new BestFit(deepCopy(p4), MAINMEM, TIME, true);
		BestFit best5 = new BestFit(deepCopy(p5), MAINMEM, TIME, true);
		BestFit best10 = new BestFit(deepCopy(p1), MAINMEM, TIME, false);
		BestFit best20 = new BestFit(deepCopy(p2), MAINMEM, TIME, false);
		BestFit best30 = new BestFit(deepCopy(p3), MAINMEM, TIME, false);
		BestFit best40 = new BestFit(deepCopy(p4), MAINMEM, TIME, false);
		BestFit best50 = new BestFit(deepCopy(p5), MAINMEM, TIME, false);
		
		//returns the number of processes that was swapped in
		int BFprocess1 = best1.run();
		int BFprocess2 = best2.run();
		int BFprocess3 = best3.run();
		int BFprocess4 = best4.run();
		int BFprocess5 = best5.run();
		int BFprocess10 = best10.run();
		int BFprocess20 = best20.run();
		int BFprocess30 = best30.run();
		int BFprocess40 = best40.run();
		int BFprocess50 = best50.run();
		
		System.out.println("Best fit");
		System.out.println( "Average number of process swapped with compaction for Best fit is: " + (BFprocess1+BFprocess2+BFprocess3+BFprocess4+BFprocess5) / 5.0);
		System.out.println( "Average number of MB compacted for Worst fit is: " + (best1.getMemCompact()+best2.getMemCompact()+best3.getMemCompact()+best4.getMemCompact()+best5.getMemCompact()) / 5.0 );
		System.out.println( "Average number of process swapped without compaction for Best fit is: " + (BFprocess10+BFprocess20+BFprocess30+BFprocess40+BFprocess50) / 5.0 + "\n");

		//WORST FIT
		//run 5 times
		WorstFit wf1 = new WorstFit(deepCopy(p1), MAINMEM, TIME, true);
		WorstFit wf2 = new WorstFit(deepCopy(p2), MAINMEM, TIME, true);
		WorstFit wf3 = new WorstFit(deepCopy(p3), MAINMEM, TIME, true);
		WorstFit wf4 = new WorstFit(deepCopy(p4), MAINMEM, TIME, true);
		WorstFit wf5 = new WorstFit(deepCopy(p5), MAINMEM, TIME, true);
		WorstFit wf10 = new WorstFit(deepCopy(p1), MAINMEM, TIME, false);
		WorstFit wf20 = new WorstFit(deepCopy(p2), MAINMEM, TIME, false);
		WorstFit wf30 = new WorstFit(deepCopy(p3), MAINMEM, TIME, false);
		WorstFit wf40 = new WorstFit(deepCopy(p4), MAINMEM, TIME, false);
		WorstFit wf50 = new WorstFit(deepCopy(p5), MAINMEM, TIME, false);
		
		//returns the number of processes that was swapped in
		int wfp1 = wf1.run();
		int wfp2 = wf2.run();
		int wfp3 = wf3.run();
		int wfp4 = wf4.run();
		int wfp5 = wf5.run();
		int wfp10 = wf10.run();
		int wfp20 = wf20.run();
		int wfp30 = wf30.run();
		int wfp40 = wf40.run();
		int wfp50 = wf50.run();
		
		System.out.println("Worst fit");
		System.out.println( "Average number of process swapped with compaction for Worst fit is: " + (wfp1+wfp2+wfp3+wfp4+wfp5) / 5.0 );
		System.out.println( "Average number of MB compacted for Worst fit is: " + (wf1.getMemCompact()+wf2.getMemCompact()+wf3.getMemCompact()+wf4.getMemCompact()+wf5.getMemCompact()) / 5.0 );
		System.out.println( "Average number of process swapped without compaction for Worst fit is: " + (wfp10+wfp20+wfp30+wfp40+wfp50) / 5.0 + "\n");

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
