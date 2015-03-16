
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
		double h1 = fifo.run(); 
		int h2 = lfu.run(); 
		int h3 = lru.run(); 
		int h4 = mfu.run(); 
		
		//print average hit ratio
		Print.thisStringln("--Average hit ratios--");
		Print.thisStringln("FIFO: "+h1);
		Print.thisStringln("LFO:  "+h2);
		Print.thisStringln("LRU:  "+h3);
		Print.thisStringln("MFU:  "+h4);
		

	}
}
