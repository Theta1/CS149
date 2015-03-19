/**
 * First in First Out method of page replacement. This replaces the page the has
 * been in the physical memory the longest.
 * 
 * @author Theta(1)
 *
 */
public class FIFO extends PageReplacementAlgorithm {

	/**
	 * General constructor.
	 * 
	 * @param runTimeNumber
	 *            is the number of times to run.
	 * @param numberOfReferences
	 *            is the number of references to make in each run.
	 * @param physicalMemorySize
	 *            is the number of slots in physical memory.
	 * @param virtualMemorySize
	 *            is the number of slots in virtual memory.
	 */
	FIFO(int runTimeNumber, int numberOfReferences, int physicalMemorySize,
			int virtualMemorySize) {
		super(runTimeNumber, numberOfReferences, physicalMemorySize,
				virtualMemorySize);
	}

	protected int replacePage(int executionMarker, int nextPage, int time) {
		// Advance the index and put it in
		marker = (marker + 1) % physicalMemorySize;
		physicalMemory[marker] = new Page(nextPage, time);
		return marker;
	}

}
