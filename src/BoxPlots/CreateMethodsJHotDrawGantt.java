package BoxPlots;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import JHotDraw.DBDemo3JHotDraw2;
import model.Clazz;
import model.Method;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.factory.ClassFactory;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.InterfaceFactory;
import spoon.reflect.factory.MethodFactory;


	public class CreateMethodsJHotDrawGantt {



		public static void main(String[] args) throws IOException, SQLException {
//			createFiles("gantt"); 
//			createFiles("jhotdraw"); 
			createFiles("chess"); 
			createFiles("itrust"); 

		}
		
		public static void createFiles(String ProgramName) throws SQLException, IOException {
			
		
		
		
		    
		JSONArray methodList = parse("database/"+ProgramName+"/methods.json");
		for (Object o : methodList)
		{
			JSONObject mymethod = (JSONObject) o;
			String methodid = mymethod.get("id").toString();
			String method = mymethod.get("method").toString();
			
			
			File myFile = new File("log/"+ProgramName+"/methods/"+methodid+".txt");

			 if (myFile.createNewFile()) {
			        System.out.println("File created: " + myFile.getName());
			      } 
			FileWriter myWriter = new FileWriter("log/"+ProgramName+"/methods/"+methodid+".txt");
		      myWriter.write(method);
		      myWriter.close();
			
			
		}

	    	
	    

}
		
		private static JSONArray parse(String path) {
			JSONParser jsonParser = new JSONParser();
			File file = new File(path);
			try (FileReader reader = new FileReader(file.getAbsolutePath())) {
				return (JSONArray) jsonParser.parse(reader);
			}
			catch (Exception ex) { return null; }
		}
	}
