package BoxPlots;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileProcessorGanttJHotDrawReq {
	
	
	
	public static void main(String[] args) throws IOException 
	
	  { 
		String programlowercase="gantt"; 
		String programUppercase="Gantt"; 

		String path ="log/"+programlowercase+"/requirements"; //change to either gantt OR jhotdraw 
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
        FileWriter fw = new FileWriter("log\\"+programlowercase+"\\VSMTraces.json");
		fw.write("{");

		for (File file : listOfFiles) {
			String path2=""; 
		    if (file.isFile()) {
		    	 path2 =path+"\\"+file.getName(); 
		    	System.out.println(file.getName());
		        System.out.println(path2);
		        List<String> lines = Files.readAllLines(Paths.get(path2), StandardCharsets.US_ASCII);
		      
		        for(String line: lines) {
		        	//change to either Gantt or JHotDraw
		        	line=line.replaceAll("C:\\\\Users\\\\mouna\\\\ownCloud\\\\Mouna Hammoudi\\\\dumps\\\\Python\\\\data\\\\methods"+programUppercase+"\\\\", ""); 
		        	line=line.replaceAll("0\\.0\\:", "N:"); 
		        	line=line.replaceAll(".txt", ""); 
		        	
		        	if(line.contains(":")) {
		        		String substring = line.substring(0, line.indexOf(":")); 
			        	if(isNumeric(substring))  line=line.replaceAll("^.*?(:)", "T:"); 
		        	}
		        	line=line.replaceAll(": ", ";"); 
		        	if(!line.contains("Score") && !line.contains("[") && !line.contains("test_VSM")) {
		        		line=line+";"+file.getName(); 
			        	String[] parts = line.split(";"); 
			        	System.out.println(line);
			        	String req=parts[2]; 
			        	String trace=parts[0]; 
			        	String methodID=parts[1]; 
			        	
			        		fw.write(" \""+req+"-"+methodID+"\":\""+trace+"\",");
			        	
			        	fw.write("\n");
		        	}
		        
		        }
		        
		       
		    }
		    
		    
		}
		 fw.write("}");
		 fw.close(); 
	  }

	private static boolean isNumeric(String string) {
		// TODO Auto-generated method stub
		 boolean numeric = true;

	        try {
	            Double num = Double.parseDouble(string);
	        } catch (NumberFormatException e) {
	            numeric = false;
	        }

	        if(numeric)
	           return true; 
	        else
	            return false; 
	    }
	}

