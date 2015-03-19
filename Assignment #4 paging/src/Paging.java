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
		FIFO fifo = new FIFO(RUN_TIME_NUMBER, NUMBER_OF_REFERENCES,
				PHYSICAL_MEMORY_SIZE, VIRTUAL_MEMORY_SIZE);
		LFU lfu = new LFU(RUN_TIME_NUMBER, NUMBER_OF_REFERENCES,
				PHYSICAL_MEMORY_SIZE, VIRTUAL_MEMORY_SIZE);
		LRU lru = new LRU(RUN_TIME_NUMBER, NUMBER_OF_REFERENCES,
				PHYSICAL_MEMORY_SIZE, VIRTUAL_MEMORY_SIZE);
		MFU mfu = new MFU(RUN_TIME_NUMBER, NUMBER_OF_REFERENCES,
				PHYSICAL_MEMORY_SIZE, VIRTUAL_MEMORY_SIZE);

		// returns the hit ratio
		double h1 = fifo.run();
		double h2 = lfu.run();
		double h3 = lru.run();
		double h4 = mfu.run();

		// print average hit ratio
		Print.thisStringln("--Average hit ratios--");
		Print.thisStringln("FIFO: " + h1);
		Print.thisStringln("LFO:  " + h2);
		Print.thisStringln("LRU:  " + h3);
		Print.thisStringln("MFU:  " + h4);
	}
}
