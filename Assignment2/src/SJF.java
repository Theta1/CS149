
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dennis Hsu
 */

/**********************************************
 * Round Robin (preemptive)
 * 
 * Each process is assigned a quantum (time interval)
 * The process runs in its quantum
 * When the ends the next process is started
 * 
 * @author Team: Inception
 * CS149
 * 
 *********************************************/

public class SJF {
	
	
	private ArrayList<Process> processData;
	private ArrayList<Character> sjf;
	
	
	/*
	 * constructor; takes in sorted ArrayList of Processes by arrival time
	 */
	public SJF (ArrayList<Process> p)
	{
		this.processData = (ArrayList<Process>) p.clone();
		this.sjf = new ArrayList<Character>();		
	}
	
	public String[] runSJF()
	{
		double arrivalTime;
		String[] output = new String[100];
		
		arrivalTime = processData.get(0).getArrivalTime();
		
		return output;
	}
	/*
	 * 
	 */
	
	
    
}
