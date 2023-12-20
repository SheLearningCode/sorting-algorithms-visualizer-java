package gui;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algorithms.BubbleSort;
import algorithms.HeapSort;
import algorithms.InsertionSort;
import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.SelectionSort;
import algorithms.ShellSort;

@SuppressWarnings("serial")
public class MainGUI extends Frame implements ActionListener, ItemListener, ChangeListener {

	private static Button bGo, bStop, bSize, bRange, bNew;
	private Panel pButton, pAlgorithms;
	private StatisticPanel pMenuStatistic;
	private AlgorithmPanel panel1, panel2, panel3, panel4;
	private Thread thread1, thread2, thread3, thread4;
	private static TextField txtArraySize, txtRangeVon, txtRangeBis;
	private static Checkbox checkBox1;
	private static Checkbox checkBox2;
	private static Checkbox checkBox3;
	private static Checkbox checkBox4;
	private static JSlider dSlider;
	private static Choice cmbSelect1 ;
	private static Choice cmbSelect2;
	private static Choice cmbSelect3;
	private static Choice cmbSelect4;   
	private static String[] algoNameArr = {"BubbleSort", "InsertionSort", "MergeSort", "QuickSort", "HeapSort", "ShellSort", "SelectionSort"};
	private MenuBar menuBar;
	private Menu menu;
	private int delay;
	private boolean started = false;
	private static boolean tablesCreated = false;
	private int[] array;
	
	private Random rand;

	private static final int DELAY_MIN = 0;
	private static final int DELAY_MAX = 200;
	private static final int DELAY_INIT = 170;
	private static final String BACKGROUND_COLOR = "#f6f6f6";
	private static final String ALGOPANEL_BACKGROUND_COLOR= "#9E9E9E";
	
	private static int countFinished;

	public MainGUI()
	{
		super("Visualizer");
	
		initializeGUI();
		started = true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == bGo)
		{
			if(checkBox1.getState())
			{
				thread1 = new Thread(panel1);
				thread1.start();					
			}

			if(checkBox2.getState())
			{
				thread2 = new Thread(panel2);
				thread2.start();
			}

			if(checkBox3.getState())
			{
				thread3 = new Thread(panel3);
				thread3.start();
			}

			if(checkBox4.getState())
			{
				thread4 = new Thread(panel4);
				thread4.start();
			}
			
			activateButtons(false);
			
			revalidate();
			repaint();
		}

		if(e.getSource()== bStop)
		{
				if(thread1 != null) {thread1.stop(); panel1.reset();}
				if(thread2 != null) {thread2.stop(); panel2.reset();}
				if(thread3 != null) {thread3.stop(); panel3.reset();}
				if(thread4 != null) {thread4.stop(); panel4.reset();}
			
				countFinished = 0;
				
				activateButtons(true);
		}

		if(e.getSource() == bSize)
		{
			if(!checkTextField(txtArraySize)){
				return;
			}
			
			setSize(Integer.parseInt(txtArraySize.getText()));
			randomize(array);
			setArray(array);				
		}
		
		if(e.getSource() == bRange)
		{
			if(!checkTextField(txtRangeVon) && !checkTextField(txtRangeBis)) {
				return;
			}
			
			if (Integer.parseInt(txtRangeVon.getText()) >= Integer.parseInt(txtRangeBis.getText()))
			{
				txtRangeVon.requestFocus();
				txtRangeVon.select(0, txtRangeVon.getText().length());
				return;
			}
			
			randomize(array);			
			
			setArray(array);					
		}
		

		if(e.getSource() == bNew)
		{			
			randomize(array);
			
			setArray(array);
			
			if(thread1 != null && thread1.isAlive()) {panel1.reset(); }
			if(thread2 != null && thread2.isAlive()) {panel2.reset(); }
			if(thread3 != null && thread3.isAlive()) {panel3.reset(); }
			if(thread4 != null && thread4.isAlive()) {panel4.reset(); }
			
			dSlider.setEnabled(true);
		}

		if(e.getActionCommand().equals("Statistik"))
		{
			if(!pMenuStatistic.isVisible())
			{
				pMenuStatistic.dbc.connect();
				pMenuStatistic.refresh();
				pMenuStatistic.setVisible(true);
			} 
		}
		
