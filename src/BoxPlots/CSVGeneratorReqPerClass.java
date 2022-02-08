package BoxPlots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.osgi.resource.Requirement;

import mainPackage.DatabaseInput;
import mainPackage.TraceProcessor;
import model.Clazz;
import model.ClazzRTMCell;
import model.Method;
import model.MethodRTMCell;
import model.RTMCell;
import model.RTMCell.TraceValue;
import model.*; 

public class CSVGeneratorReqPerClass implements Comparator<String>{
	public enum Type {
	    ReqVersusClass, ReqVersusMethod, ClassVersusReq, MethodVersusReq 
	}
	static Type test = Type.ClassVersusReq; 
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		boolean runAllTests = false;
		ArrayList<String> programs = new ArrayList<String>();
		for(int i=0; i<1; i++) {
			programs.add("chess");
			programs.add("gantt");
			programs.add("itrust");
			programs.add("jhotdraw");
			programs.add("vod");

			
	}
		 Collections.sort(programs); 
		 
		 
		 
		 for(String program: programs) {
			 LinkedHashMap<String, counts> mymethodhashmap = new LinkedHashMap<String, counts>(); 
			 LinkedHashMap<String, counts> clazzhashmap = new LinkedHashMap<String, counts>(); 
			 LinkedHashMap<String, counts> reqMethodHashMap = new LinkedHashMap<String, counts>(); 
			 LinkedHashMap<String, counts> reqClassHashMap = new LinkedHashMap<String, counts>(); 

			 DatabaseInput.read(program);
			 if(test==Type.ReqVersusMethod) {
				 
						 
						System.out.println(program);
						 
						int i=1; 
						 System.out.println("requirement,T,N");
						 int countT=0; int countN=0; 
						 int start=1; 
						 for( model.Requirement requirement: model.Requirement.requirementsHashMap.values()) {
							 int T=0; int N=0; int U=0; 
							 for(model.Method method : model.Method.methodsHashMap.values()) {
								if(model.MethodRTMCell.methodtraces2HashMap.get(requirement.ID+"-"+method.getID()).getGoldTraceValue().equals(TraceValue.Trace)) {
									T++; 
								}else if(model.MethodRTMCell.methodtraces2HashMap.get(requirement.ID+"-"+method.getID()).getGoldTraceValue().equals(TraceValue.NoTrace)) {
									N++; 
								}else if(model.MethodRTMCell.methodtraces2HashMap.get(requirement.ID+"-"+method.getID()).getGoldTraceValue().equals(TraceValue.UndefinedTrace)) {
									U++; 
								}
							 }
							 System.out.print(N+",");
							 
							 
							 
						 }
						 System.out.println();
						 i--; 

//						 System.out.println("["+start+"-"+i+"]"+","+countT+","+countN);
					 		
						 
						 System.out.println("over");
			 }
			 if(test==Type.ReqVersusClass) {
				 
				 
					System.out.println(program);
					 
					int i=1; 
					 System.out.println("requirement,T,N");
					 int countT=0; int countN=0; 
					 int start=1; 
					 for( model.Requirement requirement: model.Requirement.requirementsHashMap.values()) {
						 int T=0; int N=0; int U=0; 
						 for(Clazz clazz: model.Clazz.clazzesHashMap.values()) {
							if(model.ClazzRTMCell.clazzTraces2HashMap.get(requirement.ID+"-"+clazz.getID()).getGoldTraceValue().equals(TraceValue.Trace)) {
								T++; 
							}else if(model.ClazzRTMCell.clazzTraces2HashMap.get(requirement.ID+"-"+clazz.getID()).getGoldTraceValue().equals(TraceValue.NoTrace)) {
								N++; 
							}
						 }
						 System.out.print(N+",");
						 
						 
						 
					 }
					 System.out.println();
					 i--; 

//					 System.out.println("["+start+"-"+i+"]"+","+countT+","+countN);
				 		
					 
					 System.out.println("over");
		 }
			 
			 
			 if(test==Type.MethodVersusReq) {
				 counts counts = new counts(); 

				 reqMethodHashMap.put("0", counts); 
				 for( model.Requirement requirement: model.Requirement.requirementsHashMap.values()) {
					  counts = new counts(); 
					 reqMethodHashMap.put(requirement.ID, counts); 
				 }
				 int i=0;int j=0; int k=0; 
				 for( Method meth: Method.methodsHashMap.values()) {
					 int reqT=0; int reqN=0;  
					 for(model.Requirement req: model.Requirement.requirementsHashMap.values()) {
						 if(model.MethodRTMCell.methodtraces2HashMap.get(req.ID+"-"+meth.ID).getGoldTraceValue().equals(RTMCell.TraceValue.Trace)) {
							 reqT++; 
						 }
						  if(model.MethodRTMCell.methodtraces2HashMap.get(req.ID+"-"+meth.ID).getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace)) {
							 reqN++; 
						 }
						
					 }
					 
					
					
					 reqMethodHashMap.get(String.valueOf(reqT)).T++; 
					 reqMethodHashMap.get(String.valueOf(reqN)).N++; 
					 if(reqT==0)  reqMethodHashMap.get("0").T++;
					 if(reqN==0)  reqMethodHashMap.get("0").N++;
				 }
//				 System.out.println("OVER");
//				 System.out.println(i+"  "+j+"  "+k);
				 System.out.println(program);
				 System.out.println("Requirement#,T");
				 for(String reqID: reqMethodHashMap.keySet()) {
					 System.out.print(reqMethodHashMap.get(reqID).T+", ");
				 }
				 System.out.println();
				 
				 System.out.println("Requirement#,N");
				 for(String reqID: reqMethodHashMap.keySet()) {
					 System.out.print(reqMethodHashMap.get(reqID).N+", ");
				 }
				 System.out.println();
				 
			 }
			 
