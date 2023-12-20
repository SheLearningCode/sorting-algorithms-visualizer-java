package algorithms;

import gui.AlgorithmPanel;

@SuppressWarnings("serial")
public class MergeSort extends AlgorithmPanel {

	public MergeSort(int delay, String name, int[] arrayAlt) {
		super(delay, name, arrayAlt);
	}
	
	public void run()
	{
		startingTime = System.currentTimeMillis();
		try {
			mergeSort(array, 0, array.length -1);
			runTime = (System.currentTimeMillis() - startingTime) - (speed * threadStops) ;
		} catch (InterruptedException e) {}	
		
		dbc.connect();
		dbc.saveToDB(this.getName(), this.getRuntime(), this.getArraySize());
		dbc.closeConnection();

		addToCountFinished();

		super.repaint();
	}
	
	private void mergeSort(int arr[], int left, int right) throws InterruptedException       
	  {
		  int middle;
		  if (left < right) {                             
			  middle = (left + right) / 2;
			  
			  mergeSort(arr, left, middle);                    
			  mergeSort(arr, middle + 1, right);               
			
			  merge(arr, left, middle, right);                
		  }
	  }
	
	private void merge(int arr[], int left, int middle, int right) throws InterruptedException
	{
		int low = middle - left + 1;                  
		int high = right - middle;                     
		
		int L[] = new int[low];                            
		int R[] = new int[high];
		
		int i = 0, j = 0;
		
		for (i = 0; i < low; i++)                              
		{
			L[i] = arr[left + i];

		}
		for (j = 0; j < high; j++)                             
		{
			R[j] = arr[middle + 1 + j];
		}
		
		int k = left;    
		i = 0;                                             
		j = 0;
		
		while (i < low && j < high)                     
		{
			if (L[i] <= R[j]) 
			{
				arr[k] = L[i];
				i++;
			}
			else 
			{
				arr[k] = R[j];
				j++;
			}
			k++;
			
			currentIndex = k;
			currentComparing = low;
			currentReading = right;
			duration = (System.currentTimeMillis() - startingTime);
			threadStops++;
			Thread.sleep(speed);
			super.repaint();
		}
		
		while (i < low)                             
		{
			arr[k] = L[i];
			i++;
			k++;
		}
		
		while (j < high)                           
		{
			arr[k] = R[j];
			j++;
			k++;
		}
	}
}
