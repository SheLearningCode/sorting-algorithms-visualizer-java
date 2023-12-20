package algorithms;

import gui.AlgorithmPanel;

@SuppressWarnings("serial")
public class InsertionSort extends AlgorithmPanel {

  public InsertionSort(int delay, String name, int[] arrayAlt) {
    
    super(delay, name, arrayAlt);
  }
    
  public void run()
  {
    startingTime = System.currentTimeMillis();

    try {
      insertionSort(array);
      runTime = (System.currentTimeMillis() - startingTime) - (speed * threadStops);
    } catch (InterruptedException e) {}
        
    dbc.connect();
    dbc.saveToDB(this.getName(), this.getRuntime(), this.getArraySize());
    dbc.closeConnection();

	addToCountFinished();

    super.repaint();
  }
  
  private void insertionSort(int[] array) throws InterruptedException
  {
    int toSort;
    int j;
    
    for(int i = 1; i < array.length; i++)
    {
      toSort = array[i];
      j = i;
      
      while(j > 0 && toSort < array[j-1])
      {
        array[j] = array[j-1];
        j--;
        currentIndex = i;
        currentComparing = j;
        duration = (System.currentTimeMillis() - startingTime);
        Thread.sleep(speed);
        threadStops++;
        
        super.repaint();
      }
      array[j] = toSort;
    }
  }
}
