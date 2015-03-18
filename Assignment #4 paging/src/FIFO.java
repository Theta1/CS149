/**
 * First in First Out method of page replacement. This replaces the page the has
 * been in the physical memory the longest.
 * 
 * @author Theta(1)
 *
 */
public class FIFO extends PageReplacementAlgorithm {
	protected int replacePage(int executionMarker, int nextPage) {
		// Advance the index and put it in
		marker = (marker + 1) % PHYSICAL_MEMORY_SIZE;
		physicalMemory[marker] = new Page(nextPage, 0);
		return marker;
	}

}
