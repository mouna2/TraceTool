package BoxPlots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class BoxPlotFileConverter {
	public static enum  Trace  {Trace, NoTrace}; 
	public static void main(String[] args) throws Exception 
	  { 
	    // pass the path to the file as a parameter 
		Trace mytrace = Trace.Trace; 
		String basePath = new File("").getAbsolutePath();
		//expect percentage column and Trace precision column +NoTrace precision column 
	    String path = new File(basePath).getAbsolutePath()+"\\src\\testLog\\ErrorRecallN.csv";
	    
	    FileReader fr = new FileReader(path); 
	    BufferedReader reader = new BufferedReader(fr); 
	    int i; 
	    String line = reader.readLine();
	    LinkedHashMap<String, List<String>> myhashmap = new LinkedHashMap<String, List<String>>(); 
	    line = reader.readLine();
	    while (line != null) {
//			System.out.println(line);
			// read next line
	    	line=line.replaceAll(",,", ", , "); 
			String[] parts = line.split(","); 
			List<String> mylist = new ArrayList<String>(); 
			if(myhashmap.get(parts[parts.length-1].trim().replaceAll(" ", ""))!=null) {
				mylist = myhashmap.get(parts[parts.length-1].trim().replaceAll(" ", "")); 
			}
			mylist.add(parts[1]+","+parts[2]); 
			myhashmap.put(parts[parts.length-1].trim().replaceAll(" ", ""), mylist); 
			line = reader.readLine();

		}
	   
	    if(mytrace==Trace.Trace) {
	    	
	    	System.out.print("value1=[");
	    	int NotComputed=0; 
	    	int Computed=0; 
	    	int total=0; 

			for(String myelement :myhashmap.get("0")) {
				if(!myelement.split(",")[0].trim().equals("") ) {
					System.out.print(myelement.split(",")[0]+", ");
					Computed++; 
				}else {
					NotComputed++; 
				}

    		total++; 
    		}
			System.out.println("]");
			//System.out.println(NotComputed+"    "+total);
			NotComputed=0; 
			Computed=0; total=0;
			total=0; 
	    	System.out.print("value2=[");
					for(String myelement :myhashmap.get("]0-20[")) {
					if(!myelement.split(",")[0].trim().equals("") ) {
						System.out.print(myelement.split(",")[0]+", ");
						Computed++; 
					}else {
						NotComputed++; 
					}
					total++; 
	    		}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);
					NotComputed=0; 
					Computed=0; total=0;
					System.out.print("value3=[");
					for(String myelement :myhashmap.get("[20-40[")) {
						if(!myelement.split(",")[0].trim().equals("") ) {
							System.out.print(myelement.split(",")[0]+", ");
							Computed++; 
						}else {
							NotComputed++; 
						}

		    			total++; 
		    		}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);

					System.out.print("value4=[");
					NotComputed=0; 
					Computed=0; total=0;
					for(String myelement :myhashmap.get("[40-60[")) {
						if(!myelement.split(",")[0].trim().equals("") ) {
							System.out.print(myelement.split(",")[0]+", ");
							Computed++; 
						}else {
							NotComputed++; 
						}

		    			total++; 
		    		}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);

					System.out.print("value5=[");
					NotComputed=0; 
					Computed=0; total=0;
					for(String myelement :myhashmap.get("[60-80[")) {
						if(!myelement.split(",")[0].trim().equals("") ) {
							System.out.print(myelement.split(",")[0]+", ");
							Computed++; 
						}else {
							NotComputed++; 
						}

		    			
						total++; 
					}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);

					System.out.print("value6=[");
					NotComputed=0; 
					Computed=0; total=0;
					for(String myelement :myhashmap.get("[80-100]")) {
						if(!myelement.split(",")[0].trim().equals("") ) {
							System.out.print(myelement.split(",")[0]+", ");
							Computed++; 
						}else {
							NotComputed++; 
						}

		    			total++; 
		    		}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);

					
				

	    }
	    else if(mytrace==Trace.NoTrace) {
	    	
	    	
	    	int	Computed=0; 
	    	int	NotComputed=0; int total=0;
	    	System.out.print("value1=[");

			for(String myelement :myhashmap.get("0")) {
				if(!myelement.split(",")[1].trim().equals("")) {
					System.out.print(myelement.split(",")[1]+", ");
					Computed++; 
				}else {
					NotComputed++; 
				}

    			total++; 
    		}
			System.out.println("]");
			//System.out.println(NotComputed+"    "+total);
			Computed=0; 
			NotComputed=0; total=0;
	    	System.out.print("value2=[");
					for(String myelement :myhashmap.get("]0-20[")) {
					if(!myelement.split(",")[1].trim().equals("")) {
						System.out.print(myelement.split(",")[1]+", ");
						Computed++; 
					}else {
						NotComputed++; 
					}
	    			total++; 
	    		}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);

					Computed=0; 
					NotComputed=0; total=0;
					System.out.print("value3=[");
					for(String myelement :myhashmap.get("[20-40[")) {
						if(!myelement.split(",")[1].trim().equals("")) {
							System.out.print(myelement.split(",")[1]+", ");
							Computed++; 
						}else {
							NotComputed++; 
						}

		    			total++; 
		    		}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);

					Computed=0; 
					NotComputed=0; total=0;
					System.out.print("value4=[");

					for(String myelement :myhashmap.get("[40-60[")) {
						if(!myelement.split(",")[1].trim().equals("")) {
			    			System.out.print(myelement.split(",")[1]+", ");
			    			Computed++; 

						}else {
							NotComputed++; 
						}
						total++; 
		    		}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);
					Computed=0; 
					NotComputed=0; total=0;
					System.out.print("value5=[");

					for(String myelement :myhashmap.get("[60-80[")) {
						if(!myelement.split(",")[1].trim().equals("")) {
			    			System.out.print(myelement.split(",")[1]+", ");
			    			Computed++; 
						}else {
							NotComputed++; 
						}

						total++; 
					}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);
					Computed=0; 
					NotComputed=0; total=0;
					System.out.print("value6=[");

					for(String myelement :myhashmap.get("[80-100]")) {
						if(!myelement.split(",")[1].trim().equals("")) {
			    			System.out.print(myelement.split(",")[1]+", ");
			    			Computed++; 
						}else {
							NotComputed++; 
						}
						total++; 
		    		}
					System.out.println("]");
					//System.out.println(NotComputed+"    "+total);
				

	    }


		//CODE FOR PRINTINT AS AN EXCEL TABLE 
