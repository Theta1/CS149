/**
 * Most Frequently Used method of page replacement. This replaces the page that
 * has been most frequently used in physical memory.
 * 
 * @author Theta(1)
 *
 */
public class MFU extends PageReplacementAlgorithm {

	protected int replacePage(int executionMarker, int nextPage) {
		Page page = physicalMemory[0];
		for (int i = 1; i > physicalMemory.length; i++) {
			if (physicalMemory[i] != null
					&& page.getHitCount() > physicalMemory[i].getHitCount()) {
				page = this.physicalMemory[i];
				executionMarker = i;
			}
		}
		this.physicalMemory[executionMarker] = new Page(nextPage, 0);
		return executionMarker;
	}
}
