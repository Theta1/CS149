import java.util.ArrayList;
/**************************************
 * Print statements
 * 
 * @author Theta1
 * CS149
 * Assignment #4 Swapping
 *************************************/

public class Print {

	
	/**
	 * Prints a memory map, e.g., [ 1][ 1][ 1][ 1][ 1][..]
	 * [..][..][..][10][10][10][10][10][..][ 3][ 3][ 3]
	 * where the characters are the process names (one character per MB) and the
	 * dots are holes ([..] per MB). Indicate which process entered or left. For
	 * an entering process, also print its size and duration.
	 * 
	 * @param memory
	 *            is an int array that represents the processes in memory.
	 * @param process
	 *            is the process that has been added or removed.
	 */
	public static void printMap(Process[] ff) {
		System.out.print("[");
		for(int i = 0; i < ff.length; i++)
		{	
			if (i == (ff.length - 1) )
			{	System.out.println("]");	}
			else if (ff[i] == null)
			{	System.out.print(".");	}
			else
			{	System.out.print(ff[i].getName());	}
		}
		
	}
	
	/**
	 * Prints the process thats added with stats
	 * @param process
	 */
	public static void printAdd(Process process) {
		System.out.println("Added: "+ process.getName() + " of size " + process.getSize() 
					+ " and durration "	+ process.getDuration()	);		
	}
	
	/**
	 * Prints the process that is completed
	 * @param process
	 */
	public static void printRemove(Process process) {
		System.out.println("Removed: Process " + process.getName() );
	}
}
