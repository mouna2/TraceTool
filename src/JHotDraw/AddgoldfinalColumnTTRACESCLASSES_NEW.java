package JHotDraw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

import spoon.Launcher;
import spoon.SpoonAPI;

public class AddgoldfinalColumnTTRACESCLASSES_NEW {
	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";
	
	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "root";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */

	private final int portNumber = 3306;
	
	private final String dbName = "databasegantt";

	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("root", this.userName);
		connectionProps.put("123456", this.password);
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/databasejhotdraw","root","123456");

		return conn;
	}

	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * Connect to MySQL and do some stuff.
	 * @throws SQLException 
	 */
	public void run() throws SQLException {
		ResultSet rs = null; 
		// Connect to MySQL
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
	
		// Create a table
		try {
			Statement st= conn.createStatement();

		   
		  
			  // AddColumns();
		
		  
		   
		   
		
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		AddColumns2();
	}

	
	
	public static void AddColumns2() throws SQLException, IOException {

		// TODO Auto-generated method stub
		Connection conn = null;
		DBDemo3JHotDraw2 DatabaseReading = new DBDemo3JHotDraw2();
		conn = DatabaseReading.getConnection();
		Statement st = conn.createStatement();
		Statement st2 = conn.createStatement();
		 FileReader fileReader = new FileReader("C:\\Users\\mouna\\new_workspace\\TracePredictor\\java\\JHotDrawFiles\\TracesClassesNEW.txt");

		st.executeUpdate("ALTER TABLE `tracesclasses` DROP COLUMN subjectGold");
		st.executeUpdate("ALTER TABLE `tracesclasses` ADD subjectGold LONGTEXT");
		
		
		 BufferedReader bufferedReader = new BufferedReader(fileReader);
	        HashMap<String,  String> ReqClassHashMap= new HashMap<String,  String> (); 
	        String line = null;
	        line = bufferedReader.readLine(); 
	        String[] requirements = line.split(","); 
	        while((line = bufferedReader.readLine()) != null) {
//	            System.out.println(line);
	        	 String[] splitted = line.split("\\,", -1);
	            
	            for(int i=1; i<splitted.length; i++) {
	            	if(i==15) {
	            		i=16; 
	            	}
	            	if(splitted[i].equals("x")) {
	            		ReqClassHashMap.put(i+"-"+splitted[0], "T"); 
	            	}else {
	            		ReqClassHashMap.put(i+"-"+splitted[0], "N"); 
	            	}
	            	
	            	
	            }
//	            System.out.println(line);
	        }   

	        // Always close files.
	        bufferedReader.close();         

		
		
	
		
		int counter2=0; 
		
		
		for (Entry <String,  String> entry : ReqClassHashMap.entrySet()) {
		    String key = entry.getKey(); 
		    String[] keys = key.split("-"); 
		   String ReqID=keys[0]; 
		   String ClassName=keys[1]; 
		     List<String> List= new ArrayList<String>(); 
		 	st.executeUpdate("UPDATE `tracesclasses` SET `subjectGold` ='"+ entry.getValue().trim() +"'WHERE requirementid='"+ReqID+"' AND classname='"+ClassName+"'");
//			System.out.println(ReqClass);
			System.out.println("counter "+counter2);
			counter2++; 

		}
		
	 	st.executeUpdate("UPDATE `tracesclasses` SET `subjectGold` ='"+ "E" +"'WHERE subjectGold is null");

		
	}
}
