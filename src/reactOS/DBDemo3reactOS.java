package reactOS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.pattern.FullLocationPatternConverter;

import Chess.CountTNE;
import Chess.SubjectTSubjectNObject;
import model.Clazz;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtTargetedExpression;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.ClassFactory;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.InterfaceFactory;
import spoon.reflect.factory.MethodFactory;
import spoon.reflect.path.CtPath;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.FieldAccessFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtConstructorCallImpl;
import spoon.support.reflect.code.CtNewClassImpl;
import spoon.support.reflect.declaration.CtInterfaceImpl;
import spoon.support.reflect.declaration.CtTypeImpl;
import tables.methodcalls;
import tables.methods;
import tables.tracesmethods;

/**
 * This class demonstrates how to connect to MySQL and run some basic commands.
 * 
 * In order to use this, you have to download the Connector/J driver and add
 * its .jar file to your build path.  You can find it here:
 * 
 * http://dev.mysql.com/downloads/connector/j/
 * 
 * You will see the following exception if it's not in your class path:
 * 
 * java.sql.SQLException: No suitable driver found for jdbc:mysql://localhost:3306/
 * 
 * To add it to your class path:
 * 1. Right click on your project
 * 2. Go to Build Path -> Add External Archives...
 * 3. Select the file mysql-connector-java-5.1.24-bin.jar
 *    NOTE: If you have a different version of the .jar file, the name may be
 *    a little different.
 *    
 * The user name and password are both "root", which should be correct if you followed
 * the advice in the MySQL tutorial. If you want to use different credentials, you can
 * change them below. 
 * 
 * You will get the following exception if the credentials are wrong:
 * 
 * java.sql.SQLException: Access denied for user 'userName'@'localhost' (using password: YES)
 * 
 * You will instead get the following exception if MySQL isn't installed, isn't
 * running, or if your serverName or portNumber are wrong:
 * 
 * java.net.ConnectException: Connection refused
 */
public class DBDemo3reactOS {

	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "root";

	/** The name of the computer running MySQL */  
	
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "databasereactos";
	
