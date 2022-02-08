package databaseReading;
import Chess.SubjectTSubjectNObject;

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

import org.apache.commons.io.serialization.ClassNameMatcher;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.pattern.FullLocationPatternConverter;

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
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;
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
public class DBiTrustFixed {

	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "root";


	

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
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_databaseitrust"+"?useLegacyDatetimeCode=false&serverTimezone=UTC","root","123456");

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
			Spoon(); 
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}


		
		
		
	}
	
	/**
	 * Connect to the DB and do some stuff
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		DBiTrustFixed app = new DBiTrustFixed();
		app.run();
	}
	
	public void Spoon() throws SQLException, IOException {
		DBiTrustFixed dao = new DBiTrustFixed();
	Connection conn=getConnection();
	Statement st= conn.createStatement();
	
	Statement st2= conn.createStatement();
	Statement st3= conn.createStatement();
	Statement st4= conn.createStatement();
	Statement st5= conn.createStatement();
	
		SpoonAPI spoon = new Launcher();
    	spoon.addInputResource("C:\\Users\\mouna\\ownCloud\\Mouna Hammoudi\\dumps\\SOURCE_CODE\\iTrust");
    	spoon.getEnvironment().setAutoImports(true);
    	spoon.getEnvironment().setNoClasspath(true);
    	CtModel model = spoon.buildModel();
    	//List<String> classnames= new ArrayList<String>(); 
  
    	// Interact with model
    	Factory factory = spoon.getFactory();
    	ClassFactory classFactory = factory.Class();
    	MethodFactory methodFactory = factory.Method(); 
    	InterfaceFactory interfaceFactory = factory.Interface(); 
    	int i=1; 
        /*********************************************************************************************************************************************************************************/	
        /*********************************************************************************************************************************************************************************/	
        /*********************************************************************************************************************************************************************************/	  	
    
    	

		
		
    	
    	
    	
        	
            /*********************************************************************************************************************************************************************************/	
            /*********************************************************************************************************************************************************************************/	
            /*********************************************************************************************************************************************************************************/	  	
        
        	
        	
        	
        	
      //  	BUILD CLASSES TABLE 
        	for(CtType<?> clazz : classFactory.getAll()) {
        		
        	
        		
    			Set<CtType<?>> nested = clazz.getNestedTypes();
    			
    				for(CtType<?> mynested: nested) {
    					System.out.println(mynested.getQualifiedName());
    					String line = mynested.getQualifiedName(); 
    					String[] lineData = line.split("\\$");
    					st.executeUpdate("INSERT INTO `classes`(`package`,`name`) VALUES ('"+lineData[0]+"','"+lineData[1]+"');");
    					
    					
    					
    					Set<CtType<?>> nested2 = mynested.getNestedTypes();
    					for(CtType<?> mynested2: nested2) {
    						String line2 = mynested2.getQualifiedName(); 
        					String[] lineData2 = line2.split("\\$");
        					st.executeUpdate("INSERT INTO `classes`(`package`,`name`) VALUES ('"+lineData2[0]+"','"+lineData2[1]+"');");
    						
    						
    						}
    					}
    				
    			
    		
    			String FullClassName= clazz.getPackage()+"."+clazz.getSimpleName(); 
    			System.out.println(FullClassName);
    			st.executeUpdate("INSERT INTO `classes`(`package`,`name`) VALUES ('"+clazz.getPackage()+"','"+clazz.getSimpleName()+"');");
    				
       		
        		
        				
        	
       
        		

        	}
//        	
//        	
//        	
//        	 
////////        	/*********************************************************************************************************************************************************************************/	
////////            /*********************************************************************************************************************************************************************************/	
////////            /*********************************************************************************************************************************************************************************/
        //	BUILD SUPERCLASSES TABLE 
