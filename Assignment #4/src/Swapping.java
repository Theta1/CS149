import java.util.ArrayList;

public class Swapping {
    private static final int MAINMEM = 100;
    private static final int TOTALPROCESSES = 500;
	
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
		
		//FIRST FIT
		//run 5 times
		FirstFit ff1 = new FirstFit(p1, MAINMEM);
		FirstFit ff2 = new FirstFit(p2, MAINMEM);
		FirstFit ff3 = new FirstFit(p3, MAINMEM);
		FirstFit ff4 = new FirstFit(p4, MAINMEM);
		FirstFit ff5 = new FirstFit(p5, MAINMEM);
		
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

}
