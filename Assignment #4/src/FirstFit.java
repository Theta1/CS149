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
	private static int[]ff;
	private static int mbCounter;
	private static int time;
	
	FirstFit( ArrayList<Process> p, int m, int t) {
		processes = p;
		maxMem = m;
		time = t;
		ff = new int[m];
		mbCounter = 0;
	}
	
	/**
	 * Run algorithm for 60 seconds
	 * @return number of processes that started
	 */
	public static int run() {
		for(int i = 0; i < time; i++) {
			Process p = processes.get(mbCounter);
			
			//get first empty location
			int location = findFirstEmpty(p.getSize());
			//add process
			if(location < 0) {
				addProcess(p, location);
			}
			
			//remove completed processes
			complete();
			
			//add runtime for the 
			addRuntime();
		}		
		
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
			if (ff[i] == 0) {
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
				
			
			if ((end - start) <= size) {
				return start;
			}
		}

		return -1;
	}
	
	/**
	 * 
	 * @param p
	 * @param start
	 */
	public static void addProcess( Process p, int start) {
		for (; start < p.getSize(); start++) {
			ff[start] = p.getName();
		}
		
		Print.printAdd(p);
	}
}
