package JHotDraw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Chess.SubjectTSubjectNObject;

public class ReplaceCommaWithDot {
	public static void main(String[] args) throws SQLException, IOException {
		AddColumns();

	}

	private static void AddColumns() throws IOException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\mouna\\new_workspace\\TracePredictor\\src\\JHotDrawFiles\\JHotDrawMethodsFormatted.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
	 	 File FileWritten = new File("C:\\Users\\mouna\\new_workspace\\TracePredictor\\src\\JHotDrawFiles\\JHotDrawMethodsFormatted2.txt");
	 	  FileWriter fw = new FileWriter(FileWritten);
		  BufferedWriter bw = new BufferedWriter(fw);
		String line= bufferedReader.readLine();
		List<SubjectTSubjectNObject> mylist= new ArrayList<SubjectTSubjectNObject>(); 
		try {
			
			int  counter=1; 
			int counter2=1; 
			while (line != null) {
				System.out.println(counter);
				System.out.println(line);
				String[] splittedline = line.split("\\,", -1); 
				
				String s=""; 
				s=splittedline[0]; 
				
				try {
					Integer.parseInt(splittedline[1]); 
					
				}catch(NumberFormatException  n) {
//					System.out.println("yes");
					String[] mysecondsplit = splittedline[0].split("\\."); 
					if(mysecondsplit[mysecondsplit.length-1].equals(splittedline[1])) {
						s=splittedline[0]+".-init-";
					}
					else{
						s=splittedline[0]+"."+splittedline[1];
					}
				
					counter=2; 
				}
				
				for(int i=counter; i<splittedline.length; i++) {
					s=s+","+splittedline[i]; 
				}
				bw.write(s);
				bw.newLine();
				line=bufferedReader.readLine(); 
				counter2++; 
			}
			fileReader.close();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		//st.executeUpdate("SELECT * FROM `traces` where method LIKE `% %`"); 
	
	}
}
