package main;

import gui.MainGUI;
import helpers.RoundRobinScheduler;

public class VisualizerApp {

  public static void main(String[] args)
  {
    new RoundRobinScheduler(10).start();
    new MainGUI().setVisible(true);
  }
}
