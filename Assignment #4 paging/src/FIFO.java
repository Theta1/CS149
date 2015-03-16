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
	private final int REFERENCE_NUMBER = 100;
	private int physicalMemory[];
	private int physicalMemorySize;
	private int virtualMemorySize;

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
		double averageHitRatio = 0;
		// Number of times run
		for (int i = 0; i < this.RUN_TIME_NUMBER; i++) {
			int hitRatio = 0;
			// clears mem
			this.physicalMemory = new int[physicalMemorySize];
			// starts at the first index
			int indexMarker = 0;
			// loads first index from virtual mem
			this.physicalMemory[indexMarker] = 0;
			// Number of page references each run
			for (int j = 0; j < this.REFERENCE_NUMBER; j++) {
				// finds the next page randomly
				int nextPage = RandomPick.pickAPage(
						this.physicalMemory[indexMarker
								% this.physicalMemorySize], virtualMemorySize);
				// if the new page is not in memory
				if (this.pageNotInMemory(nextPage)) {
					// put it there and advance the index.
					this.physicalMemory[++indexMarker % this.physicalMemorySize] = nextPage;
				} else {
					// increment the hit ratio
					hitRatio++;
				}
			}
			// Store a fraction of the hitRatio average.
			averageHitRatio += hitRatio / this.RUN_TIME_NUMBER;
		}
		// return full average
		return averageHitRatio;
	}

	/**
	 * Checks the memory for the page number
	 * 
	 * @param page
	 *            an int representing the page number
	 * @return true if the page is in mememory.
	 */
	private boolean pageNotInMemory(int page) {
		for (int i = 0; i < this.physicalMemorySize; i++) {
			if (this.physicalMemory[i] == page)
				return false;
		}
		return true;
	}
}
