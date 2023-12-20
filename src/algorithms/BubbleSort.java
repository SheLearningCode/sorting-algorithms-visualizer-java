package algorithms;

import gui.AlgorithmPanel;

@SuppressWarnings("serial")
public class BubbleSort extends AlgorithmPanel {

	public BubbleSort(int delay, String name, int[] arrayAlt)
	{
		super(delay, name, arrayAlt);
	}
		
	public void run()
	{
		startingTime = System.currentTimeMillis();
				
		try {
			bubbleSort(array);

			runTime = (System.currentTimeMillis() - startingTime) - (speed * threadStops);		
			} catch (InterruptedException e) {}
		
		dbc.connect();
		dbc.saveToDB(this.getName(), this.getRuntime(), this.getArraySize());
		dbc.closeConnection();
		
		addToCountFinished();

		super.repaint();
	}
	
	private void bubbleSort(int[] array) throws InterruptedException
	{
		boolean getauscht = true;
		int temp;
		
		for(int i = 0; i < array.length && getauscht; i++)
		{
			getauscht = false;
			for(int j = 0; j < array.length-1; j++)
			{
				if(array[j] > array[j+1])
				{
					temp = array[j];
					array[j]= array[j+1];
					array[j+1] = temp;
					
					currentIndex = j;
					currentComparing = j+1;
					currentReading = -1;
					duration = (System.currentTimeMillis() - startingTime);
					Thread.sleep(speed);
					threadStops++;
					
					getauscht = true;
				}
				super.repaint();
			}
		}
	}
}
