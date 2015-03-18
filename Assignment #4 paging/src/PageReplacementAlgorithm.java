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
 * Simulate the FIFO and random pick page replacement algorithms. Run algorithm
 * 5 times, 100 page references each time, to compute an average hit ratio of
 * pages already in memory. For each reference, print the page numbers of the
 * pages in memory and which page (if any) needed to be paged in and which page
 * was evicted.
 * 
 * @author Theta(1)
 *
 */
public abstract class PageReplacementAlgorithm {
	Page[] physicalMemory;
	public final int RUN_TIME_NUMBER = 5;
	protected int NUMBER_OF_REFERENCES = 100;
	public int PHYSICAL_MEMORY_SIZE = 4;
	public int VIRTUAL_MEMORY_SIZE = 10;

	abstract protected void replacePage(int indexMarker, int nextPage);

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
		for (int i = 0; i < RUN_TIME_NUMBER; i++) {
			// clears memory memory
			timesHit = 0;
			executionMarker = 0;
			physicalMemory = new Page[PHYSICAL_MEMORY_SIZE];

			// starts by loading the first page into memory
			physicalMemory[executionMarker] = new Page(0, 0);

			// Number of page references to run (minus the first above)
			for (int j = 0; j < NUMBER_OF_REFERENCES - 1; j++) {
				// finds the next page randomly
				int nextPageVirtualAddress = RandomPick.pickAPage(
						physicalMemory[executionMarker].getVirtualAddress(),
						VIRTUAL_MEMORY_SIZE);
				// Look for the page in memory
				int indexOfPage = getPageIndex(nextPageVirtualAddress);
				if (indexOfPage == -1) {
					replacePage(executionMarker, nextPageVirtualAddress);
				} else {
					// Otherwise it was there. Increase the hit and set the
					// index marker.
					physicalMemory[indexOfPage].hit();
					physicalMemory[indexOfPage].setTimeLastUsed(j);
					timesHit++;
					executionMarker = indexOfPage;
				}
			}
			// Store a fraction of the hitRatio average.
			averageHitRatio += timesHit / this.RUN_TIME_NUMBER;
		}
		// return full average
		return averageHitRatio;
	}

	/**
	 * Checks the memory for the page number
	 * 
	 * @param virtualPageAddress
	 *            an int representing the page address
	 * @return the index if the page is in memory or -1 if not.
	 */
	protected int getPageIndex(int virtualPageAddress) {
		for (int i = 0; i < this.PHYSICAL_MEMORY_SIZE; i++) {
			if (this.physicalMemory[i] != null
					&& this.physicalMemory[i].getVirtualAddress() == virtualPageAddress)
				return i;
		}
		return -1;
	}
}
