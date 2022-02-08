package databaseReading;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class comparator{
	
/** The name of the MySQL account to use (or empty for anonymous) */
	private static final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private static final String password = "root";

	public static Connection getConnection(String database) throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("root", userName);
		connectionProps.put("123456", password);
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database+"?useLegacyDatetimeCode=false&serverTimezone=UTC","root","123456");

		return conn;
	}


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

		
	public void run(String database) throws IOException {
		ResultSet rs = null; 
		// Connect to MySQL
		Connection conn = null;
		try {
			conn = comparator.getConnection(database);
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
	}
		
	
	
	/**
	 * Connect to the DB and do some stuff
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException {
		comparator app = new comparator();
		app.run("databasechess");
		
		 Connection conn=getConnection("databasechess");
		Statement st= conn.createStatement();
		List<String> methcalls = new ArrayList<String>(); 
		ResultSet results = st.executeQuery("SELECT * from methodcalls ");  
		while(results.next()){
			String methcall= results.getString("callermethodid")+"-"+results.getString("calleemethodid"); 
			methcalls.add(methcall); 
			
	}
		
		app.run("new_databasechess");
		
		 Connection conn2=getConnection("new_databasechess");
		Statement st2= conn2.createStatement();
		List<String> methcalls2 = new ArrayList<String>(); 
		ResultSet results2 = st2.executeQuery("SELECT * from methodcalls ");  
		while(results2.next()){
			String methcall2= results2.getString("callermethodid")+"-"+results2.getString("calleemethodid"); 
			methcalls2.add(methcall2); 
			
	}
		
		
		methcalls.removeAll(methcalls2);
	
		for(String methcall: methcalls) {
			System.out.println(methcall);
		}
	
	}
}