/**
 * The page object an required methods for operations.
 * 
 * @author Theta(1)
 *
 */
public class Page {
	private int timeIn;
	private int virtualAddress;

	/**
	 * Gets the virtual address of this page.
	 * 
	 * @return an int representing the virtual adress the the page.
	 */
	public int getVirtualAddress() {
		return virtualAddress;
	}

	/**
	 * Probably not needed.
	 * 
	 * @param virtualAddress
	 *            an int representing the address of this page in memory
	 */
	Page(int virtualAddress) {
		this.timeIn = 0;
		this.virtualAddress = virtualAddress;
	}

	/**
	 * Page constructor.
	 *
	 * @param virtualAddress
	 *            an int representing the address of this page in memory
	 * @param timeIn
	 *            an int representing the relative time this page was brought
	 *            into physical memory
	 */
	Page(int virtualAddress, int timeIn) {
		this.timeIn = timeIn;
		this.virtualAddress = virtualAddress;
	}

	/**
	 * Gets the time this was put into memory.
	 * 
	 * @return an int representing the relative time this was put in physical
	 *         memory
	 */
	public int getTimeIn() {
		return this.timeIn;
	}
}