		if(e.getActionCommand().equals("Beenden"))
		{
			System.exit(0);
			if(panel1.dbc != null) {panel1.dbc.closeConnection();};
			if(panel2.dbc != null) {panel2.dbc.closeConnection();};
			if(panel3.dbc != null) {panel3.dbc.closeConnection();};
			if(panel4.dbc != null) {panel4.dbc.closeConnection();};								
		}
	}

	private AlgorithmPanel setAlgorithm(String panel, String selection)
	{
		AlgorithmPanel algo = null;

		switch(panel)
		{
		case "p1": algo = panel1;
			break;
		case "p2": algo = panel2;
			break;
		case "p3": algo = panel3;
			break;
		case "p4": algo = panel4;
			break;
		}

		getDelay();
		
		switch (selection)
		{
		case "BubbleSort": algo = new BubbleSort(delay, "BubbleSort", array);
			break;
		case "MergeSort": algo = new MergeSort(delay, "MergeSort", array);
			break;
		case "InsertionSort": algo = new InsertionSort(delay, "InsertionSort", array);
			break;
		case "QuickSort": algo = new QuickSort(delay, "QuickSort", array);
			break;
		case "HeapSort": algo = new HeapSort(delay, "HeapSort", array);
			break;
		case "ShellSort": algo = new ShellSort(delay, "ShellSort", array);
			break;
		case "SelectionSort": algo = new SelectionSort(delay, "SelectionSort", array);
			break;
		}
		return algo;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if(e.getSource() == checkBox1)
		{
			if(checkBox1.getState())
			{
				panel1.setVisible(true);
				cmbSelect1.setEnabled(true);
				
			} else {
				panel1.setVisible(false);
				cmbSelect1.setEnabled(false);

				if(thread1 != null)
				{
					thread1.interrupt();
				}
			}
		}

		if(e.getSource() == checkBox2)
		{
			if(checkBox2.getState())
			{
				panel2.setVisible(true);
				cmbSelect2.setEnabled(true);

			} else {
				panel2.setVisible(false);
				cmbSelect2.setEnabled(false);

				if(thread2 != null)
				{
					thread2.interrupt();
				}
			}
		}

		if(e.getSource() == checkBox3)
		{
			if(checkBox3.getState())
			{
				panel3.setVisible(true);
				cmbSelect3.setEnabled(true);

			} else {
				panel3.setVisible(false);
				cmbSelect3.setEnabled(false);

				if(thread3 != null)
				{
					thread3.interrupt();
				}
			}
		}

		if(e.getSource() == checkBox4)
		{
			if(checkBox4.getState())
			{
				panel4.setVisible(true);
				cmbSelect4.setEnabled(true);

			} else {
				panel4.setVisible(false);
				cmbSelect4.setEnabled(false);

				if(thread4 != null)
				{
					thread4.interrupt();
				}
			}
		}
		
		if(e.getSource() == cmbSelect1)
		{
			String selection = (String) cmbSelect1.getSelectedItem();
			if(started)
			{
				pAlgorithms.remove(panel1);
				
				panel1 = setAlgorithm("p1", selection);
				
				panel1.setBounds(10, 10, 400, 150);
				
				pAlgorithms.add(panel1);
				
				setPanelAlgorithms();
			}			
		}
		
		if(e.getSource() == cmbSelect2)
		{
			String selection = (String) cmbSelect2.getSelectedItem();

			if(started)
			{
				pAlgorithms.remove(panel2);
				
				panel2 = setAlgorithm("p2", selection);
				
				panel2.setBounds(420, 10, 400, 150);
				
				pAlgorithms.add(panel2);
				
				setPanelAlgorithms();
			}
		}

		if(e.getSource() == cmbSelect3)
		{
			String selection = (String) cmbSelect3.getSelectedItem();
			if(started) 
			{
				pAlgorithms.remove(panel3);
				
				panel3 = setAlgorithm("p3", selection);
				
				panel3.setBounds(10, 170, 400, 150);
				
				pAlgorithms.add(panel3);
				
				setPanelAlgorithms();
			}
		}

		if(e.getSource() == cmbSelect4)
		{
			String selection = (String) cmbSelect4.getSelectedItem();

			if(started) {
				pAlgorithms.remove(panel4);
				
				panel4 = setAlgorithm("p4", selection);
				
				panel4.setBounds(420, 170, 400, 150);
				
				pAlgorithms.add(panel4);
				
				setPanelAlgorithms();
			}
		}
	}

	public void setPanelAlgorithms()
	{
		if(started)
		{
			pAlgorithms.revalidate();
			pAlgorithms.repaint();
		}
	}

	public void initializeGUI()
	{
		rand = new Random();

		array = new int[100];

		randomize(array);

		delay = DELAY_INIT;

		dSlider = new JSlider(SwingConstants.HORIZONTAL, DELAY_MIN, getDelayMax(), DELAY_INIT);
		
		pAlgorithms = new Panel();
		pButton = new Panel();

		bGo = new Button("Start");
		bStop = new Button("Stop");
		bSize = new Button("set Size");
		bRange = new Button("set Range");
		bNew = new Button("New");

		checkBox1 = new Checkbox("Panel1");
		checkBox2 = new Checkbox("Panel2");
		checkBox3 = new Checkbox("Panel3");
		checkBox4 = new Checkbox("Panel4");

		menuBar = new MenuBar();
		menu = new Menu("Men\u00fc");
		MenuItem menuItemsStatisik = new MenuItem("Statistik");
		MenuItem menuItemExit = new MenuItem("Beenden");
	
		txtArraySize = new TextField();
		txtRangeVon = new TextField();
		txtRangeBis = new TextField();

		cmbSelect1 = new Choice();

		cmbSelect2 = new Choice();
		
		cmbSelect3 = new Choice();
		
		cmbSelect4 = new Choice();
				
		panel1 = new BubbleSort(delay, "BubbleSort", array);
		tablesCreated = true;
		
		panel2 = new InsertionSort(delay, "InsertionSort", array);
		panel3 = new MergeSort(delay, "MergeSort", array);
		panel4 = new QuickSort(delay, "QuickSort", array);
		
		pMenuStatistic = new StatisticPanel();

		txtArraySize.setBounds(10, 220, 81 , 25);
		txtArraySize.setText("100");

		txtRangeVon.setBounds(10, 250, 39 , 25);
		txtRangeVon.setText("1");
		txtRangeBis.setBounds(52, 250, 39 , 25);
		txtRangeBis.setText("100");
		
		bGo.addActionListener(this);
		bGo.setBounds(10,300,50,25);

		bStop.addActionListener(this);
		bStop.setBounds(70,300,50,25);
		
		bRange.addActionListener(this);
		bRange.setBounds(100, 250, 80, 25);

		bNew.addActionListener(this);
		bNew.setBounds(130, 300, 50, 25);
		
		bSize.addActionListener(this);
		bSize.setBounds(100, 220, 80, 25);
		
		checkBox1.setBounds(10, 10, 60, 15);
		checkBox1.setLabel("Feld1");
		checkBox1.addItemListener(this);
		checkBox1.setState(true);

		checkBox2.setBounds(100, 10, 60, 15);
		checkBox2.setLabel("Feld2");
		checkBox2.addItemListener(this);
		checkBox2.setState(true);

		checkBox3.setBounds(10, 80, 60, 15);
		checkBox3.setLabel("Feld3");
		checkBox3.addItemListener(this);
		checkBox3.setState(true);

		checkBox4.setBounds(100, 80, 60, 15);
		checkBox4.setLabel("Feld4");
		checkBox4.addItemListener(this);
		checkBox4.setState(true);

		dSlider.addChangeListener(this);
		dSlider.setMajorTickSpacing(50);
		dSlider.setMinorTickSpacing(10);
		dSlider.setBounds(2,140,185,60);
		dSlider.setFont(new Font("Veranda", Font.PLAIN, 12));
		dSlider.setPaintTicks(true);
		dSlider.setBackground(Color.decode(BACKGROUND_COLOR));
		dSlider.setPaintLabels(true);

		cmbSelect1.setBounds(10, 10 + 20, 80, 20);
		cmbSelect1.setBackground(Color.decode(BACKGROUND_COLOR));
		cmbSelect1.addItemListener(this);

		cmbSelect2.setBounds(100,10+20, 80, 20);
		cmbSelect2.setBackground(Color.decode(BACKGROUND_COLOR));
		cmbSelect2.addItemListener(this);

		cmbSelect3.setBounds(10, 100, 80, 20);
		cmbSelect3.setBackground(Color.decode(BACKGROUND_COLOR));
		cmbSelect3.addItemListener(this);

		cmbSelect4.setBounds(100,100, 80, 20);
		cmbSelect4.setBackground(Color.decode(BACKGROUND_COLOR));
		cmbSelect4.addItemListener(this);

		for(String s: algoNameArr)
		{
			cmbSelect1.addItem(s);
			cmbSelect2.addItem(s);
			cmbSelect3.addItem(s);
			cmbSelect4.addItem(s);
		}

		cmbSelect1.select("BubbleSort");
		cmbSelect2.select("InsertionSort");
		cmbSelect3.select("MergeSort");
		cmbSelect4.select("QuickSort");

		pButton.setLayout(null);
		pButton.setSize(190,700);

		pButton.add(bGo);
		pButton.add(bStop);
		pButton.add(bNew);
		pButton.add(checkBox1);
		pButton.add(checkBox2);
		pButton.add(checkBox3);
		pButton.add(checkBox4);
		pButton.add(dSlider);
		pButton.add(txtArraySize);
		pButton.add(txtRangeBis);
		pButton.add(txtRangeVon);
		pButton.add(bSize);
		pButton.add(bRange);
		pButton.add(cmbSelect1);
		pButton.add(cmbSelect2);
		pButton.add(cmbSelect3);
		pButton.add(cmbSelect4);

		pButton.setBackground(Color.decode(BACKGROUND_COLOR));
		
		panel1.setBounds(10, 10, 400, 150);
		panel2.setBounds(420, 10, 400, 150);
		panel3.setBounds(10, 170, 400, 150);
		panel4.setBounds(420, 170, 400, 150);

		pAlgorithms.setLayout(null);
		pAlgorithms.add(panel1);
		pAlgorithms.add(panel2);
		pAlgorithms.add(panel3);
		pAlgorithms.add(panel4);
		pAlgorithms.setBackground(Color.decode(BACKGROUND_COLOR));
		
		pMenuStatistic.setBounds(0,0,1050,430);
		
		menuItemsStatisik.addActionListener(this);
		menuItemExit.addActionListener(this);

		menu.add(menuItemsStatisik);
		menu.addSeparator();
		menu.add(menuItemExit);
		menuBar.add(menu);

		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
				if(panel1.dbc != null) {panel1.dbc.closeConnection();};
				if(panel2.dbc != null) {panel2.dbc.closeConnection();};
				if(panel3.dbc != null) {panel3.dbc.closeConnection();};
				if(panel4.dbc != null) {panel4.dbc.closeConnection();};			
				}} );

		setLayout(new BorderLayout());
		setSize(new Dimension(1050,430));
		setFont(new Font("Veranda", Font.PLAIN, 12));
		setBackground(Color.decode(BACKGROUND_COLOR));
		setMenuBar(menuBar);
		setResizable(false);
		
		add(pMenuStatistic, BorderLayout.CENTER);
		add(pButton, BorderLayout.WEST);
		add(pAlgorithms, BorderLayout.CENTER);
	}

	private void setSize(int size)
	{
		array = new int[size];
	}

	private void randomize(int[] array) {
		int von = 1;
		int bis = 100;
		
		if(txtRangeVon != null && txtRangeBis != null)
		{
			von = Integer.parseInt(txtRangeVon.getText());
			bis = Integer.parseInt(txtRangeBis.getText());							
		}
		
		for(int i = 0; i < array.length; i++)
		{
			array[i] = rand.nextInt(bis-von)+von;
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider src = (JSlider) e.getSource();

		if(!src.getValueIsAdjusting())
		{
			getDelay();
			panel1.setSpeed(delay);
			panel1.reset();
			panel2.setSpeed(delay);
			panel2.reset();
			panel3.setSpeed(delay);
			panel3.reset();
			panel4.setSpeed(delay);
			panel4.reset();
		}
	}
	
	public int getDelay()
	{
		return delay = dSlider.getValue();
	}
	
	public void setArray(int[] array)
	{
		panel1.setArray(array);
		panel2.setArray(array);
		panel3.setArray(array);
		panel4.setArray(array);
	}
	
	private boolean checkTextField(TextField tf)
	{
		if(tf == txtArraySize && tf.getText().charAt(0) == '0')
		{
			tf.requestFocus();
			tf.select(0, tf.getText().length());
			return false;
		}
		
		for(int i = 0; i < tf.getText().length(); i++)
		{
			if(!(tf.getText().charAt(i) >= '0' && tf.getText().charAt(i) <= '9'))
			{
				tf.requestFocus();
				tf.select(0, tf.getText().length());
				return false;
			}
		}
		return true;
	}
	
	public static  String[] getNameArray()
	{
		return algoNameArr;
	}
	
	public static boolean getTablesCreated()
	{
		return tablesCreated;
	}
	
	public static void activateButtons(boolean bool)
	{
		dSlider.setEnabled(bool);
		bGo.setEnabled(bool);
		bRange.setEnabled(bool);
		bSize.setEnabled(bool);
		bNew.setEnabled(bool);
		cmbSelect1.setEnabled(bool);
		cmbSelect2.setEnabled(bool);
		cmbSelect3.setEnabled(bool);
		cmbSelect4.setEnabled(bool);
		checkBox1.setEnabled(bool);
		checkBox2.setEnabled(bool);
		checkBox3.setEnabled(bool);
		checkBox4.setEnabled(bool);
		txtArraySize.setEnabled(bool);
		txtRangeVon.setEnabled(bool);
		txtRangeBis.setEnabled(bool);
	}
	
	public static int getCountFinished()
	{
		return countFinished;
	}
	
	public static void setCountFinished(int i)
	{
		countFinished = i;
	}

	public static int getDelayMax() {
		return DELAY_MAX;
	}

	public static String getAlgopanelBackgroundColor() {
		return ALGOPANEL_BACKGROUND_COLOR;
	}
}

