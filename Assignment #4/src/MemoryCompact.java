import java.util.ArrayList;


public class MemoryCompact {
	private int[] nonCompact;
	private int[] compact;
	private static final int MB = 100;
	
	MemoryCompact( int[] p) {
		nonCompact = p;
		compact = new int[MB];
	}
	
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
