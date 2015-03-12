import java.util.ArrayList;
/**************************************
 * 
 * @author Theta1
 * CS149
 *************************************/

public class Print {

	
	/**
	 * 
	 * @param mem the memory map
	 */
	void printMap(ArrayList<Integer> mem) {
		System.out.print("[");
		for(int i: mem)
		{	
			if (i == mem.get(mem.size()-1))
			{	System.out.println(i + "]");	}
			else if (i == 0)
			{	System.out.print(".");	}
			else
			{	System.out.print(i);	}
		}
	}
}
