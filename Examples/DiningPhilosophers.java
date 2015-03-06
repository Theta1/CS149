package philosophers;

public class DiningPhilosophers 
{
    static Philosopher philosophers[];  // philosopher threads
    static ForkMonitor monitor;

    public static void main(String args[]) throws Exception 
    {
        int philosopherCount = Integer.parseInt(args[0]);
        int seconds = Integer.parseInt(args[1]);
        
        System.out.printf("Dining Philosophers Simulation\n" +
                          "with %d philosophers for %d seconds\n\n", 
                          philosopherCount, seconds);
        
        philosophers = new Philosopher[philosopherCount];
        monitor = new ForkMonitor(philosophers);

        // Create the philosopher threads.
        for (int i = 0; i < philosopherCount; i++) {
            philosophers[i] = new Philosopher(i, monitor);
        }
        
        print();

        // Start the philosopher threads.
        for (int i = 0; i < philosopherCount; i++) {
            philosophers[i].start();
        }
        
        // Sleep while the philosopher threads run.
        try {
            Thread.sleep(1000*seconds);
        }
        catch(InterruptedException ex) {}

        // Kill the philosopher threads.
        for (int i = 0; i < philosopherCount; i++) {
            philosophers[i].timesUp();
            philosophers[i].join();
        }
        
        // Print how many times each philosopher ate.
        for (int i = 0; i < philosopherCount; i++) {
            System.out.printf("%-12d", philosophers[i].eaten());
        }
        System.out.println();
    }

    public static void print()
    {
        String flag = "";
        
        // Print philosophers' states with paranoid checking.
        for (int i = 0; i < philosophers.length; i++) {
            int j = (i + 1)%philosophers.length;
            if (   (philosophers[i].state() == Philosopher.State.EATING)
                && (philosophers[j].state() == Philosopher.State.EATING)) {
                flag = "***";
            }
            
            System.out.printf("%-12s", philosophers[i].state());
        }
        
        System.out.printf("%5s\n", flag);
    }
}

