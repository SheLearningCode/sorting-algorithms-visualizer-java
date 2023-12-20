package algorithms;

import gui.AlgorithmPanel;

@SuppressWarnings("serial")
public class ShellSort extends AlgorithmPanel {

	public ShellSort(int delay, String name, int[] arrayAlt) 
	{
		super(delay, name, arrayAlt);
	}
	
	public void run()
	{
		startingTime = System.currentTimeMillis();

		try {
			shellSort(array);
			runTime = (System.currentTimeMillis() - startingTime) - (speed * threadStops);
		} catch (InterruptedException e) {}
		
		dbc.connect();
		dbc.saveToDB(this.getName(), this.getRuntime(), this.getArraySize());
		dbc.closeConnection();
	
		addToCountFinished();

		super.repaint();
	}

	private void shellSort(int arr[]) throws InterruptedException
    {
        int n = arr.length;
        int j = 0;
        int temp = 0;
        
        for (int gap = n/2; gap > 0; gap /= 2)
        {
            for (int i = gap; i < n; i += 1)
            {
                temp = arr[i];
                
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                	
                	arr[j] = arr[j - gap];
                	currentIndex = i;
                	currentComparing = j;
                	currentReading = temp;
                	duration = (System.currentTimeMillis() - startingTime);
                	Thread.sleep(speed);
                	threadStops++;

                	super.repaint();
                }
                arr[j] = temp;
            }
        }
    }
}
