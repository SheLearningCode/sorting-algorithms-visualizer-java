package algorithms;
import java.util.Random;

import gui.AlgorithmPanel;

@SuppressWarnings("serial")
public class QuickSort extends AlgorithmPanel {
	
	public QuickSort(int delay, String name, int[] arrayAlt) 
	{
		super(delay, name, arrayAlt);
		rand = new Random();
	}

	public void run()
	{		
		this.startingTime = System.currentTimeMillis();
		
		try {
			quicksort(array);
			
			this.runTime = (System.currentTimeMillis() - this.startingTime) - (this.speed * this.threadStops);
			
		} catch (InterruptedException e) {
		}
				
		dbc.connect();
		dbc.saveToDB(this.getName(), this.getRuntime(), this.getArraySize());
		dbc.closeConnection();
		
		addToCountFinished();
		
		super.repaint();
	}
	
	private void quicksort(int[] array, int lowIndex, int highIndex) throws InterruptedException 
	{
		if (lowIndex >= highIndex) 
		{
			return;
		}

		int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
		int pivot = array[pivotIndex];
		
		currentIndex = pivotIndex;

		swap(array, pivotIndex, highIndex);
		
		int leftPointer = partition(array, lowIndex, highIndex, pivot);
		
		quicksort(array, lowIndex, leftPointer - 1);
	
		quicksort(array, leftPointer + 1, highIndex);
	}

	private void quicksort(int[] array) throws InterruptedException {
		quicksort(array, 0, array.length - 1);
	}

	private int partition(int[] array, int lowIndex, int highIndex, int pivot) throws InterruptedException {
		int leftPointer = lowIndex;
		int rightPointer = highIndex;

		while (leftPointer < rightPointer) {

			while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
				leftPointer++;
			}

			while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
				rightPointer--;
			}

			currentReading = leftPointer;
			currentComparing = rightPointer;
			duration = (System.currentTimeMillis() - startingTime);
			Thread.sleep(speed);
			threadStops++;

			super.repaint();
			
			swap(array, leftPointer, rightPointer);
		}
		swap(array, leftPointer, highIndex);
		return leftPointer;
	}

	private void swap(int[] array, int index1, int index2) throws InterruptedException {
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
}
