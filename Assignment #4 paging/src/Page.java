/**
 * The page object an required methods for operations.
 * 
 * @author Theta(1)
 *
 */
public class Page {
	private int timeIn;
	private int virtualAddress;
	private int physicalAddress;
	private int hitCount;
	private int timeLastUsed;

	/**
	 * @return
	 */
	public int getPhysicalAddress() {
		return physicalAddress;
	}

	/**
	 * @param physicalAddress
	 */
	public void setPhysicalAddress(int physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	/**
	 * Gets the virtual address of this page.
	 * 
	 * @return an int representing the virtual adress the the page.
	 */
	public int getVirtualAddress() {
		return virtualAddress;
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
		this.timeLastUsed = timeIn;
		this.virtualAddress = virtualAddress;
		hitCount=1;
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

	/**
	 * Used to increment the number of times the page has been hit.
	 */
	public void hit() {
		hitCount++;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setTimeLastUsed(int j) {
		timeLastUsed=j;
	}

	public int getTimeLastUsed() {
		return timeLastUsed;
	}
}
