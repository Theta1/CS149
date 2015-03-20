/**
 * @author Standard
 *
 */
public class Print {
	static boolean verbose = true;

	/**
	 * Wrapper for system.out.print
	 * 
	 * @param s
	 *            the string to be printed
	 */
	public static void print(String s) {
		if (verbose)
			System.out.print(s);
	}

	public static void setVerbose(boolean verbose) {
		Print.verbose = verbose;
	}

	public static void printf(String string, int i, int j, int k) {
		if (verbose) {
			if (j == -1 && k == -1)
				System.out.printf(string, i);
			else if (k == -1)
				System.out.printf(string, i, j);
			else
				System.out.printf(string, i, j, k);
		}
	}

	/**
	 * Wrapper for system.out.print
	 * 
	 * @param s
	 *            the string to be printed
	 */
	public static void println(String s) {
		print(s + "\n");
	}

	/**
	 * public static void main(String[] args) { Page[] p = new Page[3]; p[0] =
	 * new Page(0, 0); Print.reference(p, 0, -1); }
	 */
	public static void memoryMap(Page[] physicalMemory) {
		for (int i = 0; i < physicalMemory.length; i++) {
			if (physicalMemory[i] != null) {
				printf("|%2d,h%2d,t%2d", physicalMemory[i].getVirtualAddress(),
						physicalMemory[i].getHitCount(),
						physicalMemory[i].getTimeLastUsed());
			} else {
				print("|          ");
			}
		}
		print("| ");
	}

	public static void printf(String string, int i) {
		printf(string, i, -1);
	}

	public static void printf(String string, int i, int j) {
		if (j == -1) {
			printf(string, i, -1, -1);
		} else {
			printf(string, i, j, -1);
		}
	}
}
