/**
 * @author Standard
 *
 */
public class Print {
	/**
	 * Wrapper for system.out.print
	 * 
	 * @param s
	 *            the string to be printed
	 */
	public static void thisString(String s) {
		System.out.print(s);
	}

	/**
	 * Wrapper for system.out.print
	 * 
	 * @param s
	 *            the string to be printed
	 */
	public static void thisStringln(String s) {
		System.out.println(s);
	}

	/**
	 * For each reference, print the page numbers of the pages in memory and
	 * which page (if any) needed to be paged in and which page was evicted.
	 * 
	 * @param physicalMemory
	 *            is an int array of page numbers
	 * @param incomingPage
	 *            is the page whom was brought in or whom was in memory
	 * @param evictedPage
	 *            is the page removed. If no page is being removed this value
	 *            should be -1
	 */
	public static void reference(Page[] physicalMemory, int incomingPage,
			int evictedPage) {

	}

	/*
	 * public static void main(String[] args) { Page[] p = new Page[3]; p[0] =
	 * new Page(0, 0); Print.reference(p, 0, -1); }
	 */

	public static void memoryMap(Page[] physicalMemory) {
		for (int i = 0; i < physicalMemory.length; i++) {
			if (physicalMemory[i] != null) {
				System.out
						.printf("|%2d*%2d", physicalMemory[i].getVirtualAddress(), physicalMemory[i].getHitCount());
			} else {
				System.out.printf("|     ");
			}
		}
		System.out.print("| ");
	}
}
