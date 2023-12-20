package helpers;

public class RoundRobinScheduler extends Thread {
	private int timeslice;
	
	public RoundRobinScheduler(int timeslice)
	{
		this.timeslice = timeslice;
		setPriority(Thread.MAX_PRIORITY);
		setDaemon(true);
	}
	
	public void run()
	{
		while(true)
		{
			try {
				sleep(timeslice);
			}
			catch(Exception e) {}
		}
	}
}

