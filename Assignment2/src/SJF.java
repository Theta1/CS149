/**
* @author Dennis Hsu
*/
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
import java.util.ArrayList;
public class SJF {
	private ArrayList<Process> processData;
	private ArrayList<Process> queue;
	private Process current;
	private ArrayList<String> output;
	private int count;

	//constructor; takes in sorted ArrayList of Processes by arrival time
	public SJF (ArrayList<Process> p)
	{
		this.processData = (ArrayList<Process>) p.clone();
		this.count = 0;
		this.queue = new ArrayList<Process>();
		this.current = new Process();
		this.output = new ArrayList<String>();
	}
	
	public ArrayList<String> getStringList()
	{
		return output;
	}
	
	//create String list
	public void createList()
	{
		int shortest;
		//all processes with arrival time less than 100
		while (count < 100 || queue.size() > 0)
		{
			//add Processes to a queue for processes that have arrived	
			while (!processData.isEmpty() && processData.get(0).getArrivalTime() < count)
			{				
					queue.add(processData.get(0));
					processData.remove(0);
			}
			
			//if queue is empty, add null; otherwise add shortest process
			if (queue.size() > 0)
			{
				//find shortest process out of arrived processes
				shortest = 0;
				for (int j=0; j< queue.size(); j++)
				{
					if (queue.get(j).getRunTime() < queue.get(shortest).getRunTime())
					{
						shortest = j;
					}
				}
								
				//add to time quanta current shortest process
				float runtime = 0;
				current = queue.get(shortest);
				runtime = current.getRunTime();
				
				while (runtime > 0)
				{
					output.add(current.getName());	
					
					runtime --;
					count++;
					
				}
				queue.remove(current);
				
				
			}
			else
			{
				//queue was empty				
				output.add("");
				count++;
			}			
		}
		
	}
}
