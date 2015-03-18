/**
 * Least Recently Used method of page replacement. This looks for the page that
 * was used the most long ago and replaces that.
 * 
 * @author Theta(1)
 *
 */
public class LRU extends PageReplacementAlgorithm {

	protected int replacePage(int executionMarker, int nextPage, int time) {
		Page page = physicalMemory[0];
		for (int i = 1; i < physicalMemory.length; i++) {
			if (physicalMemory[i] != null) {
				if (page.getTimeLastUsed() > physicalMemory[i]
						.getTimeLastUsed()) {
					page = this.physicalMemory[i];
					executionMarker = i;
				}
			} else {
				executionMarker = i;
				break;
			}
		}
		physicalMemory[executionMarker] = new Page(nextPage, time);
		return executionMarker;
	}
}
