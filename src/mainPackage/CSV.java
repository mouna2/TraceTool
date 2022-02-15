package mainPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Generated;

import BoxPlots.counts;
import evaluation.Seeder;
import model.Clazz;
import model.ClazzRTMCell;
import model.Method;
import model.MethodRTMCell;
import model.MethodRTMCellList;
import model.PredictionPattern;
import model.RTMCell;
import model.RTMCellList;
import model.Variable;
import model.VariableList;
import traceRefiner.TraceRefinerPredictionPattern;
import traceValidator.TraceValidatorPredictionPattern;
import weka.gui.ProgrammaticProperty;

public class CSV {
	 
	public static boolean AtLeastOneInstance=true; 

    static File file = new File("log\\data.txt");
    static File chessTestFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\chessTest.arff");
    static File ganttTestFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ganttTest.arff");
    static File itrustTestFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\itrustTest.arff");
    static File jhotdrawTestFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\jhotdrawTest.arff");

    static File ganttitrustjhotTrainFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ganttiTrustJHotTrain.arff");
    static File chessitrustjhotTrainFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\chessiTrustJHotTrain.arff");
    static File chessganttjhotTrainFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ChessGanttJHotTrain.arff");
    static File chessganttitrustTrainFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ChessGanttiTrustTrain.arff");


    public static boolean Seeding=false; 
    public static boolean step2=true; 
    CSV csv=new CSV();  
    static double[] TSeeds = new double[]{5,10,15,20,25,50,75}; 
    static double[] NSeeds = new double[]{0.5,1.0,1.5,2.0,2.5,5,10}; 
    static HashMap<String, List<MethodRTMCell>> mergedHashMap = new HashMap<>(); 

    static String headers="gold,Program,MethodType,"

    		+ "CallersT,CallersN,CallersU,"
    		+ "CallersCallersT,CallersCallersN,CallersCallersU,"
    		+ "CalleesT,CalleesN,CalleesU,"
    		+ "CalleesCalleesT,CalleesCalleesN,CalleesCalleesU,"
    		+ "CompleteCallersCallees,"
    		+ "classGold"
    		
    		
    		; 
    
    static String headersAtLeastOneInstance="gold,Program,MethodType,"

    		+ "CallersT,CallersN,CallersU,"
    		+ "CallersCallersT,CallersCallersN,CallersCallersU,"
    		+ "CalleesT,CalleesN,CalleesU,"
    		+ "CalleesCalleesT,CalleesCalleesN,CalleesCalleesU,classGold,VariableTraceValue"
    		
    		; 
    static String headersAtLeastOneReqMethodInstance="gold,Program,MethodType,"
    		+"RequirementID,MethodID,"
    		+ "CallersT,CallersN,CallersU,"
    		+ "CallersCallersT,CallersCallersN,CallersCallersU,"
    		+ "CalleesT,CalleesN,CalleesU,"
    		+ "CalleesCalleesT,CalleesCalleesN,CalleesCalleesU,classGold,VariableTraceValue"; 
    		//////////////////////////////
     static String headersStep2="gold,MethodType,"
    		    		+"PredictedTraceValue,MethodCategory,"
    		    		+ "CallersT,CallersN,CallersU,"
    		    		+ "CallersCallersT,CallersCallersN,CallersCallersU,"
    		    		+ "CalleesT,CalleesN,CalleesU,"
    		    		+ "CalleesCalleesT,CalleesCalleesN,CalleesCalleesU,classGold"
    		
    		;
     ///////////////////////////////
    static String headersAtLeastOneInstanceNoProgram="gold,MethodType,"

    		+ "CallersT,CallersN,CallersU,"
    		+ "CallersCallersT,CallersCallersN,CallersCallersU,"
    		+ "CalleesT,CalleesN,CalleesU,"
    		+ "CalleesCalleesT,CalleesCalleesN,CalleesCalleesU,classGold,VariableTraceValue"
    		
