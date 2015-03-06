package philosophers;

import philosophers.Philosopher.State;

// Monitor to protect the shared forks.
public class ForkMonitor 
{
    private boolean forks[];
    private Philosopher philosophers[];

    public ForkMonitor(Philosopher philosophers[]) 
    {
        // All forks are initially available (true).
        forks = new boolean[philosophers.length];
        for (int i = 0; i < forks.length; i++) {
            forks[i] = true;
        }
        
        this.philosophers = philosophers;
    }

    // Only one philosopher can be getting forks at a time.
    public synchronized void getForks(int id) throws Exception 
    {
        int left  = id;                    // left  fork index
        int right = (id + 1)%forks.length; // right fork index

        // Try to get both left and right forks.
        while (!forks[left] || !forks[right]) {
            philosophers[id].changeState(!forks[left] 
                                            ? Philosopher.State.WAIT_LEFT
                                            : Philosopher.State.WAIT_RIGHT);
            
            // Wait for both forks 
            // to become available.
            wait();
        }

        // Take forks and start eating.
        forks[left]  = false;
        forks[right] = false;
        philosophers[id].changeState(State.EATING);
        philosophers[id].ateAgain();
    }

    // Only one philosopher can be releasing forks at a time.
    public synchronized void releaseForks(int id) throws Exception 
    {
        int left  = id;                    // left  fork index
        int right = (id + 1)%forks.length; // right fork index

        // Release forks and resume thinking.
        forks[left]  = true;
        forks[right] = true;
        philosophers[id].changeState(State.THINKING);
        
        // Notify any waiting philosophers 
        // that forks are now available.
        notifyAll();
    }
}