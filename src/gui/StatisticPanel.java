package gui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.DBConnect;

@SuppressWarnings("serial")
public class StatisticPanel extends JPanel implements ActionListener, ItemListener {

	private Button back;

	private Label lblTableAll;
	private JTable tblAll, tblSingle;
	private JScrollPane scrlAll, scrlSingle;
	
	private Choice cmbSelect;
	
	String[] names = {"Algorithmus", "Anzahl Durchläufe", "Durchschnitt Laufzeit"};
	String[] columnNamesSingle = {"id", "Runtime" , "arraySize"};
	
	public DBConnect dbc;
	
	String[][] dataAll, dataSingle;
		
	public StatisticPanel() {
		
		try {
			dbc = new DBConnect();
			dbc.connect();
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}

		lblTableAll = new Label("Datenbank Gesamt: ");
				
		dataAll = new String[MainGUI.getNameArray().length][3];
				
		dataSingle = dbc.getSingleData("bubblesort");

		cmbSelect = new Choice();
		
		for( int i = 0; i < MainGUI.getNameArray().length; i++)
		{
			dataAll[i][0] = MainGUI.getNameArray()[i];
			dataAll[i][1] = dbc.getCount(i+1);
			dataAll[i][2] = dbc.getAvg(i+1);
			cmbSelect.addItem(MainGUI.getNameArray()[i]);
		}
		
		tblAll = new JTable(dataAll , names );
		scrlAll = new JScrollPane(tblAll);
				
		tblSingle = new JTable(dataSingle, columnNamesSingle);
		scrlSingle = new JScrollPane(tblSingle);
		
		back = new Button("zur\u00fcck");

		scrlAll.setBounds(50,100,450,200);
		
		scrlSingle.setBounds(550, 100, 450, 200);
				
		lblTableAll.setBounds(50, 70, 150, 25);

		cmbSelect.setBounds(550, 70, 100, 25);
		cmbSelect.addItemListener(this);
		
		back.addActionListener(this);
		back.setBounds(525- 50 , 350, 100, 25);

		add(lblTableAll);
		add(scrlAll);
		add(scrlSingle);
		add(cmbSelect);
		add(back);
		
		setLayout(null);
		this.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == back)
		{
			setVisible(false);
			dbc.closeConnection();
		}
	}
	
	public void refresh()
	{
		for( int i = 0; i < MainGUI.getNameArray().length; i++)
		{
			dataAll[i][1] = dbc.getCount(i+1);
			dataAll[i][2] = dbc.getAvg(i+1);
		}
		
		refreshDataSingle();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if(e.getSource() == cmbSelect)
		{
			refreshDataSingle();
		}	
	}
	
	private void refreshDataSingle()
	{
		String str = (String) cmbSelect.getSelectedItem();
		
		dataSingle = dbc.getSingleData(str.toLowerCase());
		
		remove(scrlSingle);
		
		tblSingle = new JTable(dataSingle, columnNamesSingle);
		scrlSingle = new JScrollPane(tblSingle);
		
		scrlSingle.setBounds(550, 100, 450, 200);
	
		add(scrlSingle);
		
		repaint();
	}
}
