import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** 
 * Shortest Job First runs the schedule by running the shortest job first when a new queue item is available
 * @author Standard
 */
public class SJF {
    private List <Process>l;
    /**
     * Constructs a new Shortest Job First queue of 100 quantums in length. 
     * @param l1
     */
    public SJF(List <Process>l1){
	l=l1;
	//sorts list by arrival time
	Collections.sort(l, new Comparator<Process>(){
	    public int compare(Process p1, Process p2){
		if(p1.getArrivalTime()>p2.getArrivalTime()){
		    return 1;
		}else if(p1.getArrivalTime()<p2.getArrivalTime()){
		    return -1;
		}
		return 0;
	    }
	    
	    
	});
    }
    
}
