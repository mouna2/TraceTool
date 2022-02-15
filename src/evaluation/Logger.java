package evaluation;

import model.MethodRTMCell;
import model.PredictionPattern;
import model.RTMCell;
import model.Requirement;
import model.RTMCell.TraceValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.maven.shared.invoker.SystemOutHandler;
import org.json.simple.JSONObject;

import BoxPlots.BoxPlotFileConverter.Trace;
import mainPackage.TraceProcessor;


public class Logger {
	public	static List <String> programNamesList= new ArrayList<String>(); 
	public	static double TP_T=0; 
	public	static double TP_N=0; 
	public	static double FP_T=0; 
	public	static double FP_N=0; 
	public	static double FN_T=0; 
	public	static double FN_N=0; 
	public	static double U_T=0; 
	public	static double U_N=0; 

	
	
    static public void logBasics(String programName, String parameter) {
        try {
            File file = new File("log\\" + programName + "\\basics-"+parameter+".txt");
            FileOutputStream stream = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));

            writer.write("MethodID;MethodName;RequirementID;RequirementName;ClassID;ClassName;ClassTraceValue;MethodTraceValue"
                    + ";Callers;CallersTraceValues;CallersCallers;CallersCallersTraceValues"
                    + ";Callees;CalleesTraceValues;CalleesCallees;CalleesCalleesTraceValues"
                    + ";Prediction;MethodGoldTraceValue;TPFPTNFN;pattern\n"
            );
            TP_T=0; 
        	TP_N=0; 
        	FP_T=0; 
        	FP_N=0; 
        	FN_T=0; 
        	FN_N=0; 
        	U_T=0; 
        	U_N=0; 
            for ( MethodRTMCell cell : MethodRTMCell.Totalmethodtraces2HashMap.get(programName).values()) {

                writer.write(
                        cell.getMethod().getID() + ";" + cell.getMethod().getName() + ";" +
                                cell.getRequirement().getID() + ";" + cell.getRequirement().getName() + ";" + cell.getClazzRTMCell().getClazz().getID() + ";" + cell.getClazzRTMCell().getClazz().getName() + ";" +
                                cell.getClazzRTMCell().logTraceValueString() + ";" + cell.logTraceValueString() + ";" +

                                ";;;;;;;;"+
                                //cell.getMethod().getCallers().logIDs() + ";" + cell.getCallers().logTraceValues() + ";" +
                                //cell.getMethod().getCallers().getCallers().logIDs() + ";" + cell.getCallers().getCallers().logTraceValues() + ";" +
                                //cell.getMethod().getCallees().logIDs() + ";" + cell.getCallees().logTraceValues() + ";" +
                                //cell.getMethod().getCallees().getCallees().logIDs() + ";" + cell.getCallees().getCallees().logTraceValues() + ";" +

                                cell.logPredictedTraceValueString() + ";" +cell.logGoldTraceValueString() + ";" + cell.logTPFPTNFN(programName) + ";" + cell.logPredictionPattern() + "\n"
                );
            }
            writer.close();
            System.out.println("N Precision:"+(TP_N*100/(TP_N+FP_N)));  
            System.out.println("T Precision:"+(TP_T*100/(TP_T+FP_T)));  
            System.out.println("N Recall:"+(TP_N*100/(TP_N+FN_N+U_N)));  
            System.out.println("T Recall:"+(TP_T*100/(TP_T+FN_T+U_T)));  
            System.out.println("TP_T: "+TP_T+" FP_T: "+FP_T+"TP_N: "+TP_N+" FP_N: "+FP_N+"FN_T: "+FN_T+"FN_N: "+FN_N+ " U_T: "+ U_T+ " U_N: "+ U_N);
            System.out.println("ENDING");
        } catch (Exception ex) {
        }
    }

    static public void logDetailed(String programName, String parameter) {
        try {
            File file = new File("log\\" + programName + "\\details-"+parameter+".txt");
            FileOutputStream stream = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));

