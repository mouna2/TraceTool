package evaluation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import mainPackage.TraceProcessor;
import model.*;
import traceValidator.TraceValidatorPredictionPattern;

public class Seeder {

	static public void reset() {
		for (Requirement requirement : Requirement.requirementsHashMap.values()) {
			requirement.setSeedTtoE( 0 );
			requirement.setSeedNtoE( 0 );
			requirement.setSeedTtoN( 0 );
			requirement.setSeedNtoT( 0 );
		}
	}

	static public void seedInputMethodTraceValuesWithDeveloperGold() {
		for (MethodRTMCell cell : MethodRTMCell.methodtraces2HashMap.values()) {
			cell.setTraceValue(cell.getGoldTraceValue());
		}
	}
	
	
	

	static public void seedPredictedMethodTraceValuesWithUndefinedTraces() {
		for (MethodRTMCell cell : MethodRTMCell.methodtraces2HashMap.values()) {
			cell.resetPrediction();
		}
	}

	static public void seedInputMethodRTMCellWithPredicted(boolean overwriteIfDifferent) {
		for (MethodRTMCell cell : MethodRTMCell.methodtraces2HashMap.values()) {
			if (cell.getTraceValue().equals(RTMCell.TraceValue.UndefinedTrace))
				cell.setTraceValue(cell.getPredictedTraceValue());
			else if (overwriteIfDifferent && !cell.getTraceValue().equals(cell.getPredictedTraceValue()))
				cell.setTraceValue(cell.getPredictedTraceValue());
		}
	}

	static public void seedInputClazzTraceValuesWithDeveloperGold() {
		for (ClazzRTMCell cell : ClazzRTMCell.clazzTraces2HashMap.values()) {
			cell.setTraceValue(cell.getGoldTraceValue());
		}
	}

	public static void seedInputMethodRTM(boolean seedTtoE, boolean seedNtoE, boolean seedTtoN,  boolean seedNtoT, String program, String type, Random generator, int iteration) throws Exception {
		
	    

		for (Requirement requirement : Requirement.requirementsHashMap.values()) {
			MethodRTMCellList traceList = requirement.getRTMMethodCellList().retainTraceValuesEqualTraces(false);
			MethodRTMCellList noTraceList = requirement.getRTMMethodCellList().retainTraceValuesEqualNoTraces(false); 

			if (seedTtoE) {
				int percentage= Seeder.seedInputMethodRTM(traceList, RTMCell.TraceValue.UndefinedTrace, type, generator, requirement, iteration, program) ; 
				requirement.setSeedTtoE(percentage);
				
			}
			if (seedNtoE) {
				int percentage= Seeder.seedInputMethodRTM(noTraceList, RTMCell.TraceValue.UndefinedTrace, type, generator, requirement, iteration, program) ; 
				requirement.setSeedNtoE(percentage);


			}
			if (seedTtoN) {
				int percentage= Seeder.seedInputMethodRTM(traceList, RTMCell.TraceValue.NoTrace, type, generator,  requirement, iteration, program) ; 
				requirement.setSeedTtoN(percentage) ;

			}
			if (seedNtoT) {
				int percentage=  Seeder.seedInputMethodRTM(noTraceList, RTMCell.TraceValue.Trace, type, generator,  requirement, iteration, program) ; 
				requirement.setSeedNtoT(percentage);

			}
		
		}
		
		
		

	}





	static public int seedInputMethodRTM(MethodRTMCellList list, RTMCell.TraceValue value, String type, Random generator, Requirement requirement, int iteration, String program) throws IOException, ParseException {
		if(list.size()==0) return 0; 
		 
		double randomperc=0;       
		int howMany =0;          
	    int howManyPercentage= 0;  
		 double  howManyPercentageDouble =0; 

		 if(list.size()==1) {
			 randomperc=100; 
			 howMany=1; 
			 howManyPercentageDouble =100.0; 
			 howManyPercentage=100; 
		 }
		while(howManyPercentage==0 && list.size()!=1) {
			 randomperc= generator.nextDouble(); 
			 howMany = (int)(randomperc * list.size());
			 howManyPercentageDouble = (double)howMany / (list.size())*100;
			 howManyPercentage= (int) Math.round(howManyPercentageDouble); 
//			 System.out.println(howManyPercentage+"  "+list.size());
		}

		
		for (int i=0; i<howMany; i++) {
			RTMCell cell = list.get((int)(generator.nextDouble() * list.size()));
			cell.setTraceValue(value);
			list.remove(cell);
		}
//		System.out.println(howManyPercentage);
		return howManyPercentage;
	}

	public static long readJSON(int iteration, String program, String type) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
    
		String fileName =".\\log\\percentages\\"+type+program+".json"; 
		System.out.println(fileName);
	FileReader reader = new FileReader(fileName); 
	long seed;
	File myFile = new File(fileName); 
	if (myFile.length() != 0) {
	
		Object obj = jsonParser.parse(reader);
         JSONObject iterationList = (JSONObject) obj; 
         String myiterationSeed = (String) iterationList.get("Iteration"+iteration); 
        seed=Long.parseLong(myiterationSeed); 
        System.out.println(seed);
        System.out.println("***");
  }   
	else {
		 seed=(long) ((Math.random() * ((1000000 - 1) + 1)) + 1); 

	}
	return seed; 
	}

	
}
