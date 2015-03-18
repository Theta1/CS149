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
public class FIFO {
	private final int RUN_TIME_NUMBER = 5;
	private final int NUMBER_OF_REFERENCES = 100;
	private int physicalMemorySize;
	private int virtualMemorySize;
	Page[] physicalMemory;

	/**
	 * Constructor, sets operational parameters.
	 * 
	 * @param virtualMemorySize
	 *            is an int representing the number of pages slots needed by the
	 *            process.
	 * @param physicalMemorySize
	 *            is an int representing the number of pages available in the
	 *            physical memory.
	 */
	FIFO(int virtualMemorySize, int physicalMemorySize) {
		this.physicalMemorySize = physicalMemorySize;
		this.virtualMemorySize = virtualMemorySize;
	}

	/**
	 * Run method.
	 * 
	 * @return
	 */
	public double run() {
		// initializations
		double averageHitRatio = 0;
		int timesHit;
		int indexMarker;

		// Number of times run
		for (int i = 0; i < this.RUN_TIME_NUMBER; i++) {
			// clears memory memory
			timesHit = 0;
			indexMarker = 0;
			physicalMemory = new Page[physicalMemorySize];

			// starts by loading the first page into memory
			physicalMemory[indexMarker] = new Page(0, 0);

			// Number of page references to run (minus the first above)
			for (int j = 0; j < this.NUMBER_OF_REFERENCES - 1; j++) {
				// finds the next page randomly
				int nextPage = RandomPick.pickAPage(
						physicalMemory[indexMarker].getVirtualAddress(),
						virtualMemorySize);
				// Look for the page in memeory
				int indexOfPage = this.pageNotInMemory(nextPage);
				if (indexOfPage == -1) {
					// Advance the index and put it in
					indexMarker = (indexMarker + 1) % this.physicalMemorySize;
					physicalMemory[indexMarker] = new Page(nextPage, 0);
				} else {
					// Otherwise it was there. Increase the hit and set the
					// index marker.
					timesHit++;
					indexMarker = indexOfPage;
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
	private int pageNotInMemory(int virtualPageAddress) {
		for (int i = 0; i < this.physicalMemorySize; i++) {
			if (this.physicalMemory[i] != null
					&& this.physicalMemory[i].getVirtualAddress() == virtualPageAddress)
				return i;
		}
		return -1;
	}
}