    		; 
	public static void main (String [] args) throws Exception {
		

		
		
		mergedHashMap.put("T", new ArrayList<MethodRTMCell>()); 
		mergedHashMap.put("N", new ArrayList<MethodRTMCell>()); 

	


		
		ArrayList<String> programs = new ArrayList<String>();
//		 if(!AtLeastOneInstance)
//			 writer.write(headers+"\n");
//		 else if(AtLeastOneInstance && !Seeding && !step2)
//			 writer.write(headersAtLeastOneReqMethodInstance+"\n");
//		 else if(AtLeastOneInstance && !Seeding && step2)
//			 writer.write(headersStep2+"\n");

			programs.add("chess");
//			programs.add("gantt");
//			programs.add("itrust");
//			programs.add("jhotdraw");
//			programs.add("vod"); 
			
//			programs.add("vod");
			System.out.println("countNoCalleesU,countLowCalleesU,countMediumCalleesU,countHighCalleesU,countNoCallersU,countLowCallersU,countMediumCallersU,"
					+ "countHighCallersU,NoCallersUAndNoCalleesU,LowCombination,MediumCombination,HighCombination"); 
			
		int i=0; 
		if(Seeding) {
			//MERGE ALL HASHMAPS 
			for(String programName: programs) {
					DatabaseInput.read(programName);
					System.out.println(programName);
					
					
					
					

					

				for(int k=0; k<TSeeds.length; k++) {
					for(int j=0; j<10; j++) {
						mergedHashMap=new HashMap<>(); 
						ArrayList<MethodRTMCell> List = new ArrayList<MethodRTMCell>(); 
						mergedHashMap.put("T", List); 
						mergedHashMap.put("N", List); 

						List<MethodRTMCell> Ts = new ArrayList<MethodRTMCell>(); 
						List<MethodRTMCell> Ns = new ArrayList<MethodRTMCell>(); 
						retrieveTsAndNs(programName, Ts, Ns); 
						 Ts = mergedHashMap.get("T"); 
						 Ns = mergedHashMap.get("N"); 
						setTraceValues(Ts, Ns); 
						List<MethodRTMCell> TsNs = SeedTtoN(Ts,Ns, TSeeds[k]); 
						String fileName="TtoN"+"-"+TSeeds[k]+"-"+j; 
						generateFile(TsNs,fileName, programName);
						System.out.println();
					}
				}
				
				
				for(int k=0; k<NSeeds.length; k++) {
					for(int j=0; j<10; j++) {
						mergedHashMap=new HashMap<>(); 
						ArrayList<MethodRTMCell> List = new ArrayList<MethodRTMCell>(); 
						mergedHashMap.put("T", List); 
						mergedHashMap.put("N", List); 
						
						
						List<MethodRTMCell> Ts = new ArrayList<MethodRTMCell>(); 
						List<MethodRTMCell> Ns = new ArrayList<MethodRTMCell>(); 
						 Ts = mergedHashMap.get("T"); 
						 Ns = mergedHashMap.get("N"); 
						retrieveTsAndNs(programName, Ts, Ns); 

						setTraceValues(Ts, Ns); 
						List<MethodRTMCell> TsNs = SeedNtoT(Ts,Ns, NSeeds[k]); 
						String fileName="NtoT"+"-"+NSeeds[k]+"-"+j; 
						generateFile(TsNs,fileName, programName);
						System.out.println();
					}
				}
				
				
		}
		}else {
//			for(String programName: programs) {
//				DatabaseInput.read(programName);
//				System.out.println(programName);
//				
//			}
			List<String> mylistgantt = new ArrayList<>(); 	
			mylistgantt.add("gantt"); 
			
			List<String> mylistitrust = new ArrayList<>(); 	
			mylistitrust.add("itrust"); 
			
			List<String> mylistjhot = new ArrayList<>(); 	
			mylistjhot.add("jhotdraw"); 
			
			List<String> mylistchess = new ArrayList<>(); 	
			mylistchess.add("chess"); 
			
			
			List<String> mylistganttitrustjhot= new ArrayList<>(); 	
			mylistganttitrustjhot.add("gantt"); 
			mylistganttitrustjhot.add("itrust"); 
			mylistganttitrustjhot.add("jhotdraw"); 

			List<String> mylistchessitrustjhot= new ArrayList<>(); 	
			mylistchessitrustjhot.add("chess"); 
			mylistchessitrustjhot.add("itrust"); 
			mylistchessitrustjhot.add("jhotdraw"); 
			
			List<String> mylistchessganttjhot= new ArrayList<>(); 	
			mylistchessganttjhot.add("chess"); 
			mylistchessganttjhot.add("gantt"); 
			mylistchessganttjhot.add("jhotdraw"); 
			
			List<String> mylistchessganttitrust= new ArrayList<>(); 	
			mylistchessganttitrust.add("chess"); 
			mylistchessganttitrust.add("gantt"); 
			mylistchessganttitrust.add("itrust"); 
			
			FileWriter writer = new FileWriter(chessTestFile,false);
			generateCSVFile2(writer,mylistchess,chessTestFile);

			 writer = new FileWriter(ganttTestFile,false);
			generateCSVFile2(writer,mylistgantt, ganttTestFile);
			
			 writer = new FileWriter(itrustTestFile,false);
				generateCSVFile2(writer,mylistitrust, itrustTestFile);
				
				 writer = new FileWriter(jhotdrawTestFile,false);
					generateCSVFile2(writer,mylistjhot, jhotdrawTestFile);	
					
					
					 writer = new FileWriter(chessganttitrustTrainFile,false);
						generateCSVFile2(writer,mylistchessganttitrust, chessganttitrustTrainFile);	
						
				 writer = new FileWriter(chessganttjhotTrainFile,false);
							generateCSVFile2(writer,mylistchessganttjhot, chessganttjhotTrainFile);	
							
							 writer = new FileWriter(chessitrustjhotTrainFile,false);
								generateCSVFile2(writer,mylistchessitrustjhot, chessitrustjhotTrainFile);	
								
								 writer = new FileWriter(ganttitrustjhotTrainFile,false);
									generateCSVFile2(writer,mylistganttitrustjhot, ganttitrustjhotTrainFile);	
									
									
									System.out.println("DONE");

		  
		
		}
			
			

	

	}
	