			 if(test==Type.ClassVersusReq) {
				 counts counts = new counts(); 

				 reqClassHashMap.put("0", counts); 
				 for( model.Requirement requirement: model.Requirement.requirementsHashMap.values()) {
					  counts = new counts(); 
					  reqClassHashMap.put(requirement.ID, counts); 
				 }
				 int i=0;int j=0; int k=0; 
				 for( Clazz clazz: model.Clazz.clazzesHashMap.values()) {
					 int reqT=0; int reqN=0;  
					 for(model.Requirement req: model.Requirement.requirementsHashMap.values()) {
						 if(model.ClazzRTMCell.clazzTraces2HashMap.get(req.ID+"-"+clazz.ID).getGoldTraceValue().equals(RTMCell.TraceValue.Trace)) {
							 reqT++; 
						 }
						  if(model.ClazzRTMCell.clazzTraces2HashMap.get(req.ID+"-"+clazz.ID).getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace)) {
							 reqN++; 
						 }
						
					 }
					 
					
					 reqClassHashMap.get(String.valueOf(reqT)).T++; 
					 reqClassHashMap.get(String.valueOf(reqN)).N++; 
					 if(reqT==0)  reqClassHashMap.get("0").T++;
					 if(reqN==0)  reqClassHashMap.get("0").N++;
					 
				 }
//				 System.out.println("OVER");
//				 System.out.println(i+"  "+j+"  "+k);
				 System.out.println(program);
				 System.out.println("Requirement#,T");
				 for(String reqID: reqClassHashMap.keySet()) {
					 System.out.print(reqClassHashMap.get(reqID).T+", ");
				 }
				 System.out.println();
				 
				 System.out.println("Requirement#,N");
				 for(String reqID: reqClassHashMap.keySet()) {
					 System.out.print(reqClassHashMap.get(reqID).N+", ");
				 }
				 System.out.println();
				 
			 }
			 
			 
			
			 System.out.println("********************************************");
			 
		 }
	}
	@Override
	public int compare(String arg0, String arg1) {
		int first=Integer.parseInt(arg0); 
		int second=Integer.parseInt(arg1); 
		return first-second; 
	}

}
