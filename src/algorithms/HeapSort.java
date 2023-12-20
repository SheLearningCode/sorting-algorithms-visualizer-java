package algorithms;

import gui.AlgorithmPanel;

@SuppressWarnings("serial")
public class HeapSort extends AlgorithmPanel {

	public HeapSort(int delay, String name, int[] arrayAlt)
	{
		super(delay, name, arrayAlt);
	}
	
	public void run()
	{
		startingTime = System.currentTimeMillis();

		try {
			heapSort(array);
			runTime = (System.currentTimeMillis() - startingTime) - (speed * threadStops) ;
		} catch (InterruptedException e) {}
	
		dbc.connect();
		dbc.saveToDB(this.getName(), this.getRuntime(), this.getArraySize());
		dbc.closeConnection();
		
		addToCountFinished();
		
		super.repaint();
	}
	
	private void heapSort(int arr[]) throws InterruptedException
    {
        int n = arr.length;
  
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
  
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            currentIndex = temp;
        
            arr[0] = arr[i];
            arr[i] = temp;
  
            heapify(arr, i, 0);
        }
    }
 
	private void heapify(int arr[], int n, int i) throws InterruptedException
    {
        int largest = i;
        int l = 2 * i + 1; 
        int r = 2 * i + 2;
  
        if (l < n && arr[l] > arr[largest])
            largest = l;
  
        if (r < n && arr[r] > arr[largest])
            largest = r;
  
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
  
            heapify(arr, n, largest);
        }
        
        currentIndex = r;
        currentComparing = l;
        currentReading = i;
        duration = (System.currentTimeMillis() - startingTime);
        threadStops++;
		Thread.sleep(speed);
		super.repaint();
    }
}