	/** The name of the table we are testing with */
	

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
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/databasereactos"+"?useLegacyDatetimeCode=false&serverTimezone=UTC","root","123456");

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
	 * @throws IOException 
	 */
	public void run() throws IOException {
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
			
			
//		   st.executeUpdate("CREATE TABLE `databasereactos`.`traces` (\r\n" + 
//		   		"  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + 
//		   		"  `requirement` LONGTEXT NULL,\r\n" + 
//		   		"  `requirementid` INT,\r\n" + 
//		   		"  `method` LONGTEXT NULL,\r\n" + 
//		   		"  `methodname` LONGTEXT NULL,\r\n" + 
//		   		"  `fullmethod` LONGTEXT NULL,\r\n" +
//		   		"  `methodid` INT NULL,\r\n" + 
//		   		"  `classname` LONGTEXT NULL,\r\n" + 
//		   		"  `classid` LONGTEXT NULL,\r\n" + 
//		   		"  `goldfinal` LONGTEXT NULL,\r\n" +
//		   		
//		   		"  PRIMARY KEY (`id`));\r\n" + 	
//		   		""); 
//		 
//		   
//		  
//			 st.executeUpdate("CREATE TABLE `databasereactos`.`tracesclasses` (\r\n" + 
//			 		"  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + 
//			 		"  `requirement` LONGTEXT NULL,\r\n" + 
//			 		"  `requirementid` INT NULL,\r\n" + 
//			 		"  `classname` LONGTEXT NULL,\r\n" + 
//			 		"  `classid` INT NULL,\r\n" + 
//			 		"  `SubjectGold` LONGTEXT NULL,\r\n" +
//			 		"  `goldfinal` LONGTEXT NULL,\r\n" + 
//			 		"  PRIMARY KEY (`id`),\r\n" + 
//			 		"  UNIQUE INDEX `idtracesclasses_UNIQUE` (`id` ASC));\r\n" + 
//			 		""); 
   
			

		   try {
			Spoon();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  
		   
		   
		
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		
	}
	
	/**
	 * Connect to the DB and do some stuff
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		DBDemo3reactOS app = new DBDemo3reactOS();
		app.run();
	}
	
	public void Spoon() throws SQLException, IOException {
		DBDemo3reactOS dao = new DBDemo3reactOS();
	Connection conn=getConnection();
	Statement st= conn.createStatement();
	
	Statement st2= conn.createStatement();
	Statement st3= conn.createStatement();
	Statement st4= conn.createStatement();
	Statement st5= conn.createStatement();
	
		
    //////////////
    ///////////////*********************************************************************************************************************************************************************************/	
    ///////////////*********************************************************************************************************************************************************************************/	
    ///////////////*********************************************************************************************************************************************************************************/   
    ////
    ////////////////CREATE TRACES TABLE 
    ////////////
    	 File file = new File("C:\\Users\\mouna\\git\\TraceProcessor\\src\\reactOSFiles\\reactos traces methods developer.csv");
		 FileReader fileReader = new FileReader(file);
		 BufferedReader bufferedReader = new BufferedReader(fileReader);
		 StringBuffer stringBuffer = new StringBuffer();
		String line;
		line = bufferedReader.readLine(); 
		HashMap<String, SubjectTSubjectNObject> mytracehashmap= new HashMap<String, SubjectTSubjectNObject>(); 
		
		
		
		int counter;
		while ((line = bufferedReader.readLine()) != null) {
			String[] splittedline = line.split(",", -1); 
			
			  counter = 1; 
			for(int k=1; k<splittedline.length; k+=2) {
				SubjectTSubjectNObject SubjectTSubjectNObj = new SubjectTSubjectNObject(); 
				String methodname= splittedline[0]; 
		
				String RequirementID= ""+counter;
				if(splittedline[k].equals("0") && splittedline[k+1].equals("1")) {
					SubjectTSubjectNObj.setGoldfinal("N");
				}
				else if(splittedline[k].equals("1") && splittedline[k+1].equals("0")) {
					SubjectTSubjectNObj.setGoldfinal("T");
				}
				else  {
					SubjectTSubjectNObj.setGoldfinal("E");
				}
				SubjectTSubjectNObj.setMethodName(methodname);
				SubjectTSubjectNObj.setRequirementID(RequirementID);
				
				
				String key=counter+"-"+methodname; 
				mytracehashmap.put(key,SubjectTSubjectNObj); 
				counter++; 
			}
		
		}
		fileReader.close();
		
		
		  file = new File("C:\\Users\\mouna\\git\\TraceProcessor\\src\\reactOSFiles\\reactos traces methods students.csv");
		  fileReader = new FileReader(file);
		  bufferedReader = new BufferedReader(fileReader);
		  stringBuffer = new StringBuffer();
		 
		line = bufferedReader.readLine(); 
		
		
		
	
		while ((line = bufferedReader.readLine()) != null) {
			String[] splittedline = line.split(",", -1); 
			
			  counter = 1; 
			for(int k=1; k<splittedline.length; k+=2) {
				String methodname= splittedline[0]; 
				String key=counter+"-"+methodname; 
				SubjectTSubjectNObject SubjectTSubjectNObj = mytracehashmap.get(key); 
				
				if(SubjectTSubjectNObj!=null) {
					SubjectTSubjectNObj.setSubjectT(splittedline[k]);
					SubjectTSubjectNObj.setSubjectN(splittedline[k+1]);
				}
				

				
				
				
				
				

				mytracehashmap.put(key,SubjectTSubjectNObj); 
				counter++; 
			}
		
		}
		fileReader.close();
		
	    HashMap<String, String> RequirementIDNameHashMap=new HashMap<String, String> (); 
	    RequirementIDNameHashMap.put("1", "R0"); 
	    RequirementIDNameHashMap.put("2", "R1"); 
	    RequirementIDNameHashMap.put("3", "R2"); 
	    RequirementIDNameHashMap.put("4", "R3"); 
	    RequirementIDNameHashMap.put("5", "R4"); 
	    RequirementIDNameHashMap.put("6", "R5"); 
	    RequirementIDNameHashMap.put("7", "R6"); 
	    RequirementIDNameHashMap.put("8", "R7"); 
	    RequirementIDNameHashMap.put("9", "R8"); 
	    RequirementIDNameHashMap.put("10", "R9"); 
	    RequirementIDNameHashMap.put("11", "R10"); 
	    RequirementIDNameHashMap.put("12", "R11"); 
	    RequirementIDNameHashMap.put("13", "R12"); 
	    RequirementIDNameHashMap.put("14", "R13"); 
	    RequirementIDNameHashMap.put("15", "R13"); 
	    RequirementIDNameHashMap.put("16", "R13"); 

	    List<String> MYLIST= new ArrayList<String>(); 

	    counter=0; 
    ResultSet mymeths = st2.executeQuery("SELECT methods.* from methods"); 
    while(mymeths.next()){
    String methodid = mymeths.getString("id"); 
    String method = mymeths.getString("methodabbreviation"); 
    String methodname = mymeths.getString("methodname"); 
    String fullmethod = mymeths.getString("fullmethod"); 

    String classname = mymeths.getString("classname"); 
    String classid = mymeths.getString("classid"); 
    String fullmethod2=fullmethod.substring("".length()); 
   
    for(String key: RequirementIDNameHashMap.keySet()) {
    tracesmethods tr= new tracesmethods(key, methodid,  classid); 
   SubjectTSubjectNObject sub = mytracehashmap.get(tr.getRequirementid()+"-"+fullmethod); 
   if(sub!=null) {
	   if(Integer.parseInt(sub.getSubjectT())>0 && Integer.parseInt(sub.getSubjectN())==0) {
		   sub.SubjectGold="T"; 
	   }else if(Integer.parseInt(sub.getSubjectN())>0 && Integer.parseInt(sub.getSubjectT())==0) {
		   sub.SubjectGold="N"; 
	   }else
		   sub.SubjectGold="E"; 

	   String statement = "INSERT INTO `traces`(`requirement`, `requirementid`, `method`, `methodname`, `fullmethod`,  `methodid`,`classname`, `classid`,`goldfinal`,`SubjectT`,`SubjectN`,`SubjectGold`) VALUES ('"+RequirementIDNameHashMap.get(tr.getRequirementid())+"','" +tr.getRequirementid()+"','" +method+"','" +methodname+"','" +fullmethod+"','" +methodid+"','"+classname +"','" +classid+"','" +sub.getGoldfinal()
	   +"','" +sub.getSubjectT()+"','" +sub.getSubjectN()+"','" +sub.SubjectGold+"')";
	  
	    st.executeUpdate(statement);
	    MYLIST.add(tr.getRequirementid()+"-"+method); 
	    counter++; 
   }
   else {
	   String statement = "INSERT INTO `traces`(`requirement`, `requirementid`, `method`, `methodname`, `fullmethod`,  `methodid`,`classname`, `classid`,`goldfinal`,`SubjectT`,`SubjectN`,`SubjectGold`) VALUES ('"+RequirementIDNameHashMap.get(tr.getRequirementid())+"','" +tr.getRequirementid()+"','" +method+"','" +methodname+"','" +fullmethod+"','" +methodid+"','"+classname +"','" +classid+"','" +"E"
			   +"','" +"0"+"','" +"0"+"','" +"E"+"')";
	    st.executeUpdate(statement);
   }
   

    }
  
//
    }
    
    
    
    
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
/////////////////*********************************************************************************************************************************************************************************/   
////
////////////////CREATE TRACES CLASSES TABLE 
////////////
 fileReader = new FileReader("C:\\Users\\mouna\\git\\TraceProcessor\\src\\reactOSFiles\\reactos traces classes developer.csv");
 bufferedReader = new BufferedReader(fileReader);
HashMap<String,  String> ReqClassHashMap= new HashMap<String,  String> (); 
 line = null;
line = bufferedReader.readLine(); 
String[] requirements = line.split(","); 
List<String> TraceClassList = new ArrayList<String>(); 
while((line = bufferedReader.readLine()) != null) {
//System.out.println(line);
String[] splitted = line.split("\\,", -1);
int count=1; 
for(int k=1; k<splitted.length; k+=2) {
	String s =count+"-"+splitted[0]; 
	s=s.substring(0, s.length()-2); 
if(splitted[k].equals("1") && splitted[k+1].equals("0")) {
ReqClassHashMap.put(s, "T"); 
}else if(splitted[k].equals("0") && splitted[k+1].equals("1")){
ReqClassHashMap.put(s, "N"); 
}else {
	
	ReqClassHashMap.put(s, "E"); 

}

TraceClassList.add(k+"-"+splitted[0]); 
count++; 
}
//System.out.println(line);
}   

//Always close files.
bufferedReader.close();         

Hashtable<String,List<String>> RequirementClassHashMapUnionGold=new Hashtable<String,List<String>>(); 
List<String> ListUnionGold= new ArrayList<String>(); 
List<String> mylist = new ArrayList<String>(); 
ResultSet traces = st.executeQuery("SELECT traces.* from traces "); 
while(traces.next()){		
//THIS IS GOLD 2
	

String requirementid=traces.getString("requirementid").trim(); 
String classid=traces.getString("classid").trim(); 


SubjectTSubjectNObject sub = mytracehashmap.get(requirementid+"-"+traces.getString("fullmethod").trim()); 



String ReqClass=requirementid+"-"+classid; 
String goldfinal=traces.getString("SubjectGold").trim(); 
if(RequirementClassHashMapUnionGold.get(ReqClass)==null) {
ListUnionGold= new ArrayList<String>(); 
ListUnionGold.add(goldfinal); 
RequirementClassHashMapUnionGold.put(ReqClass, ListUnionGold); 
}else {
ListUnionGold = RequirementClassHashMapUnionGold.get(ReqClass); 
ListUnionGold.add(goldfinal); 
RequirementClassHashMapUnionGold.put(ReqClass, ListUnionGold); 
}


}
HashMap <String, String > RequirementClassHashMap= new HashMap <String, String > (); 

String classname=""; 
String classid=""; 
String requirementname=""; 
String requirementid="";
ResultSet Traces = st.executeQuery("SELECT classes.* from classes "); 
while(Traces.next()){
classname = Traces.getString("classname"); 
classid = Traces.getString("id"); 
for(String keyreq: RequirementIDNameHashMap.keySet()) {
String key= keyreq+"/"+classid; 
String val= keyreq+"/"+RequirementIDNameHashMap.get(keyreq)+"/"+classid+"/"+classname; 

RequirementClassHashMap.put(key, val); 
}





}





for(Entry<String, String> entry :RequirementClassHashMap.entrySet()) {
String myvalue = entry.getValue(); 
String[] myvalues = myvalue.split("/"); 
//System.out.println(myvalues[1]);
//System.out.println(myvalues[0]);
//System.out.println(myvalues[3]);
//System.out.println(myvalues[2]);
int CountT=0, CountN=0, CountE=0; 
List<String> list = RequirementClassHashMapUnionGold.get(myvalues[0]+"-"+myvalues[2]); 
CountTNE count=ComputeProportions(list, CountT, CountN, CountE); 

String SubjectGeneralization=ComputeSubjectGeneralization(count);
String reqclassValue = ReqClassHashMap.get(myvalues[0]+"-"+myvalues[3]); 
if(reqclassValue!=null) {
String statement8= "INSERT INTO `tracesclasses`(`requirement`, `requirementid`,  `classname`, `classid`,`subjectGold`,`goldfinal`) VALUES ('"+myvalues[1]+"','" +myvalues[0]+"','"  +myvalues[3]+"','" +myvalues[2]
+"','"  +SubjectGeneralization+"','"  +reqclassValue+"')";	
st2.executeUpdate(statement8);
TraceClassList.remove(myvalues[0]+"-"+myvalues[3]); 
}else {
String statement8= "INSERT INTO `tracesclasses`(`requirement`, `requirementid`,  `classname`, `classid`,`subjectGold`,`goldfinal`) VALUES ('"+myvalues[1]+"','" +myvalues[0]+"','"  +myvalues[3]+"','" +myvalues[2]
+"','"  +SubjectGeneralization+"','"  +"E"+"')";	
st2.executeUpdate(statement8);
}

}

    
    
    

//    for(String key: mytracehashmap.keySet()) {
//    	if(!MYLIST.contains(key)) {
//    		System.out.println(key);
//    	}
//    }
//    System.out.println("OVER");
    //
    //
    //
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
    /////////////////*********************************************************************************************************************************************************************************/   
    ////
    ////////////////CREATE TRACES CLASSES TABLE 
    ////////////
    //
    //
    
//     fileReader = new FileReader("C:\\Users\\mouna\\new_workspace\\TraceProcessor\\src\\vodFiles\\VODTracesClassesDeveloper.txt");
//
//	
//	    List<String> MYLIST2= new ArrayList<String>(); 
//
//	
//	  bufferedReader = new BufferedReader(fileReader);
//        HashMap<String,  String> ReqClassHashMap= new HashMap<String,  String> (); 
//         line = null;
//        line = bufferedReader.readLine(); 
//        String[] requirements = line.split(","); 
//        while((line = bufferedReader.readLine()) != null) {
////            System.out.println(line);
//            String[] splitted = line.split("\\,", -1);
//            
//            for(int p=1; p<splitted.length; p++) {
//            	if(splitted[p].equals("x")) {
//            		ReqClassHashMap.put(p+"-"+splitted[0], "T"); 
//            	}else if(splitted[p].equals("")) {
//            		ReqClassHashMap.put(p+"-"+splitted[0], "N"); 
//            	}
//            }
////            System.out.println(line);
//        }   
//
//        // Always close files.
//        bufferedReader.close();         
//
//    HashMap <String, String > RequirementClassHashMap= new HashMap <String, String > (); 
//
//    String classname=""; 
//    String classid=""; 
//    String requirementname=""; 
//    String requirementid="";
//    ResultSet Traces = st.executeQuery("SELECT classes.* from classes "); 
//    while(Traces.next()){
//    classname = Traces.getString("classname"); 
//    classid = Traces.getString("id"); 
//    for(String keyreq: RequirementIDNameHashMap.keySet()) {
//    	String key= keyreq+"-"+classid; 
//    	String val= keyreq+"-"+RequirementIDNameHashMap.get(keyreq)+"-"+classid+"-"+classname; 
//
//    	RequirementClassHashMap.put(key, val); 
//    }
//
//
//
//
//
//    }
//
//    for(Entry<String, String> entry :RequirementClassHashMap.entrySet()) {
//    String myvalue = entry.getValue(); 
//    String[] myvalues = myvalue.split("-"); 
//   String goldfinal = ReqClassHashMap.get(myvalues[0]+"-"+myvalues[3]);
//   if(goldfinal!=null) {
//	   String statement8= "INSERT INTO `tracesclasses`(`requirement`, `requirementid`,  `classname`, `classid`,`SubjectGold`,`goldfinal`) VALUES ('"+myvalues[1]+"','" +myvalues[0]+"','"  +myvalues[3]+"','" +myvalues[2]
//			   +"','" +"E"  +"','" +goldfinal+"')";
//	    st2.executeUpdate(statement8); 
//	    MYLIST2.add(myvalues[0]+"-"+myvalues[3]); 
//   }
//   else {
//	   String statement8= "INSERT INTO `tracesclasses`(`requirement`, `requirementid`,  `classname`, `classid`,`SubjectGold`,`goldfinal`) VALUES ('"+myvalues[1]+"','" +myvalues[0]+"','"  +myvalues[3]+"','" +myvalues[2]
//			   +"','" +"E"  +"','" +"E"+"')";	
//	    st2.executeUpdate(statement8); 
//   }
//    }
//
//    for(String key: ReqClassHashMap.keySet()) {
//    	if(!MYLIST2.contains(key)) {
//    		System.out.println(key);
//    	}
//    }
//    System.out.println("OVER");





////      /////////////*********************************************************************************************************************************************************************************/	
//        /////////////*********************************************************************************************************************************************************************************/	
//        /////////////*********************************************************************************************************************************************************************************/   
//        //////////////CREATE METHOD CALLS EXECUTED TABLE 
//        ////////////
//          file = new File("C:\\Users\\mouna\\new_workspace\\TraceGenerator\\src\\ChessFiles\\methodcallsExecutedFormatted.txt");
//          fileReader = new FileReader(file);
//          bufferedReader = new BufferedReader(fileReader);
//          stringBuffer = new StringBuffer();
//       
//        try {
//        
//        
//          line = "";
//          counter = 0; 
//		while ((line = bufferedReader.readLine()) != null) {
//			String CallerExecutedClassID=null; 
//		    String  CallerExecutedMethodID=null; 
//		    String CalleeExecutedClassID = null; 
//		    String  CalleeExecutedMethodID=null; 
//        System.out.println(line);
//        String[] lines = line.split("---"); 
//        String Caller= lines[0]; 
//        String Callee= lines[1]; 
//        String CallerNonParameters = Caller.substring(0, Caller.indexOf("(")).trim(); 
//        String CallerParameters = Caller.substring(Caller.indexOf("("), Caller.length()).trim(); 
//
//        
//        String CalleeNonParameters = Callee.substring(0, Callee.indexOf("(")).trim(); 
//        String CalleeParameters = Callee.substring(Callee.indexOf("("), Callee.length()).trim(); 
//        
//        
//       String CallerClass=  CallerNonParameters.substring(0, CallerNonParameters.lastIndexOf(".")).trim(); 
//       String CallerMethod=  CallerNonParameters.substring(CallerNonParameters.lastIndexOf(".")+1, CallerNonParameters.length())+CallerParameters.trim(); 
//       String CalleeClass=  CalleeNonParameters.substring(0, CalleeNonParameters.lastIndexOf(".")).trim(); 
//       String CalleeMethod=  CalleeNonParameters.substring(CalleeNonParameters.lastIndexOf(".")+1, CalleeNonParameters.length())+CalleeParameters.trim(); 
//       System.out.println("yes");
//       
//       ResultSet rs= st.executeQuery("SELECT * from classes where classes.classname='"+CallerClass+"'"); 
//       while(rs.next()) {
//    	   CallerExecutedClassID= rs.getString("ID"); 
//       }
//        rs= st.executeQuery("SELECT * from methods where methods.methodname='"+CallerMethod+"' and methods.classname='"+CallerClass+"'"); 
//       while(rs.next()) {
//    	   CallerExecutedMethodID= rs.getString("ID"); 
//       }
//        rs= st.executeQuery("SELECT * from classes where classes.classname='"+CalleeClass+"'"); 
//       while(rs.next()) {
//    	   CalleeExecutedClassID= rs.getString("ID"); 
//       }
//        rs= st.executeQuery("SELECT * from methods where methods.methodname='"+CalleeMethod+"' and methods.classname='"+CalleeClass+"'"); 
//       while(rs.next()) {
//    	   CalleeExecutedMethodID= rs.getString("ID"); 
//       }
//        String fullcaller = CallerClass+"."+CallerMethod; 
//        String fullcallee =  CalleeClass+"."+CalleeMethod; 
//       if(CallerExecutedClassID!=null && CallerExecutedMethodID!=null && CalleeExecutedClassID!=null && CalleeExecutedMethodID!=null) {
//    	   String statement = "INSERT INTO `methodcallsexecuted`(`callermethodid`,  `callername`,  `callerclass`, `callerclassid`,`fullcaller`,`calleemethodid`,  `calleename`, `calleeclass`,  `calleeclassid`,  `fullcallee`) VALUES "
//    	   		+ "('"+CallerExecutedMethodID +"','" +CallerMethod+"','" +CallerClass+"','" +CalleeExecutedClassID+"','" +fullcaller+"','" +CalleeExecutedMethodID+"','" +CalleeMethod+"','" +CalleeClass+"','" +CalleeExecutedClassID+"','" +fullcallee+"')";
////			
//			st.executeUpdate(statement);
//       }
//    
//       System.out.println("yes");
//       counter++; 
//        }
//        
//        
//        
//        
//        }
//        
//        catch (IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//        }
//
	}
	
//  /////////////*********************************************************************************************************************************************************************************/	
    /////////////*********************************************************************************************************************************************************************************/	
    /////////////*********************************************************************************************************************************************************************************/   
	private String WriteMethodIntoDatabase(CtMethod<?> constructor) {
		// TODO Auto-generated method stub
		List<CtComment> CommentList = constructor.getElements(new TypeFilter<CtComment>(CtComment.class));
    	List<CtComment> NewCommentList= CommentList; 
    	NewCommentList = constructor.getElements(new TypeFilter<CtComment>(CtComment.class));
    	int size=NewCommentList.size(); 
    	System.out.println(constructor);
    	int  j=0; 
    	if(CommentList!=null) {
    		CtMethod newmethod=constructor; 
    		
    		
    		while(j<size) {
    			
    			CtComment newcomment = NewCommentList.get(j); 
    			newmethod=newmethod.removeComment(newcomment); 
    			 size=NewCommentList.size(); 
    			 j++; 
    		}
    		
    		constructor=newmethod; 
    	}
    	 String methodString = constructor.toString().replaceAll("\\/\\/.*", ""); 
    	 methodString = methodString.toString().replaceAll("\'", ""); 
	 	
		String FullConstructorName=constructor.getSignature().toString(); 
		
		
		return methodString; 
	}

