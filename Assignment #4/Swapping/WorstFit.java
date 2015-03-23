import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
/**************************************
 * Worst fit
 * Memory is allocated for a process
 * by the biggest sized slot
 * 
 * @author Theta1
 * CS149
 * Assignment #4 Swapping
 *************************************/

public class WorstFit {
	private ArrayList<Process> processes;
	private int maxMem;
	private Process[]ff;
	private int mbCounter;
	private int time;
	private int memCompact;
	private boolean compact;
	
	WorstFit( ArrayList<Process> p, int m, int t, boolean c) {
		processes = p;
		maxMem = m;
		time = t;
		ff = new Process[m];
		mbCounter = 0;
		memCompact = 0;
		compact = c;
	}
	
	/**
	 * Run algorithm for 60 seconds
	 * @return number of processes that started
	 */
	public int run() {
		mbCounter = 0;
		for(int i = 0; i < time; i++) {
			Process p = processes.get(mbCounter);
			
			//get first empty location
			int location = findWorstEmpty(p.getSize());
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
			
			//compaction
			if (i==30 && compact == true) {
				MemoryCompact compact = new MemoryCompact(ff);
				ff = compact.compact();
				memCompact = compact.getMemCompact();
				Print.printMap(ff);
			}
			
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
	public int findWorstEmpty(int size) {
		int start = 0;
		int end = -1;
		
		//create a hashtable(size, position)
		Hashtable<Integer, Integer> best = new Hashtable<Integer, Integer>();
		
		for(int i = 0; i < maxMem; i++) {
			if(i == maxMem -1 && (end - start + 1) >= size) {
				best.put((end - start + 1), start);				
			}
			else if (ff[i] == null) {
				if (start > end)
				{	
					start = i;
					end = i;
				}
				else
				{	end = i;	}
			}
			else {
				//add to hashtable (size=key, starting position=value)  
				if((end - start + 1) >= size) {
					best.put((end - start + 1), start);
				}
				start = i;
			}
		}
		
		//find the smallest size in the hash table
		if(!best.isEmpty()) {
			return findWorst(best);
		}
		return -1;
		
	}
	
	
	public Integer findWorst(Hashtable<Integer, Integer> best){
		ArrayList<Integer> keys = new ArrayList<Integer>(best.keySet());
		Collections.sort(keys);
		Collections.reverse(keys);
				
		return best.get(keys.get(0));
	}
	
	/**
	 * Adds a process to the array
	 * @param p is a process
	 * @param start the starting location to add the process
	 */
	public void addProcess( Process p, int start) {
		int i = start;
		for (; start < (i + p.getSize()); start++) {
			ff[start] = p;
		}
		
		Print.printAdd(p);
	}
	
	/**
	 * removes complete processes
	 */
	public boolean complete() {
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
	public void addRuntime() {
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

	public int getMemCompact() {
		return memCompact;
	}
	
	
	
	
}
