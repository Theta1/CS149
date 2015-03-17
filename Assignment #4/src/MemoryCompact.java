import java.util.ArrayList;
/************************************
 * Compacts the memory
 * so there are no empty spaces
 * between processes
 * 
 * @author Theta1
 * CS149
 * Assignment #4 Swapping
 ***********************************/

public class MemoryCompact {
	private int[] nonCompact;
	private int[] compact;
	private static final int MB = 100;
	
	MemoryCompact( int[] p) {
		nonCompact = p;
		compact = new int[MB];
	}
	
	/**
	 * Takes an non compacted array and
	 * compacts them. 
	 * @return an int array with no spaces
	 */
	public int[] compact() {
		int nonCompactPos = 0;
		int compactPos = 0;
		
		while(nonCompactPos <= MB) {
			if(nonCompactPos > 0)
			{
				compact[compactPos] = nonCompact[nonCompactPos];
				nonCompactPos++;
				compactPos++;
			}
			else
			{	nonCompactPos++;	}
		}
		
		return compact;
	}
}