	public String WriteConstructorIntoDatabase(CtConstructor constructor) {
		// TODO Auto-generated method stub
		 List<CtComment> CommentList = constructor.getElements(new TypeFilter<CtComment>(CtComment.class));
	    	List<CtComment> NewCommentList= CommentList; 
	    	NewCommentList = constructor.getElements(new TypeFilter<CtComment>(CtComment.class));
	    	int size=NewCommentList.size(); 
	    	System.out.println(constructor);
	    	int  j=0; 
	    	if(CommentList!=null) {
	    		CtConstructor newmethod=constructor; 
	    		
	    		
	    		while(j<size) {
	    			
	    			CtComment newcomment = NewCommentList.get(j); 
	    			newmethod=newmethod.removeComment(newcomment); 
	    			 size=NewCommentList.size(); 
	    			 j++; 
	    		}
	    		
	    		constructor=newmethod; 
	    	}
	    	 String methodString = constructor.toString().replaceAll("\\/\\/.*", ""); 
	    	 methodString = methodString.toString().replaceAll("\'", ""); 
		 	
			String FullConstructorName=constructor.getSignature().toString(); 
			
			
			return methodString; 
	}

	private String GetMethodNameAndParams(String method) {
		// TODO Auto-generated method stub
		System.out.println("METH BEFORE TRUNCATION"+method);
		String params=method.substring(method.indexOf("("), method.length()); 
		String BeforeParams=method.substring(0, method.indexOf("(")); 
		String methname=BeforeParams.substring(BeforeParams.lastIndexOf(".")+1, BeforeParams.length()); 
		String res= methname+params; 
		System.out.println("RES"+ res);
		return res;
	}

	
	
	
	
