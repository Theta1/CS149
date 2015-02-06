import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author David-Eric Thorpe
 */
public class Assignment2 {

    /**
     * Creates 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	
	List <Process>l1 = new ArrayList <Process>();
	
	for(int i=0;i<30;i++){
	    Process n = new Process(i);
	    l1.add(n);
	}
	
	SJF sjf = new SJF(l1);
	
    }    
}