//		List<String> mylist1 = myhashmap.get("]0-20["); 
//		List<String> mylist2 = myhashmap.get("[20-40["); 
//		List<String> mylist3 = myhashmap.get("[40-60["); 
//		List<String> mylist4 = myhashmap.get("[60-80["); 
//		List<String> mylist5 = myhashmap.get("[80-100]"); 
//		
//		int [] mynumbers = {mylist1.size(), mylist2.size(), mylist3.size(), mylist4.size(), mylist5.size()}; 
//		int max = getMaxValue(mynumbers);
////		System.out.println(max);
//		System.out.println("]0-20[, [20-40[, [40-60[, [60-80[,  [80-100]");
//		for(int k=0; k<max; k++) {
//			if(k<mylist1.size() && mytrace==Trace.Trace)
//				System.out.print(mylist1.get(k).split(",")[0]+",");
//			else if(k<mylist1.size() && mytrace==Trace.NoTrace)
//				System.out.print(mylist1.get(k).split(",")[1]+",");
//			else 
//				System.out.print(",");
//			
//			////////////////////////////
//			
//			if(k<mylist2.size() && mytrace==Trace.Trace)
//				System.out.print(mylist2.get(k).split(",")[0]+",");
//			else if(k<mylist2.size() && mytrace==Trace.NoTrace)
//				System.out.print(mylist2.get(k).split(",")[1]+",");
//			else 
//				System.out.print(",");
//			
//			////////////////////
//			
//			if(k<mylist3.size() && mytrace==Trace.Trace)
//				System.out.print(mylist3.get(k).split(",")[0]+",");
//			else if(k<mylist3.size() && mytrace==Trace.NoTrace)
//				System.out.print(mylist3.get(k).split(",")[1]+",");
//			else 
//				System.out.print(",");
//			
//			//////////////////
//			
//			if(k<mylist4.size() && mytrace==Trace.Trace)
//				System.out.print(mylist4.get(k).split(",")[0]+",");
//			else if(k<mylist4.size() && mytrace==Trace.NoTrace)
//				System.out.print(mylist4.get(k).split(",")[1]+",");
//			else 
//				System.out.print(",");
//			
//			///////////////////
//			if(k<mylist5.size() && mytrace==Trace.Trace)
//				System.out.print(mylist5.get(k).split(",")[0]+",");
//			else if(k<mylist5.size() && mytrace==Trace.NoTrace)
//				System.out.print(mylist5.get(k).split(",")[1]+",");
//			else 
//				System.out.print(",");
//			
//			
//			System.out.println();
//		}
		reader.close();
	  } 
	
	public static int getMaxValue(int[] numbers){
		  int maxValue = numbers[0];
		  for(int i=1;i < numbers.length;i++){
		    if(numbers[i] > maxValue){
			  maxValue = numbers[i];
			}
		  }
		  return maxValue;


}
}
