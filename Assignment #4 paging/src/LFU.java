/**
 * Least Frequently used method of page replacement. This replaces the page that
 * has been used the least since it was brought into physical memory.
 * 
 * @author Theta(1)
 *
 */
public class LFU extends PageReplacementAlgorithm {

	protected int replacePage(int executionMarker, int nextPage) {
		Page page = physicalMemory[0];
		for (int i = 1; i < physicalMemory.length; i++) {
			if (physicalMemory[i] != null) {
				if (page.getHitCount() > physicalMemory[i].getHitCount()) {
					page = this.physicalMemory[i];
					executionMarker = i;
				}
			}else{
				executionMarker = i;
				break;
			}
		}
		this.physicalMemory[executionMarker] = new Page(nextPage, 0);
		return executionMarker;
	}
}
