package Gantt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MethodCallsExecutedFormatter {

	
	public static void main (String [] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\mouna\\new_workspace\\TraceGenerator\\src\\GanttFiles\\dataMethodCallsExecutedGantt.txt"));
		String line = reader.readLine(); 
		File myfile3 = new File("C:\\Users\\mouna\\new_workspace\\TraceGenerator\\src\\GanttFiles\\dataMethodCallsExecutedGanttFormatted2.txt");
		FileOutputStream myFileOutputStream3 = new FileOutputStream(myfile3);
		 BufferedWriter bwWriter = new BufferedWriter(new OutputStreamWriter(myFileOutputStream3));
		while(line!=null) {
			
			 String[] lines = line.split("---"); 
			 String caller =lines[0].substring(0, lines[0].indexOf(")")+1); 
			 String callee =lines[1].substring(0, lines[1].indexOf(")")+1); 
			 System.out.println(caller);
			 System.out.println(callee);
			 line = reader.readLine(); 
			 bwWriter.write(caller+"---"+callee);
			 bwWriter.newLine();
		}
		bwWriter.close();
	
	}
	
}