//        	for(CtType<?> clazz : classFactory.getAll(true)) {
//        		
//        		String childFullClassName= clazz.getQualifiedName(); 
//    
//    	if(clazz.getSuperclass()!=null  ) {
//    		
//        			String superclass= clazz.getSuperclass().toString();
//        		i++; 
//        		String superclassPackageName =null; 
//        		String superclassClassName=null; 
//        		if(superclass.toString().contains(".")) {
//        			int lastIndxDot = superclass.lastIndexOf(".");
//            		 superclassPackageName = superclass.substring(0, lastIndxDot);
//            		 superclassClassName = superclass.substring(lastIndxDot+1, superclass.length());
//        		}
//        		
//        		String superclassID=""; 
//        		String superclassName=""; 
//        					ResultSet superclasses = st.executeQuery("SELECT * from classes where name='"+superclassClassName+"'"
//                  				+ "and package='"+superclassPackageName+"'");  
//        					while(superclasses.next()){
//        						 superclassID= superclasses.getString("id"); 
//        						 superclassName= superclasses.getString("name"); 
//
//        			   		   }
//
//        						
//        		String childclassID=""; 
//        		String childclassName=""; 
//        		String childPackageName=""; 
//        		if(childFullClassName.toString().contains(".")) {
//        			int lastIndxDot = childFullClassName.lastIndexOf(".");
//            		 childPackageName = childFullClassName.substring(0, lastIndxDot);
//            		 childclassName = childFullClassName.substring(lastIndxDot+1, childFullClassName.length());
//        		}
//        		
//        					ResultSet childClasses = st.executeQuery("SELECT * from classes where name='"+childclassName+"'"
//        							+"and package='"+childPackageName+"'"); 
//        					while(childClasses.next()){
//        						childclassID= childClasses.getString("id"); 
//        						childclassName= childClasses.getString("name"); 
//
//        			   		   }
//        					
//        			if(!superclassID.equals("") && !childclassID.equals("") && childclassID!=null)
//        			st.executeUpdate("INSERT INTO `superclasses`(`superclassid`, `superclassname`, `ownerclassid`, `ownerclassname`) VALUES"
//        					+ " ('"+superclassID +"','" +superclassName+"','" +childclassID+"','" +childclassName+"')");
//        			
//        		
//        		
//        		
//            		
//        		}
//        	}
//////////////////        	/*********************************************************************************************************************************************************************************/	
//////////////////            /*********************************************************************************************************************************************************************************/	
//////////////////            /*********************************************************************************************************************************************************************************/	
////////////////        	  	
////////////         	//BUILD INTERFACES TABLE 
//        	 
//
//        	List<String> mylist2 = new ArrayList<String>(); 
//        	for(CtType clazz : classFactory.getAll(true)) {
//        		
//        		if(clazz instanceof CtClass) {
//        			String myinterfaceclassid = null;
//            		String myinterfacename = null;
//            		String myclassid = null;
//            		String myclassname = null;
//            		
//        			String FullClassName= clazz.getQualifiedName(); 
//        			Set<CtTypeReference<?>> interfaces = clazz.getSuperInterfaces(); 
//
//        			for(CtTypeReference<?> inter: interfaces) {
//        			
//        					String interPackageName=null; 
//        					String interClassName=null; 
//        				if(inter.toString().contains(".")) {
//                			int lastIndxDot = inter.toString().lastIndexOf(".");
//                    		 interPackageName = inter.toString().substring(0, lastIndxDot);
//                    		 interClassName = inter.toString().substring(lastIndxDot+1, inter.toString().length());
//                		}
//        		
//        					
//        				ResultSet interfacesnames = st.executeQuery("SELECT * from classes where name='"+interClassName+"'"
//        						+ "and package='"+interPackageName+"'");  
//    					while(interfacesnames.next()){
//    						myinterfacename= interfacesnames.getString("name"); 
//    						myinterfaceclassid= interfacesnames.getString("id"); 
//
//    			   		   }
//    					if(myinterfaceclassid==null) {
//    						String interclassname = inter.toString().replaceAll("\\.(?!.*\\.)","\\$");
//    						 interfacesnames = st.executeQuery("SELECT * from classes where name='"+interClassName+"'"
//    								+ "and package='"+interPackageName+"'");  
//        					while(interfacesnames.next()){
//        						myinterfacename= interfacesnames.getString("classname"); 
//        						myinterfaceclassid= interfacesnames.getString("id"); 
//
//        			   		   }
//    					}
//    					
//    					
//    					String impPackageName=null; 
//    					String impClassName=null; 
//    					if(FullClassName.toString().contains(".")) {
//                			int lastIndxDot = FullClassName.toString().lastIndexOf(".");
//                    		 impPackageName = FullClassName.toString().substring(0, lastIndxDot);
//                    		 impClassName = FullClassName.toString().substring(lastIndxDot+1, FullClassName.toString().length());
//                		}
//        		
//    					
//    					ResultSet classesnames= st.executeQuery("SELECT * from classes where name='"+impClassName+"'"
//								+ "and package='"+impPackageName+"'");  
//    					while(classesnames.next()){
//    						myclassname= classesnames.getString("name"); 
//    						myclassid= classesnames.getString("id"); 
//
//    			   		   }
//        					String interface1= myinterfaceclassid+ myinterfacename;  
//        					String implementation1= myclassid+ myclassname; 
//        					
//        						System.out.println("INTERRRR "+inter.getQualifiedName());
//        						System.out.println("CLAZZZZ "+clazz.getQualifiedName());
//        					
//        		
//        		
//    					
//    					
//    					if(myinterfaceclassid!=null && !mylist2.contains(interface1+implementation1) && myclassid!=null) {
//    		    			st.executeUpdate("INSERT INTO `interfaces`(`interfaceid`,`interfacename`,`implementationid`, `implementationname`) VALUES ('"+myinterfaceclassid +"','" +myinterfacename+"','" +myclassid+"','" +myclassname+"')");
//    		    			mylist2.add(interface1+implementation1); 
//    					}
//        			}
//
//    					
//    				
//    					
//    					
//    					
//    			
//    				
//    				
//    				
//    			}
//    			
//        		
//           	List<String> mylist = new ArrayList<String>(); 
//
//         		if(clazz instanceof CtInterface) {
//        			String myinterfaceclassid = null;
//            		String myinterfacename = null;
//            		String myclassid = null;
//            		String myclassname = null;
//            		
//        			String FullClassName= clazz.getQualifiedName(); 
//        			Set<CtTypeReference<?>> interfaces = clazz.getSuperInterfaces(); 
//
//        			for(CtTypeReference<?> inter: interfaces) {
//        			String interPackageName =""; 
//        			String interClassName=""; 
//        				if(inter.toString().contains(".")) {
//                			int lastIndxDot = inter.toString().lastIndexOf(".");
//                    		 interPackageName = inter.toString().substring(0, lastIndxDot);
//                    		 interClassName = inter.toString().substring(lastIndxDot+1, inter.toString().length());
//                		}
//        				
//        				ResultSet interfacesnames = st.executeQuery("SELECT * from classes where name='"+interClassName+
//								 "'and package='"+interPackageName+"'");  
//    					while(interfacesnames.next()){
//    						myinterfacename= interfacesnames.getString("name"); 
//    						myinterfaceclassid= interfacesnames.getString("id"); 
//
//    			   		   }
//    					
//    					String FullclassNamePackageName=""; 
//    					String fullclassname=""; 
//    					if(FullClassName.toString().contains(".")) {
//                			int lastIndxDot = FullClassName.toString().lastIndexOf(".");
//                    		 FullclassNamePackageName = FullClassName.toString().substring(0, lastIndxDot);
//                    		 fullclassname = FullClassName.toString().substring(lastIndxDot+1, FullClassName.toString().length());
//                		}
//    					ResultSet classesnames= st.executeQuery("SELECT * from classes where name='"+fullclassname+
//    							 "'and package='"+FullclassNamePackageName+"'");  
//    					while(classesnames.next()){
//    						myclassname= classesnames.getString("name"); 
//    						myclassid= classesnames.getString("id"); 
//
//    			   		   }
//    					
//    					
//    					String interface1= myinterfaceclassid+ myinterfacename;  
//    					String implementation1= myclassid+ myclassname; 
//        				
//        		
//        					
//        				
//        					
//        					
//        						System.out.println("INTERRRR2 "+inter.getQualifiedName());
//        						System.out.println("CLAZZZZ2 "+clazz.getQualifiedName());
//        					
//        		
//        		
//    					
//    					
//    					if(myinterfaceclassid!=null && !mylist.contains(interface1+implementation1) ) {
//    		    			st.executeUpdate("INSERT INTO `superclasses`(`superclassid`, `superclassname`, `ownerclassid`, `ownerclassname`) "
//    		    					+ "VALUES ('"+myinterfaceclassid +"','" +myinterfacename+"','" +myclassid+"','" +myclassname+"')");
//
//    		    			mylist.add(interface1+implementation1); 
//    					}
//        			}
//
//    					
//    				
//    					
//    					
//    					
//    			
//    				
//    				
//    				
//    			}
//        		
//
//        	}
//
////
////
//////////////////////////        	
//////    ////////////////////    
//////////////////////////        	
////////////////////////////        	/*********************************************************************************************************************************************************************************/	
////////////////////////////            /*********************************************************************************************************************************************************************************/	
////////////////////////////            /*********************************************************************************************************************************************************************************/	  	
////////////////////////////        	//BUILD METHODS TABLE 
//        	List<String> mymethodlist = new ArrayList<>(); 
//        	for(CtType<?> clazz : classFactory.getAll(true)) {
//        		
//        	
//        		String myclassid = null;
//        		String myclassname = null;
//        		
//        		//ALTERNATIVE: Collection<CtMethod<?>> methods = clazz.getAllMethods(); 
//    			Collection<CtMethod<?>> methods = clazz.getMethods(); 
//    			String FullClassName= clazz.getQualifiedName(); 
//    			
//    			System.out.println("count:   "+" "+FullClassName);
//    			//NEEDS TO BE CHANGED 
//    		//	if(count==2) {
//    			 List<CtConstructor> MyContructorlist = clazz.getElements(new TypeFilter<>(CtConstructor.class)); 
//    			 for(CtConstructor<?> constructor: MyContructorlist) {
//    				 String constructorString =""; 
//    				 
//            		 
//            		 
//            		 
//    					String FullConstructorName=constructor.getSignature().toString(); 
//    					
//    					String methodabbreviation=FullConstructorName.substring(0, FullConstructorName.indexOf("(")); 
//    					 methodabbreviation=FullClassName+".-init-"; 
//
//
//    					//st.executeUpdate("INSERT INTO `fields`(`fieldname`) VALUES ('"+field+"');");
//    					//24 is the size of the string "net.sourceforge.ganttproject.javaGantt."
//    					int packagesize= "de.java_gantt.javaGantt.".length(); 
//    						FullConstructorName=FullConstructorName.substring(packagesize, FullConstructorName.length()); 
//    						FullConstructorName="-init-"+FullConstructorName.substring(FullConstructorName.lastIndexOf('('));  
//    						
//    						System.out.println(FullClassName);
//    						String PackageName=""; 
//    						String ClassName=""; 
//    						if(FullClassName.toString().contains(".")) {
//                    			int lastIndxDot = FullClassName.toString().lastIndexOf(".");
//                        		 PackageName = FullClassName.toString().substring(0, lastIndxDot);
//                        		 ClassName = FullClassName.toString().substring(lastIndxDot+1, FullClassName.toString().length());
//                    		}
//    						
//    						if(ClassName.toString().contains("$")) {
//    							String myclassnameBeforeDollar = ClassName.substring(0, ClassName.indexOf("$")); 
//    							String myclassnameAfterDollar = ClassName.substring(ClassName.indexOf("$")+1, ClassName.length()); 
//    							PackageName=PackageName+"."+myclassnameBeforeDollar; 
//    							ClassName=myclassnameAfterDollar; 
//
//    						}
//    						ResultSet classesreferenced = st.executeQuery("SELECT * from classes where name='"+ClassName+
//    								 "'and package='"+PackageName+"'");  
//    						while(classesreferenced.next()){
//    							myclassid= classesreferenced.getString("id"); 
//    							myclassname= classesreferenced.getString("name"); 
//
//    				   		   }
//    						
//    						
//    					
//    							String FullMethodNameRefined=FullConstructorName.substring(0, FullConstructorName.indexOf("(")); 
//    							//String FullMethodName=constructor.getSignature().toString(); 
//    							String fullmeth= myclassname+"."+FullConstructorName; 
//    							System.out.println(FullClassName);
//    							String mymethodo = fullmeth+"-"+ myclassid+"-"+ myclassname; 
//    							if(!mymethodlist.contains(mymethodo) ) {
//    								
//    								
////    								System.out.println(myclassname);
//    								String packageName= "de.java_gantt.javaGantt."; 
//    									constructorString=constructorString.replaceAll("'", ""); 
//    									String newcons=""; 
//    									try {
//        									 newcons=constructor.toString().replaceAll("'", ""); 
//
//    									}catch(Exception e ) {
//    										
//    									}
//    									String fullmethod =""; 
//    									if(fullmeth.contains("GameStatus")) {
//        									 fullmethod =PackageName+"$"+fullmeth; 
//
//    									}else {
//        									 fullmethod =PackageName+"."+fullmeth; 
//
//    									}
//    					    			st.executeUpdate("INSERT INTO `methods`(`name`,  `fullmethod`,`ownerclassid`, `ownerclassname`, `sourcecode`) VALUES ('"+FullConstructorName+"','" +fullmethod+"','" +myclassid+"','" +myclassname+"','" +newcons+"')");
//
//    									
//    					    			mymethodlist.add(mymethodo); 
//    								}
//    						
////    							 List<CtMethod> MyMethodsInsideCons = constructor.getElements(new TypeFilter<>(CtMethod.class)); 
////    							 for(CtMethod mymethodInsideCons: MyMethodsInsideCons) {
////    								
////    								 String FullMethodName=mymethodInsideCons.getSignature().toString(); 
////    									System.out.println("==============>"+mymethodInsideCons.getShortRepresentation().toString());
////    									//st.executeUpdate("INSERT INTO `fields`(`fieldname`) VALUES ('"+field+"');");
////    								//	System.out.println(FullClassName);
////    									String FullMethodNameRefinedInsideCons=FullMethodName.substring(0, FullMethodName.indexOf("(")); 
////    									String longmethInsideCons= clazz.getQualifiedName()+"."+FullMethodName; 
////    									String methodabbreviationInsideCons=longmethInsideCons.substring(0, longmethInsideCons.indexOf("(")); 
////    										ResultSet classesreferenced2 = st.executeQuery("SELECT * from classes where classname='"+FullClassName+"'"); 
////    										while(classesreferenced2.next()){
////    											myclassid= classesreferenced2.getString("id");
////    											myclassname= classesreferenced2.getString("classname"); 
////
////    									//		System.out.println("class referenced: "+clazz);
////    								   		   }
////    										
////    									
////    									
////    											String fullmeth2= myclassname+"."+FullMethodName; 
////    											System.out.println(FullClassName);
////    								 
////    								 
////    					    			st.executeUpdate("INSERT INTO `methods`(`methodname`, `methodnamerefined`, `methodabbreviation`, `fullmethod`,`classid`, `classname`) VALUES ('"+FullMethodName+"','" +FullMethodNameRefinedInsideCons +"','" +methodabbreviationInsideCons+"','" +fullmeth2+"','" +myclassid+"','" +myclassname+"')");
////
////    							 }
//
//    						}
//    			 
//    			 
//    			 
//    			for(CtMethod<?> method: methods) {
//    				 
//    				String methodString=""; 
//    				try {
//    					 methodString = method.toString(); 
//    					methodString=methodString.replaceAll("'", ""); 
////    					System.out.println(methodString);
//    				}catch(Exception e) {
//    					System.out.println("---------"+methodString);
//    				}
//    				String FullMethodName=method.getSignature().toString(); 
//    				System.out.println("==============>"+method.getShortRepresentation().toString());
//    				//st.executeUpdate("INSERT INTO `fields`(`fieldname`) VALUES ('"+field+"');");
//    			//	System.out.println(FullClassName);
//    				String FullMethodNameRefined=FullMethodName.substring(0, FullMethodName.indexOf("(")); 
//    				String longmeth= clazz.getQualifiedName()+"."+FullMethodName; 
//    				String methodabbreviation=longmeth.substring(0, longmeth.indexOf("(")); 
//    				
//    				String PackageName=""; 
//    				String ClassName=""; 
//    				if(FullClassName.toString().contains(".")) {
//            			int lastIndxDot = FullClassName.toString().lastIndexOf(".");
//                		 PackageName = FullClassName.toString().substring(0, lastIndxDot);
//                		 ClassName = FullClassName.toString().substring(lastIndxDot+1, FullClassName.toString().length());
//            		}
//    					ResultSet classesreferenced = st.executeQuery("SELECT * from classes where name='"+ClassName+
//								 "'and package='"+PackageName+"'");  
//
//    					while(classesreferenced.next()){
//    						myclassid= classesreferenced.getString("id"); 
//    						myclassname= classesreferenced.getString("name"); 
//    			   		   }
//    					
//    					
//    				
//    				
//    						String fullmeth= myclassname+"."+FullMethodName; 
//    						System.out.println(FullClassName);
//    						String meth= FullMethodName+"-"+myclassid+"-"+myclassname; 
//    						if(mymethodlist.contains(meth)==false ) {
//    							
//    							st.executeUpdate("INSERT INTO `methods`(`name`,   `fullmethod`,`ownerclassid`, `ownerclassname`, `sourcecode`) VALUES ('"+FullMethodName +"','" +longmeth+"','" +myclassid+"','" +myclassname+"','" +methodString+"')");
//
//    							
//    			    			mymethodlist.add(meth); 
//    						}
//    						
//    						
//       	
//    					}
//
//    					
//    				
//    				
//    			//}
//    			
//    			
//    		
//    			
//    		
//    			
//    			
//    			
//        	}
//        	
//        	
//        	
//        	for(CtType<?> myinterface : interfaceFactory.getAll(true)) {
//        		Collection<CtMethod<?>> methods = myinterface.getMethods(); 
//
//        		for(CtMethod<?> method: methods) {
//    				 
//        			String myinterfaceid=null; 
//        			String myinterfacename=null; 
//    				String FullMethodName=method.getSignature().toString(); 
//    				System.out.println("==============>"+method.getShortRepresentation().toString());
//    				//st.executeUpdate("INSERT INTO `fields`(`fieldname`) VALUES ('"+field+"');");
//    			//	System.out.println(FullClassName);
//    				String FullMethodNameRefined=FullMethodName.substring(0, FullMethodName.indexOf("(")); 
//    				String longmeth= myinterface.getQualifiedName()+"."+FullMethodName; 
//    				String methodabbreviation=longmeth.substring(0, longmeth.indexOf("(")); 
//    				String inter=myinterface.getQualifiedName(); 
//    				
//    				String interClassName=""; 
//    				String interPackageName=""; 
//    				if(inter.toString().contains(".")) {
//            			int lastIndxDot = inter.toString().lastIndexOf(".");
//                		 interPackageName = inter.toString().substring(0, lastIndxDot);
//                		 interClassName = inter.toString().substring(lastIndxDot+1, inter.toString().length());
//            		}
//    				
//    					ResultSet classesreferenced = st.executeQuery("SELECT classes.* from classes where name='"+interClassName+
//								 "'and package='"+interPackageName+"'");  
//    					System.out.println("INTER"+myinterface.getQualifiedName());
//    					while(classesreferenced.next()){
//    						myinterfaceid= classesreferenced.getString("id"); 
//    						myinterfacename= classesreferenced.getString("name"); 
//    				//		System.out.println("class referenced: "+clazz);
//    			   		   }
//    				
//    					
//    				
//    				
//    						String fullmeth= myinterfacename+"."+FullMethodName; 
//    						System.out.println(fullmeth);
//    						String mymethod=FullMethodName+"-"+ myinterfaceid+"-"+ myinterfacename; 
//    						if(!mymethodlist.contains(mymethod) ) {
//    							String methodString = method.toString(); 
//    							methodString=methodString.replaceAll("'", ""); 
//    			    			st.executeUpdate("INSERT INTO `methods`(`name`, `fullmethod`,`ownerclassid`, `ownerclassname`, `sourcecode`) VALUES ('"+FullMethodName +"','" +longmeth+"','" +myinterfaceid+"','" +myinterfacename+"','" +methodString+"')");
//
//    							
//    			    			mymethodlist.add(mymethod); 
//    						}
//    						
//    						
//       	
//    					}
//    			
//    		
//        	}
//        	
////        	
////        	
////    ///////////////////*********************************************************************************************************************************************************************************/	
////    ///////////////////*********************************************************************************************************************************************************************************/	
////    ///////////////////*********************************************************************************************************************************************************************************/   	
////    ////////////////////BUILD METHODSCALLED TABLE
////
////
////
////
////
//            int counter=0; 
//            //METHODS CALLING METHODS 
//            List<String> methodcallsList=new ArrayList<String>(); 
//            String calleeDeclaringTypeName=null; 
//
//            for(CtType<?> clazz : classFactory.getAll(true)) {
//            List<CtMethod> callers = clazz.getElements(new TypeFilter<CtMethod>(CtMethod.class));
//             for(CtMethod caller: callers) {
//            	 String fullcaller= clazz.getQualifiedName()+"."+caller.getSignature(); 
////            	 System.out.println("========"+fullcaller);
//
//            	 
//            	 ResultSet methods = st.executeQuery("SELECT methods.* from methods where fullmethod='"+fullcaller+"'");  
//            	 String callerID=null;
//            	 String callerownerclassID=null; 
//            	 String callerownerclassName=null; 
//            	 String callermethodName=null; 
//					while(methods.next()){
//						
//						 callerID=methods.getString("id"); 
//						 callerownerclassID= methods.getString("ownerclassid"); 
//						 callerownerclassName= methods.getString("ownerclassname"); 
//						 callermethodName= methods.getString("name"); 
//
//						
//					
//					}
//            	 
//            	 
//                 List<CtInvocation> callees = caller.getElements(new TypeFilter<CtInvocation>(CtInvocation.class));
//                 for(CtInvocation callee: callees) {
//                	 
//                		 String fullcallee = callee.getExecutable().getDeclaringType()+"."+callee.getExecutable();
////                    	 System.out.println("----"+fullcallee);
//
//                		 ResultSet methodsreferenced = st.executeQuery("SELECT methods.* from methods where fullmethod='"+fullcallee+"'");  
//                		 String calleeID=null; 
//                		 String calleeownerclassID=null; 
//                		 String calleeownerclassName=null; 
//                		 String calleemethodName=null; 
//    					try {
//    						
//    					}catch(Exception e ) {
//    						 if(methodsreferenced.next()){
//    	    						
//        						 calleeID=methodsreferenced.getString("id"); 
//        						 calleeownerclassID= methodsreferenced.getString("ownerclassid"); 
//        						 calleeownerclassName= methodsreferenced.getString("ownerclassname"); 
//        						 calleemethodName= methodsreferenced.getString("name"); 
//
//        						
//        					
//        					}else if(callee.toString().contains("super")){
//    							String mySuperCallee= callee.getExecutable().getSignature(); 
//    							String mysupercalleewithoutparentheses = mySuperCallee.substring(0, mySuperCallee.indexOf("(")); 
//    							 String params = mySuperCallee.substring( mySuperCallee.indexOf("("), mySuperCallee.length()); 
//
//    							String changedName = mysupercalleewithoutparentheses+".-init-"+params; 
////    							System.out.println(changedName);
////    							System.out.println("yes");
//    							
//    							  ResultSet resultset = st2.executeQuery("SELECT methods.* from methods where fullmethod='"+changedName+"'");  
//    			            	  calleeID=null;
//    			            	  calleeownerclassID=null; 
//    			            	  calleeownerclassName=null; 
//    			            	  calleemethodName=null; 
//    								if(resultset.next()){
//    									
//    									 calleeID=resultset.getString("id"); 
//    									 calleeownerclassID= resultset.getString("ownerclassid"); 
//    									 calleeownerclassName= resultset.getString("ownerclassname"); 
//    									 calleemethodName= resultset.getString("name"); 
//
//    									
//    								
//    								}
//    							
//    						}
//        					
//    					}
//                		
//    					if(callerID!=null && calleeID!=null && !methodcallsList.contains(callerID+"-"+calleeID) && callerownerclassName!=null && calleeownerclassName!=null) {
//    						st.executeUpdate("INSERT INTO `new_databasegantt`.`methodcalls` (`callermethodid`, `callermethodname`, `callerclassname`, `callerclassid`, `fullcaller`, `calleemethodid`, `calleemethodname`, `calleeclassname`, `calleeclassid`, `fullcallee`) VALUES ('"+callerID+"','"+callermethodName+"','"+callerownerclassName+"','"+callerownerclassID+"','"+fullcaller+"','"+
//    		    					calleeID+"','"+calleemethodName+"','"+calleeownerclassName+"','"+calleeownerclassID+"','"+fullcallee+"')"); 
//    						methodcallsList.add(callerID+"-"+calleeID); 
//    					}
//		    			//METHODS CALLING CONSTRUCTORS 
//    					 List<CtConstructorCall> constructorCalls = caller.getElements(new TypeFilter<CtConstructorCall>(CtConstructorCall.class));
//    	                 for(CtConstructorCall consCall: constructorCalls) {
//                    		 String fullcalleeCons = consCall.getExecutable().toString(); 
////                    		 System.out.println(fullcalleeCons);
//                    		 String consWithoutParams = fullcalleeCons.substring(0, fullcalleeCons.indexOf("(")); 
//                    		 String params =  fullcalleeCons.substring(fullcalleeCons.indexOf("("), fullcalleeCons.length());
////                    		 System.out.println("over");
//                    		 String fullconstructor= consWithoutParams+".-init-"+params; 
//                    		 
//                    		 
//                    		 
//                    		  methodsreferenced = st.executeQuery("SELECT methods.* from methods where fullmethod='"+fullconstructor+"'");  
//                    		  calleeID=null; 
//                    		  calleeownerclassID=null; 
//                    		  calleeownerclassName=null; 
//                    		  calleemethodName=null; 
//        					if(methodsreferenced.next()){
//        						
//        						 calleeID=methodsreferenced.getString("id"); 
//        						 calleeownerclassID= methodsreferenced.getString("ownerclassid"); 
//        						 calleeownerclassName= methodsreferenced.getString("ownerclassname"); 
//        						 calleemethodName= methodsreferenced.getString("name"); 
//
//        						
//        					
//        					}else if(consCall.toString().contains("super")){
//								String mySuperCallee= consCall.getExecutable().getSignature(); 
//								String mysupercalleewithoutparentheses = mySuperCallee.substring(0, mySuperCallee.indexOf("(")); 
//								 params = mySuperCallee.substring( mySuperCallee.indexOf("("), mySuperCallee.length()); 
//
//								String changedName = mysupercalleewithoutparentheses+".-init-"+params; 
////								System.out.println(changedName);
////								System.out.println("yes");
//								
//								  ResultSet resultset = st2.executeQuery("SELECT methods.* from methods where fullmethod='"+changedName+"'");  
//				            	  calleeID=null;
//				            	  calleeownerclassID=null; 
//				            	  calleeownerclassName=null; 
//				            	  calleemethodName=null; 
//									if(resultset.next()){
//										
//										 calleeID=resultset.getString("id"); 
//										 calleeownerclassID= resultset.getString("ownerclassid"); 
//										 calleeownerclassName= resultset.getString("ownerclassname"); 
//										 calleemethodName= resultset.getString("name"); 
//
//										
//									
//									}
//								
//							}
//        					
//        					if(callerID!=null && calleeID!=null && !methodcallsList.contains(callerID+"-"+calleeID)&& callerownerclassID!=null && calleeownerclassID!=null) {
//        						st.executeUpdate("INSERT INTO `new_databasegantt`.`methodcalls` (`callermethodid`, `callermethodname`, `callerclassname`, `callerclassid`, `fullcaller`, `calleemethodid`, `calleemethodname`, `calleeclassname`, `calleeclassid`, `fullcallee`) VALUES ('"+callerID+"','"+callermethodName+"','"+callerownerclassName+"','"+callerownerclassID+"','"+fullcaller+"','"+
//        		    					calleeID+"','"+calleemethodName+"','"+calleeownerclassName+"','"+calleeownerclassID+"','"+fullconstructor+"')"); 
//        						methodcallsList.add(callerID+"-"+calleeID); 
//
//        					}
//                    		 
//                    		 
//                    		 
//    	             }
//                	 
//                	 
//                 }
//                 //METHODS CALLING CONSTRUCTORS 
//				 List<CtConstructorCall> constructorCalls2 = caller.getElements(new TypeFilter<CtConstructorCall>(CtConstructorCall.class));
//				 for(CtConstructorCall consCall: constructorCalls2) {
//					 String fullcalleeCons = consCall.getExecutable().toString(); 
////            		 System.out.println(fullcalleeCons);
//            		 String consWithoutParams = fullcalleeCons.substring(0, fullcalleeCons.indexOf("(")); 
//            		 String params =  fullcalleeCons.substring(fullcalleeCons.indexOf("("), fullcalleeCons.length());
////            		 System.out.println("over");
//            		 String fullconstructor= consWithoutParams+".-init-"+params; 
//            		 
//            		 
//            		 ResultSet methodsreferenced = st.executeQuery("SELECT methods.* from methods where fullmethod='"+fullconstructor+"'");  
//           		 String calleeID=null; 
//           		 String calleeownerclassID=null; 
//           		 String calleeownerclassName=null; 
//           		 String calleemethodName=null; 
//					if(methodsreferenced.next()){
//						
//						 calleeID=methodsreferenced.getString("id"); 
//						 calleeownerclassID= methodsreferenced.getString("ownerclassid"); 
//						 calleeownerclassName= methodsreferenced.getString("ownerclassname"); 
//						 calleemethodName= methodsreferenced.getString("name"); 
//
//						
//					
//					}else if(consCall.toString().contains("super")){
//						String mySuperCallee= consCall.getExecutable().getSignature(); 
//						String mysupercalleewithoutparentheses = mySuperCallee.substring(0, mySuperCallee.indexOf("(")); 
//						 params = mySuperCallee.substring( mySuperCallee.indexOf("("), mySuperCallee.length()); 
//
//						String changedName = mysupercalleewithoutparentheses+".-init-"+params; 
////						System.out.println(changedName);
////						System.out.println("yes");
//						
//						  ResultSet resultset = st2.executeQuery("SELECT methods.* from methods where fullmethod='"+changedName+"'");  
//		            	  calleeID=null;
//		            	  calleeownerclassID=null; 
//		            	  calleeownerclassName=null; 
//		            	  calleemethodName=null; 
//							if(resultset.next()){
//								
//								 calleeID=resultset.getString("id"); 
//								 calleeownerclassID= resultset.getString("ownerclassid"); 
//								 calleeownerclassName= resultset.getString("ownerclassname"); 
//								 calleemethodName= resultset.getString("name"); 
//
//								
//							
//							}
//						
//					}
//					
//					if(callerID!=null && calleeID!=null && !methodcallsList.contains(callerID+"-"+calleeID)&& callerownerclassID!=null && calleeownerclassID!=null) {
//						st.executeUpdate("INSERT INTO `new_databasegantt`.`methodcalls` (`callermethodid`, `callermethodname`, `callerclassname`, `callerclassid`, `fullcaller`, `calleemethodid`, `calleemethodname`, `calleeclassname`, `calleeclassid`, `fullcallee`) VALUES ('"+callerID+"','"+callermethodName+"','"+callerownerclassName+"','"+callerownerclassID+"','"+fullcaller+"','"+
//		    					calleeID+"','"+calleemethodName+"','"+calleeownerclassName+"','"+calleeownerclassID+"','"+fullconstructor+"')"); 
//						methodcallsList.add(callerID+"-"+calleeID); 
//
//					}
//				 }
//            		 
//            		 
//             
//                 
//                 
//                 
//                 
//                 
//                 
//                 
//                 
//
//
//                 
//                 
//                 
//                 
//                 
//                 
//             }
//             //CONSTRUCTORS CALLING METHODS 
//             List<CtConstructor> constructors = clazz.getElements(new TypeFilter<CtConstructor>(CtConstructor.class));
//
//             for(CtConstructor cons: constructors) {
////            	 System.out.println(cons.getSignature());
//            	 String constructor = cons.getSignature(); 
//            	 String consNameWithoutParams = constructor.substring(0, constructor.indexOf("(")); 
//            	 String consParams = constructor.substring(constructor.indexOf("("), constructor.length()); 
//            	 String fixedCons= consNameWithoutParams+".-init-"+consParams; 
////            	 System.out.println(fixedCons);
//            	 String fullcaller = fixedCons; 
//            	 ResultSet methods = st.executeQuery("SELECT methods.* from methods where fullmethod='"+fixedCons+"'");  
//            	 String callerID=null;
//            	 String callerownerclassID=null; 
//            	 String callerownerclassName=null; 
//            	 String callermethodName=null; 
//					while(methods.next()){
//						
//						 callerID=methods.getString("id"); 
//						 callerownerclassID= methods.getString("ownerclassid"); 
//						 callerownerclassName= methods.getString("ownerclassname"); 
//						 callermethodName= methods.getString("name"); 
//
//						
//					
//					}
//		             List<CtInvocation> invokedmethods = cons.getElements(new TypeFilter<CtInvocation>(CtInvocation.class));
//		             for(CtInvocation invoked: invokedmethods) {
//		            	String fullcallee=""; 
//		            	 if(invoked.getExecutable().getDeclaringType()!=null) {
//		            		 String invokedMeth = invoked.getExecutable().getDeclaringType().toString(); 
//			            	 if(invokedMeth.contains("GameStatus")) {
//			            		 String invokedMeth2 = invokedMeth.substring(0, invokedMeth.lastIndexOf('.'))+"$"+invokedMeth.substring(invokedMeth.lastIndexOf('.')+1);
//					               fullcallee= invokedMeth2+"."+invoked.getExecutable().getSignature(); 
//
//			            	 } else {
//					              fullcallee= invoked.getExecutable().getDeclaringType()+"."+invoked.getExecutable().getSignature(); 
//
//			            	 }
//		            	 }
//		            	
//
////		            	String  fullcallee= invoked.getExecutable().getDeclaringType()+"."+invoked.getExecutable().getSignature(); 
//		            	if(invoked.getExecutable().toString().contains("GameStatus"))   fullcallee= invoked.getExecutable().getDeclaringType()+"."+invoked.getExecutable().getSignature();
//		            	 ResultSet callees = st2.executeQuery("SELECT methods.* from methods where fullmethod='"+fullcallee+"'");  
//		            	 String calleeID=null;
//		            	 String calleeownerclassID=null; 
//		            	 String calleeownerclassName=null; 
//		            	 String calleemethodName=null; 
//							if(callees.next()){
//								
//								 calleeID=callees.getString("id"); 
//								 calleeownerclassID= callees.getString("ownerclassid"); 
//								 calleeownerclassName= callees.getString("ownerclassname"); 
//								 calleemethodName= callees.getString("name"); 
//
//								
//							
//							}else if(invoked.toString().contains("super")){
//								String mySuperCallee= invoked.getExecutable().getSignature(); 
//								String mysupercalleewithoutparentheses = mySuperCallee.substring(0, mySuperCallee.indexOf("(")); 
//								String params = mySuperCallee.substring( mySuperCallee.indexOf("("), mySuperCallee.length()); 
//
//								String changedName = mysupercalleewithoutparentheses+".-init-"+params; 
////								System.out.println(changedName);
////								System.out.println("yes");
//								
//								  callees = st2.executeQuery("SELECT methods.* from methods where fullmethod='"+changedName+"'");  
//				            	  calleeID=null;
//				            	  calleeownerclassID=null; 
//				            	  calleeownerclassName=null; 
//				            	  calleemethodName=null; 
//									if(callees.next()){
//										
//										 calleeID=callees.getString("id"); 
//										 calleeownerclassID= callees.getString("ownerclassid"); 
//										 calleeownerclassName= callees.getString("ownerclassname"); 
//										 calleemethodName= callees.getString("name"); 
//
//										
//									
//									}
//								
//							}
//							
//							
//							
//							if(callerID!=null && calleeID!=null && !methodcallsList.contains(callerID+"-"+calleeID)&& callerownerclassID!=null && calleeownerclassID!=null) {
//        						st.executeUpdate("INSERT INTO `new_databasegantt`.`methodcalls` (`callermethodid`, `callermethodname`, `callerclassname`, `callerclassid`, `fullcaller`, `calleemethodid`, `calleemethodname`, `calleeclassname`, `calleeclassid`, `fullcallee`) VALUES ('"+callerID+"','"+callermethodName+"','"+callerownerclassName+"','"+callerownerclassID+"','"+fixedCons+"','"+
//        		    					calleeID+"','"+calleemethodName+"','"+calleeownerclassName+"','"+calleeownerclassID+"','"+fullcallee+"')"); 
//        						methodcallsList.add(callerID+"-"+calleeID); 
//
//        					}
//							 //CONSTRUCTORS CALLING OTHER CONSTRUCTORS
//				             List<CtConstructorCall> consCalls = cons.getElements(new TypeFilter<CtConstructorCall>(CtConstructorCall.class));
////				             System.out.println(cons);
//				             for(CtConstructorCall consCall: consCalls) {
//				            	 String constructor2 = consCall.getExecutable().toString(); 
//				            	 String consNameWithoutParams2 = constructor2.substring(0, constructor2.indexOf("(")); 
//				            	 String consParams2 = constructor2.substring(constructor2.indexOf("("), constructor2.length()); 
//				            	 String fixedCons2= consNameWithoutParams2+".-init-"+consParams2; 
////				            	 System.out.println(fixedCons2);
//				            	 String fullcaller2 = fixedCons2; 
//				            	 
//				            	 ResultSet methods2 = st.executeQuery("SELECT methods.* from methods where fullmethod='"+fixedCons2+"'");  
//				            	  calleeID=null;
//				            	  calleeownerclassID=null; 
//				            	  calleeownerclassName=null; 
//				            	  calleemethodName=null; 
//									while(methods2.next()){
//										
//										 calleeID=methods2.getString("id"); 
//										 calleeownerclassID= methods2.getString("ownerclassid"); 
//										 calleeownerclassName= methods2.getString("ownerclassname"); 
//										 calleemethodName= methods2.getString("name"); 
//
//										
//									
//									}
//									
//									if(callerID!=null && calleeID!=null && !methodcallsList.contains(callerID+"-"+calleeID)&& callerownerclassID!=null && calleeownerclassID!=null) {
//		        						st.executeUpdate("INSERT INTO `new_databasegantt`.`methodcalls` (`callermethodid`, `callermethodname`, `callerclassname`, `callerclassid`, `fullcaller`, `calleemethodid`, `calleemethodname`, `calleeclassname`, `calleeclassid`, `fullcallee`) VALUES ('"+callerID+"','"+callermethodName+"','"+callerownerclassName+"','"+callerownerclassID+"','"+fixedCons+"','"+
//		        		    					calleeID+"','"+calleemethodName+"','"+calleeownerclassName+"','"+calleeownerclassID+"','"+fixedCons2+"')"); 
//		        						methodcallsList.add(callerID+"-"+calleeID); 
//
//		        					}
//				            	 
//				            	 
//				             }
//				             
//				             
//		             }
//					
//					
//		           
//
//            	 
//             }
//             
////             System.out.println("over");
//             
//          
//
//
//
//
//            }      
//
//
//
////
////    /////////////*********************************************************************************************************************************************************************************/	
////    /////////////*********************************************************************************************************************************************************************************/	
////    /////////////*********************************************************************************************************************************************************************************/   
////    //////////////CREATE REQUIREMENTS TABLE 
////    ////////////
//    File file = new File("C:\\Users\\mouna\\git\\TraceTool\\TraceTool\\src\\GanttFiles\\RequirementsGantt.txt");
//    FileReader fileReader = new FileReader(file);
//    BufferedReader bufferedReader = new BufferedReader(fileReader);
//    StringBuffer stringBuffer = new StringBuffer();
//    
//    
//    try {
//    
//    
//    String line="";
//	while ((line = bufferedReader.readLine()) != null) {
//    System.out.println(line);
//    
//    
//    
//    
//    
//    String statement = "INSERT INTO `requirements`(`name`) VALUES ('"+line+"')";		
//    st.executeUpdate(statement);
//    
//    
//    
//    }
//    
//    
//    
//    
//    }
//    
//    catch (IOException e) {
//    // TODO Auto-generated catch block
//    e.printStackTrace();
//    }
////    //////////////
////    ///////////////*********************************************************************************************************************************************************************************/	
////    ///////////////*********************************************************************************************************************************************************************************/	
////    ///////////////*********************************************************************************************************************************************************************************/   
////    ////
//    ////////////////CREATE TRACES TABLE 
//    ////////////
//    	 file = new File("C:\\Users\\mouna\\git\\TraceTool\\TraceTool\\src\\GanttFiles\\TracesGantt.txt");
//		 fileReader = new FileReader(file);
//		 bufferedReader = new BufferedReader(fileReader);
//		 stringBuffer = new StringBuffer();
//		String line;
//		line = bufferedReader.readLine(); 
//		HashMap<String, SubjectTSubjectNObject> mytracehashmap= new HashMap<String, SubjectTSubjectNObject>(); 
//		
//		
//		
//		while ((line = bufferedReader.readLine()) != null) {
//			String[] splittedline = line.split(",", -1); 
//			
//			  counter = 1; 
//			for(int k=4; k<13; k++) {
//				SubjectTSubjectNObject SubjectTSubjectNObj = new SubjectTSubjectNObject(); 
//				String methodname= splittedline[1]+"."+splittedline[2]; 
//		
//				String RequirementID= ""+counter;
//				if(splittedline[k].equals("")) {
//					SubjectTSubjectNObj.setGoldfinal("N");
//				}
//				else {
//					SubjectTSubjectNObj.setGoldfinal("T");
//				}
//				SubjectTSubjectNObj.setMethodName(methodname);
//				SubjectTSubjectNObj.setRequirementID(RequirementID);
//				
//				
//				String key=counter+"-"+methodname; 
//				mytracehashmap.put(key,SubjectTSubjectNObj); 
//				counter++; 
//			}
//		
//		}
//		fileReader.close();
//		
//	    HashMap<String, String> RequirementIDNameHashMap=new HashMap<String, String> (); 
//	    RequirementIDNameHashMap.put("1", "R0"); 
//	    RequirementIDNameHashMap.put("2", "R1"); 
//	    RequirementIDNameHashMap.put("3", "R2"); 
//	    RequirementIDNameHashMap.put("4", "R3"); 
//	    RequirementIDNameHashMap.put("5", "R4"); 
//	    RequirementIDNameHashMap.put("6", "R5"); 
//	    RequirementIDNameHashMap.put("7", "R6"); 
//	    RequirementIDNameHashMap.put("8", "R7"); 
//	    List<String> MYLIST= new ArrayList<String>(); 
//
//	    counter=0; 
//    ResultSet mymeths = st2.executeQuery("SELECT methods.* from methods"); 
//    while(mymeths.next()){
//    String methodid = mymeths.getString("id"); 
//    String methodname = mymeths.getString("name"); 
//    String fullmethod = mymeths.getString("fullmethod"); 
//
//    String classname = mymeths.getString("ownerclassname"); 
//    String classid = mymeths.getString("ownerclassid"); 
//
//   
//    for(String key: RequirementIDNameHashMap.keySet()) {
//    tracesmethods tr= new tracesmethods(key, methodid,  classid); 
//	   String fullmethodwithoutPar = fullmethod.substring(0, fullmethod.indexOf("(")); 
//
//   SubjectTSubjectNObject sub = mytracehashmap.get(tr.getRequirementid()+"-"+fullmethodwithoutPar); 
//   if(sub!=null) {
//	   String statement = "INSERT INTO `tracesmethods`(`requirementname`, `requirementid`, `methodname`,  `fullmethod`,  `methodid`,`classname`, `classid`,`value`) VALUES ('"+RequirementIDNameHashMap.get(tr.getRequirementid())+"','" +tr.getRequirementid()+"','" +methodname+"','" +fullmethod+"','" +methodid+"','"+classname +"','" +classid+"','" +mytracehashmap.get(tr.getRequirementid()+"-"+fullmethodwithoutPar).getGoldfinal()+"')";
//	    st.executeUpdate(statement);
//	    MYLIST.add(tr.getRequirementid()+"-"+fullmethod); 
//	    counter++; 
//   }
//   else {
//	   String statement = "INSERT INTO `tracesmethods`(`requirementname`, `requirementid`, `methodname`,  `fullmethod`,  `methodid`,`classname`, `classid`,`value`) VALUES ('"+RequirementIDNameHashMap.get(tr.getRequirementid())+"','" +tr.getRequirementid()+"','" +methodname+"','" +fullmethod+"','" +methodid+"','"+classname +"','" +classid+"','" +"U"+"')";
//	    st.executeUpdate(statement);
//   }
//   
//
//    }
//  
////
//    }
//
////    for(String key: mytracehashmap.keySet()) {
////    	if(!MYLIST.contains(key)) {
////    		System.out.println(key);
////    	}
////    }
////    System.out.println("OVER");
//    //
//    //
//    //
////    //
////    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
////    /////////////////*********************************************************************************************************************************************************************************/   
////    ////
////    ////////////////CREATE TRACES CLASSES TABLE 
////    ////////////
////    //
////    //
//    
//     fileReader = new FileReader("C:\\Users\\mouna\\git\\TraceTool\\TraceTool\\src\\GanttFiles\\TracesClassesNEW.txt");
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
//    String packageName=""; 
//    String classid=""; 
//    String requirementname=""; 
//    String requirementid="";
//    String className=""; 
//    ResultSet Traces = st.executeQuery("SELECT classes.* from classes "); 
//    while(Traces.next()){
//    packageName = Traces.getString("package"); 
//    className = Traces.getString("name"); 
//
//    classid = Traces.getString("id"); 
//    for(String keyreq: RequirementIDNameHashMap.keySet()) {
//    	String key= keyreq+"-"+classid; 
//    	String val= keyreq+"-"+RequirementIDNameHashMap.get(keyreq)+"-"+classid+"-"+packageName+"."+className; 
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
//   String subjectGold = ReqClassHashMap.get(myvalues[0]+"-"+myvalues[3]);
//   if(subjectGold!=null) {
//	   String statement8= "INSERT INTO `traceclasses`(`requirementname`, `requirementid`,  `classname`, `classid`,`value`) VALUES ('"+myvalues[1]+"','" +myvalues[0]+"','"  +myvalues[3]+"','" +myvalues[2]
//			   +"','" +subjectGold+"')";
//	    st2.executeUpdate(statement8); 
//	    MYLIST2.add(myvalues[0]+"-"+myvalues[3]); 
//   }
//   else {
//	   String statement8= "INSERT INTO `traceclasses`(`requirementname`, `requirementid`,  `classname`, `classid`,`value`) VALUES ('"+myvalues[1]+"','" +myvalues[0]+"','"  +myvalues[3]+"','" +myvalues[2]
//			   +"','" +"U"+"')";	
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
//
//
//
////
////
//////      /////////////*********************************************************************************************************************************************************************************/	
////        /////////////*********************************************************************************************************************************************************************************/	
////        /////////////*********************************************************************************************************************************************************************************/   
////        //////////////CREATE METHOD CALLS EXECUTED TABLE 
////        ////////////
//          file = new File("C:\\Users\\mouna\\git\\TraceTool\\TraceTool\\src\\GanttFiles\\methodcallsExecutedFormatted.txt");
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
//       String CallerPackage = CallerClass.substring(0, CallerClass.lastIndexOf(".")); 
//       String CallerClassName = CallerClass.substring(CallerClass.lastIndexOf(".")+1, CallerClass.length()); 
//       String CallerMethod=  CallerNonParameters.substring(CallerNonParameters.lastIndexOf(".")+1, CallerNonParameters.length())+CallerParameters.trim(); 
//       
//       String CalleeClass=  CalleeNonParameters.substring(0, CalleeNonParameters.lastIndexOf(".")).trim(); 
//       String CalleeMethod=  CalleeNonParameters.substring(CalleeNonParameters.lastIndexOf(".")+1, CalleeNonParameters.length())+CalleeParameters.trim(); 
//       String CalleePackage = CalleeClass.substring(0, CalleeClass.lastIndexOf(".")); 
//       String CalleeClassName = CalleeClass.substring(CalleeClass.lastIndexOf(".")+1, CalleeClass.length()); 
//       
//       
//       System.out.println("yes");
//       String fullcaller = CallerClass+"."+CallerMethod; 
//       String fullcallee =  CalleeClass+"."+CalleeMethod; 
//       ResultSet rs= st.executeQuery("SELECT * from classes where classes.package='"+CallerPackage+"'and name='"+CallerClassName+"'"); 
//       while(rs.next()) {
//    	   CallerExecutedClassID= rs.getString("ID"); 
//       }
//        rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+fullcaller+"'"); 
//       while(rs.next()) {
//    	   CallerExecutedMethodID= rs.getString("ID"); 
//       }
//        rs= st.executeQuery("SELECT * from classes where classes.package='"+CalleePackage+"' and name='"+CalleeClassName+"'"); 
//       while(rs.next()) {
//    	   CalleeExecutedClassID= rs.getString("ID"); 
//       }
//        rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+fullcallee+"'"); 
//       while(rs.next()) {
//    	   CalleeExecutedMethodID= rs.getString("ID"); 
//       }
//      
//       if(CallerExecutedClassID!=null && CallerExecutedMethodID!=null && CalleeExecutedClassID!=null && CalleeExecutedMethodID!=null) {
//    	   String statement = "INSERT INTO `methodcallsexecuted`(`callermethodid`,  `callermethodname`,  `callerclassname`, `callerclassid`,`fullcaller`,`calleemethodid`,  `calleemethodname`, `calleeclassname`,  `calleeclassid`,  `fullcallee`) VALUES "
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
//
//
// 	   //  /////////////*********************************************************************************************************************************************************************************/	
//     /////////////*********************************************************************************************************************************************************************************/	
//     /////////////*********************************************************************************************************************************************************************************/ 
// 	// FIELD CLASSES 
// 		List<String> fieldClasses = new ArrayList<>(); 
// 		 for(CtType<?> clazz : classFactory.getAll()) {
// 	 			List<CtField> fields = clazz.getElements(new TypeFilter<CtField>(CtField.class));
// 	 		   retrieveFieldClasses(fields, st, fieldClasses, st2); 
// 	 		  Set<CtType<?>> nested = clazz.getNestedTypes();
// 		
// 	 		// FIELD CLASSES IN NESTED CLASSES  
//				for(CtType<?> mynested: nested) {
//					List<CtField> fields2 = mynested.getElements(new TypeFilter<CtField>(CtField.class));
//  	 		   retrieveFieldClasses(fields2, st, fieldClasses, st2); 
//						
//
//				}
// 		 }
//	
// 		 //  /////////////*********************************************************************************************************************************************************************************/	
// 	        /////////////*********************************************************************************************************************************************************************************/	
// 	        /////////////*********************************************************************************************************************************************************************************/ 
// 	    	// FIELD METHODS  
// 	    		List<String> fieldMethods = new ArrayList<>(); 
// 	    		 for(CtType<?> clazz : classFactory.getAll()) {
// 	    	 			List<CtMethod> methods = clazz.getElements(new TypeFilter<CtMethod>(CtMethod.class));
// 	    	 		   for(CtMethod myMethod: methods) {
//    	    	 				List<CtFieldAccess> methodFields = myMethod.getElements(new TypeFilter<CtFieldAccess>(CtFieldAccess.class));
//    	    	 					retrieveMethodFields(methodFields, myMethod, clazz, st, st2, fieldMethods); 
//    	    	 				
// 	    	 		   }
// 	    	 		   
// 	    	 		   
// 	    	 		   
// 	    	 		  List<CtConstructor> constructors = clazz.getElements(new TypeFilter<CtConstructor>(CtConstructor.class));
//	    	 		   for(CtConstructor myconstructor: constructors) {
//   	    	 				List<CtFieldAccess> methodFields = myconstructor.getElements(new TypeFilter<CtFieldAccess>(CtFieldAccess.class));
//   	    	 					retrieveConstructorFields(methodFields, myconstructor, clazz, st, st2, fieldMethods); 
//   	    	 				
//	    	 		   }
// 	    	 		   
// 	    		 }
// 		 
// 	    		
//	
//    	/******************************************************************************************************************************************************/
//    	/******************************************************************************************************************************************************/
//    	/******************************************************************************************************************************************************/
//    	/******************************************************************************************************************************************************/
////    	List<String> mylist = new ArrayList<>(); 
//// 		List<String> fieldMethods = new ArrayList<>(); 
////		 for(CtType<?> clazz : classFactory.getAll(true)) {
////			 System.out.println("====== "+clazz.getQualifiedName());
////	 			List<CtField> classFields = clazz.getElements(new TypeFilter<CtField>(CtField.class));
////	 			HashMap<String, String> myHashMap = new HashMap<>(); 
////	 			for(CtField myclassField: classFields) {
////	 			
////	 				myHashMap.put(myclassField.getSimpleName(), myclassField.getType().toString()); 
////	 				
////	 			}
////	 			String classID=null; 
////	 			ResultSet	 rs= st.executeQuery("SELECT * from classes where classes.classname='"+clazz.getQualifiedName()+"'"); 
////					while(rs.next()) {
////						classID= rs.getString("id"); 
////					}
////	 			
////	 			List<CtMethod> methods = clazz.getElements(new TypeFilter<CtMethod>(CtMethod.class));
////	 			List<CtConstructor> constructors = clazz.getElements(new TypeFilter<CtConstructor>(CtConstructor.class));
////
////	 			//VARIABLE READS  
////	 			for(CtConstructor myMethod: constructors) {
////	 				System.out.println(myMethod.getSignature());
////	 				String methodname = myMethod.getSignature().substring(0, myMethod.getSignature().indexOf("(")); 
////	 				String params = myMethod.getSignature().substring(myMethod.getSignature().indexOf("("), myMethod.getSignature().length()); 
////	 				String fullmethodname = methodname+".-init-"+params; 
////	 				System.out.println("---------------------");
////	 				
////
////		 			   System.out.println(myMethod);
////		 					List<CtVariableRead> myvarList = myMethod.getElements(new TypeFilter<CtVariableRead>(CtVariableRead.class));
////
////	   	 					String methodID=null; 
////	   	 						 rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+fullmethodname+"'"); 
////	   	 					while(rs.next()) {
////	   	 					methodID= rs.getString("id"); 
////	   	 					}
////	   	 					for(CtVariableRead variableRead: myvarList) {
////	   	 					//====>	
////	   	 						String entry = methodID+"-"+variableRead+"-"+myHashMap.get(variableRead.toString())+"-"+1; 
////	   	 					String variableDataTypeid=null; 
////		 						 rs= st.executeQuery("SELECT * from classes where classes.classname='"+myHashMap.get(variableRead.toString())+"'"); 
////		 					while(rs.next()) {
////		 						variableDataTypeid= rs.getString("id"); 
////		 					}	
////	   	 					if(myHashMap.get(variableRead.toString())!=null  && !mylist.contains(entry) && variableDataTypeid!=null && methodID!=null) {
////	   	 						mylist.add(entry); 
////	   	 						System.out.println(fullmethodname);
////	   	   	 					String statement8= "INSERT INTO `assignments`(`methodname`, `methodid`,`classname`, `classid`, `variable`, `variableDataType`, `variableDataTypeid`,  `read` ) VALUES"+ " ('"+fullmethodname+"','" +methodID +"','"  +clazz.getQualifiedName() +"','"  +	classID+"','" +	variableRead+"','"  + myHashMap.get(variableRead.toString())
////	   	   	 				+ "','" +variableDataTypeid + "','" +1+ "')";
////	   						   	   	 st2.executeUpdate(statement8); 
////	   	   	 					}
////	   	 					
////	   	 					}
////	   	 					//VARIABLE WRITES 
////	   	 				List<CtVariableWrite> myvarListWritten = myMethod.getElements(new TypeFilter<CtVariableWrite>(CtVariableWrite.class));
////
////	   	 		   	 					 methodID=null; 
////	   	 		   	 						 rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+fullmethodname+"'"); 
////	   	 		   	 					while(rs.next()) {
////	   	 		   	 					methodID= rs.getString("id"); 
////	   	 		   	 					}
////	   	 		   	 					for(CtVariableWrite variableWritten: myvarListWritten) {
////	   	 		   	 					//====>	
////	   	 		   	 						String entry = methodID+"-"+variableWritten+"-"+myHashMap.get(variableWritten.toString())+"-"+1; 
////	   	 		   	 					String variableDataTypeid=null; 
////	   	 			 						 rs= st.executeQuery("SELECT * from classes where classes.classname='"+myHashMap.get(variableWritten.toString())+"'"); 
////	   	 			 					while(rs.next()) {
////	   	 			 						variableDataTypeid= rs.getString("id"); 
////	   	 			 					}	
////	   	 		   	 					if(myHashMap.get(variableWritten.toString())!=null  && !mylist.contains(entry) && variableDataTypeid!=null && methodID!=null) {
////	   	 		   	 						mylist.add(entry); 
////	   	 		   	 						System.out.println(fullmethodname);
////	   	 		   	   	 					String statement8= "INSERT INTO `assignments`(`methodname`, `methodid`,`classname`, `classid`, `variable`, `variableDataType`, `variableDataTypeid`,  `read` ) VALUES"+ " ('"+fullmethodname+"','" +methodID +"','"  +clazz.getQualifiedName() +"','"  +	classID+"','" +	variableWritten+"','"  + myHashMap.get(variableWritten.toString())
////	   	 		   	   	 				+ "','" +variableDataTypeid + "','" +1+ "')";
////	   	 		   						   	   	 st2.executeUpdate(statement8); 
////	   	 		   	   	 					}
////	   	 		   	 					
////	   	 		   	 					}
////	   	 				
////	   	 				
////	   	 				
////	   	 				
////	   	 				
////	   	 				
////	   	 				
////	   	 				
////	   	 				
////	   	 				
////	   	 				
////	   	 				List<CtAssignment> assignments = myMethod.getElements(new TypeFilter<CtAssignment>(CtAssignment.class));
////	   	 				for(CtAssignment assignment: assignments) {
////	   	 					
////	   	 					 myvarList = assignment.getAssignment().getElements(new TypeFilter<CtVariableRead>(CtVariableRead.class));
////	   	 					
////	   	 					 methodID=null; 
////	   	 						 rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+fullmethodname+"'"); 
////	   	 					while(rs.next()) {
////	   	 					methodID= rs.getString("id"); 
////	   	 					}
////	   	 					
////	   	 					//VARIABLE READS 
////	   	 					
////							for(CtVariableRead variableRead: myvarList) {
////	   	 					//====>	
////	   	 						String entry = methodID+"-"+variableRead+"-"+myHashMap.get(variableRead.toString())+"-"+1; 
////	   	 					String variableDataTypeid=null; 
////		 						 rs= st.executeQuery("SELECT * from classes where classes.classname='"+myHashMap.get(variableRead.toString())+"'"); 
////		 					while(rs.next()) {
////		 						variableDataTypeid= rs.getString("id"); 
////		 					}	
////	   	 					if(myHashMap.get(variableRead.toString())!=null  && !mylist.contains(entry) && variableDataTypeid!=null && methodID!=null) {
////	   	 						mylist.add(entry); 
////	   	 						System.out.println(fullmethodname);
////	   	   	 					String statement8= "INSERT INTO `assignments`(`methodname`, `methodid`,`classname`, `classid`, `variable`, `variableDataType`, `variableDataTypeid`,  `read` ) VALUES"+ " ('"+fullmethodname+"','" +methodID +"','"  +clazz.getQualifiedName() +"','"  +	classID+"','" +	variableRead+"','"  + myHashMap.get(variableRead.toString())
////	   	   	 				+ "','" +variableDataTypeid + "','" +1+ "')";
////	   						   	   	 st2.executeUpdate(statement8); 
////	   	   	 					}
////	   	 					}
////	   	 					//===> variable write 
////							String	 variableDataTypeid=null; 
////	   	 					rs= st.executeQuery("SELECT * from classes where classes.classname='"+myHashMap.get(assignment.getAssigned().toString())+"'"); 
////		 					while(rs.next()) {
////		 							variableDataTypeid= rs.getString("id"); 
////		 					}	
////		 					String entry = methodID+"-"+assignment.getAssigned().toString()+"-"+myHashMap.get(assignment.getAssigned().toString())+"-"+0; 
////	   	 					if(myHashMap.get(assignment.getAssigned().toString())!=null  && methodID!=null && !mylist.contains(entry) && variableDataTypeid!=null) {
////	   	 						mylist.add(entry); 
////
////	   	 					 String statement8= "INSERT INTO `assignments`(`methodname`, `methodid`,`classname`, `classid`,`variable`, `variableDataType`, `variableDataTypeid`,  `read` ) VALUES"+ " ('"+fullmethodname+"','"  +methodID+"','"  +clazz.getQualifiedName() +"','"  +	classID+"','" +	assignment.getAssigned()+"','"  + myHashMap.get(assignment.getAssigned().toString())
////	   	 					+ "','" +variableDataTypeid  + "','" +0	+ "')";
////	   	 					 System.out.println(clazz.getQualifiedName());
////	   	 					 System.out.println(myMethod.getSignature());
////	   	 					 System.out.println(fullmethodname);
////						   	   	 st2.executeUpdate(statement8); 
////	   	 					}
////	   	 				
////	   	 				}
////	   	 				System.out.println();
////		 		   
////	 			}
////	 			for(CtMethod myMethod: methods) {
////	 			   System.out.println(myMethod);
////	 					List<CtVariableRead> myvarList = myMethod.getElements(new TypeFilter<CtVariableRead>(CtVariableRead.class));
////String fullmethodname=clazz.getQualifiedName()+"."+myMethod.getSignature(); 
////
////   	 					String methodID=null; 
////   	 						 rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+fullmethodname+"'"); 
////   	 					while(rs.next()) {
////   	 					methodID= rs.getString("id"); 
////   	 					}
////   	 					for(CtVariableRead variableRead: myvarList) {
////   	 					//====>	
////   	 						String entry = methodID+"-"+variableRead+"-"+myHashMap.get(variableRead.toString())+"-"+1; 
////   	 					String variableDataTypeid=null; 
////	 						 rs= st.executeQuery("SELECT * from classes where classes.classname='"+myHashMap.get(variableRead.toString())+"'"); 
////	 					while(rs.next()) {
////	 						variableDataTypeid= rs.getString("id"); 
////	 					}	
////   	 					if(myHashMap.get(variableRead.toString())!=null  && !mylist.contains(entry) && variableDataTypeid!=null && methodID!=null) {
////   	 						mylist.add(entry); 
////   	 						System.out.println(fullmethodname);
////   	   	 					String statement8= "INSERT INTO `assignments`(`methodname`, `methodid`,`classname`, `classid`, `variable`, `variableDataType`, `variableDataTypeid`,  `read` ) VALUES"+ " ('"+fullmethodname+"','" +methodID +"','"  +clazz.getQualifiedName() +"','"  +	classID+"','" +	variableRead+"','"  + myHashMap.get(variableRead.toString())
////   	   	 				+ "','" +variableDataTypeid + "','" +1+ "')";
////   						   	   	 st2.executeUpdate(statement8); 
////   	   	 					}
////   	 					
////   	 					}
////   	 					//VARIABLE WRITES 
////   	 				List<CtVariableWrite> myvarListWritten = myMethod.getElements(new TypeFilter<CtVariableWrite>(CtVariableWrite.class));
////   	 		 fullmethodname=clazz.getQualifiedName()+"."+myMethod.getSignature(); 
////
////   	 		   	 					 methodID=null; 
////   	 		   	 						 rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+fullmethodname+"'"); 
////   	 		   	 					while(rs.next()) {
////   	 		   	 					methodID= rs.getString("id"); 
////   	 		   	 					}
////   	 		   	 					for(CtVariableWrite variableWritten: myvarListWritten) {
////   	 		   	 					//====>	
////   	 		   	 						String entry = methodID+"-"+variableWritten+"-"+myHashMap.get(variableWritten.toString())+"-"+1; 
////   	 		   	 					String variableDataTypeid=null; 
////   	 			 						 rs= st.executeQuery("SELECT * from classes where classes.classname='"+myHashMap.get(variableWritten.toString())+"'"); 
////   	 			 					while(rs.next()) {
////   	 			 						variableDataTypeid= rs.getString("id"); 
////   	 			 					}	
////   	 		   	 					if(myHashMap.get(variableWritten.toString())!=null  && !mylist.contains(entry) && variableDataTypeid!=null && methodID!=null) {
////   	 		   	 						mylist.add(entry); 
////   	 		   	 						System.out.println(fullmethodname);
////   	 		   	   	 					String statement8= "INSERT INTO `assignments`(`methodname`, `methodid`,`classname`, `classid`, `variable`, `variableDataType`, `variableDataTypeid`,  `read` ) VALUES"+ " ('"+fullmethodname+"','" +methodID +"','"  +clazz.getQualifiedName() +"','"  +	classID+"','" +	variableWritten+"','"  + myHashMap.get(variableWritten.toString())
////   	 		   	   	 				+ "','" +variableDataTypeid + "','" +1+ "')";
////   	 		   						   	   	 st2.executeUpdate(statement8); 
////   	 		   	   	 					}
////   	 		   	 					
////   	 		   	 					}
////   	 				
////   	 				
////   	 				
////   	 				
////   	 				
////   	 				
////   	 				
////   	 				
////   	 				
////   	 				
////   	 				
////   	 				List<CtAssignment> assignments = myMethod.getElements(new TypeFilter<CtAssignment>(CtAssignment.class));
////   	 				for(CtAssignment assignment: assignments) {
////   	 					
////   	 					 myvarList = assignment.getAssignment().getElements(new TypeFilter<CtVariableRead>(CtVariableRead.class));
////   	 					 fullmethodname=clazz.getQualifiedName()+"."+myMethod.getSignature(); 
////   	 					
////   	 					 methodID=null; 
////   	 						 rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+fullmethodname+"'"); 
////   	 					while(rs.next()) {
////   	 					methodID= rs.getString("id"); 
////   	 					}
////   	 					
////   	 					//VARIABLE READS 
////   	 					
////						for(CtVariableRead variableRead: myvarList) {
////   	 					//====>	
////   	 						String entry = methodID+"-"+variableRead+"-"+myHashMap.get(variableRead.toString())+"-"+1; 
////   	 					String variableDataTypeid=null; 
////	 						 rs= st.executeQuery("SELECT * from classes where classes.classname='"+myHashMap.get(variableRead.toString())+"'"); 
////	 					while(rs.next()) {
////	 						variableDataTypeid= rs.getString("id"); 
////	 					}	
////   	 					if(myHashMap.get(variableRead.toString())!=null  && !mylist.contains(entry) && variableDataTypeid!=null && methodID!=null) {
////   	 						mylist.add(entry); 
////   	 						System.out.println(fullmethodname);
////   	   	 					String statement8= "INSERT INTO `assignments`(`methodname`, `methodid`,`classname`, `classid`, `variable`, `variableDataType`, `variableDataTypeid`,  `read` ) VALUES"+ " ('"+fullmethodname+"','" +methodID +"','"  +clazz.getQualifiedName() +"','"  +	classID+"','" +	variableRead+"','"  + myHashMap.get(variableRead.toString())
////   	   	 				+ "','" +variableDataTypeid + "','" +1+ "')";
////   						   	   	 st2.executeUpdate(statement8); 
////   	   	 					}
////   	 					}
////   	 					//===> variable write 
////						String	 variableDataTypeid=null; 
////   	 					rs= st.executeQuery("SELECT * from classes where classes.classname='"+myHashMap.get(assignment.getAssigned().toString())+"'"); 
////	 					while(rs.next()) {
////	 							variableDataTypeid= rs.getString("id"); 
////	 					}	
////	 					String entry = methodID+"-"+assignment.getAssigned().toString()+"-"+myHashMap.get(assignment.getAssigned().toString())+"-"+0; 
////   	 					if(myHashMap.get(assignment.getAssigned().toString())!=null  && methodID!=null && !mylist.contains(entry) && variableDataTypeid!=null) {
////   	 						mylist.add(entry); 
////
////   	 					 String statement8= "INSERT INTO `assignments`(`methodname`, `methodid`,`classname`, `classid`,`variable`, `variableDataType`, `variableDataTypeid`,  `read` ) VALUES"+ " ('"+fullmethodname+"','"  +methodID+"','"  +clazz.getQualifiedName() +"','"  +	classID+"','" +	assignment.getAssigned()+"','"  + myHashMap.get(assignment.getAssigned().toString())
////   	 					+ "','" +variableDataTypeid  + "','" +0	+ "')";
////   	 					 System.out.println(clazz.getQualifiedName());
////   	 					 System.out.println(myMethod.getSignature());
////   	 					 System.out.println(fullmethodname);
////					   	   	 st2.executeUpdate(statement8); 
////   	 					}
////   	 				
////   	 				}
////   	 				System.out.println();
////	 		   }
////	 		   
////	 		   
////	 		   
////	 
////	 		   
////		 }
// 
//		
//
//    	
//    	
/////////////////*********************************************************************************************************************************************************************************/	
// /////////////*********************************************************************************************************************************************************************************/	
// /////////////*********************************************************************************************************************************************************************************/   
////		// PARAMETERS TABLE 
// 	List<String> mylist = new ArrayList<>(); 
//		 for(CtType<?> clazz : classFactory.getAll()) {
//  		
//
//  		
//			//NON NESTED CONSTRUCTORS 
//			List<CtConstructor> constructors = clazz.getElements(new TypeFilter<CtConstructor>(CtConstructor.class));
//			retrieveConstructorParams(constructors, st, st2, mylist); 	
//			
//				// CONSTRUCTORS IN NESTED CLASSES  
//		    Set<CtType<?>> nested = clazz.getNestedTypes();
//
//				for(CtType<?> mynested: nested) {
//			 
//						constructors = mynested.getElements(new TypeFilter<CtConstructor>(CtConstructor.class));
//						retrieveConstructorParams(constructors, st, st2, mylist); 	
//
//				}
//				
//				//NON NESTED METHODS
//	 			List<CtMethod> methods = clazz.getElements(new TypeFilter<CtMethod>(CtMethod.class));
//				retrieveMethodParams(methods, st, st2, mylist); 	
//	 			
//	 				// METHODS  IN NESTED CLASSES 
//	 				for(CtType<?> mynested: nested) {
//				 
//	 						List<CtMethod> methodscalled = mynested.getElements(new TypeFilter<CtMethod>(CtMethod.class));
//	 		 				retrieveMethodParams(methods, st, st2, mylist); 	
//
//	 				}
//	 			
//				
//	 
//	
//		 }
//    	
////    	readFields( st, classFactory); 
////    	readparams(st,classFactory, methodFactory); 
//	 }
//	private void readparams(Statement st, ClassFactory classFactory, MethodFactory methodFactory) throws SQLException {
//		// TODO Auto-generated method stub
//		ResultSet rs= st.executeQuery("SELECT * from parameters"); 
//		List<String> mylist = new ArrayList<String>(); 
//		
//		 while(rs.next()) {
//			 String elem=""; 
//			 String parametername = rs.getString("parametername");
//			 String parametertype = rs.getString("parametertype");
//
//			 String parameterclass = rs.getString("parameterclass");
//
//			 String classid = rs.getString("classid");
//
//			 String classname = rs.getString("classname");
//			 String methodid = rs.getString("methodid");
//			 String methodname = rs.getString("methodname");
//			 String isreturn = rs.getString("isreturn");
//			 String sourcecode = rs.getString("sourcecode");
//			 elem=parametername+","+parametertype+","+parameterclass+","+classid+","+classname+","+methodid+","+methodname+","+isreturn+","+sourcecode; 
//
//		 }
//		
//		 for(CtType<?> clazz : classFactory.getAll(true)) {
//		 		Set<CtMethod<?>> methods = clazz.getMethods(); 
//		 		String classname=clazz.getQualifiedName(); 
//		 		ResultSet rs2= st.executeQuery("SELECT * from classes where classname='"+clazz.getQualifiedName()+"'"); 
//		 		 String classid =null; 
//				 while(rs2.next()) {
//					  classid = rs2.getString("id");
//					 
//				 }
//		 		
//		 		for(CtMethod method: methods) {
//					List<CtReturn> returnStatements = method.getElements(new TypeFilter<CtReturn>(CtReturn.class));
//					System.out.println(method.getSignature()+ "           "+returnStatements);
//					for(CtReturn returnStatement: returnStatements) {
//						if(!returnStatement.getReferencedTypes().isEmpty()) {
//							rs2= st.executeQuery("SELECT * from classes where classname='"+returnStatement.getReferencedTypes().iterator().next()+"'"); 
//					 		 String parameterClassID ="0"; 
//							 while(rs2.next()) {
//								 parameterClassID = rs2.getString("id");
//								 
//							 }
//							
//							String fullmethod=clazz.getQualifiedName()+"."+method.getSignature(); 
//							 ResultSet rs3 = st.executeQuery("SELECT * from methods where fullmethod='"+fullmethod+"'"); 
//							String methodID=null; 
//							 while(rs3.next()) {
//								 methodID = rs3.getString("id");
//								 
//							 }
//					 		String methodString=method.toString().replaceAll("\\'", ""); 
//							  String statement8= "INSERT INTO `parameters`(`parametername`, `parametertype`,  `parameterclass`, `classid`,`classname`, `methodid`, `methodname`, `isreturn`, `sourcecode`) VALUES ('"+
//									  returnStatement+"','" +returnStatement.getReferencedTypes().iterator().next()
//									  +"','"  +parameterClassID+"','" +classid+"','"+classname+"','"+methodID+"','"+fullmethod+"','"+1+"','"+methodString+"')";
//					 		  String row=returnStatement+"','" +returnStatement.getReferencedTypes().iterator().next()
//									  +"','"  +parameterClassID+"','" +classid+"','"+classname+"','"+methodID+"','"+fullmethod+"','"+1+"','"+methodString;   	   
//							  
//					 		  
//					 		  if(!mylist.contains(row))
//					 			  st.executeUpdate(statement8); 
////					 		    mylist.add(newparamList); 
//
//							System.out.println("hey");
//						}
//						
//					
//					}
//					
//		 		}
//		 }
//		 System.out.println("8888888888888888888888888888888");
//			
//	 
//		 
//		 
//		 
//		 
//	}
//
//	private void readFields(Statement st, ClassFactory classFactory) throws SQLException {
//		// TODO Auto-generated method stub
//		List<String> mylist = new ArrayList<String>(); 
//		ResultSet rs= st.executeQuery("SELECT * from sootfieldmethods"); 
//		 while(rs.next()) {
//			 String fieldclassid = rs.getString("fieldclassid");
//			 String fieldname=rs.getString("fieldname");
//			 String ownerclassname=rs.getString("ownerclassname");
//			 String ownerclassid=rs.getString("ownerclassid");
//			 String ownermethodname=rs.getString("ownermethodname");
//			 String ownermethodid=rs.getString("ownermethodid");
//			 String read=rs.getString("read");
//			 String union=fieldclassid+"-"+fieldname+"-"+ownerclassname+"-"+ownerclassid+"-"+ownermethodname+"-"+ownermethodid+"-"+read; 
//			 if(!mylist.contains(union)) {
//				 mylist.add(union); 
//			 }
//			 
//		 }
//		 for(CtType<?> clazz : classFactory.getAll()) {
//			String ownerclassid=null; 
//			String methodid=null; 
//			 String fieldname=null; 
//			 String ownerclassname=null; 
//			 String fieldclassid=null; 
//			 
//			 String ownermethodname=null; 
//			 String ownermethodid=null; 
//			 String read="0"; 
//
//			 System.out.println(clazz.getSimpleName());
//			  for(CtMethod<?> method :clazz.getMethods()) {
//				  System.out.println(method);
//					List<CtFieldAccess> fields = method.getElements(new TypeFilter<CtFieldAccess>(CtFieldAccess.class));
//					for(CtFieldAccess field: fields) {
//						ownermethodname=method.getSignature();
//
//						field.getType(); 
//						fieldname=field.toString();  
//						ownerclassname=clazz.getQualifiedName(); 
//						String union=null; 
//						String var=null; 
//						String classname=null; 
//						try {
//							rs= st.executeQuery("SELECT * from classes where classname='"+field.getVariable().getDeclaringType().toString()+"'"); 
//
//						 while(rs.next()) {
//							 ownerclassid=rs.getString("id"); 
//							 
//							 }
//					
//						  var= field.getVariable().toString().substring(field.getVariable().toString().lastIndexOf(".") + 1).trim(); 
//						  classname= field.getVariable().getDeclaringType().toString(); 
//						 
//						 rs= st.executeQuery("SELECT * from fieldclasses where classname='"+field.getVariable().getDeclaringType().toString()+"'and fieldname='"+field.getVariable().toString().substring(field.getVariable().toString().lastIndexOf(".") + 1).trim()+"'"); 
//						 while(rs.next()) {
//							 fieldclassid=rs.getString("id"); 
//							 }
//						 rs= st.executeQuery("SELECT * from methods where classname='"+clazz.getQualifiedName()+"'and methodname='"+ownermethodname+"'"); 
//						 while(rs.next()) {
//							 ownermethodid=rs.getString("id"); 
//							 }
//						System.out.println();
//						
//						  union=fieldclassid+"-"+var+"-"+classname+"-"+ownerclassid+"-"+ownermethodname+"-"+ownermethodid+"-"+read; 
//						 System.out.println(union);
//						 System.out.println();
//						}catch(Exception e ) {
//							
//						}
//						if(union!=null)
//						 if(!mylist.contains(union) && fieldclassid!=null && !var.equals("out") && union!=null) {
//							 System.out.println("HERE");
//							 String statement8= "INSERT INTO `sootfieldmethods`(`fieldclassid`, `fieldname`,  `ownerclassname`,"
//								 		+ " `ownerclassid`,`ownermethodname`,"
//								 		+ " `ownermethodid`, "
//								 		+ "`read`"
//								 		+ ") VALUES"
//								 		+ " ('"+	 fieldclassid+"','" +var+"','"  +classname
//								 		+"','" +ownerclassid+"','"+ ownermethodname
//								 		+"','"+ownermethodid+"','"+
//								 		read
//										 +"')";
//								   	   	 st.executeUpdate(statement8); 
//										 mylist.add(union); 
//
//						 }
//					}
//
//			  }
//			
//		 }		 
//		 
//	}
//
//	private void retrieveConstructorFields(List<CtFieldAccess> methodFields, CtConstructor myconstructor, CtType<?> clazz,
//			Statement st, Statement st2, List<String> fieldMethods) throws SQLException {
//		for(CtFieldAccess methodField : methodFields) {
//		 	System.out.println(methodField.getShortRepresentation()+"   "+myconstructor.getSignature()+"  "+myconstructor.getDeclaringType().getQualifiedName());
//		 	String methodID=null; 
//		 	String ownerMethodName=null; 
//			String formattedCons=myconstructor.getSignature().substring(0, myconstructor.getSignature().indexOf("("))+".-init-"+myconstructor.getSignature().substring(myconstructor.getSignature().indexOf("("), myconstructor.getSignature().length()); 
//			System.out.println(formattedCons);
//		 	ResultSet rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+formattedCons+"'"); 
//			 while(rs.next()) {
//				 methodID= rs.getString("id");
//				 ownerMethodName=rs.getString("fullmethod"); 
//			 }
//			 String packagename=clazz.getQualifiedName().substring(0, clazz.getQualifiedName().lastIndexOf(".")); 
//			 String classname=clazz.getQualifiedName().substring(clazz.getQualifiedName().lastIndexOf(".")+1, clazz.getQualifiedName().length()); 
//
//			 String ownerclassID=null; 
//			  rs= st.executeQuery("SELECT * from classes where classes.package='"+packagename+"' and classes.name='"+classname+"'"); 
//			 while(rs.next()) {
//				  ownerclassID = rs.getString("id"); 
//			 }
//			 String packagenameMethodField=null; 
//			 String classnameMethodField=null; 
//			 if(methodField.getType().getQualifiedName().contains(".")) {
//				  packagenameMethodField=methodField.getType().getQualifiedName().substring(0, methodField.getType().getQualifiedName().lastIndexOf(".")); 
//				  classnameMethodField=methodField.getType().getQualifiedName().substring(methodField.getType().getQualifiedName().lastIndexOf(".")+1, methodField.getType().getQualifiedName().length()); 
//			 }
//			
//			 String fieldTypeclassid=null; 
//			 
//			 
//			 String fieldid=null; 
//			  rs= st.executeQuery("SELECT * from fields where fields.name='"+methodField.getShortRepresentation()+"' and fields.ownerclassid='"+ownerclassID+"'"); 
//			 while(rs.next()) {
//				   fieldid = rs.getString("id"); 
//			 }
//			 
//			 
//			
//			try {
//
//			  rs= st.executeQuery("SELECT * from classes where classes.package='"+packagenameMethodField+"' and classes.name='"+classnameMethodField+"'"); 
//			 while(rs.next()) {
//				 fieldTypeclassid = rs.getString("id"); 
//			 }
//			 }catch(Exception e) {
//				 
//			 }
//			 String s= methodField.getShortRepresentation()+","+methodID+","+ownerclassID; 
//			 if(ownerclassID!=null && methodID!=null && !fieldMethods.contains(s)) {
//				 String statement8= "INSERT INTO `fieldaccesses`(`fieldid`, `fieldname`,  `accessingclassname`,"
//				 		+ " `accessingclassid`,`accessingmethodname`,"
//				 		+ " `accessingmethodid`, "
//				 		+ "`read`"
//				 		+ ") VALUES"
//				 		+ " ('"+	 fieldid+"','" +methodField+"','"  +classname
//				 		+"','" +ownerclassID+"','"+ ownerMethodName
//				 		+"','"+methodID+"','"+
//				 		0
//						 +"')";
//				   	   	 st2.executeUpdate(statement8); 
//				 
//				   	     fieldMethods.add(s); 
//			 }
//		}
//		
//			
//		
//		// TODO Auto-generated method stub
//		
//	}
//
//	////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////
//private void retrieveMethodFields(List<CtFieldAccess> methodFields, CtMethod myMethod, CtType<?> clazz, Statement st, Statement st2,
//			List<String> fieldMethods) throws SQLException {
//	for(CtFieldAccess methodField : methodFields) {
//	 	System.out.println(methodField+"   "+myMethod.getSignature()+"  "+myMethod.getDeclaringType().getQualifiedName());
//	 	String methodID=null; 
//	 	String ownerMethodName=null; 
//	 	ResultSet rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+myMethod.getDeclaringType().getQualifiedName()+"."+myMethod.getSignature()+"'"); 
//		 while(rs.next()) {
//			 methodID= rs.getString("id");
//			 ownerMethodName=rs.getString("fullmethod"); 
//		 }
//		 
//		 
//		 String ownerclassID=null; 
//		 String packageName=clazz.getQualifiedName().substring(0, clazz.getQualifiedName().lastIndexOf(".")); 
//		 String className=clazz.getQualifiedName().substring(clazz.getQualifiedName().lastIndexOf(".")+1, clazz.getQualifiedName().length()); 
//
//		  rs= st.executeQuery("SELECT * from classes where classes.package='"+packageName+"' and classes.name='"+className+"'"); 
//		 while(rs.next()) {
//			  ownerclassID = rs.getString("id"); 
//		 }
//		 String packageName2=null; 
//		 String className2=null; 
//		 if(methodField.getType()!=null) {
//			 if(methodField.getType().getQualifiedName().contains(".")) {
//				 packageName2=methodField.getType().getQualifiedName().substring(0, methodField.getType().getQualifiedName().lastIndexOf(".")); 
//				  className2=methodField.getType().getQualifiedName().substring(methodField.getType().getQualifiedName().lastIndexOf(".")+1, methodField.getType().getQualifiedName().length()); 
//			 }
//			 
//		 }
//		
//		 String fieldTypeclassid=null; 
//		 try {
//			 rs= st.executeQuery("SELECT * from classes where classes.package='"+packageName2+"' and classes.name='"+className2+"'"); 
//			 while(rs.next()) {
//				 fieldTypeclassid = rs.getString("id"); 
//			 }
//		 }catch(Exception e) {
//			 
//		 }
//		 String fieldid=null; 
//		 rs= st.executeQuery("SELECT * from fields where fields.name='"+methodField+"' and fields.ownerclassid='"+ownerclassID+"'"); 
//		 while(rs.next()) {
//			 fieldid = rs.getString("id"); 
//		 }
//		  
//		 String s= methodField.getShortRepresentation()+","+methodID+","+ownerclassID; 
//		 if(ownerclassID!=null && methodID!=null && !fieldMethods.contains(s) && fieldid!=null) {
//			 String statement8= "INSERT INTO `fieldaccesses`(`fieldid`, `fieldname`,  `accessingclassname`,"
//			 		+ " `accessingclassid`,`accessingmethodname`,"
//			 		+ " `accessingmethodid`, "
//			 		+ "`read`"
//			 		+ ") VALUES"
//			 		+ " ('"+	 fieldid+"','" + methodField+"','" +clazz.getQualifiedName()+"','"  +ownerclassID
//			 		+"','" +ownerMethodName+"','"+ methodID
//			 		+"','"+1
//					 +"')";
//			   	   	 st2.executeUpdate(statement8); 
//			 
//			   	     fieldMethods.add(s); 
//		 }
//	}
//	
//		
//	}
/////////////////////////////////////////////////////////////////////////////////////////////////////
//
//private void retrieveFieldClasses(List<CtField> fields, Statement st, List<String> fieldClasses, Statement st2) throws SQLException { 
//	for(CtField myField : fields) {
//	System.out.println(myField.getSimpleName()+"   "+myField.getDeclaringType().getQualifiedName()+"  "+myField.getType().getQualifiedName());
//	String fieldTypeClassID="0"; 
//	String packageNamefieldType=null; 
//	String classnamefieldType=null; 
//	if(myField.getType().getQualifiedName().contains(".")) {
//		 packageNamefieldType=myField.getType().getQualifiedName().substring(0, myField.getType().getQualifiedName().lastIndexOf(".")); 
//		 classnamefieldType=myField.getType().getQualifiedName().substring( myField.getType().getQualifiedName().lastIndexOf(".")+1, myField.getType().getQualifiedName().length()); 
//	}
//	
//
//	 ResultSet rs= st.executeQuery("SELECT * from classes where classes.package='"+packageNamefieldType+"' and name='"+classnamefieldType+"'"); 
//		 while(rs.next()) {
//			fieldTypeClassID= rs.getString("id"); 
//		 }
//		 
//		 
//		 String packageNamedeclaringType=myField.getDeclaringType().getQualifiedName().substring(0, myField.getDeclaringType().getQualifiedName().lastIndexOf(".")); 
//		 String classnamefielddeclaringType=myField.getDeclaringType().getQualifiedName().substring( myField.getDeclaringType().getQualifiedName().lastIndexOf(".")+1, myField.getDeclaringType().getQualifiedName().length()); 
//			
//			
//			
//		String declaringTypeClassID=null; 
//	  rs= st.executeQuery("SELECT * from classes where classes.package='"+packageNamedeclaringType+"' and name='"+classnamefielddeclaringType+"'"); 
//	 while(rs.next()) {
//		declaringTypeClassID= rs.getString("id"); 
//	 }
//	 
//	 String fieldClass= myField.getSimpleName()+","+fieldTypeClassID+","+declaringTypeClassID; 
//	 
//	 if(declaringTypeClassID!=null && myField.getSimpleName()!=null && !fieldClasses.contains(fieldClass) && fieldTypeClassID!=null) {
//		 
//	  String statement8= "INSERT INTO `fields`(`name`, `typeclassid`,  `typeclassname`, `ownerclassid`,`ownerclassname`) VALUES ('"+
//			myField.getSimpleName()+"','" +fieldTypeClassID+"','"  +myField.getType().getQualifiedName()+"','" +declaringTypeClassID+"','"+myField.getDeclaringType().getQualifiedName()+"')";
//	   st2.executeUpdate(statement8); 
//	fieldClasses.add(fieldClass); 
//		 
//		 
//		 
//	 }else {
//		 
//		  String statement8= "INSERT INTO `fields`(`name`, `typeclassid`,  `typeclassname`, `ownerclassid`,`ownerclassname`) VALUES ('"+
//				myField.getSimpleName()+"','" +0+"','"  +0+"','" +declaringTypeClassID+"','"+myField.getDeclaringType().getQualifiedName()+"')";
//		   st2.executeUpdate(statement8); 
//		fieldClasses.add(fieldClass); 
//	 }
//	 
//	 
//	
//	
//}}
//
//////////////////////////////////////////////////
//
//private void retrieveConstructorParams(List<CtConstructor> constructors, Statement st, Statement st2, List<String> mylist) throws SQLException {
//		// TODO Auto-generated method stub
//	
//	for(CtConstructor constructor: constructors) {
//		List<CtParameter> params = constructor.getParameters(); 
//		
//		for(CtParameter param: params) {
//			String parameterClassID="0"; 
//			String formattedCons=constructor.getSignature().substring(0, constructor.getSignature().indexOf("("))+".-init-"+constructor.getSignature().substring(constructor.getSignature().indexOf("("), constructor.getSignature().length()); 
//			System.out.println(constructor.getSignature()+" ======  "+param);
//			String packageName = null; 
//			String className=null; 
//			if(param.getType().getQualifiedName().contains(".")) {
//				 packageName = param.getType().getQualifiedName().substring(0, param.getType().getQualifiedName().lastIndexOf(".")); 
//				 className = param.getType().getQualifiedName().substring(param.getType().getQualifiedName().lastIndexOf(".")+1, param.getType().getQualifiedName().length());
//			}
//			 
//			 ResultSet rs= st.executeQuery("SELECT * from classes where classes.package='"+packageName+"' and classes.name='"+className+"'"); 
//			 while(rs.next()) {
//				  parameterClassID= rs.getString("id"); 
//				 
//				 
//			 }
//			  rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+formattedCons+"'"); 
//	       while(rs.next()) {
//	    	   String classid = rs.getString("ownerclassid"); 
//	    	   String classname =rs.getString("ownerclassname"); 
//	    	   String methodID =rs.getString("id"); 
//	    	   
//	    	   System.out.println(classid);
//	    	   String newparamList = param+","+parameterClassID+","+classid+","+methodID+","+0; 
//	    	   if(classid!=null && methodID!=null && !mylist.contains(newparamList)) {
////		    	   if(parameterClassID!=null && classid!=null && methodID!=null ) {
//
//	    		   String mycons=""; 
//	    		   try {
//		    		    mycons= constructor.toString(); 
//
//	    		   }catch(Exception ex) {
//	    			   
//	    		   }
//	    		   String constructor2 = mycons.replaceAll("'", ""); 
//	    		   String statement8= "INSERT INTO `parameters`(`name`, `typeclassname`,  `typeclassid`, `ownerclassid`,`ownerclassname`, `ownermethodid`, `ownermethodname`, `isreturn`) VALUES ('"+
//	    		    	   param+"','" +param.getType().getQualifiedName()+"','"  +parameterClassID+"','" +classid+"','"+classname+"','"+methodID+"','"+formattedCons+"','"+0+"')";
//	    		    	   st2.executeUpdate(statement8); 
//	    		    	   mylist.add(newparamList); 
//	    	   }
//	    	  
//	       }
//	       
//	       
//	
//	       
//	       
//		
//		
//		}
//	}
//	}
//
//////////////////////////////////////////////////
//private void retrieveMethodParams(List<CtMethod> methods, Statement st, Statement st2, List<String> mylist) throws SQLException {
//	// TODO Auto-generated method stub
//for(CtMethod method: methods) {
//	List<CtParameter> params = method.getParameters(); 
//	for(CtParameter param: params) {
//		String parameterClassID="0"; 
//		System.out.println(method.getSignature()+" ======  "+param);
//		String packageName = null; 
//		String className=null; 
//		if(param.toString().contains(".")) {
//			 packageName = param.getType().getQualifiedName().substring(0, param.getType().toString().lastIndexOf(".")); 
//			 className = param.getType().getQualifiedName().substring(param.getType().toString().lastIndexOf(".")+1, param.getType().toString().length()); 
//		}
//		
//
//		 ResultSet rs= st.executeQuery("SELECT * from classes where classes.package='"+packageName+"'and classes.name='"+className+"'"); 
//		 while(rs.next()) {
//			  parameterClassID= rs.getString("id"); 
//			 
//			 
//		 }
//		 String fullmethod = method.getDeclaringType().getQualifiedName()+"."+method.getSignature();
//		 System.out.println(fullmethod);
//		  rs= st.executeQuery("SELECT * from methods where methods.fullmethod='"+fullmethod+"'"); 
//    while(rs.next()) {
// 	   String classid = rs.getString("ownerclassid"); 
// 	   String classname =rs.getString("ownerclassname"); 
// 	   String methodID =rs.getString("id"); 
// 	   
// 	   System.out.println(classid);
// 	   String newparamList = param+","+parameterClassID+","+classid+","+methodID+","+0; 
// 	   String constructor2 = ""; 
// 	   if( methodID!=null && !mylist.contains(newparamList)) {
////     	   if(parameterClassID!=null && classid!=null && methodID!=null ) {
//
// 		   try {
//     		    constructor2 = method.toString().replaceAll("'", ""); 
//
// 		   }
// 		   catch(Exception ex) {
// 			   
// 		   }
// 		   String statement8= "INSERT INTO `parameters`(`name`, `typeclassname`,   `typeclassid`,`ownerclassid`, `ownerclassname`, `ownermethodid`,`ownermethodname`, `isreturn`) VALUES ('"+
// 		    	   param+"','" +param.getType().getQualifiedName()+"','"  +parameterClassID+"','" +classid+"','"+classname+"','"+methodID+"','"+fullmethod+"','"+0+"')";
// 		    	   st2.executeUpdate(statement8); 
// 		    	   mylist.add(newparamList); 
// 	   }
// 	  
// 	   CtTypeReference returnType = method.getType(); 
// 	  String packageNameReturn =null; 
// 	  String classNameReturn=null; 
// 	   if(returnType.toString().contains(".")) {
// 		   packageNameReturn = returnType.getQualifiedName().substring(0, returnType.toString().lastIndexOf(".")); 
// 			 classNameReturn = returnType.getQualifiedName().substring(returnType.toString().lastIndexOf(".")+1, returnType.toString().length()); 
// 	   }
// 	  
// 	   
// 	   
// 	   String returnTypeclassID = null; 
// 	    rs= st.executeQuery("SELECT * from classes where classes.package='"+packageNameReturn+"'and classes.name='"+classNameReturn+"'"); 
//		 while(rs.next()) {
//   	    newparamList = returnType.toString()+","+returnTypeclassID+","+classid+","+methodID+","+1; 
//
//   	   if(parameterClassID!=null && classid!=null && methodID!=null && !mylist.contains(newparamList)) {
////   		   if(parameterClassID!=null && classid!=null && methodID!=null ) {
//			returnTypeclassID= rs.getString("id"); 
//			String method2=""; 
//			try {
//				method2=method.toString().replaceAll("'", "");
//			}
//			catch(Exception e) {
//				
//			}
//			 String statement8= "INSERT INTO `parameters`(`name`, `typeclassname`,  `typeclassid`, `ownerclassid`,`ownerclassname`, `ownermethodid`, `ownermethodname`, `isreturn`) VALUES ('"+
//					returnType.toString()+"','" +returnType.toString()+"','"  +returnTypeclassID+"','" +classid+"','"+classname+"','"+methodID+"','"+fullmethod+"','"+1+"')";
//		    	   st2.executeUpdate(statement8); 
//		    	   mylist.add(newparamList); 
//
//			 
//		 }
//   	   else if( !mylist.contains(newparamList)){
//			 String statement8= "INSERT INTO `parameters`(`name`, `typeclassname`,  `typeclassid`, `ownerclassid`,`ownerclassname`, `ownermethodid`, `ownermethodname`, `isreturn`) VALUES ('"+
//						returnType.toString()+"','" +"null"+"','"  +returnTypeclassID+"','" +classid+"','"+classname+"','"+methodID+"','"+fullmethod+"','"+1+"')";
//			    	   st2.executeUpdate(statement8); 
//			    	   mylist.add(newparamList); 
//		 }
//		 }
//    }
//    
//    
//
//    
//    
//	
//	
//	}
//}
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
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	

}
