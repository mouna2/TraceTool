package Gantt;

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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

import Chess.CountTNE;
import spoon.Launcher;
import spoon.SpoonAPI;

public class AddSubjectGoldTRACESCLASSES_NEW {
	/** The name of the MySQL account to use (or empty for anonymous) */
	private static final String userName = "root";
	
	/** The password for the MySQL account (or empty for anonymous) */
	private static final String password = "root";

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
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("root", userName);
		connectionProps.put("123456",password);
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/databasegantt","root","123456");

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

		   
		  
//			   AddColumns();
		
			AddColumns2();
		   
		   
		
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		AddColumns2();
	}

	
	/***********************************************************************/
	public static void AddColumns2() throws SQLException {
		// TODO Auto-generated method stub


		// TODO Auto-generated method stub
		Connection conn = null;
		conn = getConnection();
		Statement st = conn.createStatement();
	

		st.executeUpdate("ALTER TABLE `tracesclasses` DROP COLUMN subjectGold");

		

		st.executeUpdate("ALTER TABLE `tracesclasses` ADD subjectGold LONGTEXT");



		int  TracesNumber=0; 
		int counter=0; 
		String classid=""; 
		String classname=""; 
		String requirementid= ""; 
		String requirementname= ""; 
		
		Hashtable<String,String> RequirementClassHashMapNames=new Hashtable<String,String>(); 

		Hashtable<String,List<String>> RequirementClassHashMap=new Hashtable<String,List<String>>(); 
	
		Hashtable<String,List<String>> RequirementClassHashMapUnionGold=new Hashtable<String,List<String>>(); 
		List<String> ListUnionGold= new ArrayList<String>(); 
		List<String> mylist= new ArrayList<String>(); 
		ResultSet TracesCount=st.executeQuery("SELECT COUNT(*) FROM traces"); 
		while(TracesCount.next()) {
			 TracesNumber= TracesCount.getInt(1); 
			System.out.println(TracesNumber);
		}
		
		while(counter<TracesNumber) {
			ResultSet traces = st.executeQuery("SELECT traces.* from traces where id='"+counter+"'"); 
			while(traces.next()){		
				//THIS IS GOLD 2
				 requirementid=traces.getString("requirementid").trim(); 
				 classid=traces.getString("classid").trim(); 
				 requirementname=traces.getString("requirement").trim(); 
				 classname=traces.getString("classname").trim(); 
				String ReqClass=requirementid+"-"+classid;
				String ReqClassNames=requirementid+"-"+requirementname+"-"+classid+"-"+classname;

				RequirementClassHashMap.put(ReqClass, mylist); 
				RequirementClassHashMapNames.put(ReqClass, ReqClassNames); 
				String goldfinal=traces.getString("subjectGold").trim();
				if(RequirementClassHashMapUnionGold.get(ReqClass)==null) {
					ListUnionGold= new ArrayList<String>(); 
					RequirementClassHashMapUnionGold.put(ReqClass, ListUnionGold); 
					ListUnionGold = RequirementClassHashMapUnionGold.get(ReqClass); 
					ListUnionGold.add(goldfinal); 
					RequirementClassHashMapUnionGold.put(ReqClass, ListUnionGold); 
				}else {
					ListUnionGold = RequirementClassHashMapUnionGold.get(ReqClass); 
					ListUnionGold.add(goldfinal); 
					RequirementClassHashMapUnionGold.put(ReqClass, ListUnionGold); 
				}
				
				
				
				
			
				
				
				
			

	   		   }
			System.out.println("COUNTER 1  "+counter);
			counter++; 
		}
		
		
		System.out.println("finished buolding hashmaps");
		
	
		

		

	counter=0; 
		for(Entry<String, List<String>>  entry: RequirementClassHashMapUnionGold.entrySet()) {

			   System.out.println(entry.getKey() + " = " );
			    requirementid= entry.getKey().substring(0, entry.getKey().indexOf("-")); 
			     classid= entry.getKey().substring(entry.getKey().indexOf("-")+1, entry.getKey().length()); 
			     
			     List<String> MyValues = entry.getValue(); 
			     java.util.Collections.sort(MyValues); 
			    
//				for(String val: MyValues) {
//			    	 System.out.println("VAL  "+val);
//			    	 
//			     }
				   int CountT=0, CountN=0, CountE=0; 
			     CountTNE count=ComputeProportions(MyValues, CountT, CountN, CountE); 
			
//			    System.out.println("CountT "+count.CountT);
//			    System.out.println("CountN "+count.CountN);
//			    System.out.println("CountE "+count.CountE);
			     
		
			     
			     
			     
			     if(MyValues.size()>0) {
				     System.out.println(MyValues.size());
					    int newsize = MyValues.size()/2; 
					    System.out.println(newsize);
					    	   String charac = MyValues.get(newsize); 
					    
					  
					    	   if(count.CountT>0) {		
									st.executeUpdate("UPDATE `tracesclasses` SET `subjectGold` ='"+ "T" +"'WHERE requirementid='"+requirementid+"' AND classid='"+classid+"'");

						     } 
//						    else  if(charac.trim().equals("N")) {

						    else  if(count.CountN>0 && count.CountT==0 && count.CountE>=0) {
						    		
						    		
						    		
						    	 st.executeUpdate("UPDATE `tracesclasses` SET `subjectGold` ='"+ "N" +"'WHERE requirementid='"+requirementid+"' AND classid='"+classid+"'");



						    			     }
						     else {
									st.executeUpdate("UPDATE `tracesclasses` SET `subjectGold` ='"+ "E" +"'WHERE requirementid='"+requirementid+"' AND classid='"+classid+"'");

						    	 
						     }
					     
			     }
			     else {
						st.executeUpdate("UPDATE `tracesclasses` SET `subjectGold` ='"+ "E" +"'WHERE requirementid='"+requirementid+"' AND classid='"+classid+"'");

			    	 
			     }
		
			     
			     
			     
			     
			     
			     System.out.println("COUNTER 1  "+counter);
					counter++; 
			     
		}
		
		
		
		
	}
	private static CountTNE ComputeProportions(List<String> myValues, int countT, int countN, int countE) {
		// TODO Auto-generated method stub
		
		for(String s: myValues) {
//			System.out.println("=====>"+s);
			if(s.trim().equals("T")) {
				countT++; 
			}
			else if(s.trim().equals("N")) {
				countN++; 
			}
			else if(s.trim().equals("E")) {
				countE++; 
			}
		}
		CountTNE count= new CountTNE(); 
		count.setCountT(countT);
		count.setCountN(countN);
		count.setCountE(countE);
		return count; 

	}

	

	
	
	
	
}
