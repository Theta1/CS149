

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
	 * @param memory
	 *            is an int array of page numbers
	 * @param incomingPage
	 *            is the page whom was brought in or whom was in memory
	 * @param evictedPage
	 *            is the page removed. If no page is being removed this value
	 *            should be -1
	 */
	public static void reference(int memory[], int incomingPage, int evictedPage) {
		boolean inMemory = false;
		for (int i = 0; i < memory.length; i++) {
			if (incomingPage == memory[i])
				inMemory = true;
			System.out.printf("|%2d", memory[i]);
		}
		System.out.print("| ");
		if (!inMemory) {
			System.out.printf("Incoming: %2d", incomingPage);
			System.out.print(" ");
		}
		if (evictedPage != -1) {
			System.out.printf("Evicted: %2d", evictedPage);
		}
		System.out.println();
	}
}
