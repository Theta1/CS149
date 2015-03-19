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
	private Process[] nonCompact;
	private Process[] compact;
	private static final int MB = 100;
	
	MemoryCompact( Process[] p) {
		nonCompact = p;
		compact = new Process[MB];
	}
	
	/**
	 * Takes an non compacted array and
	 * compacts them. 
	 * @return an array with no spaces
	 */
	public Process[] compact() {
		int compactPos = 0;
		
		for(int nonCompactPos = 0; nonCompactPos < MB; nonCompactPos++) {
			if(nonCompact[nonCompactPos] != null)
			{
				compact[compactPos] = nonCompact[nonCompactPos];
				compactPos++;
			}
		}
		
		return compact;
	}
}
