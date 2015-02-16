import java.util.ArrayList;

/**********************************************
* Shortest Job First (SJF) (nonpreemptive)
*
* Each process is assigned a quantum (time interval)
* The process runs in its quantum
* When the ends the next process is started
* Shortest Job has priority
*
* @author Team: Inception
* CS149
*
*********************************************/

public class SJF implements SchedulerInterface{
	private ArrayList<Process> processData;
	private ArrayList<Process> queue;
	private Process shortest;
	private ArrayList<String> output;
	private int quantum;
	private ArrayList<Process> stats;

	public SJF (ArrayList<Process> processData)
	{
		this.processData = processData;
		this.quantum = 0;
		this.queue = new ArrayList<Process>();
		this.shortest = new Process();
		this.output = new ArrayList<String>();
		this.stats = new ArrayList<Process>();
		
		run();		
	}
	
	public ArrayList<String> getStringList() {
		return output;
	}
	
	public ArrayList<Process> getStats() {
		return stats;
	}


	/**
	 * Runs algorithm to execute processes
	 */
	public void run()
	{
		//all processes with arrival time less than 100
		while (quantum < 100)
		{
			//add Processes to a queue for processes that have arrived
			for (Process p: processData)
			{
				if (p.getArrivalTime() < quantum)
				{	queue.add(p);	}
			}
			processData.removeAll(queue);
			
			//if queue is empty, add null; otherwise add shortest process
			if(queue.isEmpty())
			{
				output.add("");
				quantum++;
			}
			else
			{
				//find shortest process out of arrived processes
				shortest = queue.get(0);
				for (Process q: queue)
				{
					if (q.getRunTime() < shortest.getRunTime())
					{
						shortest = q;
					}
				}
				
				shortest.setActualStartTime(quantum);
				
				//add to time quanta current shortest process
				while (shortest.getRunTime() > 0)
				{
					output.add(shortest.getName());
					shortest.decrementRunTime();
					shortest.incrementQuantaTime();
					quantum++;
				}
				shortest.setEndTime(quantum - 1);
				stats.add(shortest);
				queue.remove(shortest);
			}
		}
	}
}
