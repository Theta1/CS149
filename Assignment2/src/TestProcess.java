import java.util.ArrayList;

/**
 * 
 */

/**
 * Main Method for testing Process Object
 * 
 * @author Standard
 *
 */
public class TestProcess {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

	for(int i=0; i<30; i++)
	{
	    Process n = new Process(i);
	    System.out.println((n.getArrivalTime()));
	}
    }

}