	public static String RewriteFullMethodCallExecutedRemoveDollarsTraces(String input) {
		
		String res=input; 
		StringBuilder buf = new StringBuilder();
		


			boolean flag=false; 
			char[] chars = res.toCharArray();
			int r = 0; 
			int pos=0; 
			
			int myindex= input.indexOf("$"); 
			char c= chars[myindex+1]; 
			if((Character.isDigit(c) && myindex+2==chars.length) || (Character.isDigit(c) && chars[myindex+2]=='(')) {
				System.out.println("yeah");
				while(r<chars.length) {
					if(chars[r]=='$' ) {
					 pos=r; 
					// temp = chars[r+1]; 
					StringBuilder sb = new StringBuilder();
					sb.append(chars);
					sb.deleteCharAt(r);
					chars = sb.toString().toCharArray();
					flag=true; 
					}
					int i=1; 
					if(pos>0) {
						while( flag==true) {
							if(chars[pos-1]!='.'&& chars[pos-1]!='('&& chars[pos-1]!=')' && pos-1<chars.length ) {
								System.out.println(chars[r]);
								StringBuilder sb = new StringBuilder();
								sb.append(chars);
								sb.deleteCharAt(pos);
								chars = sb.toString().toCharArray();
								pos++; 
								//r++; 
								if(pos>chars.length) {
									flag=false; 
								}
								if(chars[pos-1]=='(') {
									flag=false; 
								}
							}
						

							}
					}

					
						r++; 
					
					

					}
				
			}
			else if(Character.isDigit(c)) {
				while(r<chars.length) {
					if(chars[r]=='$' ) {
					 pos=r; 
					// temp = chars[r+1]; 
					StringBuilder sb = new StringBuilder();
					sb.append(chars);
					sb.deleteCharAt(r);
					chars = sb.toString().toCharArray();
					flag=true; 
					}
					int i=1; 
					if(pos>0) {
						while( flag==true) {
							if(chars[pos-1]!='.'&& chars[pos-1]!='('&& chars[pos-1]!=')' && pos-1<chars.length ) {
								System.out.println(chars[r]);
								StringBuilder sb = new StringBuilder();
								sb.append(chars);
								sb.deleteCharAt(pos);
								chars = sb.toString().toCharArray();
								pos++; 
								//r++; 
								if(chars[pos-1]=='.') {
									flag=false; 
								}
							}
						

							}
					}

					
						r++; 
					
					

					}
			}
			else {
				
				while(r<chars.length) {
					if(chars[r]=='$' ) {
					 pos=r; 
					// temp = chars[r+1]; 
					StringBuilder sb = new StringBuilder();
					sb.append(chars);
					sb.deleteCharAt(r);
					chars = sb.toString().toCharArray();
					flag=true; 
					}
					int i=1; 
					if(pos>0) {
						while(chars[pos-1]!='.'&& chars[pos-1]!='('&& chars[pos-1]!=')' && pos<chars.length && flag==true) {
							pos=r-i; 
							System.out.println(chars[r]);
							StringBuilder sb = new StringBuilder();
							sb.append(chars);
							sb.deleteCharAt(pos);
							chars = sb.toString().toCharArray();
					i++; 
							//r++; 

							}
					}

					
						r++; 
					
					

					}
			}
			

			res = String.valueOf(chars);
			System.out.println(res);
			return res; 
		}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public static String RewriteFullMethodCallExecutedRemoveDollars(String input) {
		
		String res=input; 
		StringBuilder buf = new StringBuilder();
		


			boolean flag=false; 
			char[] chars = res.toCharArray();
			int r = 0; 
			int pos=0; 
			
			int myindex= input.indexOf("$"); 
			char c= chars[myindex+1]; 
			if(Character.isDigit(c) && myindex+2==chars.length) {
				System.out.println("yeah");
				while(r<chars.length) {
					if(chars[r]=='$' ) {
					 pos=r; 
					// temp = chars[r+1]; 
					StringBuilder sb = new StringBuilder();
					sb.append(chars);
					sb.deleteCharAt(r);
					chars = sb.toString().toCharArray();
					flag=true; 
					}
					int i=1; 
					if(pos>0) {
						while( flag==true) {
							if(chars[pos-1]!='.'&& chars[pos-1]!='('&& chars[pos-1]!=')' && pos-1<chars.length ) {
								System.out.println(chars[r]);
								StringBuilder sb = new StringBuilder();
								sb.append(chars);
								sb.deleteCharAt(pos);
								chars = sb.toString().toCharArray();
								pos++; 
								//r++; 
								if(pos>chars.length) {
									flag=false; 
								}
							}
						

							}
					}

					
						r++; 
					
					

					}
				
			}
			else if(Character.isDigit(c)) {
				while(r<chars.length) {
					if(chars[r]=='$' ) {
					 pos=r; 
					// temp = chars[r+1]; 
					StringBuilder sb = new StringBuilder();
					sb.append(chars);
					sb.deleteCharAt(r);
					chars = sb.toString().toCharArray();
					flag=true; 
					}
					int i=1; 
					if(pos>0) {
						while( flag==true) {
							if(chars[pos-1]!='.'&& chars[pos-1]!='('&& chars[pos-1]!=')' && pos-1<chars.length ) {
								System.out.println(chars[r]);
								StringBuilder sb = new StringBuilder();
								sb.append(chars);
								sb.deleteCharAt(pos);
								chars = sb.toString().toCharArray();
								pos++; 
								//r++; 
								if(chars[pos-1]=='.') {
									flag=false; 
								}
							}
						

							}
					}

					
						r++; 
					
					

					}
			}
			else {
				
				while(r<chars.length) {
					if(chars[r]=='$' ) {
					 pos=r; 
					// temp = chars[r+1]; 
					StringBuilder sb = new StringBuilder();
					sb.append(chars);
					sb.deleteCharAt(r);
					chars = sb.toString().toCharArray();
					flag=true; 
					}
					int i=1; 
					if(pos>0) {
						while(chars[pos-1]!='.'&& chars[pos-1]!='('&& chars[pos-1]!=')' && pos<chars.length && flag==true) {
							pos=r-i; 
							System.out.println(chars[r]);
							StringBuilder sb = new StringBuilder();
							sb.append(chars);
							sb.deleteCharAt(pos);
							chars = sb.toString().toCharArray();
					i++; 
							//r++; 

							}
					}

					
						r++; 
					
					

					}
			}
			

			res = String.valueOf(chars);
			System.out.println(res);
			return res; 
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
public String RewriteFullMethod(String input) {
	

	StringBuilder buf = new StringBuilder();
	String params= input.substring(input.indexOf("("), input.indexOf(")")+1); 
	String methname= input.substring(0, input.indexOf("(") );
	int i=0; 

	while(i<params.length()-1) {

	if(((params.charAt(i)=='L'|| params.charAt(i)=='Z'||params.charAt(i)=='B'||params.charAt(i)=='I'||params.charAt(i)=='J'||params.charAt(i)=='S'||params.charAt(i)=='C')
	&& ((params.charAt(i+1)=='L'|| params.charAt(i+1)=='Z'||params.charAt(i+1)=='B'||params.charAt(i+1)=='I'||params.charAt(i+1)=='J'||params.charAt(i+1)=='S'||params.charAt(i+1)=='C')||
	params.charAt(i+1)==')') && params.charAt(i-1)!='.') ||

	((params.charAt(i)=='L'|| params.charAt(i)=='Z'||params.charAt(i)=='B'||params.charAt(i)=='I'||params.charAt(i)=='J'||params.charAt(i)=='S'||params.charAt(i)=='C')
	&& ((params.charAt(i+2)=='L'|| params.charAt(i+2)=='Z'||params.charAt(i+2)=='B'||params.charAt(i+2)=='I'||params.charAt(i+2)=='J'||params.charAt(i+2)=='S'||params.charAt(i+2)=='C')||
	params.charAt(i+1)==')') && params.charAt(i-1)!='.' ) ||

	(params.charAt(i)=='[' && params.charAt(i-1)==',')||
	(params.charAt(i)=='L'|| params.charAt(i)=='Z'||params.charAt(i)=='B'||params.charAt(i)=='I'||params.charAt(i)=='J'||params.charAt(i)=='S'||params.charAt(i)=='C')
	&& ((params.charAt(i-1)=='['))) {


	if(params.charAt(i+1)=='C') {
	String params1 = params.substring(0, i); 
	String params2 = params.substring(i+2, params.length()); 
	params=params1+",char,"+params2; 
	}	

	if(params.charAt(i+1)=='S') {
	String params1 = params.substring(0, i); 
	String params2 = params.substring(i+2, params.length()); 
	params=params1+",short,"+params2; 
	}
	if(params.charAt(i+1)=='V') {
	String params1 = params.substring(0, i+1); 
	String params2 = params.substring(i+2, params.length()); 
	params=params1+",void,"+params2; 
	}
	if(params.charAt(i+1)=='Z') {
	String params1 = params.substring(0, i+1); 
	String params2 = params.substring(i+2, params.length()); 
	params=params1+",boolean,"+params2; 
	}
	if(params.charAt(i+1)=='J') {
	String params1 = params.substring(0, i+1); 
	String params2 = params.substring(i+2, params.length()); 
	params=params1+",long,"+params2; 
	}
	if(params.charAt(i+1)=='B') {
	String params1 = params.substring(0, i+1); 
	String params2 = params.substring(i+2, params.length()); 
	params=params1+",byte,"+params2; 
	}


	if(params.charAt(i+1)=='I') {
	String params1 = params.substring(0, i+1); 
	String params2 = params.substring(i+2, params.length()); 
	params=params1+",int,"+params2; 
	}






	if(params.charAt(i)=='S') {
	if(i==1) {
	String params1 = params.substring(0, 1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+"short,"+params2; 
	}
	else {
	if(params.charAt(i-1)=='[') {

	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i-1, i); 
	String params3 = params.substring(i+2, params.length()); 
	params=params1+","+params2+"short,"+params3; 	
	}
	else {
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",short,"+params2; 	
	}

	}
	}
	if(params.charAt(i)=='C') {
	if(i==1) {
	String params1 = params.substring(0, 1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+"char,"+params2; 
	}

	else {
	if(params.charAt(i-1)=='[') {

	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i-1, i); 
	String params3 = params.substring(i+1, params.length()); 
	params=params1+","+params2+"char,"+params3; 	
	}
	else{
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",char,"+params2; 	
	}

	}
	}
	if(params.charAt(i)=='V') {
	if(i==1) {
	String params1 = params.substring(0, 1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+"void,"+params2; 
	}
	else {
	if(params.charAt(i-1)=='[') {

	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i-1, i); 
	String params3 = params.substring(i+2, params.length()); 
	params=params1+","+params2+"void,"+params3; 	
	}else{
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",void,"+params2; 	
	}

	}
	}
	if(params.charAt(i)=='Z') {
	if(i==1) {
	String params1 = params.substring(0, 1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+"boolean,"+params2; 
	}
	else{
	if(params.charAt(i-1)=='[') {

	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i-1, i); 
	String params3 = params.substring(i+2, params.length()); 
	params=params1+","+params2+"boolean,"+params3; 	
	}else{
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",boolean,"+params2; 	
	}
	}

	}
	if(params.charAt(i)=='J') {
	if(i==1) {
	String params1 = params.substring(0, 1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+"long,"+params2; 
	}
	else {
	if(params.charAt(i-1)=='[') {

	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i-1, i); 
	String params3 = params.substring(i+2, params.length()); 
	params=params1+","+params2+"long,"+params3; 	
	}else{
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",long,"+params2; 	
	}
	}

	}
	if(params.charAt(i)=='B') {
	if(i==1) {
	String params1 = params.substring(0, 1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+"byte,"+params2; 
	}
	else{
	if(params.charAt(i-1)=='[') {

	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i-1, i); 
	String params3 = params.substring(i+2, params.length()); 
	params=params1+","+params2+"byte"+params3; 	
	}else{
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",byte,"+params2; 	
	}
	}

	}
	if(params.charAt(i)=='I') {
	if(i==1) {
	String params1 = params.substring(0, 1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+"int,"+params2; 
	}
	else{
	if(params.charAt(i-1)=='[') {

	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i-1, i); 
	String params3 = params.substring(i+1, params.length()); 
	params=params1+","+params2+"int,"+params3; 	
	}else{
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",int,"+params2; 	
	}
	}
	}

	System.out.println(params.charAt(i)); 
	if(params.charAt(i-1)=='[') {
		if(i==1) {
		String params1 = params.substring(0, 1); 
		String params2 = params.substring(i+1, params.length()); 
		params=params1+","+params2; 
		}
		else{
		if(params.charAt(i-2)==',') {
			String[] parts = params.split(",");
			String AppendedParts=""; 
			for(String part: parts) {
				if(part.charAt(0)=='[') {
					part=part.substring(1, part.length()); 
					part=part+"[]"; 
				}
				AppendedParts=AppendedParts+part+","; 
				params=AppendedParts; 
			}
//		String params1 = params.substring(0, i-1); 
//		String params2 = params.substring(i-1, i); 
//		String params3 = params.substring(i+1, params.length()); 
//		params=params1+","+params2+","+params3; 	
		}else{
		String params1 = params.substring(0, i-1); 
		String params2 = params.substring(i+1, params.length()); 
		params=params1+",,"+params2; 	
		}
		}
		}


	if(params.charAt(i+1)==')' && params.charAt(i)=='I') {
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",int,"+params2; 
	}
	if(params.charAt(i+1)==')' && params.charAt(i)=='S') {
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",short,"+params2; 
	}
	if(params.charAt(i+1)==')' && params.charAt(i)=='J') {
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",long,"+params2; 
	}
	if(params.charAt(i+1)==')' && params.charAt(i)=='B') {
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",byte,"+params2; 
	}
	if(params.charAt(i+1)==')' && params.charAt(i)=='Z') {
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",boolean,"+params2; 
	}
	if(params.charAt(i+1)==')' && params.charAt(i)=='V') {
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",void,"+params2; 
	}
	if(params.charAt(i+1)==')' && params.charAt(i)=='C') {
	String params1 = params.substring(0, i-1); 
	String params2 = params.substring(i+1, params.length()); 
	params=params1+",char,"+params2; 
	}
	}
	i++; 
	}
	String res= methname+params; 

	//System.out.println(res);
	res=res.replaceAll("\\(,", "\\("); 
	res=res.replaceAll(",\\)", "\\)"); 
	res=res.replaceAll(",,", ","); 
	res=res.replaceAll(";", ","); 
	res=res.replaceAll(",,", ","); 
	res=res.replaceAll(",\\[,", ",\\["); 
	res=res.replaceAll(",\\)", "\\)"); 
	//res=res.replaceAll("Ljava", "java"); 
	//System.out.println("here  "+res);



	boolean flag=false; 
	char[] chars = res.toCharArray();
	int r=0; 
	int pos=10000000; 
	char temp='\0'; 
	while(r<chars.length) {
	if(chars[r]=='[' ) {
	pos=r; 
	// temp = chars[r+1]; 
	StringBuilder sb = new StringBuilder();
	sb.append(chars);
	sb.deleteCharAt(r);
	chars = sb.toString().toCharArray();
	flag=true; 
	}
	if(flag==true) {
	pos=r; 
	// temp = chars[r+1]; 
	if(chars[r]==',' ) {
	StringBuilder sb = new StringBuilder();
	sb.append(chars);
	sb.deleteCharAt(r);

	sb.insert(r, "[],");
	chars = sb.toString().toCharArray();
	flag=false; 	 
	}
	else if(chars[r]==')' ) {
	StringBuilder sb = new StringBuilder();
	sb.append(chars);
	sb.deleteCharAt(r);
	sb.insert(r, "[])");
	chars = sb.toString().toCharArray();
	flag=false; 	 
	}


	}

	r++; 
	}


	flag=false; 
	chars = res.toCharArray();
	r=0; 


	while(r<chars.length) {
	if(chars[r]=='$' ) {
	pos=r; 
	// temp = chars[r+1]; 
	StringBuilder sb = new StringBuilder();
	sb.append(chars);
	sb.deleteCharAt(r);
	chars = sb.toString().toCharArray();
	flag=true; 
	}
	while(chars[r]!='.'&& chars[r]!='('&& chars[r]!=')'&& flag==true) {
	System.out.println(chars[r]);
	StringBuilder sb = new StringBuilder();
	sb.append(chars);
	sb.deleteCharAt(r);
	chars = sb.toString().toCharArray();

	//r++; 

	}
	flag=false; 

	r++; 

	}
	res = String.valueOf(chars);
	System.out.println("final res : "+res);
	return res;
	

	
	
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

public String KeepOnlyMethodName(String constructor) {
	String params= constructor.substring(constructor.indexOf("("), constructor.length()); 
	constructor=constructor.substring(0, constructor.indexOf("(")); 
	constructor=constructor.substring(constructor.lastIndexOf(".")+1, constructor.length()); 
	constructor=constructor+params; 

return constructor; 
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

public String TransformConstructorIntoInit(String constructor) {
	String params= constructor.substring(constructor.indexOf("("), constructor.length()); 
	constructor= constructor.substring(0, constructor.indexOf("(")); 
	
	//String part2= constructor.substring(constructor.indexOf("("), constructor.length()); 
	constructor=constructor+".-init-"+params; 
	
	return constructor; 
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

public String RemoveDollarConstructor(String text) {

	
	
	String res=""; 
	
	boolean  flag=false; 
	char[] chars = text.toCharArray();
	 int r = 0; 
	 int pos = text.indexOf("$"); 
	//System.out.println("HERE IS THE TEXT "+text);
	 int count = StringUtils.countMatches("text", "$");
if(count==1) {
	if(text.contains("$")) {
		if(chars.length-pos>7 && chars[pos+2]!='(') {
			
		
		while(r<chars.length ) {
			if(chars[r]=='$' ) {
				// pos = r; 
				// temp = chars[r+1]; 
				StringBuilder sb = new StringBuilder();
				sb.append(chars);
				sb.deleteCharAt(r);
				chars = sb.toString().toCharArray();
				flag=true; 
				 pos--; 
				
				
			}
			
			 while( flag==true ) {
				 if(chars[pos]!='.'&& chars[pos]!='('&& chars[pos]!=')') {
					 r--; 
					 pos--; 
				//	 System.out.println(chars[r]);
					 StringBuilder sb = new StringBuilder();
					 sb.append(chars);
					
					 sb.deleteCharAt(r);
				//	 System.out.println(sb);
					 chars = sb.toString().toCharArray();
					 int length=chars.length; 
//					 if(r==length) {
//						 flag=false; 
//					 }
					
					
				 }
				 else {
					 flag=false; 
				 }
				
				 //r++; 
				 
			 }
			flag=false; 
			 if(flag==true) {
				 r--; 
			 }else {
				 r++;  
			 }
			 
			
		}
		 res = String.valueOf(chars);
	}else {
		String part2=""; 
		String part1=""; 
		 res = String.valueOf(chars);
		  part1=res.substring(0,res.indexOf("$")); 
		 if(res.contains("(")) {
			  part2=res.substring(res.indexOf("("),res.length()); 
		 }
		res=part1+part2; 
	}
		
	}

	
}else if(count==2) {
	String methodname=text.substring(0, text.indexOf("(")); 
	String parameters=text.substring(text.indexOf("("), text.length()); 
	
	methodname=methodname.substring(0, methodname.lastIndexOf("$")); 
	String part1meth=methodname.substring(0, methodname.lastIndexOf(".")); 
	String part2meth=methodname.substring(methodname.indexOf("$"), methodname.length()); 
	res=part1meth+part2meth+parameters; 
}
	//System.out.println("RES====>"+res);
else {
	res=text; 
}

	
	return res; 


}



	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public String RemoveDollar(String text) {	
		
		boolean  flag=false; 
	char[] chars = text.toCharArray();
	 int r = 0; 
	 int pos = text.indexOf("$"); 
//	System.out.println("HERE IS THE TEXT "+text);
	if(text.contains("$")) {
		while(r<chars.length) {
			if(chars[r]=='$' ) {
				// pos = r; 
				// temp = chars[r+1]; 
				StringBuilder sb = new StringBuilder();
				sb.append(chars);
				sb.deleteCharAt(r);
				chars = sb.toString().toCharArray();
				flag=true; 
				 pos--; 
				
				
			}
			
			 while( flag==true ) {
				 if(chars[pos]!='.'&& chars[pos]!='('&& chars[pos]!=')') {
					 r--; 
					 pos--; 
				//	 System.out.println(chars[r]);
					 StringBuilder sb = new StringBuilder();
					 sb.append(chars);
					
					 sb.deleteCharAt(r);
				//	 System.out.println(sb);
					 chars = sb.toString().toCharArray();
					 int length=chars.length; 
//					 if(r==length) {
//						 flag=false; 
//					 }
					
					
				 }
				 else {
					 flag=false; 
				 }
				
				 //r++; 
				 
			 }
			flag=false; 
			 if(flag==true) {
				 r--; 
			 }else {
				 r++;  
			 }
			 
			
		}
		
	}
	String res = String.valueOf(chars);
//	System.out.println("RES====>"+res);
	return res; 
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public String ParseLine(String line) {
		System.out.println(line);
		String[] linesplitted = line.split(","); 
		String method = linesplitted[1]; 
		String requirement = linesplitted[2]; 
		String gold = linesplitted[4]; 
		String subject = linesplitted[5]; 
		System.out.println("HERE IS THIS SHORT METHOD========>"+ method); 
		
		String shortmethod=method.substring(0, method.indexOf("("));
		String regex = "(.)*(\\d)(.)*";      
		Pattern pattern = Pattern.compile(regex);
		boolean containsNumber = pattern.matcher(shortmethod).matches();
		String[] firstpart;
		String FinalMethod = null;
		shortmethod=shortmethod.replaceAll("clinit", "init"); 
		if(shortmethod.contains("$") && shortmethod.matches(".*\\d+.*")) {
			 firstpart = shortmethod.split("\\$");
			String myfirstpart= firstpart[0]; 
			FinalMethod=myfirstpart; 
			if(StringUtils.isNumeric(firstpart[1])==false) {
				String[] secondpart = firstpart[1].split("\\d"); 
				System.out.println("my first part "+ myfirstpart+ "firstpart"+ firstpart[1]);
				
				String mysecondpart=secondpart[1]; 
				
				 FinalMethod=myfirstpart+mysecondpart; 
				System.out.println("FINAL RESULT:    "+FinalMethod);
			}
			
		}
		
		else if(shortmethod.contains("$") && containsNumber==false) {
			 firstpart = shortmethod.split("\\$");
			
			System.out.println("FINAL STRING:   "+firstpart[0]);
			firstpart[1]=firstpart[1].substring(firstpart[1].indexOf("."), firstpart[1].length()); 
			System.out.println("FINAL STRING:   "+firstpart[1]);
			 FinalMethod= firstpart[0]+firstpart[1]; 
			System.out.println("FINAL STRING:   "+FinalMethod);
		}
		else {
			FinalMethod=shortmethod; 
		}
		return FinalMethod; 
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public String transformstring(String s) {
		s=s.replace("/", "."); 
		s=s.replace(";", ","); 
		  int endIndex = s.lastIndexOf(",");
		    if (endIndex != -1)  
		    {
		    	s = s.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
		    }
		s=s.replace("Lde", "de"); 
		s=s.replace("Ljava", "java"); 
		return s; 
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public String[] ExtractParams(String method) {
		String Paramlist=method.substring(method.indexOf("(")+1, method.indexOf(")")); 
		 String[] data = Paramlist.split(",");
		 return data; 
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
public String ExtractParams2(String method) {
String Paramlist=method.substring(method.indexOf("(")+1, method.indexOf(")")); 

return Paramlist; 
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
private CountTNE ComputeProportions(List<String> list, int countT, int countN, int countE) {
	// TODO Auto-generated method stub
	if(list!=null) {
		for(String s: list) {
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
	}
	
	CountTNE count= new CountTNE(); 
	count.setCountT(countT);
	count.setCountN(countN);
	count.setCountE(countE);
	return count; 

}

private String ComputeSubjectGeneralization(CountTNE count) {
	// TODO Auto-generated method stub
	String SubjectGold=""; 
	  if(count.CountT>0) {		
			SubjectGold="T"; 

     } 
//    else  if(charac.trim().equals("N")) {

    else  if(count.CountN>0 && count.CountT==0 && count.CountE>=0) {
    	SubjectGold="N"; 
    		
    		



    			     }
     else {
    	 	SubjectGold="E"; 
    	 
     }
	  return SubjectGold; 
}




///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	

}