//            writer.write("MethodID;MethodName;RequirementID;RequirementName;ClassID;ClassName;Value(Method);Value(Class);"
//                    + "Interfaces;InterfacesTraceValues;Implementations;ImplementationsTraceValues;Parents;ParentsTraceValues;Children;ChildrenTraceValues;"
//                    + "Callers;Callers;CallersOwnerValues;"
//                    + "InterfaceCallers;InterfaceCallersTraceValues;InterfaceCallersOwnerValues;"
//                    + "ImplementationsCallers;ImplementationsCallersTraceValues;ImplementationsCallersOwnerValues;"
//                    + "ParentsCallers;ParentsCallersTraceValues;ParentsCallersOwnerValues;"
//                    + "ChildrenCallers;ChildrenCallersTraceValues;ChildrenCallersOwnerValues;"
//                    + "Callees;Callees;CalleesOwnerValues;"
//                    + "InterfaceCallees;InterfaceCalleesTraceValues;InterfaceCalleesOwnerValues;"
//                    + "ImplementationsCallees;ImplementationsCalleesTraceValues;ImplementationsCalleesOwnerValues;"
//                    + "ParentsCallees;ParentsCalleesTraceValues;ParentsCalleesOwnerValues;"
//                    + "ChildrenCallees;ChildrenCalleesTraceValues;ChildrenCalleesOwnerValues;"
//                    + "ExtendedCallers;ExtendedCallersTraceValues;ExtendedCallersOwnerValues;ExtendedCallersGodClassValues;ExtendedCallersCallers;ExtendedCallersCallersTraceValues;ExtendedCallersCallersOwnerValues;"
//                    + "ExtendedCallees;ExtendedCalleesTraceValues;ExtendedCalleesOwnerValues;ExtendedCalleesGodClassValues;ExtendedCalleesCallees;ExtendedCalleesCalleesTraceValues;ExtendedCalleesCalleesOwnerValues;"
//                    + "ExecutedCallers;ExecutedCallersTraceValues;ExecutedOwnerCallers;ExecutedCallees;ExecutedCalleesTraceValues;ExecutedOwnerCallees;"
//                    + "Prediction;"
//                    + "TPFPTNFN;pattern\n"
//            );
//            for ( MethodRTMCell cell : MethodRTMCell.methodtraces2HashMap.values()) {
//                writer.write(
//                        cell.getMethod().getID() + ";" + cell.getMethod().getName() + ";" +
//                                cell.getRequirement().getID() + ";" + cell.getRequirement().getName() + ";" + cell.getClazzRTMCell().getClazz().getID() + ";" + cell.getClazzRTMCell().getClazz().getName() + ";" +
//
//                                cell.logGoldTraceValueString() + ";" + cell.getClazzRTMCell().logTraceValueString() + ";" +
//
//                                cell.getInterfaces().logMethodIDs() + ";" + cell.getInterfaces().logTraceValues() + ";" +
//                                cell.getImplementations().logMethodIDs() + ";" + cell.getImplementations().logTraceValues() + ";" +
//                                cell.getParents().logMethodIDs() + ";" + cell.getParents().logTraceValues() + ";" +
//                                cell.getChildren().logMethodIDs() + ";" + cell.getChildren().logTraceValues() + ";" +
//
//                                cell.getBasicCallers().logMethodIDs() + ";" + cell.getBasicCallers().logTraceValues() + ";" + cell.getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getInterfaces().getBasicCallers().logMethodIDs() + ";" + cell.getInterfaces().getBasicCallers().logTraceValues() + ";" + cell.getInterfaces().getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getImplementations().getBasicCallers().logMethodIDs() + ";" + cell.getImplementations().getBasicCallers().logTraceValues() + ";" + cell.getImplementations().getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getParents().getBasicCallers().logMethodIDs() + ";" + cell.getParents().getBasicCallers().logTraceValues() + ";" + cell.getParents().getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getChildren().getBasicCallers().logMethodIDs() + ";" + cell.getChildren().getBasicCallers().logTraceValues() + ";" + cell.getChildren().getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//
//                                cell.getBasicCallees().logMethodIDs() + ";" + cell.getBasicCallees().logTraceValues() + ";" + cell.getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getInterfaces().getBasicCallees().logMethodIDs() + ";" + cell.getInterfaces().getBasicCallees().logTraceValues() + ";" + cell.getInterfaces().getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getImplementations().getBasicCallees().logMethodIDs() + ";" + cell.getImplementations().getBasicCallees().logTraceValues() + ";" + cell.getImplementations().getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getParents().getBasicCallees().logMethodIDs() + ";" + cell.getParents().getBasicCallees().logTraceValues() + ";" + cell.getParents().getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getChildren().getBasicCallees().logMethodIDs() + ";" + cell.getChildren().getBasicCallees().logTraceValues() + ";" + cell.getChildren().getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//
//                                cell.getExtendedCallers().logMethodIDs() + ";" + cell.getExtendedCallers().logTraceValues() + ";" + cell.getExtendedCallers().getClazzesRTMCellList().logTraceValues() + ";" + cell.getExtendedCallers().getClazzes().logGodValues() + ";" +
//                                cell.getExtendedCallers().getExtendedCallers().logMethodIDs() + ";" + cell.getExtendedCallers().getExtendedCallers().logTraceValues() + ";" + cell.getExtendedCallers().getExtendedCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                
//                                cell.getExtendedCallees().logMethodIDs() + ";" + cell.getExtendedCallees().logTraceValues() + ";" + cell.getExtendedCallees().getClazzesRTMCellList().logTraceValues() + ";" + cell.getExtendedCallees().getClazzes().logGodValues() + ";" +
//                                
//                                cell.getExtendedCallees().getExtendedCallees().logMethodIDs() + ";" + cell.getExtendedCallees().getExtendedCallees().logTraceValues() + ";" + cell.getExtendedCallees().getExtendedCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getExecutedCallers().logMethodIDs() + ";" + cell.getExecutedCallers().logTraceValues() + ";" + cell.getExecutedCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getExecutedCallees().logMethodIDs() + ";" + cell.getExecutedCallees().logTraceValues() + ";" + cell.getExecutedCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//
//                                cell.logPredictedTraceValueString() + ";" + cell.logTPFPTNFN(programName) + ";" + cell.logPredictionPattern()+"\n"
//                );
//            }
            
            writer.write("MethodID;MethodName;RequirementID;RequirementName;ClassID;ClassName;Value(Method);Value(Class);"
//                    + "Interfaces;InterfacesTraceValues;Implementations;ImplementationsTraceValues;Parents;ParentsTraceValues;Children;ChildrenTraceValues;"
//                    + "Callers;Callers;CallersOwnerValues;"
//                    + "InterfaceCallers;InterfaceCallersTraceValues;InterfaceCallersOwnerValues;"
//                    + "ImplementationsCallers;ImplementationsCallersTraceValues;ImplementationsCallersOwnerValues;"
//                    + "ParentsCallers;ParentsCallersTraceValues;ParentsCallersOwnerValues;"
//                    + "ChildrenCallers;ChildrenCallersTraceValues;ChildrenCallersOwnerValues;"
//                    + "Callees;Callees;CalleesOwnerValues;"
//                    + "InterfaceCallees;InterfaceCalleesTraceValues;InterfaceCalleesOwnerValues;"
//                    + "ImplementationsCallees;ImplementationsCalleesTraceValues;ImplementationsCalleesOwnerValues;"
//                    + "ParentsCallees;ParentsCalleesTraceValues;ParentsCalleesOwnerValues;"
//                    + "ChildrenCallees;ChildrenCalleesTraceValues;ChildrenCalleesOwnerValues;"
                    + "ExtendedCallers;ExtendedCallersTraceValues;"
//                    + "ExtendedCallersOwnerValues;"
//                    + "ExtendedCallersGodClassValues;ExtendedCallersCallers;ExtendedCallersCallersTraceValues;ExtendedCallersCallersOwnerValues;"
                    + "ExtendedCallees;ExtendedCalleesTraceValues;"
//                    + "ExtendedCalleesOwnerValues;"
//                    + "ExtendedCalleesGodClassValues;ExtendedCalleesCallees;ExtendedCalleesCalleesTraceValues;ExtendedCalleesCalleesOwnerValues;"
//                    + "ExecutedCallers;ExecutedCallersTraceValues;ExecutedOwnerCallers;ExecutedCallees;ExecutedCalleesTraceValues;ExecutedOwnerCallees;"
                    + "Prediction;"
                    + "TPFPTNFN;pattern\n"
            );
            int i=0; 
            for ( MethodRTMCell cell : MethodRTMCell.Totalmethodtraces2HashMap.get(programName).values()) {
            	if(!cell.getGoldTraceValue().equals(TraceValue.UndefinedTrace))
                writer.write(
                        cell.getMethod().getID() + ";" + cell.getMethod().getName() + ";" +
                                cell.getRequirement().getID() + ";" + cell.getRequirement().getName() + ";" + cell.getClazzRTMCell().getClazz().getID() + ";" + cell.getClazzRTMCell().getClazz().getName() + ";" +

                                cell.logGoldTraceValueString() + ";" + cell.getClazzRTMCell().getGoldTraceValue() + ";" +

//                                cell.getInterfaces().logMethodIDs() + ";" + cell.getInterfaces().logTraceValues() + ";" +
//                                cell.getImplementations().logMethodIDs() + ";" + cell.getImplementations().logTraceValues() + ";" +
//                                cell.getParents().logMethodIDs() + ";" + cell.getParents().logTraceValues() + ";" +
//                                cell.getChildren().logMethodIDs() + ";" + cell.getChildren().logTraceValues() + ";" +
//
//                                cell.getBasicCallers().logMethodIDs() + ";" + cell.getBasicCallers().logTraceValues() + ";" + cell.getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getInterfaces().getBasicCallers().logMethodIDs() + ";" + cell.getInterfaces().getBasicCallers().logTraceValues() + ";" + cell.getInterfaces().getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getImplementations().getBasicCallers().logMethodIDs() + ";" + cell.getImplementations().getBasicCallers().logTraceValues() + ";" + cell.getImplementations().getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getParents().getBasicCallers().logMethodIDs() + ";" + cell.getParents().getBasicCallers().logTraceValues() + ";" + cell.getParents().getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getChildren().getBasicCallers().logMethodIDs() + ";" + cell.getChildren().getBasicCallers().logTraceValues() + ";" + cell.getChildren().getBasicCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//
//                                cell.getBasicCallees().logMethodIDs() + ";" + cell.getBasicCallees().logTraceValues() + ";" + cell.getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getInterfaces().getBasicCallees().logMethodIDs() + ";" + cell.getInterfaces().getBasicCallees().logTraceValues() + ";" + cell.getInterfaces().getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getImplementations().getBasicCallees().logMethodIDs() + ";" + cell.getImplementations().getBasicCallees().logTraceValues() + ";" + cell.getImplementations().getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getParents().getBasicCallees().logMethodIDs() + ";" + cell.getParents().getBasicCallees().logTraceValues() + ";" + cell.getParents().getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getChildren().getBasicCallees().logMethodIDs() + ";" + cell.getChildren().getBasicCallees().logTraceValues() + ";" + cell.getChildren().getBasicCallees().getClazzesRTMCellList().logTraceValues() + ";" +

                                cell.getExtendedCallers().logMethodIDs() + ";" + cell.getExtendedCallers().logTraceValues() + ";" +
//                                cell.getExtendedCallers().getClazzesRTMCellList().logTraceValues() + ";" + cell.getExtendedCallers().getClazzes().logGodValues() + ";" +
//                                cell.getExtendedCallers().getExtendedCallers().logMethodIDs() + ";" + cell.getExtendedCallers().getExtendedCallers().logTraceValues() + ";" + cell.getExtendedCallers().getExtendedCallers().getClazzesRTMCellList().logTraceValues() + ";" +
                                
                                cell.getExtendedCallees().logMethodIDs() + ";" + cell.getExtendedCallees().logTraceValues() + ";" + 
//                                cell.getExtendedCallees().getClazzesRTMCellList().logTraceValues() + ";" + cell.getExtendedCallees().getClazzes().logGodValues() + ";" +
                                
//                                cell.getExtendedCallees().getExtendedCallees().logMethodIDs() + ";" + cell.getExtendedCallees().getExtendedCallees().logTraceValues() + ";" + cell.getExtendedCallees().getExtendedCallees().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getExecutedCallers().logMethodIDs() + ";" + cell.getExecutedCallers().logTraceValues() + ";" + cell.getExecutedCallers().getClazzesRTMCellList().logTraceValues() + ";" +
//                                cell.getExecutedCallees().logMethodIDs() + ";" + cell.getExecutedCallees().logTraceValues() + ";" + cell.getExecutedCallees().getClazzesRTMCellList().logTraceValues() + ";" +

                                cell.logPredictedTraceValueString() + ";" + cell.getDecision() + ";" + cell.logPredictionPattern()+"\n"
                );
                i++; 
            }
            writer.close();
        } catch (Exception ex) {
        	System.out.println(ex);
        }
    }


    static public HashMap<Object, String> logPatterns = new HashMap<>();

    public static void logPatternsEntry(String programName, String parameter, ArrayList<PredictionPattern> patterns) {
    	logPatterns.put("1", logPatterns.get("1")+";"+programName+";;");
        logPatterns.put("2", logPatterns.get("2")+";"+parameter+";;");
        logPatterns.put("3", logPatterns.get("3")+";T;N;U");
        for (PredictionPattern pattern: patterns) {
            String p = logPatterns.get(pattern);
            if (p==null) p="";
            logPatterns.put(pattern, p+";" +pattern.T+ ";" +pattern.N+ ";" +pattern.U);
        }
 }

    public static void logPatterns(String programName, ArrayList<PredictionPattern> patterns, String parameter) {
        try {
            File file = new File("log\\" + programName + "\\predictionpatterns-"+parameter+".txt");
            FileOutputStream stream = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
            writer.write(";"+ logPatterns.get("1")+"\n");
            writer.write(";"+ logPatterns.get("2")+"\n");
            writer.write("Pattern;Prediction;T;N;U;T;N;U;T;N;U"+ logPatterns.get("3")+"\n");

            for (PredictionPattern pattern: patterns) {
                writer.write(pattern.pattern+ ";" +pattern.value.toString()+ logPatterns.get(pattern) +"\n");
            }
            writer.write("\n");

            writer.close();
        } catch (Exception ex) {
        }
    }

    static public void logPatternsReset(ArrayList<PredictionPattern> patterns) {
        logPatterns.put("1","");
        logPatterns.put("2","");
        logPatterns.put("3","");

        for (PredictionPattern pattern: patterns) {
            logPatterns.put(pattern,"");
        }
    }


    public static void logSummary(String programName) {

    }

    
    
	public static void writePercentagestoJSONfile(String type, int iteration, String program, List<Long> mySeeds) throws IOException {
		 	String fileName =".\\log\\percentages\\"+type+program+".json"; 
			File myFile = new File(fileName); 
			if (myFile.length() == 0) {
				
			
			 FileWriter jsonfileWriter = new FileWriter(fileName);
	
	        // Constructs a FileWriter given a file name, using the platform's default charset
		
			   jsonfileWriter.write("{");
			   int it=1; 
			for(int i=0; i<mySeeds.size()-1; i++) {
				jsonfileWriter.write(" \"Iteration"+it+"\":\""+mySeeds.get(i)+"\",");
				it++; 
			}
			jsonfileWriter.write(" \"Iteration"+it+"\":\""+mySeeds.get(mySeeds.size()-1)+"\"");
			jsonfileWriter.write("}");

		    jsonfileWriter.close();
			}
       

  
}
	public static void logPatternsSeeding(String programName, ArrayList<PredictionPattern> patterns, String parameter) {
        try {
        	String path ="log\\" + programName + "\\"+parameter+".txt"; 
        	System.out.println(path);
			File file = new File(path);
            FileOutputStream stream = new FileOutputStream(file, true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
            
            if(!programNamesList.contains(programName) )	{
            	//clear file
            	PrintWriter printwriter = new PrintWriter(file);
            	printwriter.print("");
            	printwriter.close();
            	//end clearing the file 
//            	writer.write("ProgramName; Requirement; T->U%; N->U%;T->N%; N->T%; TP_T; FP_T; TN_T; FN_T; TP_NT; FP_NT; TN_NT; FN_NT; U; U->T; U->N; Tpred; Npred; Upred; TracePrecision_T; TraceRecall_T; "
//            			+ "TracePrecision_NT;  TraceRecall_NT; UPred-TorNGold;TorNGold\n");
            	writer.write("ProgramName; Requirement; T->U%; N->U%;T->N%; N->T%; TPred; NPred; UPred; TP_T; FP_T; TN_T; FN_T; FN_T_NPred; FN_T_UPred; TP_NT; FP_NT; TN_NT; FN_NT; FN_NT_NPred;FN_NT_UPred\n");
            	programNamesList.add(programName); 

            }
            	
            for(Requirement requirement: Requirement.requirementsHashMap.values()) {
            	//We do not want to print information for requirements that do not have any requirements that trace to a given method  
            	if((!requirement.getRTMMethodCellList().retainTraceValuesEqualTraces(true).isEmpty() && (parameter.contains("IncompletenessT") ||parameter.contains("ErrorT")) )||
            		(!requirement.getRTMMethodCellList().retainTraceValuesEqualNoTraces(true).isEmpty() &&  (parameter.contains("IncompletenessN") ||parameter.contains("ErrorN")))
            		|| parameter.contains("NoSeeding") || parameter.contains("Ghabi"))
            		writer.write(programName+";"+requirement.ID+";"+requirement.seedTtoE+";"+requirement.seedNtoE+";"+requirement.seedTtoN+";"+requirement.seedNtoT+";");

                int TP_T=0;int TP_NT=0; int FP_T=0; int FP_NT=0; int TN_T=0;int TN_NT=0; int FN_T=0;int FN_NT=0;  int U=0; int UtoT=0; int UtoN=0; int Tpred=0; int Npred=0; int Upred=0; int FN_T_UPred=0; 
                int UPredTorNGold=0; int TorNGold=0; int FN_T_NPred=0; int FN_NT_TPred=0; int FN_NT_UPred=0; 
                for( RTMCell entry:requirement.getRTMMethodCellList()) {
                	
         

            		
            		if (entry.getGoldTraceValue().equals(TraceValue.UndefinedTrace)) U++; 
            		if ((entry.getPredictedTraceValue().equals(TraceValue.NoTrace)|| entry.getPredictedTraceValue().equals(TraceValue.UndefinedTrace)) && entry.getGoldTraceValue().equals(TraceValue.Trace))  FN_T++; 
            		
            		if (entry.getPredictedTraceValue().equals(TraceValue.UndefinedTrace) && entry.getGoldTraceValue().equals(TraceValue.Trace))  FN_T_UPred++; 
            		if (entry.getPredictedTraceValue().equals(TraceValue.NoTrace) && entry.getGoldTraceValue().equals(TraceValue.Trace))  FN_T_NPred++; 

            				
            		 if ((entry.getPredictedTraceValue().equals(TraceValue.Trace)|| entry.getPredictedTraceValue().equals(TraceValue.UndefinedTrace)) 	&& entry.getGoldTraceValue().equals(TraceValue.NoTrace))  FN_NT++; 
            		 

            			if (entry.getPredictedTraceValue().equals(TraceValue.UndefinedTrace) && entry.getGoldTraceValue().equals(TraceValue.NoTrace))  FN_NT_UPred++; 
                		if (entry.getPredictedTraceValue().equals(TraceValue.Trace) && entry.getGoldTraceValue().equals(TraceValue.NoTrace))  FN_NT_TPred++; 

            		 if ((entry.getPredictedTraceValue().equals(TraceValue.Trace)|| entry.getPredictedTraceValue().equals(TraceValue.UndefinedTrace)) 
            					&& entry.getGoldTraceValue().equals(TraceValue.Trace))  TN_NT++;
            		 if ((entry.getPredictedTraceValue().equals(TraceValue.NoTrace)|| entry.getPredictedTraceValue().equals(TraceValue.UndefinedTrace)) 
            					&& entry.getGoldTraceValue().equals(TraceValue.NoTrace))  TN_T++; 
            		 
            		
            		 if ((entry.getGoldTraceValue().equals(TraceValue.NoTrace)|| entry.getGoldTraceValue().equals(TraceValue.Trace)) 
         					&& entry.getPredictedTraceValue().equals(TraceValue.UndefinedTrace))  UPredTorNGold++; 
            		 
            		 if (entry.getGoldTraceValue().equals(TraceValue.NoTrace)|| entry.getGoldTraceValue().equals(TraceValue.Trace) )  TorNGold++; 
            		 
            		 

            		 
            		 if (entry.getPredictedTraceValue().equals(TraceValue.Trace) && entry.getGoldTraceValue().equals(TraceValue.NoTrace))  FP_T++; 
            		 if (entry.getPredictedTraceValue().equals(TraceValue.NoTrace) && entry.getGoldTraceValue().equals(TraceValue.Trace))  FP_NT++; 
            		 
            		 
            		 if (entry.getPredictedTraceValue().equals(TraceValue.NoTrace) && entry.getGoldTraceValue().equals(TraceValue.NoTrace))  TP_NT++; 
            		 if (entry.getPredictedTraceValue().equals(TraceValue.Trace) && entry.getGoldTraceValue().equals(TraceValue.Trace))  TP_T++; 

            	
                
                	
                	if (entry.getPredictedTraceValue().equals(TraceValue.NoTrace) && entry.getGoldTraceValue().equals(TraceValue.UndefinedTrace))UtoN++;     //completion       			
            		if (entry.getPredictedTraceValue().equals(TraceValue.Trace) && entry.getGoldTraceValue().equals(TraceValue.UndefinedTrace)) UtoT++; 	//completion 
                	if(entry.getPredictedTraceValue().equals(TraceValue.Trace)) Tpred++; 
                	if(entry.getPredictedTraceValue().equals(TraceValue.NoTrace)) Npred++; 
                	if(entry.getPredictedTraceValue().equals(TraceValue.UndefinedTrace)) Upred++; 


                }
                String TracePrecision_T=""; String TraceRecall_T=""; 
				if(TP_T+FP_T!=0) TracePrecision_T= String.valueOf(TP_T*100/(TP_T+FP_T)); else TracePrecision_T=""; 
				
                if(TP_T+FN_T!=0 && !TracePrecision_T.equals("")) TraceRecall_T= String.valueOf(TP_T*100/(TP_T+FN_T));  else TraceRecall_T=""; 
                if(TraceRecall_T.equals("")) TracePrecision_T=""; 

                
                String TracePrecision_NT=""; String TraceRecall_NT=""; 
				if(TP_NT+FP_NT!=0) TracePrecision_NT= String.valueOf(TP_NT*100/(TP_NT+FP_NT)); else TracePrecision_NT=""; 
                if(TP_NT+FN_NT!=0 && !TracePrecision_NT.equals("")) TraceRecall_NT= String.valueOf(TP_NT*100/(TP_NT+FN_NT));  else TraceRecall_NT=""; 
                if(TraceRecall_NT.equals("")) TracePrecision_NT=""; 
                //We do not want to print information for requirements that do not have any requirements that trace to a given method  

                if((!requirement.getRTMMethodCellList().retainTraceValuesEqualTraces(true).isEmpty() && (parameter.contains("IncompletenessT") ||parameter.contains("ErrorT")) )||
                	(!requirement.getRTMMethodCellList().retainTraceValuesEqualNoTraces(true).isEmpty() &&  (parameter.contains("IncompletenessN") ||parameter.contains("ErrorN")))|| parameter.contains("NoSeeding")
                	|| parameter.contains("Ghabi"))
//            		writer.write(TP_T+";"+FP_T+";"+TN_T+";"+FN_T+";"+TP_NT+";"+FP_NT+";"+TN_NT+";"+FN_NT+";"+U+";"+UtoT+";"+UtoN+";"+Tpred+";"+Npred+";"+Upred+";"+TracePrecision_T+";"+TraceRecall_T
//            				 +";"+TracePrecision_NT+";"+TraceRecall_NT+";"+UPredTorNGold+";"+TorNGold+"\n");
                	
                	writer.write(Tpred+";"+Npred+";"+Upred+";"+TP_T+";"+FP_T+";"+TN_T+";"+FN_T+";"+FN_T_NPred+";"+FN_T_UPred+";"+TP_NT+";"+FP_NT+";"+TN_NT+";"+FN_NT+";"+FN_NT_TPred+";"+FN_NT_UPred+"\n");

                

             }
          

            writer.close();
        } catch (Exception ex) {
        }
    }
}