		private static void generateCSVFile2(FileWriter writer, List<String> mylist, File file2) throws Exception {
		// TODO Auto-generated method stub
		    file2.createNewFile();

			 writer.write("@RELATION traces\n"+ 

				"@ATTRIBUTE gold	{T,N,U}\n"+ 
				"@ATTRIBUTE MethodType 	{Inner, Leaf, Root}\n"+
				"@ATTRIBUTE PredictedTraceValue {UndefinedTrace,NoTrace, Trace, NA}\n"+
				"@ATTRIBUTE MethodCategory {getter,setter,init,method, equals, toString, print, add, sort, compare, read, open, write, save, update}\n"+
				"@ATTRIBUTE Return {0,1}\n"+
				"@ATTRIBUTE MethodLOC {0,1, From2to5, From6to10, From11to20, From21}\n"+
				
//				"@ATTRIBUTE TClassTraces {0,From1to5, From6to10,From11to15,From16to20, From21}\n"+
				"@ATTRIBUTE TClassTraces {0,1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12, 13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34}\n"+			
				"@ATTRIBUTE NClassTraces {0,1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12, 13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34}\n"+			

				
				"@ATTRIBUTE CallerSize {0,From1to5, From6to10, From11to15, From16to20, From21}\n"+
				"@ATTRIBUTE CalleeSize {0,From1to5, From6to10, From11to15, From16to20, From21}\n"+
				
				"@ATTRIBUTE CallersT {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CallersN {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CallersU {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CallersCallersT {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CallersCallersN {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CallersCallersU {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CalleesT {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CalleesN {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CalleesU {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CalleesCalleesT {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CalleesCalleesN {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE CalleesCalleesU {Low, High, Medium, -1}  \n"+
				"@ATTRIBUTE classGold {Trace,NoTrace,UndefinedTrace} \n"
				+ "@DATA\n\n\n\n\n\n"
				); 

//			List<String> mylist = new ArrayList<>(); 	
////			mylist.add("chess"); 
//		   mylist.add("gantt"); 
////			mylist.add("itrust");
////			mylist.add("jhotdraw");
		
		
		
		
		

				for(String program: mylist) {
					
					DatabaseInput.read(program);		
					// TODO Auto-generated method stub
					counts callers = new counts(); 
					counts callersCallers= new counts(); 
					counts callees= new counts(); 
					counts calleesCallees= new counts(); 
		 
						for ( MethodRTMCell methodtrace : MethodRTMCell.Totalmethodtraces2HashMap.get(program).values()) {
//							System.out.println(methodtrace.getMethod().getName());
			            	if(!methodtrace.getGoldTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) {
			            		
			            		String MethodCategory=""; 
								if(methodtrace.getMethod().fullMethodName.contains("equals") ) {
									MethodCategory="equals"; 
								}else if(methodtrace.getMethod().fullMethodName.contains("get") ) {
									MethodCategory="getter"; 
								}
								else if(methodtrace.getMethod().fullMethodName.contains("set") ) {
									MethodCategory="setter"; 
								}else if(methodtrace.getMethod().fullMethodName.contains("init") ) {
									MethodCategory="init"; 
								}else if(methodtrace.getMethod().fullMethodName.contains("toString") ) {
									MethodCategory="toString"; 
								}
								else if(methodtrace.getMethod().fullMethodName.contains("print") ) {
									MethodCategory="print"; 
								}else if(methodtrace.getMethod().fullMethodName.contains("add") ) {
									MethodCategory="add"; 
								}else if(methodtrace.getMethod().fullMethodName.contains("sort") ) {
									MethodCategory="sort"; 
								}
								else if(methodtrace.getMethod().fullMethodName.contains("compare") ) {
									MethodCategory="compare"; 
								}
								else if(methodtrace.getMethod().fullMethodName.contains("read") ) {
									MethodCategory="read"; 
								}else if(methodtrace.getMethod().fullMethodName.contains("open") ) {
									MethodCategory="open"; 
								}
								else if(methodtrace.getMethod().fullMethodName.contains("write") ) {
									MethodCategory="write"; 
								}
								else if(methodtrace.getMethod().fullMethodName.contains("save") ) {
									MethodCategory="save"; 
								}else if(methodtrace.getMethod().fullMethodName.contains("update") ) {
									MethodCategory="update"; 
								}
								else {
									MethodCategory="method"; 
								}
			    				String gold= methodtrace.logGoldTraceValueString(); 

			    				String content = methodtrace.getMethod().getContent(); 
			    				
//			    				System.out.println(content);
			    				String NumLines=""; 
			    				
			    				
			    				int MethodLOC=countLines(content); 
			    				
			    				if(MethodLOC==0) NumLines="0"; 
			    				else if(MethodLOC==1) NumLines="1"; 
			    				else if(MethodLOC>=2 && MethodLOC<=5) NumLines="From2to5"; 
			    				else if(MethodLOC>=6 && MethodLOC<=10) NumLines="From6to10"; 
			    				else if(MethodLOC>=11 && MethodLOC<=20) NumLines="From11to20"; 
			    				else NumLines="From21"; 
			       		 		String s= gold+","; 
//			       		 		s=s+program+","; 
			       		 		String CallerSizeCategory= "";
			       		 		String CalleeSizeCategory= "";
			       		 		String TCountClassCategory= "";
			       		 		String NCountClassCategory= "";

					       		 int calleeSize=methodtrace.getCallees().size();
					       		 int callerSize=methodtrace.getCallers().size();
					       		 
					       		 int TCountClass= Integer.parseInt(methodtrace.getClazzRTMCell().getClazz().getTcount());
			       		 
//					       		if(TCountClass>=1 && TCountClass<=5) TCountClassCategory="From1to5";
//						       	 else if(TCountClass>=6 && TCountClass<=10) TCountClassCategory="From6to10";	
//						       	 else  if(TCountClass>10 && TCountClass<=15) TCountClassCategory="From11to15";
//					       		 else if(TCountClass>15 && TCountClass<=20) TCountClassCategory="From16to20";
//					       		 else if(TCountClass>20 ) TCountClassCategory="From21";
//					       		 else 
					       			 TCountClassCategory= String.valueOf(TCountClass);
					       			 
						       		 int NCountClass= Integer.parseInt(methodtrace.getClazzRTMCell().getClazz().getNcount());
					       			 NCountClassCategory= String.valueOf(NCountClass);

								 if(calleeSize>=1 && calleeSize<=5) CalleeSizeCategory="From1to5";
						       	 else
						       		 if(calleeSize>=6 && calleeSize<=10) CalleeSizeCategory="From6to10";
					       		 else if(calleeSize>10 && calleeSize<=15) CalleeSizeCategory="From11to15";
					       		 else if(calleeSize>15 && calleeSize<=20) CalleeSizeCategory="From16to20";
					       		 else if(calleeSize>20 ) CalleeSizeCategory="From21";
					       		 else CalleeSizeCategory= String.valueOf(calleeSize);

								 if(callerSize>=1 && callerSize<=5) CallerSizeCategory="From1to5";
								 else
									 if(callerSize>=6 && callerSize<=10) CallerSizeCategory="From6to10";
					       		 else if(callerSize>10 && callerSize<=15) CallerSizeCategory="From11to15";
					       		 else if(callerSize>15 && callerSize<=20) CallerSizeCategory="From16to20";
					       		 else if(callerSize>20 ) CallerSizeCategory="From21";
					       		 else CallerSizeCategory= String.valueOf(callerSize);
					       		 
					       		 
					       		 
					       		 
			       		 
			       		 		if(!methodtrace.getCallers().isEmpty() && !methodtrace.getCallees().isEmpty()) {
			       		 			s=s+"Inner,"; 
			       		 		}else if( methodtrace.getCallees().isEmpty() ) {
			       		 			s=s+"Leaf,"; 
			       		 		}else if( methodtrace.getCallers().isEmpty() ) {
			       		 			s=s+"Root,"; 
			       		 		}else if( methodtrace.getCallers().isEmpty() && methodtrace.getCallees().isEmpty()) {
			       		 			s=s+"Isolated,"; 
			       		 		}
			       		 		
			       		 		
			       		 		
			       		 	
			    				Seeder.seedInputClazzTraceValuesWithDeveloperGold();

			       		 		
			       		 		
//			    				 callers=generateCountsTNUAtLeastOneInstance(methodtrace.getCallers());
//			   		 			 callersCallers=generateCountsTNUAtLeastOneInstance(methodtrace.getCallers().getCallers());
//			   		 			 callees=generateCountsTNUAtLeastOneInstance(methodtrace.getCallees());
//					 			 calleesCallees=generateCountsTNUAtLeastOneInstance(methodtrace.getCallees().getCallees());
			       		 			 
			    				String name = methodtrace.getMethod().fullMethodName; 
			    				name=name.replaceAll(",", ""); 
			    				name=name.replaceAll(";", ""); 
			    				name=name.replaceAll("\\,", ""); 
			    				name=name.replaceAll("\\;", ""); 

				       		 		 callers=generateCountsTNU(methodtrace.getCallers());
				   		 			 callersCallers=generateCountsTNU(methodtrace.getCallers().getCallers());
				   		 			 callees=generateCountsTNU(methodtrace.getCallees());
						 			 calleesCallees=generateCountsTNU(methodtrace.getCallees().getCallees());
						 			TraceRefinerPredictionPattern.define();
						 			if (methodtrace.getClazzRTMCell().getTraceValue().equals(RTMCell.TraceValue.NoTrace))
										methodtrace.setPrediction(TraceRefinerPredictionPattern.Step1ClassNoTraceImpliesMethodNoTracePattern);
						 			 Clazz ret = methodtrace.getMethod().getReturnType().dataType; 
					    				String rettype="0"; 
//					    				System.out.println(ret);
					    				
					    				
					    				if(ret!=null) {
					    					rettype="1"; 
					    				}
						 	
						 				s=s+""
//						 				+methodtrace.getRequirement().ID+","+methodtrace.getMethod().ID+","
						 						+methodtrace.getPredictedTraceValue()+","+MethodCategory+","+rettype+","+NumLines+","+

						 						
										TCountClassCategory+","+NCountClassCategory+","+CallerSizeCategory+","+CalleeSizeCategory+","+
							 					
							 					callers.amountT+","+callers.amountN+","+callers.amountU+","; 
					   		 			s=s+callersCallers.amountT+","+callersCallers.amountN+","+callersCallers.amountU+","; 
							 			s=s+callees.amountT+","+callees.amountN+","+callees.amountU+","; 
							 			s=s+calleesCallees.amountT+","+calleesCallees.amountN+","+calleesCallees.amountU
							 					+","+
												ClazzRTMCell.clazzTraces2HashMap.get(methodtrace.getRequirement().ID+"-"+methodtrace.getClazzRTMCell().getClazz().ID).getGoldTraceValue(); 
							 			
						 				
						 			s=s+"\n"; 
									writer.write(s);
									
			            	}
						}
						
				}
						writer.close();
						System.out.println("done");
		
	}
		private static int countLines(String str){
			   String[] lines = str.split("\r\n|\r|\n");
			   return  lines.length;
			}
		private static void retrieveTsAndNs(String programName, List<MethodRTMCell> Ts, List<MethodRTMCell> Ns) {		
			LinkedHashMap<String, MethodRTMCell> clonedLinkedHashMap = (LinkedHashMap<String, MethodRTMCell> )MethodRTMCell.methodtraces2HashMap.clone(); 
	
	//  System.out.println(clonedLinkedHashMap);
		for ( String key : clonedLinkedHashMap.keySet()) {
	
			MethodRTMCell methodCell = clonedLinkedHashMap.get(key); 
			methodCell.ProgramName=programName; 
				if(methodCell.getGoldTraceValue().equals(RTMCell.TraceValue.Trace)){
					mergedHashMap.get("T").add(methodCell); 
				}
				else if(methodCell.getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace)){
					mergedHashMap.get("N").add(methodCell); 
				}
		}
		
		
		
	
			
		}
	/*/****************************************************************************/
	private static void setTraceValues(List<MethodRTMCell> Ts, List<MethodRTMCell> Ns) {
		for(  MethodRTMCell methodtrace: Ts) {
			methodtrace.setTraceValue(methodtrace.getGoldTraceValue());
		}
		for(  MethodRTMCell methodtrace: Ns) {
			methodtrace.setTraceValue(methodtrace.getGoldTraceValue());
		}
		
	}
	/*/****************************************************************************/

	private static List<MethodRTMCell> SeedTtoN(List<MethodRTMCell> Ts, List<MethodRTMCell> Ns, double perc) throws IOException {
		int numberOfTs= (int)perc*Ts.size()/100; 
		
		Random random = new Random();
		int i=0; 
		while(i<numberOfTs) {
			int randomInt = random.nextInt(Ts.size());
			Ts.get(randomInt).setTraceValue(RTMCell.TraceValue.NoTrace);
			Ns.add(Ts.get(randomInt)); 
			Ts.remove(randomInt); 
			i++; 
		}
		List<MethodRTMCell> TsNs = new ArrayList<MethodRTMCell>();
		TsNs.addAll(Ts); 
		TsNs.addAll(Ns); 
		return TsNs; 

		
		
	}
	
	/*/****************************************************************************/

	private static List<MethodRTMCell> SeedNtoT(List<MethodRTMCell> Ts, List<MethodRTMCell> Ns, double perc) throws IOException {
		int numberOfNs= (int)perc*Ns.size()/100; 
		
		Random random = new Random();
		int i=0; 
		while(i<numberOfNs) {
			int randomInt = random.nextInt(Ns.size());
			Ns.get(randomInt).setTraceValue(RTMCell.TraceValue.Trace);
			Ts.add(Ns.get(randomInt)); 
			Ns.remove(randomInt); 
			i++; 
		}
		List<MethodRTMCell> TsNs = new ArrayList<MethodRTMCell>();
		TsNs.addAll(Ts); 
		TsNs.addAll(Ns); 
		return TsNs; 

		
		
	}

	
	
	
	/**
	 * @param tsNs 
	 * @param programName2 
	 * @param writer2 
	 * @throws IOException *****************************************************************************************************/
	public static void generateFile(List<MethodRTMCell> tsNs, String fileName, String programName2) throws IOException {
			 File myFile = new File("log\\"+fileName+".txt");
			 counts callers = new counts(); 
			counts callersCallers= new counts(); 
			counts callees= new counts(); 
			counts calleesCallees= new counts(); 
			ArrayList<String> programs = new ArrayList<String>();
			TraceProcessor.variableStorageTraces.put("chess", new LinkedHashMap<String, String>());
			TraceProcessor.variableStorageTraces.put("gantt", new LinkedHashMap<String, String>());
			TraceProcessor.variableStorageTraces.put("itrust", new LinkedHashMap<String, String>());
			TraceProcessor.variableStorageTraces.put("jhotdraw", new LinkedHashMap<String, String>());
	
			int i=1; 
			Seeder.seedInputClazzTraceValuesWithDeveloperGold();
			int countNoCallersU=0; int countLowCallersU=0; int countMediumCallersU=0; int countHighCallersU=0; 
			int countNoCalleesU=0; int countLowCalleesU=0; int countMediumCalleesU=0; int countHighCalleesU=0; 
			int NoCallersUAndNoCalleesU=0; int LowCombination=0; int MediumCombination=0;int HighCombination=0;
			int size=0; 
			int k=0; 
			TraceProcessor.dataVariablesTraceDefinition(); 
			TraceProcessor.dataVariablesPrintforPythonInputFile(); 
		 System.out.println(fileName);
		 FileWriter writer = new FileWriter(myFile, true);
		 if(programName2.equals("chess")) {
			 writer.write(headersAtLeastOneInstanceNoProgram+"\n");

		 }
		 if(AtLeastOneInstance) {
	 			for(MethodRTMCell methodtrace: tsNs) {
	            	if(!methodtrace.getGoldTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) {

	 				String s=""; 
	 				String programName= methodtrace.ProgramName; 
	 				s=s+methodtrace.logGoldTraceValueString()+","; 
	 				if(!methodtrace.getCallers(programName).isEmpty() && !methodtrace.getCallees(programName).isEmpty()) {
	   		 			s=s+"Inner,"; 
	   		 		}else if( methodtrace.getCallees(programName).isEmpty() ) {
	   		 			s=s+"Leaf,"; 
	   		 		}else if( methodtrace.getCallers(programName).isEmpty() ) {
	   		 			s=s+"Root,"; 
	   		 		}else if( methodtrace.getCallers(programName).isEmpty() && methodtrace.getCallees(programName).isEmpty()) {
	   		 			s=s+"Isolated,"; 
	   		 		}
				 
	 				String reqMethod= methodtrace.getRequirement().getID()+"-"+methodtrace.getMethodID();
//	 				System.out.println(reqMethod);
			 		  callers = generateCountsTNUAtLeastOneInstance(methodtrace.getCallers(programName));
		 			  callersCallers = generateCountsTNUAtLeastOneInstance(methodtrace.getCallers(programName).getCallers(programName));
		 			  callees = generateCountsTNUAtLeastOneInstance(methodtrace.getCallees(programName));
		 			  calleesCallees = generateCountsTNUAtLeastOneInstance(methodtrace.getCallees(programName).getCallees(programName));
		 		
//	 			System.out.println(programName);
//	   		 	System.out.println(methodtrace.getRequirement().ID+"-"+methodtrace.getClazzRTMCell());
	   		 	
	   		 	if(ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(methodtrace.getRequirement().ID+"-"+methodtrace.getMethod().getClazz().ID)==null) {
//	   		 		System.out.println("here"+ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(methodtrace.getRequirement().ID+"-"+methodtrace.getMethod().getClazz().ID));
	   		 	}
	   		 	
//	 			System.out.println("broke");
	 				s=s+callers.amountT+","+callers.amountN+","+callers.amountU+","; 
		 			s=s+callersCallers.amountT+","+callersCallers.amountN+","+callersCallers.amountU+","; 
		 			s=s+callees.amountT+","+callees.amountN+","+callees.amountU+","; 
		 			s=s+calleesCallees.amountT+","+calleesCallees.amountN+","+calleesCallees.amountU+","+
							ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(methodtrace.getRequirement().ID+"-"+methodtrace.getMethod().getClazz().ID)
							+","+methodtrace.getVariabletraceValue();
		 					; 
		 			
		 			writer.write(s+"\n");
//		 			System.out.println(s);
	 			}
	 			}
	 			
		 }
		 writer.close();
	}
	/*****************************************************************************************************/

	private static void generateCSVFile(String programName, FileWriter writer) throws IOException {
		// TODO Auto-generated method stub
			counts callers = new counts(); 
			counts callersCallers= new counts(); 
			counts callees= new counts(); 
			counts calleesCallees= new counts(); 
			ArrayList<String> programs = new ArrayList<String>();
			TraceProcessor.variableStorageTraces.put("chess", new LinkedHashMap<String, String>());
			TraceProcessor.variableStorageTraces.put("gantt", new LinkedHashMap<String, String>());
			TraceProcessor.variableStorageTraces.put("itrust", new LinkedHashMap<String, String>());
			TraceProcessor.variableStorageTraces.put("jhotdraw", new LinkedHashMap<String, String>());
	
			int i=1; 
			Seeder.seedInputClazzTraceValuesWithDeveloperGold();
			int countNoCallersU=0; int countLowCallersU=0; int countMediumCallersU=0; int countHighCallersU=0; 
			int countNoCalleesU=0; int countLowCalleesU=0; int countMediumCalleesU=0; int countHighCalleesU=0; 
			int NoCallersUAndNoCalleesU=0; int LowCombination=0; int MediumCombination=0;int HighCombination=0;
			int size=0; 
			int k=0; 
			TraceProcessor.dataVariablesTraceDefinition(); 
			TraceProcessor.dataVariablesPrintforPythonInputFile(); 
            
			for ( MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
            	if(!methodtrace.getGoldTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) {
            		
            		Random random = new Random();
    				int rand = 0;
    				rand = random.nextInt(11);
    				String gold= methodtrace.logGoldTraceValueString(); 
    				if(gold.equals("T") && rand>=5 && step2 ) {
    					gold="U"; 
    				}
    				
            		String ProgramName=methodtrace.ProgramName; 
       		 		String s= gold+","; 
       		 		s=s+programName+","; 
       		 		
       		 		if(!methodtrace.getCallers().isEmpty() && !methodtrace.getCallees().isEmpty()) {
       		 			s=s+"Inner,"; 
       		 		}else if( methodtrace.getCallees().isEmpty() ) {
       		 			s=s+"Leaf,"; 
       		 		}else if( methodtrace.getCallers().isEmpty() ) {
       		 			s=s+"Root,"; 
       		 		}else if( methodtrace.getCallers().isEmpty() && methodtrace.getCallees().isEmpty()) {
       		 			s=s+"Isolated,"; 
       		 		}
       		 		
       		 		
       		 		
       		 	
       		 		
       		 		
       		 		
       		 		if(!AtLeastOneInstance) {
	       		 		 callers=generateCountsTNU(methodtrace.getCallers());
	   		 			 callersCallers=generateCountsTNU(methodtrace.getCallers().getCallers());
	   		 			 callees=generateCountsTNU(methodtrace.getCallees());
			 			 calleesCallees=generateCountsTNU(methodtrace.getCallees().getCallees());
       		 		}else if(AtLeastOneInstance) {
       		 			 
	       		 		 callers=generateCountsTNUAtLeastOneInstance(methodtrace.getCallers());
	   		 			 callersCallers=generateCountsTNUAtLeastOneInstance(methodtrace.getCallers().getCallers());
	   		 			 callees=generateCountsTNUAtLeastOneInstance(methodtrace.getCallees());
			 			 calleesCallees=generateCountsTNUAtLeastOneInstance(methodtrace.getCallees().getCallees());
       		 		}
			 			
		       		 	
			 			if(!step2) {
			 				s=s+methodtrace.getRequirement().ID+","+methodtrace.getMethod().ID+","+
				 					callers.amountT+","+callers.amountN+","+callers.amountU+","; 
		   		 			s=s+callersCallers.amountT+","+callersCallers.amountN+","+callersCallers.amountU+","; 
				 			s=s+callees.amountT+","+callees.amountN+","+callees.amountU+","; 
				 			s=s+calleesCallees.amountT+","+calleesCallees.amountN+","+calleesCallees.amountU
				 					+","+
									ClazzRTMCell.clazzTraces2HashMap.get(methodtrace.getRequirement().ID+"-"+methodtrace.getClazzRTMCell().getClazz().ID).getGoldTraceValue()
									+","+methodtrace.getVariabletraceValue(); 
			 			}
			 			
			 			
//			 			if(callers.amountU.equals("-1") && callees.amountU.equals("-1") && !AtLeastOneInstance) {
//			 				s=s+"1,"; 
//			 			}else if(!AtLeastOneInstance) {
//			 				s=s+"0,"; 
//			 			}
//			 			if(!AtLeastOneInstance)
//			 				s=s+","+methodtrace.getClazzRTMCell().getTraceValue(); 
			 			else if(step2) {
			 				s=s+methodtrace.getRequirement().ID+","+methodtrace.getMethod().ID+","+""+","+
				 					callers.amountT+","+callers.amountN+","+callers.amountU+","; 
		   		 			s=s+callersCallers.amountT+","+callersCallers.amountN+","+callersCallers.amountU+","; 
				 			s=s+callees.amountT+","+callees.amountN+","+callees.amountU+","; 
				 			s=s+calleesCallees.amountT+","+calleesCallees.amountN+","+calleesCallees.amountU
				 					+","+
									ClazzRTMCell.clazzTraces2HashMap.get(methodtrace.getRequirement().ID+"-"+methodtrace.getClazzRTMCell().getClazz().ID).getGoldTraceValue(); 
				 			}
			 				
			 			s=s+"\n"; 
						writer.write(s);
			 			
			 			
			 			//SEPARATE CALLERS AND CALLEES 
			 			
			 			if(callers.amountU.equals("-1")) countNoCallersU++; 
			 			else if(callers.amountU.equals("Low")) countLowCallersU++;
			 			else if(callers.amountU.equals("Medium")) countMediumCallersU++;
			 			else if(callers.amountU.equals("High")) countHighCallersU++; 
			 			
			 			
			 			if(callees.amountU.equals("-1")) countNoCalleesU++; 
			 			else if(callees.amountU.equals("Low")) countLowCalleesU++;
			 			else if(callees.amountU.equals("Medium")) countMediumCalleesU++;
			 			else if(callees.amountU.equals("High")) countHighCalleesU++; 
			 			
			 			
			 			if(callers.amountU.equals("-1") && callees.amountU.equals("-1"))  {
			 				NoCallersUAndNoCalleesU++; 
			 			}
			 			else if ((callers.amountU.equals("Low") && (callees.amountU.equals("Low") || callees.amountU.equals("-1")))
			 					|| (callees.amountU.equals("Low") && callers.amountU.equals("-1"))
			 					) {
			 				LowCombination++; 
			 			}
			 			else if ((callers.amountU.equals("Medium") && (callees.amountU.equals("Medium") || callees.amountU.equals("Low")|| callees.amountU.equals("-1")))
			 					|| (callees.amountU.equals("Medium") && (callers.amountU.equals("Low")||callers.amountU.equals("-1")))
			 					) {
			 				MediumCombination++; 
			 			}
			 			else if ((callers.amountU.equals("High") && (callees.amountU.equals("High") ||callees.amountU.equals("Medium") || callees.amountU.equals("Low")|| callees.amountU.equals("-1")))
			 					|| (callees.amountU.equals("High") && (callers.amountU.equals("Medium")||callers.amountU.equals("Low")||callers.amountU.equals("-1")))
			 					) {
			 				HighCombination++; 
			 			}
			 			
			 		
			 			
		 		
			 	
			 			size++; 
			 			}
            	k++; 
            	
            }
            double countNoCalleesUdouble=(double)countNoCalleesU/size*100; 
            int countNoCalleesUperc= (int) Math.round(countNoCalleesUdouble); 
            
            
            double countLowCalleesUdouble=(double)countLowCalleesU/size*100; 
            int countLowCalleesUperc= (int) Math.round(countLowCalleesUdouble); 

            
            double countMediumCalleesUdouble=(double)countMediumCalleesU/size*100; 
            int countMediumCalleesUperc= (int) Math.round(countMediumCalleesUdouble); 
            
            
            double countHighCalleesUdouble =(double)countHighCalleesU/size*100; 
            int countHighCalleesUperc= (int) Math.round(countHighCalleesUdouble); 

            double countNoCallersUdouble=(double)countNoCallersU/size*100; 
            int countNoCallersUperc=(int) Math.round(countNoCallersUdouble); 
            
            
            double countLowCallersUdouble=(double)countLowCallersU/size*100; 
            int countLowCallersUperc= (int) Math.round(countLowCallersUdouble); 
            
            
            double countMediumCallersUdouble=(double)countMediumCallersU/size*100;
            int countMediumCallersUperc=(int)Math.round(countMediumCallersUdouble); 
            
            double countHighCallersUdouble=(double)countHighCallersU/size*100;
            int countHighCallersUperc=(int)Math.round(countHighCallersUdouble); 
            /******/
            double NoCallersUAndNoCalleesUdouble=(double)NoCallersUAndNoCalleesU/size*100;
            int NoCallersUAndNoCalleesUperc=(int)Math.round(NoCallersUAndNoCalleesUdouble); 
            
            double LowCombinationdouble=(double)LowCombination/size*100;
            int LowCombinationperc=(int)Math.round(LowCombinationdouble); 
            
            double MediumCombinationdouble=(double)MediumCombination/size*100;
            int MediumCombinationperc=(int)Math.round(MediumCombinationdouble);
            
            double HighCombinationdouble=(double)HighCombination/size*100;
            int HighCombinationperc=(int)Math.round(HighCombinationdouble);
            
           
            
            System.out.println(countNoCalleesUperc+","+countLowCalleesUperc+","+countMediumCalleesUperc+","+countHighCalleesUperc+","+countNoCallersUperc+","+countLowCallersUperc+","+countMediumCallersUperc+","+countHighCallersUperc+","+
		 			NoCallersUAndNoCalleesUperc+","+LowCombinationperc+","+MediumCombinationperc+","+HighCombinationperc);
            

	}
	
	/*****************************************************************************************************/


	public static counts generateCountsTNUAtLeastOneInstance(MethodRTMCellList callees) {
		// TODO Auto-generated method stub
			
		counts c = counts.countMethods(callees); 
		if(c.T>=1) c.amountT="1"; 
		else if(c.T==0) c.amountT="0"; 
		
		if(c.N>=1) c.amountN="1"; 
		else if(c.N==0) c.amountN="0"; 
		
		if(c.U>=1) c.amountU="1"; 
		else if(c.U==0)  c.amountU="0"; 
		
		return c; 
	}
	/*****************************************************************************************************/
	public static counts generateCountsTNU(MethodRTMCellList callers) {
		counts c = counts.countMethods(callers); 


		if(c.T>=4) c.amountT="High"; 
		else if(c.T>1 && c.T<=3) c.amountT="Medium"; 
		else if(c.T==1) c.amountT="Low"; 
		
		
		if(c.N>=5) c.amountN="High"; 
		else if(c.N>1 && c.N<=4) c.amountN="Medium"; 
		else if(c.N==1) c.amountN="Low"; 
		
		
		if(c.U>=6) c.amountU="High"; 	
		else if(c.U>1 && c.U<=5) c.amountU="Medium"; 	
		else if(c.U==1) c.amountU="Low"; 
		
		int total=c.T+c.N+c.U; 
		double amountT=(double)c.T/total*100; 
		double amountN=(double)c.N/total*100; 
		double amountU=(double)c.U/total*100; 
		
		int amountTperc =(int) amountT; 
		int amountNperc=(int) amountN; 
		int amountUperc=(int) amountU; 
		
		c.amountTperc=amountTperc; 
		c.amountNperc=amountNperc; 
		c.amountUperc=amountUperc; 
		
		
		
		
		
		return c; 
		
		
	}
	

}
