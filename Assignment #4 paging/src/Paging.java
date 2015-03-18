
public class Paging {

	public static void main(String[] args) {
		int vMem = 10;
		int hardMem = 4;
		
		//run each 5 times
//		FIFO fifo = new FIFO();
		LFU lfu = new LFU();
//		LRU lru = new LRU();
//		MFU mfu = new MFU();
		
		//returns the hit ratio
//		double h1 = fifo.run(); 
		double h2 = lfu.run(); 
//		double h3 = lru.run(); 
//		double h4 = mfu.run(); 

		
		//print average hit ratio
		Print.thisStringln("--Average hit ratios--");
//		Print.thisStringln("FIFO: "+h1);
		Print.thisStringln("LFU:  "+h2);
//		Print.thisStringln("LRU:  "+h3);
//		Print.thisStringln("MFU:  "+h4);
		

	}
}
