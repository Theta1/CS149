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
	private ArrayList<Process> processes;
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
	
	public static int run() {
		for(int i = 0; i < time; i++) {
			
			
			
			
		}		
		
		return mbCounter;
	}
}
