
public class LFU {

	private static final int RUN_TIME_NUMBER = 5;
	private static final int REFERENCE_NUMBER = 100;
	private int[] physicalMemory;
	private int physicalMemorySize;
	private int virtualMemorySize;

	public LFU(int vMem, int hardMem) {
		this.virtualMemorySize=vMem;
		this.physicalMemorySize=hardMem;
	}

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
								% this.physicalMemorySize], this.virtualMemorySize);
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

	private boolean pageNotInMemory(int nextPage) {
		// TODO Auto-generated method stub
		return false;

	}

}
