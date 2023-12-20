package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.Random;

import javax.swing.JPanel;

import database.DBConnect;

@SuppressWarnings("serial")
public abstract class AlgorithmPanel extends JPanel implements Runnable {

  protected int currentIndex;
  protected int currentComparing;
  protected int currentReading;

  protected Random rand;
  
  protected long startingTime;
  protected long duration;
  protected long runTime;
  
  protected int threadStops;

  protected DBConnect dbc;
  
  protected int speed;
  protected String name;
  protected int[] array;
  protected double runtimeClean;   
  
  protected boolean running;
  
  public AlgorithmPanel(int delay, String name, int[] arrayAlt)
  {       
    this.speed = Math.max(MainGUI.getDelayMax() - delay, 1);
    this.name = name;
    this.threadStops = 0;
    this.currentIndex = -1;
    this.currentComparing = -1;
    this.currentReading = -1;
    this.running = false;
        
    hardCopyArray(arrayAlt);
        
    try {
      
      dbc = new DBConnect();
      dbc.createDatabase();
      
      if (!MainGUI.getTablesCreated()) {
		for (String s : MainGUI.getNameArray()) {
			dbc.createTable(s);
		} 
	}
      
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    
    setSize(new Dimension(400,500));  
    setBackground(Color.decode(MainGUI.getAlgopanelBackgroundColor()));
  }
  
  private void hardCopyArray(int[] arrayAlt)
  {
    this.array = new int[arrayAlt.length];
    
    for(int i = 0; i < arrayAlt.length; i++)
    {
      this.array[i] = arrayAlt[i];
    }
  }
  
  @Override
  public void run() {
    
  }
  
  public void paint(Graphics g)
  {
    Graphics2D graphics = (Graphics2D) g;
    super.paint(graphics);
    
    graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
          
    graphics.setPaint(Color.white);
    
    graphics.setFont(new Font("Consolas", Font.PLAIN, 14));
    graphics.drawString(name, 10, 20);
    
    graphics.setFont(new Font("Consolas", Font.PLAIN, 11));
    graphics.drawString("Size: " + array.length, 10, 30);
    graphics.drawString("Runtime: " + this.getDuration(), 10, 40);
    graphics.drawString("CleanTime: " + this.getRuntime() , 10, 50);
    
    double x = 0;
    
    double breite = Math.max(Math.floor(400 / array.length), 2);
    
    double abzug = Math.min(Math.floor(400/array.length), 1);
                
    for(int i = 0; i < array.length; i++)
    {
      if(currentIndex == i)
      {
      graphics.setPaint(Color.RED); 
      
      } else if( currentReading == i) {
      
        graphics.setPaint(Color.YELLOW);
        
      } else if(currentComparing == i)
      {
        graphics.setPaint(Color.GREEN);
      }
      else {
      
        graphics.setPaint(Color.WHITE);
      }
      graphics.fillRect((int) x , 150 - array[i] , (int) (breite - abzug) , array[i]);
      
      if(Math.floor(400d / array.length) < 1)
      {
        i += (array.length / 400) + (array.length*0.0029);
      }
      
      x += breite;
    }
    
    graphics.dispose();
    g.dispose();
  }
  
  public void setSpeed(int delay)
  {
    this.speed = MainGUI.getDelayMax() - delay + 1;
  } 
  
  public void setArray(int[] arrayAlt)
  {
    hardCopyArray(arrayAlt);
    
    reset();
    
    super.repaint();
  }
  
  public void reset()
  {
    this.runTime = 0;
    this.duration = 0;
    this.startingTime = 0;
    this.currentIndex = -1;
    this.currentComparing = -1;
    this.currentReading = -1;   
    this.threadStops = 0;
    this.running = false;
  }
  
  public double getRuntime()
  {
    return (this.runTime / 1000d);
  }
  
  public double getDuration()
  {
	  return (this.duration / 1000d );
  }
  
  public int getArraySize()
  {
    return this.array.length;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void addToCountFinished()
  {
	MainGUI.setCountFinished( MainGUI.getCountFinished() + 1);
		
		if(MainGUI.getCountFinished() >= 4)
		{
			MainGUI.activateButtons(true);
			MainGUI.setCountFinished(0);
		}
  }
}
