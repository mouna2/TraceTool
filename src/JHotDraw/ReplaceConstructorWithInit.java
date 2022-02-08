package JHotDraw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class ReplaceConstructorWithInit {
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

		 FileReader fileReader = new FileReader("C:\\Users\\mouna\\new_workspace\\TracePredictor\\src\\JHotDrawFiles\\jhotdrawnew_meth_votes.txt");

		List<String> NewlineList= new ArrayList<String>(); 
		
		
		 BufferedReader bufferedReader = new BufferedReader(fileReader);
	        String line = null;
	        line = bufferedReader.readLine(); 
	        while((line = bufferedReader.readLine()) != null) {
//	            System.out.println(line);
	        	 String[] splitted = line.split("\\,", -1);
	        	 String newline=null; 
	          if(splitted[1].equals(splitted[2])) {
	        	   newline=splitted[0]+"."+splitted[1]+"."+"-init-"; 
	        	  
	            }else {
	            	  newline=splitted[0]+"."+splitted[1]+"."+splitted[2]; 
	            }
	          
	         
	          for(int i=3; i<splitted.length; i++) {
	        	  try {
	        		  Integer.parseInt(splitted[i]); 
	        		  newline=newline+","+splitted[i]; 
		          }
        		  catch(NumberFormatException e) {
        			  newline=newline+"."+splitted[i]; 
        		  }
        	  }
//	            System.out.println(line);
	        NewlineList.add(newline); 
	        }   
	        
	        // Always close files.
	        bufferedReader.close();         

		
		
	
		
	  
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\mouna\\new_workspace\\TracePredictor\\src\\JHotDrawFiles\\JHotDrawMethodsFormatted.txt")); 

            for(String myline: NewlineList) {
            	String[] splitted= myline.split("\\,");
            	String[] methodnames = splitted[0].split("\\."); 
            	int i=0; 
            	String s=""; 
            	if(methodnames[methodnames.length-1].equals(methodnames[methodnames.length-2])) {
            	
            			while(i<methodnames.length-1) {
            				s=s+methodnames[i]+"."; 
            				i++;
            			}
            		
            		s=s+"-init-";
            		int j=1; 
            		while(j<splitted.length) {
            			s=s+","+splitted[j]; 
            			j++; 
            		}
            	}else {
            		s=myline; 
            	}
                writer.write(s);
                writer.newLine();
                System.out.println(myline);
            }
            writer.close();
        }
   
	  

		
	}

