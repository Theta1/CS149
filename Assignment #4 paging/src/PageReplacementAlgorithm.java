/**
 * Simulated running process that consists of virtualMemory pages and
 * physicalMemory pages. virtualMemory pages are always available on disk. When
 * the process starts, none of its pages are in memory.
 * 
 * The process makes random references to its pages. Due to locality of
 * reference, after referencing a page i, there is a 70% probability that the
 * next reference will be to page i, i-1, or i+1. In other words, there is a 70%
 * probability that for a given i, the next call will be to itself or a
 * neighbour. Page references should wrap from n back to 0.
 * 
 * Suggested procedure:
 * 
 * Run algorithm multiple times, with multiple page references each time, to
 * compute an average hit ratio of pages already in memory. For each reference,
 * print the page numbers of the pages in memory and which page (if any) needed
 * to be paged in and which page was evicted.
 * 
 * @author Theta(1)
 *
 */
public abstract class PageReplacementAlgorithm {
	protected Page[] physicalMemory;
	private int runTimeNumber = 5;
	private int numberOfReferences = 100;
	protected int physicalMemorySize = 4;
	private int virtualMemorySize = 10;
	protected int marker;

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
	PageReplacementAlgorithm(int runTimeNumber, int numberOfReferences,
			int physicalMemorySize, int virtualMemorySize) {
		this.runTimeNumber = runTimeNumber;
		this.numberOfReferences = numberOfReferences;
		this.physicalMemorySize = physicalMemorySize;
		this.virtualMemorySize = virtualMemorySize;
	}

	/**
	 * Called from run to implement the particular paging algorithm.
	 * 
	 * @param executionMarker
	 *            is the current index in physical memory being executed.
	 * @param nextPageVirtualAddress
	 *            is the virtual address of the page to be brought in.
	 */
	abstract protected int replacePage(int executionMarker,
			int nextPageVirtualAddress, int time);

	/**
	 * Runs the paging algorithm and returns the average hit rate
	 * 
	 * @return a double representing the average hit rate
	 */
	public double run() {
		// initializations
		double averageHitRatio = 0;
		int timesHit;
		int executionMarker;

		// Number of times run
		for (int i = 0; i < runTimeNumber; i++) {
			// clears memory memory
			timesHit = 0;
			executionMarker = 0;
			physicalMemory = new Page[physicalMemorySize];
			marker = 0;

			// starts by loading the first page into memory
			physicalMemory[executionMarker] = new Page(0, 0);

			// Number of page references to run (minus the first above)
			for (int j = 1; j < numberOfReferences; j++) {
				Print.memoryMap(physicalMemory);
				// finds the next page randomly
				int nextPageVirtualAddress = RandomPick.pickAPage(
						physicalMemory[executionMarker].getVirtualAddress(),
						virtualMemorySize);
				// Look for the page in memory
				Print.thisString("Time: " + j + ", Next: "
						+ nextPageVirtualAddress + " ");
				int indexOfPage = getPageIndex(nextPageVirtualAddress);
				if (indexOfPage == -1) {
					executionMarker = replacePage(executionMarker,
							nextPageVirtualAddress, j);
					Print.reference(physicalMemory, nextPageVirtualAddress,
							executionMarker);
					Print.thisString("Loaded: "
							+ physicalMemory[executionMarker]
									.getVirtualAddress() + " At index: "
							+ executionMarker + "\n");
				} else {
					// Otherwise it was there. Increase the hit and set the
					// index marker.
					physicalMemory[indexOfPage].hit();
					physicalMemory[indexOfPage].setTimeLastUsed(j);
					timesHit++;
					executionMarker = indexOfPage;
					Print.thisString("Found:  "
							+ physicalMemory[executionMarker]
									.getVirtualAddress() + "\n");
				}
			}
			// Store a fraction of the hitRatio average.
			averageHitRatio += ((double) timesHit / numberOfReferences)
					/ runTimeNumber;
		}
		// return full average
		return averageHitRatio;
	}

	/**
	 * Checks the physical memory for the virtual page address
	 * 
	 * @param virtualPageAddress
	 *            an int representing the page address
	 * @return the index if the page is in memory or -1 if not.
	 */
	private int getPageIndex(int virtualPageAddress) {
		for (int i = 0; i < physicalMemorySize; i++) {
			if (this.physicalMemory[i] != null
					&& this.physicalMemory[i].getVirtualAddress() == virtualPageAddress)
				return i;
		}
		return -1;
	}
}
