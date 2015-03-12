
public class Paging {

	public static void main(String[] args) {
		int vMem = 10;
		int hardMem = 4;
		
		//run each 5 times
		FIFO fifo = new FIFO(vMem, hardMem);
		LFU lfu = new LFU(vMem, hardMem);
		LRU lru = new LRU(vMem, hardMem);
		MFU mfu = new MFU(vMem, hardMem);
		
		//returns the hit ratio
		int h1 = fifo.run(); 
		int h2 = lfu.run(); 
		int h3 = lru.run(); 
		int h4 = mfu.run(); 
		
		//print average hit ratio
		System.out.println("--Average hit ratios--");
		System.out.println("FIFO: "+h1);
		System.out.println("LFO:  "+h2);
		System.out.println("LRU:  "+h3);
		System.out.println("MFU:  "+h4);
	}
}
