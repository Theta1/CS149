/**
 * 
 * @author Theta(1)
 *
 */
public class Paging {
	private final static int RUN_TIME_NUMBER = 5;
	private final static int NUMBER_OF_REFERENCES = 100;
	private final static int PHYSICAL_MEMORY_SIZE = 4;
	private final static int VIRTUAL_MEMORY_SIZE = 100;
	private final static boolean VEBOSE_PRINT = true;

	public static void main(String[] args) {
		Print.setVerbose(VEBOSE_PRINT);
		
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
		Print.println("\nFirst In First Out");
		double h1 = fifo.run();
		Print.println("\nLeast Recently Used");
		double h3 = lru.run();
		Print.println("\nLeast Frequently Used");
		double h2 = lfu.run();
		Print.println("\nMost Frequently Used");
		double h4 = mfu.run();
		Print.println("\nLeast Frequently Used Short Hit Count");
		double h5 = lfunhc.run();
		Print.println("\nMost Frequently Used Short Hit Count");
		double h6 = mfunhc.run();

		// print average hit ratio
		System.out.println("--Average hit ratios--");
		System.out.printf("FIFO:    %1.3f\n", h1);
		System.out.printf("LFO:     %1.3f\n", h2);
		System.out.printf("LRU:     %1.3f\n", h3);
		System.out.printf("MFU:     %1.3f\n", h4);
		System.out.printf("LRUSHC:  %1.3f\n", h5);
		System.out.printf("MFUSHC:  %1.3f\n", h6);
	}
}
