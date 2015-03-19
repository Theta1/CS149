import java.util.ArrayList;
/**************************************
 * First fit
 * Memory is allocated for a process
 * by the first available slot
 * 
 * @author Theta1
 * CS149
 * Assignment #4 Swapping
 *************************************/

public class FirstFit {
	private static ArrayList<Process> processes;
	private static int maxMem;
	private static Process[]ff;
	private static int mbCounter;
	private static int time;
	
	FirstFit( ArrayList<Process> p, int m, int t) {
		processes = p;
		maxMem = m;
		time = t;
		ff = new Process[m];
		mbCounter = 0;
	}
	
	/**
	 * Run algorithm for 60 seconds
	 * @return number of processes that started
	 */
	public static int run() {
		mbCounter = 0;
		for(int i = 0; i < time; i++) {
			Process p = processes.get(mbCounter);
			
			//get first empty location
			int location = findFirstEmpty(p.getSize());
			//add process
			if(location >= 0) {
				addProcess(p, location);
				Print.printMap(ff);
				mbCounter++;
			}
			
			//remove completed processes
			boolean removed = false;
			removed = complete();
			if(removed) {	Print.printMap(ff);	}
			
			//add runtime for the 
			addRuntime();
			
		}		
		
		System.out.println("\n");
		return mbCounter;
	}
	
	/**
	 * finds the first empty location in the array
	 * @param size the needed to fill the process
	 * @return the first location in the array
	 * to fill the array
	 */
	public static int findFirstEmpty(int size) {
		int start = 0;
		int end = -1;
		
		for(int i = 0; i < maxMem; i++) {
			if (ff[i] == null) {
				if (start > end)
				{	
					start = i;
					end = i;
				}
				else
				{	end = i;	}
			}
			else {
				start = i;
			}
				
			
			if ((end - start + 1) >= size) {
				return start;
			}
		}

		return -1;
	}
	
	/**
	 * Adds a process to the array
	 * @param p is a process
	 * @param start the starting location to add the process
	 */
	public static void addProcess( Process p, int start) {
		int i = start;
		for (; start < (i + p.getSize()); start++) {
			ff[start] = p;
		}
		
		Print.printAdd(p);
	}
	
	/**
	 * removes complete processes
	 */
	public static boolean complete() {
		Process q = new Process();
		boolean removed = false;
		for (int i = 0; i < maxMem; i++)
		{
			for(Process p: processes)
			{
				if ( p.equals(ff[i]) )
				{
					if (p.getDuration() == 0 && !p.equals(q))
					{	
						q = ff[i];
						Print.printRemove(ff[i]);
						removed = true;
						ff[i] = null;
					}
					else if (p.getDuration() == 0)
					{	ff[i] = null;	} 
				}
			}
		}
		return removed;
	}
	
	/**
	 * removes runtime by decreasing the duration
	 */
	public static void addRuntime() {
		for (int i = 0; i < maxMem; i++)
		{
			for(Process p: processes)
			{
				if ( p.equals(ff[i]) )
				{
					if (i == 0) {	p.decrementDuration();	} 
					else if (ff[i]!= ff[i-1])	{	p.decrementDuration();	}
				}
			}
		}
	}
	
}
