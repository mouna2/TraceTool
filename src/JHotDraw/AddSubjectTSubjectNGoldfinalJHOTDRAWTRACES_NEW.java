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
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import Chess.SubjectTSubjectNObject;
import spoon.Launcher;
import spoon.SpoonAPI;

public class AddSubjectTSubjectNGoldfinalJHOTDRAWTRACES_NEW {
	/** The name of the MySQL account to use (or empty for anonymous) */
	private final static String userName = "root";
	
	/** The password for the MySQL account (or empty for anonymous) */
	private final static String password = "root";

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
		connectionProps.put("123456", password);
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
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		Statement st2 = conn.createStatement();
		st.executeUpdate("ALTER TABLE `traces` DROP COLUMN SubjectT"); 
		st.executeUpdate("ALTER TABLE `traces` DROP COLUMN SubjectN");
		st.executeUpdate("ALTER TABLE `traces` ADD SubjectT LONGTEXT"); 
		st.executeUpdate("ALTER TABLE `traces` ADD SubjectN LONGTEXT");
		try {
			File file = new File("C:\\Users\\mouna\\new_workspace\\TraceGenerator\\src\\JHotDrawFiles\\JHotDrawMethodsFormatted2.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			line = bufferedReader.readLine(); 
			List<SubjectTSubjectNObject> mylist= new ArrayList<SubjectTSubjectNObject>(); 

			while ((line = bufferedReader.readLine()) != null) {
				String[] splittedline = line.split(","); 
				stringBuffer.append(line);
				stringBuffer.append("\n");
				int counter =1; 
				for(int i=1; i<splittedline.length-1; i+=2) {
					SubjectTSubjectNObject SubjectTSubjectNObj = new SubjectTSubjectNObject(); 
					String methodname= splittedline[0]; 
					methodname=methodname.replaceAll("::", "."); 
					methodname=methodname.replaceAll("constructor", "-init-"); 
					methodname=Pattern.compile("[{}<>]").matcher(methodname).replaceAll(""); 
				
					String RequirementID= ""+counter;
					String SubjectT= splittedline[i];
					String SubjectN= splittedline[i+1]; 
					SubjectTSubjectNObj.setMethodName(methodname);
					SubjectTSubjectNObj.setRequirementID(RequirementID);
					SubjectTSubjectNObj.setSubjectT(SubjectT);
					SubjectTSubjectNObj.setSubjectN(SubjectN);
					counter++; 
					mylist.add(SubjectTSubjectNObj); 
				}
			
			}
			fileReader.close();
			System.out.println(mylist.size());
			int count=1;
			for (SubjectTSubjectNObject entry: mylist) {
				System.out.println(entry.toString()+ " "+count);
				//String name= "net.sourceforge.ganttproject."+entry.MethodName; 
				String name= "org.jhotdraw."+entry.MethodName; 
				System.out.println(name);
				
				
				String	goldfinal= PredictGoldUnionFinal(Integer.parseInt(entry.SubjectT), Integer.parseInt(entry.SubjectN)); 
				if(!entry.SubjectT.equals("0")||!entry.SubjectN.equals("0")) {
					st.executeUpdate("UPDATE `traces` SET `SubjectT` ='"+ entry.SubjectT +"',"+"`SubjectN` ='"+ entry.SubjectN +
							"',"+"`subjectGold` ='"+ goldfinal +"'WHERE requirementid='"+entry.RequirementID+"' AND method ='"+name+"'");
					//st.executeUpdate("UPDATE `traces` SET  +"'WHERE requirementid='"+entry.RequirementID+"' AND method='"+name+"'"); 
					count++;
				}
				
			}
			
		 	st.executeUpdate("UPDATE `traces` SET `subjectGold` ='"+ "E" +"'WHERE subjectGold is null");

			System.out.println(stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		//st.executeUpdate("SELECT * FROM `traces` where method LIKE `% %`"); 
	}
	
	
	static String PredictGoldUnionFinal(int SubjectT, int SubjectN) {
		String goldUnion=null; 
			
			if((SubjectT>=1 && SubjectN==0) || SubjectT>=2) 

//			if((SubjectT>=2 && SubjectN==0) || SubjectT>=3) 
			{
				goldUnion="T"; 
			}
			else if(SubjectT==0 && SubjectN>=2) {
				goldUnion="N"; 
			}
			else {
				goldUnion="E"; 
			}
			
			
			
		
		return goldUnion; 
	}
	
}
