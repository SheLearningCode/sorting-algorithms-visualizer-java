package database;

import java.sql.Connection;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect {
	
	private String ver, query;
	private Connection con;
	private Statement stmt;
	
	public DBConnect() throws ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		ver = "jdbc:mysql://localhost:3306/algovisualizer";
	}
	
	public boolean connect() {
		boolean rw = false;
		try {
			con = DriverManager.getConnection(ver, "root", "hugo");
			stmt = con.createStatement();	
			rw = true;
			
		} catch(Exception ex){
		}
		return rw;
	}
	
	public boolean saveToDB(String name, double runTime, int arraySize) {
		boolean rw = false;
		
		String query = "insert into algovisualizer." + name.toLowerCase() 
		+ "(runtime, arraySize) values('" + runTime + "', '" + arraySize + "')";
		
		try {
			stmt.executeUpdate(query);
			
			rw = true;
		} catch (SQLException e) {
		}
		return rw;
	}
	
	public void createDatabase()
	{
		try
		{
			ver = "jdbc:mysql://localhost:3306/";
			con = DriverManager.getConnection(ver, "root", "hugo");
		
			stmt = con.createStatement();
			
			query = "create database IF NOT EXISTS algovisualizer";
			
			stmt.executeUpdate(query);

			stmt.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	public void createTable(String name) {
		try
		{			
			ver = "jdbc:mysql://localhost:3306/algovisualizer";
			con = DriverManager.getConnection(ver, "root", "hugo");
		
			stmt = con.createStatement();
			
			query = "create table IF NOT EXISTS algovisualizer."+ name.toLowerCase() 
			+"(runtime double, arraySize integer, id integer primary key AUTO_INCREMENT not null)";
			
			stmt.executeUpdate(query);
						
			stmt.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	public boolean closeConnection()
	{
		boolean rw = false;
		
		try {
			if(stmt != null) {stmt.close();}
			if(con != null) {con.close();}
			
			rw = true;
			
		} catch (SQLException e) {}
		
		return rw;
	}
	
	public String getCount(int i) {
		String rw = "";

		query = "select * from (select count(*) as bubble from bubblesort) as bubble, "
				+ "(select count(*) from insertionsort) as insertion, "
				+ "(select count(*) from mergesort) as merge, "
				+ "(select count(*) as quick from quicksort) as quick,"
				+ "(select count(*) from heapsort) as heap, "
				+ "(select count(*) from shellsort) as shell,"				
				+ "(select count(*) from selectionsort) as selection";
			
		try {
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			
			rw = String.valueOf(rs.getInt(i));

		} catch (SQLException e) {}
	
		return rw;		
	}
	
	public String getAvg(int i)
	{
		String rw = "";
		
		query = "select * from (select avg(runtime) as bubble from bubblesort) as bubble, "
				+ "(select avg(runtime) from insertionsort) as insertion,"
				+ " (select avg(runtime) from mergesort) as merge, "
				+ "(select avg(runtime) as quick from quicksort) as quick,"
				+ "(select avg(runtime) from heapsort) as heap, "
				+ " (select avg(runtime) from shellsort) as shell,"
				+ "(select avg(runtime) from selectionsort) as selection";
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			
			DecimalFormat df = new DecimalFormat("#0.000");
			
			rw = String.valueOf(df.format(rs.getDouble(i)));
			
		} catch (SQLException e) {}
		
		return rw;		
	}
	
	public String[][] getSingleData(String name)
	{
		query = "select count(*) as anz from algovisualizer." +  name;
		int anz = 0;
		try {
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			anz = rs.getInt("anz");
		} catch (SQLException e) {
			e.getMessage();
		}
		
		String[][] rw;
		
		if(anz == 0)
		{
			rw =new String[0][0];
			return rw;
		}
		
		rw = new String[anz][3];
		
		query = "select * from algovisualizer." + name;
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			
			DecimalFormat df = new DecimalFormat("#0.000");
			
			int i = 0;
			while(rs.next())
			{
				rw[i][0] = String.valueOf(rs.getInt(3));
				rw[i][1] = String.valueOf(df.format(rs.getDouble(1)));
				rw[i][2] = String.valueOf(rs.getInt(2));
				i++;
			}
			
		} catch (SQLException e) {}
		return rw;	
	}
}
