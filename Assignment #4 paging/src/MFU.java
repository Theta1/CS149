/**
 * Most Frequently Used method of page replacement. This replaces the page that
 * has been most frequently used in physical memory.
 * 
 * @author Theta(1)
 *
 */
public class MFU extends PageReplacementAlgorithm {

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
	MFU(int runTimeNumber, int numberOfReferences, int physicalMemorySize,
			int virtualMemorySize) {
		super(runTimeNumber, numberOfReferences, physicalMemorySize,
				virtualMemorySize);
	}

	protected int replacePage(int executionMarker, int nextPage, int time) {
		Page page = physicalMemory[0];
		for (int i = 1; i < physicalMemory.length; i++) {
			if (physicalMemory[i] != null) {
				if (page.getHitCount() < physicalMemory[i].getHitCount()) {
					page = this.physicalMemory[i];
					executionMarker = i;
				}
			} else {
				executionMarker = i;
				break;
			}
		}
		this.physicalMemory[executionMarker] = new Page(nextPage, time);
		return executionMarker;
	}
}
