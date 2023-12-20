package algorithms;

import gui.AlgorithmPanel;

@SuppressWarnings("serial")
public class SelectionSort extends AlgorithmPanel {

	public SelectionSort(int delay, String name, int[] arrayAlt)
	{
		super(delay, name, arrayAlt);
	}

	@Override
	public void run()
	{
		startingTime = System.currentTimeMillis();

		try {
			selectionSort(array);
			runTime = (System.currentTimeMillis() - startingTime) - (speed * threadStops);
		} catch (InterruptedException e) {
		}
			
		dbc.connect();
		dbc.saveToDB(this.getName(), this.getRuntime(), this.getArraySize());
		dbc.closeConnection();

		addToCountFinished();

		super.repaint();
	}

	private void selectionSort(int arr[]) throws InterruptedException
    {
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
            {
            	if (arr[j] < arr[min_idx])
            	{
            		min_idx = j;
            		 currentIndex = j;
            		 currentComparing = j-1;
            		 duration = (System.currentTimeMillis() - startingTime);
                     Thread.sleep(speed);
                     threadStops++;
                     super.repaint();
            	}
            }
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }
}
