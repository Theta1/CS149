/**
 * The page object an required methods for operations.
 * 
 * @author Theta(1)
 *
 */
public class Page {
	private int virtualAddress;
	private int physicalAddress;
	private int hitCount;
	private int timeLastUsed;

	/**
	 * Page constructor.
	 *
	 * @param virtualAddress
	 *            an int representing the address of this page in memory
	 * @param timeIn
	 *            an int representing the relative time this page was brought
	 *            into physical memory
	 */
	Page(int virtualAddress, int timeLastUsed) {
		this.timeLastUsed = timeLastUsed;
		this.virtualAddress = virtualAddress;
		hitCount = 1;
	}

	/**
	 * @param physicalAddress
	 */
	public void setPhysicalAddress(int physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	/**
	 * @return
	 */
	public int getPhysicalAddress() {
		return physicalAddress;
	}

	/**
	 * Gets the virtual address of this page.
	 * 
	 * @return an int representing the virtual adress the the page.
	 */
	public int getVirtualAddress() {
		return virtualAddress;
	}

	public void setTimeLastUsed(int j) {
		timeLastUsed = j;
	}

	public int getTimeLastUsed() {
		return timeLastUsed;
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
}
