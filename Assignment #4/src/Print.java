import java.util.ArrayList;
/**************************************
 * 
 * @author Theta1
 * CS149
 *************************************/

public class Print {

	
	/**
	 * Each time a process is swapped in, or a process completes and therefore
	 * is removed from memory, print a memory map, e.g., [ 1][ 1][ 1][ 1][ 1][..]
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
	void printMap(ArrayList<Integer> mem, Process process) {
		boolean processAdded = false;
		System.out.print("[");
		for(int i: mem)
		{	
			if (i == mem.get(mem.size()-1))
			{	System.out.println(i + "]");	}
			else if (i == 0)
			{	System.out.print("..");	}
			else
			{	
				System.out.printf("%2d",i);
				if (i == process.getName()){
					processAdded = true;
				}
			}
		}
		if (processAdded){
			System.out.printf(" Added: %2d of size " + process.getSize() 
					+ " and durration "	+ process.getDuration(), 
					process.getName());
		}else{
			System.out.printf(" Removed: %2d\n", process.getName());
		}
	}
}
