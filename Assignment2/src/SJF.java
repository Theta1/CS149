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
	
	/*
	 * 
	 */
	
	
    
}
