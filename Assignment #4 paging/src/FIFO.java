/**
 * @author Theta(1)
 *
 */
public class FIFO extends PageReplacementAlgorithm{

	protected void replacePage(int executionMarker, int nextPage) {	
		// Advance the index and put it in
		executionMarker = (executionMarker + 1) % PHYSICAL_MEMORY_SIZE;
		this.physicalMemory[executionMarker] = new Page(nextPage, 0);
	}

}
