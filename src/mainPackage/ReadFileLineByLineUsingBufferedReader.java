package mainPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFileLineByLineUsingBufferedReader {

	public static void main(String[] args) {
	    try {
	        List<String> list = Files.readAllLines(Paths.get("C:\\Users\\mouna\\ownCloud\\Mouna Hammoudi\\dumps\\Python\\dataMachineLearning.txt"));

	        
	        int last = 0;
	        for(String myLine : list) {
	            String[] array = myLine.split(",");
	            int counter = Integer.parseInt(array[0]);
	            int num = Integer.parseInt(array[1]);
	            if(counter == 1 && num > last+1) {
	            	int newnumber= num-last-1; 
//	                System.out.println("======"+newnumber);
	                int i=0; 
	                while(i<newnumber) {
	                	last=last+1; 
	                	for(int j=1; j<=8; j++) {
	                		System.out.println(j+","+last+",");
	                	}
	                	
	                	i++; 
	                }
	            }
	            System.out.println(array[0]+","+array[1]+","+array[2]);
	            last = num;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}}