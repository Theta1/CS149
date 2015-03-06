package philosophers;

import java.util.Random;

public class Philosopher extends Thread 
{
    private int id;
    private State state;
    private int timesEaten;
    private boolean eatThink;
    private ForkMonitor monitor;
    
    private Random generator = new Random();
    
    // States that a philosopher can be in.
    public enum State {THINKING, WAIT_LEFT, WAIT_RIGHT, EATING};

    public Philosopher(int id, ForkMonitor monitor) 
    {
        this.id = id;
        this.state = State.THINKING;
        this.timesEaten = 0;
        this.eatThink = true;
        this.monitor = monitor;
    }

    public int   id()    { return this.id; }
    public int   eaten() { return this.timesEaten; }
    public State state() { return this.state; }
    
    public void changeState(State state) 
    { 
        this.state = state; 
        DiningPhilosophers.print();
    }
    
    public void ateAgain() { ++timesEaten;     }
    public void timesUp()  { eatThink = false; }

    // When started, every Java thread runs this method.
    public void run() 
    {
        // Time to get seated at the table.
        snooze();
        
        // Eat and think until it's time to go home.
        while (eatThink) {
            try {
                // Get forks and eat for a while.
                monitor.getForks(id);
                snooze();
                
                // Release forks and think for a while.
                monitor.releaseForks(this.id());
                snooze();
            } 
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void snooze()
    {
        try {
            // Sleep for random time up to a half second.
            sleep(generator.nextInt(500));
        }
        catch(InterruptedException ex) {};
    }
}
