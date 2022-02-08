package iTrust;

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
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import Chess.SubjectTSubjectNObject;
import spoon.Launcher;
import spoon.SpoonAPI;

public class AddGold2Column {
	/** The name of the MySQL account to use (or empty for anonymous) */
	 static  String userName = "root";
	
	/** The password for the MySQL account (or empty for anonymous) */
	static  String password = "root";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */

	private final int portNumber = 3306;
	
	private final String dbName = "databaseitrust";

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
		connectionProps.put("123456", password);
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/databaseitrust","root","123456");

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

		   
		  
			   AddColumns();
		
		  
		   
		   
		
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		AddColumns();

	}

	public static void AddColumns() throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;
		conn = getConnection();
		Statement st = conn.createStatement();
		Statement st2 = conn.createStatement();
//		st.executeUpdate("ALTER TABLE `traces` DROP COLUMN SubjectT"); 
		st.executeUpdate("ALTER TABLE `traces` DROP COLUMN subjectGold");
		st.executeUpdate("ALTER TABLE `traces` ADD subjectGold LONGTEXT");
		
		try {
			File file = new File("C:\\Users\\mouna\\new_workspace\\TracePredictor\\java\\iTrustFiles\\itrust_vote_dev.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			line = bufferedReader.readLine(); 
			List<SubjectTSubjectNObject> mylist= new ArrayList<SubjectTSubjectNObject>(); 

			while ((line = bufferedReader.readLine()) != null) {
				String[] splittedline = line.split(",", -1); 
				
				int counter =1; 
				for(int i=1; i<splittedline.length; i++) {
					SubjectTSubjectNObject SubjectTSubjectNObj = new SubjectTSubjectNObject(); 
					String methodname= splittedline[0]; 
					methodname=methodname.replaceAll("::", "."); 
					System.out.println(methodname);
					//methodname=methodname.replaceAll("constructor", "-init-"); 
					//methodname=PredictionPattern.compile("[{}<>]").matcher(methodname).replaceAll("");
				
					String RequirementID= ""+counter;
					String val=splittedline[i];
					if(splittedline[i].equals("")) {
						SubjectTSubjectNObj.setGoldfinal("N");
					}
					else {
						SubjectTSubjectNObj.setGoldfinal("T");
					}
					SubjectTSubjectNObj.setMethodName(methodname);
					SubjectTSubjectNObj.setRequirementID(RequirementID);
					
					counter++; 
					mylist.add(SubjectTSubjectNObj); 
				}
			
			}
			fileReader.close();
			System.out.println(mylist.size());
			int count=1;
			for (SubjectTSubjectNObject entry: mylist) {
				System.out.println(entry.toString()+ " "+count);
				String name= entry.MethodName; 
				st.executeUpdate("UPDATE `traces` SET `subjectGold` ='"+ entry.goldfinal +"'WHERE requirementid='"+entry.RequirementID+"' AND shortmethodname ='"+name+"'");
				//st.executeUpdate("UPDATE `traces` SET  +"'WHERE requirementid='"+entry.RequirementID+"' AND method='"+name+"'"); 
				count++;
			}
			
		 	st.executeUpdate("UPDATE `tracesclasses` SET `subjectGold` ='"+ "E" +"'WHERE subjectGold is null");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		//st.executeUpdate("SELECT * FROM `traces` where method LIKE `% %`"); 
	}
}
