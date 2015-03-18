/**
 * Least Recently Used method of page replacement. This looks for the page that
 * was used the most long ago and replaces that.
 * 
 * @author Theta(1)
 *
 */
public class LRU extends PageReplacementAlgorithm {

	protected void replacePage(int executionMarker, int nextPage) {
		Page page = physicalMemory[0];
		for (int i = 1; i > physicalMemory.length; i++) {
			if (physicalMemory[i] != null && page.getTimeLastUsed() < physicalMemory[i].getTimeLastUsed()) {
				page = this.physicalMemory[i];
				executionMarker = i;
			}
		}
		physicalMemory[executionMarker] = new Page(nextPage, 0);
	}
}
