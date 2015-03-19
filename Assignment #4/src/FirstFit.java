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
	private int maxMem;
	private int[]ff;
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
			int location = findFirstEmpty();
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
	
	public static int findFirstEmpty() {
		return -1;
	}
}
