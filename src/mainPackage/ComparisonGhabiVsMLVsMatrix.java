package mainPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Fibonacci Series using Recursion
class ComparisonGhabiVsMLVsMatrix
{
	public static void main (String [] args) throws IOException {
		File file=new File("C:\\Users\\mouna\\git\\TraceTool\\TraceTool\\log\\chess\\basics-val-ghabi.txt");    //creates a new file instance  
		FileReader fr=new FileReader(file);   //reads the file  
		BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
		StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
		String line;  
		HashMap<String, List<String>> myhashmap = new HashMap<>(); 
		int counter=0; 
		while((line=br.readLine())!=null)  
		{  
				String[] elems = line.split(";"); 
				List<String> mylist= new ArrayList<>(); 
				mylist.add(elems[1]); 
				for(int i=3; i<=19; i++) {
					mylist.add(elems[i]); 

				}
				

				myhashmap.put(elems[2]+"-"+elems[0],mylist); 
			
		System.out.println();
		counter++; 
		}  
		fr.close();    //closes the stream and release the resources  
		
		/*************************************************************************************************************/
		 file=new File("C:\\Users\\mouna\\git\\TraceTool\\TraceTool\\log\\chess\\basics-val-v2-extended.txt");    //creates a new file instance  
		 fr=new FileReader(file);   //reads the file  
		 br=new BufferedReader(fr);  //creates a buffering character input stream  
		 sb=new StringBuffer();    //constructs a string buffer with no characters  
		 counter=0; 
		while((line=br.readLine())!=null)  
		{  
			if(counter!=0) {
				String[] elems = line.split(";"); 
				
				

				List<String> mylist= myhashmap.get(elems[2]+"-"+elems[0]); 
				
				mylist.add(elems[elems.length-1]); 
				mylist.add(elems[elems.length-2]); 
				mylist.add(elems[elems.length-3]); 
				mylist.add(elems[elems.length-4]); 

			}
		System.out.println();
		counter++; 
		}  
		fr.close();  
		
		/*********************************************************************************************************************************/
		 file=new File("C:\\Users\\mouna\\git\\TraceTool\\TraceTool\\log\\chess\\ML.txt");    //creates a new file instance  
		 fr=new FileReader(file);   //reads the file  
		 br=new BufferedReader(fr);  //creates a buffering character input stream  
		 sb=new StringBuffer();    //constructs a string buffer with no characters  
		 counter=0; 
		while((line=br.readLine())!=null)  
		{  
			if(counter!=0) {
				String[] elems = line.split(","); 
				
				

				List<String> mylist= myhashmap.get(elems[2]+"-"+elems[3]); 
				
				mylist.add(elems[elems.length-2]); 
				

			}
		System.out.println();
		counter++; 
		}  
		fr.close();  
		
		
		
		/**********************************************/
		for(String mykey: myhashmap.keySet()) {
			System.out.print(mykey+";");
			List<String> list = myhashmap.get(mykey); 
			for(String elem: list) {
				System.out.print(elem+";");
			}
			System.out.println();
		}
		
		
		}  

	


}