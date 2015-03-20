/**
 * Least Recently Used method of page replacement. This looks for the page that
 * was used the most long ago and replaces that.
 * 
 * @author Theta(1)
 *
 */
public class LeastRecentlyUsed extends PageReplacementAlgorithm {

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
	LeastRecentlyUsed(int runTimeNumber, int numberOfReferences,
			int physicalMemorySize, int virtualMemorySize, boolean keepHitCount) {
		super(runTimeNumber, numberOfReferences, physicalMemorySize,
				virtualMemorySize, keepHitCount);
	}

	protected int replacePage(int executionMarker, int nextPage, int time) {
		Page page = physicalMemory[0];
		for (int i = 1; i < physicalMemory.length; i++) {
			if (physicalMemory[i] != null) {
				if (page.getTimeLastUsed() > physicalMemory[i]
						.getTimeLastUsed()) {
					page = this.physicalMemory[i];
					executionMarker = i;
				}
			} else {
				executionMarker = i;
				break;
			}
		}
		if (physicalMemory[executionMarker] != null)
			Print.printf(" Evicted: %2d",
					physicalMemory[executionMarker].getVirtualAddress());
		physicalMemory[executionMarker] = virtualMemory[nextPage];
		this.physicalMemory[executionMarker].setTimeLastUsed(time);

		return executionMarker;
	}
}
