/**
 * 
 * @author Theta(1)
 *
 */
public class Paging {
	private final static int RUN_TIME_NUMBER = 5;
	private final static int NUMBER_OF_REFERENCES = 100;
	private final static int PHYSICAL_MEMORY_SIZE = 4;
	private final static int VIRTUAL_MEMORY_SIZE = 10;

	public static void main(String[] args) {

		// run each 5 times
		FirstInFirstOut fifo = new FirstInFirstOut(RUN_TIME_NUMBER,
				NUMBER_OF_REFERENCES, PHYSICAL_MEMORY_SIZE,
				VIRTUAL_MEMORY_SIZE, true);
		LeastRecentlyUsed lru = new LeastRecentlyUsed(RUN_TIME_NUMBER,
				NUMBER_OF_REFERENCES, PHYSICAL_MEMORY_SIZE,
				VIRTUAL_MEMORY_SIZE, true);
		LeastFrequentlyUsed lfu = new LeastFrequentlyUsed(RUN_TIME_NUMBER,
				NUMBER_OF_REFERENCES, PHYSICAL_MEMORY_SIZE,
				VIRTUAL_MEMORY_SIZE, true);
		MostFrequentlyUsed mfu = new MostFrequentlyUsed(RUN_TIME_NUMBER,
				NUMBER_OF_REFERENCES, PHYSICAL_MEMORY_SIZE,
				VIRTUAL_MEMORY_SIZE, true);
		LeastFrequentlyUsed lfunhc = new LeastFrequentlyUsed(RUN_TIME_NUMBER,
				NUMBER_OF_REFERENCES, PHYSICAL_MEMORY_SIZE,
				VIRTUAL_MEMORY_SIZE, false);
		MostFrequentlyUsed mfunhc = new MostFrequentlyUsed(RUN_TIME_NUMBER,
				NUMBER_OF_REFERENCES, PHYSICAL_MEMORY_SIZE,
				VIRTUAL_MEMORY_SIZE, false);

		// returns the hit ratio
		System.out.println("\nFirst In First Out");
		double h1 = fifo.run();
		System.out.println("\nLeast Recently Used");
		double h3 = lru.run();
		System.out.println("\nLeast Frequently Used");
		double h2 = lfu.run();
		System.out.println("\nMost Frequently Used");
		double h4 = mfu.run();
		System.out.println("\nLeast Frequently Used Short Hit Count");
		double h5 = lfunhc.run();
		System.out.println("\nMost Frequently Used Short Hit Count");
		double h6 = mfunhc.run();

		// print average hit ratio
		Print.thisStringln("--Average hit ratios--");
		System.out.printf("FIFO:    %1.3f\n", h1);
		System.out.printf("LFO:     %1.3f\n", h2);
		System.out.printf("LRU:     %1.3f\n", h3);
		System.out.printf("MFU:     %1.3f\n", h4);
		System.out.printf("LRUSHC:  %1.3f\n", h5);
		System.out.printf("MFUSHC:  %1.3f\n", h6);
	}
}
