package mainPackage;

import evaluation.Logger;
import evaluation.Seeder;
import model.Clazz;
import model.ClazzRTMCell;
import model.ClazzRTMCellList;
import model.Definitions;
import model.Method;
import model.MethodRTMCell;
import model.MethodRTMCellList;
import model.PredictionPattern;
import model.RTMCell;
import model.RTMCell.TraceValue;
import model.RTMCellList;
import model.Requirement;
import model.Variable;
import model.VariableList;
import model.VariableRTMCell;
import traceRefiner.TraceRefiner;
import traceRefiner.TraceRefinerPredictionPattern;
import traceValidator.TraceValidator;
import traceValidator.TraceValidatorPredictionPattern;
import traceValidatorGhabi.TraceValidatorGhabi;
import traceValidatorGhabi.TraceValidatorGhabiPredictionPattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import BoxPlots.counts;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import weka.*;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove; 
public class TraceProcessor {

	public static enum Algorithm {ValidatorSingle, ValidatorCallTypes, ValidatorIterations, GhabiValidator, Refiner, 
		IncompletenessSeederT, IncompletenessSeederN, IncompletenessSeederTN, ErrorSeederT, ErrorSeederN, ErrorSeederTN, seedingTest1, seedingTest2, VSM,LSI};
    private static long startTime = System.currentTimeMillis();
    public static Algorithm test = Algorithm.Refiner;
    public static List<JSONObject> jsonArray = new  ArrayList<JSONObject> (); 
    public static LinkedHashMap<String, LinkedHashMap<String, String>> variableStorageTraces = new LinkedHashMap<String, LinkedHashMap<String, String>>();  
    static int [] indices = new int[] {0,2,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19}; 
    static double[] thresholds_T = new double[] {0.65,0.60,0.55,0.50,0.45,0.40}; 
    static double[] thresholds_N = new double[] {0.95,0.90,0.85,0.80,0.75,0.70}; 
    static int t=1; 
	static public void main(String[] args) throws Exception {
//		 jsonfileWriter = new FileWriter("C:\\Users\\mouna\\git\\TraceProcessor\\log\\percentages\\IncompletenessT.json");
		 //tests
		//more validation patterns innner/outer Tt-x-n, also multiple callers (meaning that is one outer and one inner caller with T), single callers T*N-x-N (meaning are are more than one caller with T and a single N)
		//validation with mutliple iterations
		//validation with weak and strong traces - gray zone wiht likely/unlikely traces
		//validation with seeding
		variableStorageTraces.put("chess", new LinkedHashMap<String, String>());
		variableStorageTraces.put("gantt", new LinkedHashMap<String, String>());
		variableStorageTraces.put("itrust", new LinkedHashMap<String, String>());
		variableStorageTraces.put("jhotdraw", new LinkedHashMap<String, String>());

		//*********************************
		boolean runAllTests = false;
		ArrayList<String> programs = new ArrayList<String>();
		for(int i=0; i<1; i++) {
			programs.add("chess");
			programs.add("gantt");
			programs.add("itrust");
			programs.add("jhotdraw");
//			programs.add("vod");
			
	}
		
		
		 Collections.sort(programs); 
//		programs.add("jhotdraw");
		if (test==Algorithm.seedingTest1) {
			
		
		DatabaseInput.read(programs.get(1));
		
	
	
		
		System.out.println("Requirement; x; y; z; percentageZ z/(x+y+z)");
		for(Requirement req: Requirement.requirementsHashMap.values()) {
			MethodRTMCellList mylist = req.getRTMMethodCellList(); 
			int x=0; int y=0; int z=0; 

			for(RTMCell elem: mylist) {
				if(elem.getSubjectN()==0 && elem.getSubjectT()>0) {
					y++; 
				}
				else if(elem.getSubjectT()==0 && elem.getSubjectN()>0) {
					x++; 
				}
				else if(elem.getSubjectT()>0 && elem.getSubjectN()>0) {
					z++; 
				}
			}
			double res=(double)z/(x+y+z)*100; 
		
			int percentageZ= (int) Math.round(res); 
			System.out.println(req.ID+";"+x+";"+y+";"+z+";"+percentageZ);
		}
		}
		if (test==Algorithm.seedingTest2) {
			
			
		DatabaseInput.read(programs.get(3));
		System.out.println("Requirement; x; y; z; percentageControversial (x+z)/(x+y+z)");
		for(Requirement req: Requirement.requirementsHashMap.values()) {
			MethodRTMCellList mylist = req.getRTMMethodCellList(); 
			int x=0; int y=0; int z=0; 

			for(RTMCell elem: mylist) {
				if(elem.getSubjectN()==0 && elem.getSubjectT()>0 && elem.getGoldTraceValue()==TraceValue.Trace) {
					y++; 
				}
				else if(elem.getSubjectT()==0 && elem.getSubjectN()>0 && elem.getGoldTraceValue()==TraceValue.Trace) {
					x++; 
				}
				else if(elem.getSubjectT()>0 && elem.getSubjectN()>0 && elem.getGoldTraceValue()==TraceValue.Trace) {
					z++; 
				}
			}
			double res=(double)(x+z)/(x+y+z)*100; 
		
			int percentageXZ= (int) Math.round(res); 
			System.out.println(req.ID+";"+x+";"+y+";"+z+";"+percentageXZ);
		}
		}
		//*********************************
if (test==Algorithm.ErrorSeederT ||test==Algorithm.ErrorSeederN || test==Algorithm.ErrorSeederTN) {
	// validator test single
	int iteration=0; 
	String type=""; 
	long seed=0; 
	String lastprogram=""; 
	List<Long> mySeeds = new ArrayList<>(); 
	for (String program : programs) {
		
		if(lastprogram.equals(program)) {
			iteration++; 
		}
		else {
			iteration=1; 
			Logger.writePercentagestoJSONfile( type, iteration, lastprogram, mySeeds); 
			mySeeds = new ArrayList<>(); 

		}
		if(test==Algorithm.ErrorSeederT) {
			type="ErrorT"; 
		}
		if(test==Algorithm.ErrorSeederN) {
			type="ErrorN"; 
		}
		if(test==Algorithm.ErrorSeederTN) {
			type="ErrorTN"; 
		}
		//call method that read json file get random - input: iteration num , programname
		seed=Seeder.readJSON(iteration, program, type);	
		mySeeds.add(seed); 
	Random generator = new Random(seed);
	Definitions.callerType = Definitions.CallerType.extended;
	int version = 2;
	
	TraceValidatorPredictionPattern.define(version);
	Logger.logPatternsReset(TraceValidatorPredictionPattern.patterns);

	DatabaseInput.read(program);
	Seeder.seedInputMethodTraceValuesWithDeveloperGold();
	Seeder.seedInputClazzTraceValuesWithDeveloperGold();
	Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();

	if(test==Algorithm.ErrorSeederTN) {
		Seeder.seedInputMethodRTM(false, false, true, true, program, type, generator, iteration);
	
	}
	if(test==Algorithm.ErrorSeederT) {

		Seeder.seedInputMethodRTM(false, false, true, false, program, type, generator, iteration);
	}
	if(test==Algorithm.ErrorSeederN) {

		Seeder.seedInputMethodRTM(false, false, false, true, program, type, generator, iteration);
	}
	

	TraceProcessor.validate(program, "val-v" + 2 + "-" + Definitions.callerType.toString());
	Logger.logPatternsSeeding(program, TraceValidatorPredictionPattern.patterns, type);
	System.out.println("==================== "+iteration);

	System.out.println("HERE1");

	lastprogram=program; 
}
	Logger.writePercentagestoJSONfile( type, iteration, lastprogram, mySeeds); 
}
		
		
		if (test==Algorithm.IncompletenessSeederN|| test==Algorithm.IncompletenessSeederT|| test==Algorithm.IncompletenessSeederTN) {
			// validator test single
			int iteration=0; 
			String type=""; 
			long seed=0; 
			String lastprogram=""; 
			List<Long> mySeeds = new ArrayList<>(); 
			for (String program : programs) {
				
				if(lastprogram.equals(program)) {
					iteration++; 
				}
				else {
					iteration=1; 
					Logger.writePercentagestoJSONfile( type, iteration, lastprogram, mySeeds); 
					mySeeds = new ArrayList<>(); 

				}
				if(test==Algorithm.IncompletenessSeederTN) {
					type="IncompletenessTN"; 
				}
				if(test==Algorithm.IncompletenessSeederT) {
					type="IncompletenessT"; 
				}
				if(test==Algorithm.IncompletenessSeederN) {
					type="IncompletenessN"; 
				}
				//call method that read json file get random - input: iteration num , programname
				seed=Seeder.readJSON(iteration, program, type);	
				mySeeds.add(seed); 
			Random generator = new Random(seed);
			Definitions.callerType = Definitions.CallerType.extended;
			int version = 2;
			
			TraceValidatorPredictionPattern.define(version);
			Logger.logPatternsReset(TraceValidatorPredictionPattern.patterns);

			DatabaseInput.read(program);
			Seeder.seedInputMethodTraceValuesWithDeveloperGold();
			Seeder.seedInputClazzTraceValuesWithDeveloperGold();
			Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();
		
			if(test==Algorithm.IncompletenessSeederTN) {
				Seeder.seedInputMethodRTM(true, true, false, false, program, type, generator, iteration);
			
			}
			if(test==Algorithm.IncompletenessSeederT) {

				Seeder.seedInputMethodRTM(true, false, false, false, program, type, generator, iteration);
			}
			if(test==Algorithm.IncompletenessSeederN) {

				Seeder.seedInputMethodRTM(false, true, false, false, program, type, generator, iteration);
			}
			

			TraceProcessor.validate(program, "val-v" + 2 + "-" + Definitions.callerType.toString());
			Logger.logPatternsSeeding(program, TraceValidatorPredictionPattern.patterns, type);
			System.out.println("==================== "+iteration);
		
			System.out.println("HERE1");

			lastprogram=program; 
}
			Logger.writePercentagestoJSONfile( type, iteration, lastprogram, mySeeds); 
		}
		if (test==Algorithm.ValidatorSingle) {
			// validator test single
			
			
			
		for(String program: programs) {
			int version = 2;
			Definitions.callerType = Definitions.CallerType.extended;

//			TraceValidatorPredictionPattern.define(version);
//			Logger.logPatternsReset(TraceValidatorPredictionPattern.patterns);

			DatabaseInput.read(program);
		
//			Seeder.seedInputMethodTraceValuesWithDeveloperGold();
//			Seeder.seedInputClazzTraceValuesWithDeveloperGold();
//			Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();
//			validate(program, "val-v" + version + "-" + Definitions.callerType.toString());
//			Logger.logPatterns(program, TraceValidatorPredictionPattern.patterns, "val-v" + version);
//			Logger.logPatternsSeeding(program, TraceValidatorPredictionPattern.patterns, "NoSeeding"+program);

		}
			

		}

		if (runAllTests || test==Algorithm.ValidatorCallTypes) {
			// validator tests - compare basic, extended, and executed calls

			for (int version = 1; version <= 2; version++) {

				TraceValidatorPredictionPattern.define(version);
				Logger.logPatternsReset(TraceValidatorPredictionPattern.patterns);

				for (String program : programs) {
					DatabaseInput.read(program);

					Seeder.seedInputMethodTraceValuesWithDeveloperGold();
					Seeder.seedInputClazzTraceValuesWithDeveloperGold();
					Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();
					Definitions.callerType = Definitions.CallerType.basic;
					validate(program, "val-v" + version + "-" + Definitions.callerType.toString());

					Seeder.seedInputMethodTraceValuesWithDeveloperGold();
					Seeder.seedInputClazzTraceValuesWithDeveloperGold();
					Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();
					Definitions.callerType = Definitions.CallerType.extended;
					validate(program, "val-v" + version + "-" + Definitions.callerType.toString());

					Seeder.seedInputMethodTraceValuesWithDeveloperGold();
					Seeder.seedInputClazzTraceValuesWithDeveloperGold();
					Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();
					Definitions.callerType = Definitions.CallerType.executed;
					validate(program, "val-v" + version + "-" + Definitions.callerType.toString());
				}

				Logger.logPatterns("all", TraceValidatorPredictionPattern.patterns, "val-v" + version);
			}
		}

		if (runAllTests || test==Algorithm.ValidatorIterations) {
			// validator iterator tests
			TraceValidatorPredictionPattern.define(2);
			Logger.logPatternsReset(TraceValidatorPredictionPattern.patterns);

			for (String program : programs) {
				DatabaseInput.read(program);
				Seeder.seedInputMethodTraceValuesWithDeveloperGold();
				Seeder.seedInputClazzTraceValuesWithDeveloperGold();
				Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();
				Definitions.callerType = Definitions.CallerType.extended;

				for (int iteration = 1; iteration <= 3; iteration++) {
					validate(program, "val-it" + iteration);
					Seeder.seedInputMethodRTMCellWithPredicted(true);
				}
			}
			Logger.logPatterns("all", TraceValidatorPredictionPattern.patterns, "val-it");
		}

		if (runAllTests || test==Algorithm.GhabiValidator) {
			// validator tests - ghabi
			TraceValidatorGhabiPredictionPattern.define();
			Logger.logPatternsReset(TraceValidatorGhabiPredictionPattern.patterns);
			Definitions.callerType = Definitions.CallerType.extended;

			for (String program : programs) {
				DatabaseInput.read(program);
				Seeder.seedInputMethodTraceValuesWithDeveloperGold();
				Seeder.seedInputClazzTraceValuesWithDeveloperGold();
				Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();
				validateGhabi(program, "val-ghabi");
				Logger.logPatternsSeeding(program, TraceValidatorPredictionPattern.patterns, "Ghabi");
			}

			Logger.logPatterns("all", TraceValidatorGhabiPredictionPattern.patterns, "val-ghabi");
		}

		if (runAllTests || test==Algorithm.Refiner) {

			TraceRefinerPredictionPattern.define();
			TraceValidatorPredictionPattern.define(2);
			ArrayList patterns = new ArrayList();
			patterns.addAll(TraceRefinerPredictionPattern.patterns);
			patterns.addAll(TraceValidatorPredictionPattern.patterns);
			Logger.logPatternsReset(patterns);
			Definitions.callerType = Definitions.CallerType.extended;
			
//			for(Clazz clazz: Clazz.clazzesHashMap.values()) {
//				System.out.println(clazz.ID+"   "+clazz.getTcount());
//			}
			FileWriter myWriter = new FileWriter("log//step2Data.txt");
//			myWriter.write("gold,Program,MethodType,RequirementID,MethodID,PredictedTraceValue,CallersT,CallersN,CallersU,CallersCallersT,CallersCallersN,CallersCallersU"
//					+ ",CalleesT,CalleesN,CalleesU,CalleesCalleesT,CalleesCalleesN,CalleesCalleesU,classGold\n");
			
			
		
			for (String program : programs) {

				DatabaseInput.read(program);
				Seeder.seedInputClazzTraceValuesWithDeveloperGold();
				Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();
				
				refine(program, "ref", patterns, myWriter);

			}
			randomForest(myWriter); 

			myWriter.close();
			Logger.logPatterns("all", patterns, "ref");
			
			 long endTime = System.currentTimeMillis();
		     System.out.println("It took " + (endTime - startTime) + " milliseconds");
		}
		
		if (runAllTests || test==Algorithm.VSM || test==Algorithm.LSI) {

			

			for (String program : programs) {
				DatabaseInput.read(program);
				Seeder.seedInputClazzTraceValuesWithDeveloperGold();
				Seeder.seedPredictedMethodTraceValuesWithUndefinedTraces();
				RTMCell.logTPTNFPFN2(program, "");

			}
			//test
			
			 long endTime = System.currentTimeMillis();
		     System.out.println("It took " + (endTime - startTime) + " milliseconds");
		}
		
		
	/////////////////////////////////////			
	dataAnalysisVariables(); 
	
	
	dataVariablesTraceDefinition(); 

	dataVariablesPrintforPythonInputFile(); 

	/////////////////////////////////////	
		
		
		
	}

	public static void dataVariablesTraceDefinition() {
		for(String programName: Variable.totalVariablesHashMap.keySet()) {
			LinkedHashMap<String, Variable> variablesPerProgram = Variable.totalVariablesHashMap.get(programName); 
			for(Variable variable: variablesPerProgram.values()) {
				for(Requirement req: Requirement.totalRequirementsHashMap.get(programName).values()) {
					List<Method> methods = variable.getMethodList(); 
					int countT=0; int countN=0; int countU=0; 
					for(Method method: methods) {
						LinkedHashMap<String, MethodRTMCell> methodRTMCellList = MethodRTMCell.Totalmethodtraces2HashMap.get(programName); 
						MethodRTMCell methodRTMCell = methodRTMCellList.get(req.ID+"-"+method.getID()); 
						TraceValue goldTraceValue = methodRTMCell.getGoldTraceValue(); 
						if(goldTraceValue.equals(TraceValue.Trace)) {
							countT++; 
						}
						else if (goldTraceValue.equals(TraceValue.NoTrace)) {
							countN++; 
						}
						else if (goldTraceValue.equals(TraceValue.UndefinedTrace)) {
							countU++; 
						}

					}
					
					int totalCount = countT+countN+countU; 
					double Tperc=(double)countT/totalCount*100; 
					double Nperc=(double)countN/totalCount*100; 
					double Uperc=(double)countU/totalCount*100; 
					
					if(totalCount!=0) {
						Tperc=round(Tperc,2); 
						Nperc=round(Nperc,2); 
						Uperc=round(Uperc,2); 
						//---------------------------------//
					
						assignTNUToVariableMajorityAlg(Tperc, Nperc, Uperc, req, variable, programName); 
//						assignTNUToVariableAtleast2Alg(Tperc, Nperc, Uperc, req, variable, programName);
//						assignTNUToVariableAllAlg(Tperc, Nperc, Uperc, req, variable, programName);

						
					}
					
				}
			}
		
		
		}
		
		
		
		///////////////////////////////////////////////////////////////////////////
		for(String programName: VariableRTMCell.totalVariableTracesHashMap.keySet()) {
			LinkedHashMap<String, Variable> variableTracesPerProgram = VariableRTMCell.totalVariableTracesHashMap.get(programName); 
			for(String key: variableTracesPerProgram.keySet()) {
//				System.out.println(variable.percT+"  "+variable.percN+"  "+variable.percU);
//				System.out.println(key+" :::::::::::::::"+variableTracesPerProgram.get(key).getTraceValue());

			}
		}
		System.out.println("over");
		
	}

	private static void assignTNUToVariableMajorityAlg(double percT, double percN, double percU, Requirement req, Variable variable, String ProgramName) {
		// TODO Auto-generated method stub
		
//		System.out.println(req.ID+"-"+variable.id);
		if(percT>percN && percT>percU)  {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.Trace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "T"); 
 
		}
		else if(percN>percT && percN>percU)  {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.NoTrace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "N"); 

		}
		else if(percU>percT && percU>percN)  {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.UndefinedTrace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "U"); 

		}
		else {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.UndefinedTrace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "U"); 
		}

	}
	
	
	
	private static void assignTNUToVariableAllAlg(double percT, double percN, double percU, Requirement req, Variable variable, String ProgramName) {
		// TODO Auto-generated method stub
		
//		System.out.println(req.ID+"-"+variable.id);
		if(percT>0 && percN==0 && percU==0)  {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.Trace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "T"); 
 
		}
		else if(percN>0 && percT==0 && percU==0)  {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.NoTrace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "N"); 

		}
		else if(percU>0 && percN==0 && percT==0)  {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.UndefinedTrace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "U"); 

		}
		else {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.UndefinedTrace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "U"); 
		}

	}
	
	private static void assignTNUToVariableAtleast2Alg(double percT, double percN, double percU, Requirement req, Variable variable, String ProgramName) {
		// TODO Auto-generated method stub
		
//		System.out.println(req.ID+"-"+variable.id);
		if(percT>2)  {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.Trace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "T"); 
 
		}
		else if(percN>2)  {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.NoTrace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "N"); 

		}
		else if(percU>2)  {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.UndefinedTrace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "U"); 

		}
		else {
			VariableRTMCell.totalVariableTracesHashMap.get(ProgramName).get(req.ID+"-"+variable.id).setTraceValue(TraceValue.UndefinedTrace);
			variableStorageTraces.get(ProgramName).put(req.ID+"-"+variable.id, "U"); 
		}

	}

	public static void dataVariablesPrintforPythonInputFile() throws IOException {
		// TODO Auto-generated method stub
		int i=0; 
	      FileWriter myWriter = new FileWriter("log//PythonInputDataVariables.txt");
			myWriter.write("gold,ProgramName,RequirementID,MethodID"
					+ ",VariableTrace"
					+ ",MethodType"
					+ ",CallersT,CallersN,CallersU"
					+ ",CallersCallersT,CallersCallersN,CallersCallersU,CalleesT,CalleesN,CalleesU,CalleesCalleesT,CalleesCalleesN,CalleesCalleesU"
					+ "\n");

		for (String programName : MethodRTMCell.Totalmethodtraces2HashMap.keySet()) {
			LinkedHashMap<String, MethodRTMCell> methodTraces = MethodRTMCell.Totalmethodtraces2HashMap.get(programName); 
		
			for(MethodRTMCell cell: methodTraces.values()) {
				
		if(!cell.getGoldTraceValue().equals(TraceValue.UndefinedTrace)) {
			    HashSet<Variable> vars = cell.getMethod().getMethodVars(); 
				int countT=0; int countN=0; int countU=0; 
				if(vars.size()>0) {
				    for(Variable var: vars) {
						String traceValue=variableStorageTraces.get(programName).get(cell.getRequirement().ID+"-"+var.id); 
						if(traceValue!=null) {
							if(traceValue.equals("T")) {
								countT++; 
							}
							else if(traceValue.equals("N")) {
								countN++; 
							}
							else if(traceValue.equals("U")) {
								countU++; 
							}
							else countU++; 
						}
				    }
				
							
						

					
				
				int totalCount = countT+countN+countU; 
				double percT=(double)countT/totalCount*100; 
				double percN=(double)countN/totalCount*100; 
				double percU=(double)countU/totalCount*100; 
				
				if(totalCount!=0) {
					percT=round(percT,2); 
					percN=round(percN,2); 
					percU=round(percU,2); 
				}
				
				
				//ALG 1:
				String variableTraceValue=AlgMajority(percT, percN, percU); 
				//ALG 2:
//				String variableTraceValue=AlgGreaterThan2(percT, percN, percU); 
				//ALG 3:
//				String variableTraceValue=AlgAll(percT, percN, percU); 

				
				cell.setVariabletraceValue(variableTraceValue);
				}else {
					cell.setVariabletraceValue("x");
				}
	
			
//			if(!cell.getGoldTraceValue().equals(TraceValue.UndefinedTrace)) {
				String methodType=""; String callersT="0"; String callersN="0"; String callersU="0"; 
				String callerscallersT="0"; String callerscallersN="0"; String callerscallersU="0"; 
				String calleesT="0"; String calleesN="0"; String calleesU="0"; 
				String calleescalleesT="0"; String calleescalleesN="0"; String calleescalleesU="0"; 
				if(!cell.getCallers().isEmpty() && !cell.getCallees().isEmpty()) {
					methodType="Inner"; 
				}else if(!cell.getCallers().isEmpty() && cell.getCallees().isEmpty()) {
					methodType="Leaf"; 
				}else if(cell.getCallers().isEmpty() && cell.getCallees().isEmpty()) {
					methodType="Root"; 
				}
				/****************************************************/
				if(cell.getCallers().allTs()) callersT="High"; 
				else if(cell.getCallers().atLeast2GoldT()) callersT="Medium"; 
				else if(cell.getCallers().atLeast1GoldT()) callersT="Low"; 
					
				
				if(cell.getCallees().allTs()) calleesT="High"; 
				else if(cell.getCallees().atLeast2GoldT()) calleesT="Medium"; 
				else if(cell.getCallees().atLeast1GoldT()) calleesT="Low"; 
				
				if(cell.getCallers().getCallers().allTs()) callerscallersT="High"; 
				else if(cell.getCallers().atLeast2GoldT()) callerscallersT="Medium"; 
				else if(cell.getCallers().atLeast1GoldT()) callerscallersT="Low"; 
				
				
				if(cell.getCallees().getCallees().allTs()) calleescalleesT="High"; 
				else if(cell.getCallees().atLeast2GoldT()) calleescalleesT="Medium"; 
				else if(cell.getCallees().atLeast1GoldT()) calleescalleesT="Low"; 
				/*******************************************************************/
				if(cell.getCallers().allNs()) callersN="High"; 
				else if(cell.getCallers().atLeast2GoldN()) callersN="Medium"; 
				else if(cell.getCallers().atLeast1GoldN()) callersN="Low"; 
					
				
				if(cell.getCallees().allNs()) calleesN="High"; 
				else if(cell.getCallees().atLeast2GoldN()) calleesN="Medium"; 
				else if(cell.getCallees().atLeast1GoldN()) calleesN="Low"; 
				
				if(cell.getCallers().getCallers().allNs()) callerscallersN="High"; 
				else if(cell.getCallers().atLeast2GoldN()) callerscallersN="Medium"; 
				else if(cell.getCallers().atLeast1GoldN()) callerscallersN="Low"; 
				
				
				if(cell.getCallees().getCallees().allNs()) calleescalleesN="High"; 
				else if(cell.getCallees().atLeast2GoldN()) calleescalleesN="Medium"; 
				else if(cell.getCallees().atLeast1GoldN()) calleescalleesN="Low"; 
				
//				if(cell.getCallers().atLeast1GoldT()) callersT="1"; 
//				else if (cell.getCallers().atLeast1GoldN()) callersN="1"; 
//				else if(cell.getCallers().atLeast1GoldU()) callersU="1"; 
//				
//				if(cell.getCallers().getCallers().atLeast1GoldT()) callerscallersT="1"; 
//				else if (cell.getCallers().getCallers().atLeast1GoldN()) callerscallersN="1"; 
//				else if(cell.getCallers().getCallers().atLeast1GoldU()) callerscallersU="1"; 
//				
//				
//				
//				if(cell.getCallees().atLeast1GoldT()) calleesT="1"; 
//				else if (cell.getCallees().atLeast1GoldN()) calleesN="1"; 
//				else if(cell.getCallees().atLeast1GoldU()) calleesU="1"; 
//				
//				if(cell.getCallees().getCallees().atLeast1GoldT()) calleescalleesT="1"; 
//				else if (cell.getCallees().getCallees().atLeast1GoldN()) calleescalleesN="1"; 
//				else if(cell.getCallees().getCallees().atLeast1GoldU()) calleescalleesU="1"; 
				
					myWriter.write(cell.getGoldTraceValue()+","+programName+","+cell.getRequirement().ID+","+cell.getMethodID()+","+
					cell.getVariabletraceValue()+","+
							methodType
							+","+callersT+","+callersN+","+callersU
							+","+callerscallersT+","+callerscallersN+","+callerscallersU
							+","+calleesT+","+calleesN+","+calleesU
							+","+calleescalleesT+","+calleescalleesN+","+calleescalleesU+","
							+"\n");
								
					
				i++; 
//			}
			}
		}
			
		}
		myWriter.close();}

	private static String AlgAll(double percT, double percN, double percU) {
		// TODO Auto-generated method stub
		String val="U"; 
		if(percT>0 && percN==0 && percU==0) val="T"; 
		else if (percN>0 && percT==0 && percU==0) val="N"; 
		else if(percU>0 && percT==0 && percN==0) val="U"; 
		
		return val;
	}

	private static String AlgGreaterThan2(double percT, double percN, double percU) {
		// TODO Auto-generated method stub
		String val="U"; 
		if(percT>2) val="T";
		else if(percN> 2 )val="N";
		else if(percU>2) val="U";
		return val;
	}

	private static String AlgMajority(double percT, double percN, double percU) {
		// TODO Auto-generated method stub
		String val="U"; 
		if(percT>percN && percT>percU) val="T";
		else if(percN> percT && percN>percU)val="N";
		else if(percU> percT && percU>percN) val="U";
		return val;
	}

	private static void dataAnalysisVariables() {
		HashMap<Integer, Integer> countHashmap = new HashMap<>(); 
		HashMap<Integer, Integer> MethVarsHashmap = new HashMap<>(); 
		System.out.println("METHODS USING CLASS FIELDS");
		System.out.println("HOW MANY METHODS USING 0, 1, 2, ... CLASS FIELDS WITHOUT DUPLICATES");

		for(String programName: Method.totalMethodsHashMap.keySet()) {
			LinkedHashMap<String, Method> methodhashmap = Method.totalMethodsHashMap.get(programName); 
			for( Method method: methodhashmap.values()) {
				HashSet<Variable> vars = method.getMethodVars(); 	
				 VariableList params = method.getParameters(); 	
				 vars.addAll(params); 
				HashSet<Object> seen=new HashSet<>();
				vars.removeIf(e->!seen.add(e.getId()));
				
				int numberOfVars=seen.size(); 
				
				if(MethVarsHashmap.get(numberOfVars)==null) {
					MethVarsHashmap.put(numberOfVars, 1); 
				}
				else {
					int numberOfMethods=MethVarsHashmap.get(numberOfVars)+1; 
					MethVarsHashmap.put(numberOfVars, numberOfMethods); 
				}
				
				
			}
			
		}
		
		
		
		
		
		
		for (Integer numberOfVars: MethVarsHashmap.keySet()){
			Integer numberOfMeth=MethVarsHashmap.get(numberOfVars); 
            System.out.println(numberOfVars + " " + numberOfMeth);  
	} 
		
		
		System.out.println("---------------------");
		System.out.println("HOW MANY METHODS USING 0, 1, 2, ... CLASS FIELDS");
		for(String programName: Method.totalMethodsHashMap.keySet()) {
		LinkedHashMap<String, Method> methodhashmap = Method.totalMethodsHashMap.get(programName); 
		for( Method method: methodhashmap.values()) {
			int size= method.getFieldMethods().size(); 
			
			if(countHashmap.get(size)==null) {
				countHashmap.put(size,1); 
			}
			else if(countHashmap.get(size)!=null) {
				int amount=countHashmap.get(size); 
				amount++; 
				countHashmap.put(size, amount); 
			}
			
		}
		
	}
		for (Integer variablesSize: countHashmap.keySet()){
			Integer NumberOfMethods=countHashmap.get(variablesSize); 
            System.out.println(variablesSize + " " + NumberOfMethods);  
	} 
		
		HashMap<Integer, Integer> countHashMapNew= new HashMap<>(); 
		for(Clazz myclass: Clazz.clazzesHashMap.values()) {
			List<Variable> fields = myclass.getFieldClasses(); 
			for(Variable field: fields) {
				List<Method> methods = field.getMethodList(); 
				if(countHashMapNew.get(methods.size())==null)
					{
					countHashMapNew.put(methods.size(), 1); 
					}
				else {
					int count=countHashMapNew.get(methods.size()); 
					count++; 
					countHashMapNew.put(methods.size(), count); 
				}
				
				if(methods.size()==0) {
					System.out.println(field);
					System.out.println("here");
				}
			}
		}
		
		
		for(int methodsSize: countHashMapNew.keySet()) {
			System.out.println(methodsSize+"-"+countHashMapNew.get(methodsSize));
			
		}
		/****************************/
		for(  String programName: Variable.totalVariablesHashMap.keySet()) {
			int empty=0;
			LinkedHashMap<String, Variable> varHashMap = Variable.totalVariablesHashMap.get(programName); 	
			for(  Variable var: varHashMap.values()) {
				for(   Requirement req: Requirement.totalRequirementsHashMap.get(programName).values()) {
					int countT=0; int countN=0; int countU=0; 

					if(!var.getMethodList().isEmpty()) {
						for(  Method meth: var.getMethodList()) {

							TraceValue gold =MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(req.ID+"-"+meth.ID).getGoldTraceValue(); 
							
							
						
					
							if(gold.equals(TraceValue.Trace)) countT++; 
							else if(gold.equals(TraceValue.NoTrace)) countN++; 
							else if(gold.equals(TraceValue.UndefinedTrace)) countU++; 
						}
						
						int totalCount = countT+countN+countU; 
						double Tperc=(double)countT/totalCount*100; 
						double Nperc=(double)countN/totalCount*100; 
						double Uperc=(double)countU/totalCount*100; 
						
						if(totalCount!=0) {
							Tperc=round(Tperc,2); 
							Nperc=round(Nperc,2); 
							Uperc=round(Uperc,2); 
						}
						

						
						if(totalCount!=0) {
//							System.out.println(programName+","+req.ID+","+var.variableName+","+var.ownerclazz.ID+","+var.ownerclazz.name+","+countT+","+countN+","+countU+","+totalCount+","+Tperc+","+Nperc+","+Uperc);
						}
					
					}
					else {
						empty++; 
					}
			
				}
				
			}
			
//			System.out.println("=====>EMPTY  "+programName+ "  "+empty);
		}
	System.out.println();
//	System.out.println("OVER");
	}
	
	
	
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	static public void validate(String programName, String logParameter) throws Exception {
		PredictionPattern.reset();
		TraceValidator.makePredictions();

		Logger.logBasics(programName, logParameter);
		Logger.logDetailed(programName, logParameter);
		Logger.logPatternsEntry(programName, logParameter, TraceValidatorPredictionPattern.patterns);
	}
	
	static public void validateGhabi(String programName, String logParameter) throws Exception {
		PredictionPattern.reset();

		TraceValidatorGhabi.makePredictions();

		Logger.logBasics(programName, logParameter);
		Logger.logDetailed(programName, logParameter);
		Logger.logPatternsEntry(programName, logParameter, TraceValidatorGhabiPredictionPattern.patterns);
	}


	static public void refine(String programName, String logParameter, ArrayList patterns, FileWriter myWriter) throws Exception {
		 
		PredictionPattern.reset();

		Seeder.seedInputClazzTraceValuesWithDeveloperGold();

		TraceRefiner.step1_classNs2MethodNs();
		RTMCell.logTPTNFPFN2(programName, "step 1");
		
//		TraceRefiner.step2_propagateMethodNs(1);
//		RTMCell.logTPTNFPFN2(programName, "step 2");
//
//	
//		
//		
//		
//		TraceRefiner.step3_classTs2MethodTs();
//		RTMCell.logTPTNFPFN2(programName, "step 3");
//
//		TraceRefiner.step4_propagateMethodTs(1);
//		RTMCell.logTPTNFPFN2(programName, "step 4");

		TraceRefiner.checkGoldPred(programName); 
		
		Logger.logBasics(programName, logParameter);
		Logger.logDetailed(programName, logParameter);
		Logger.logPatternsEntry(programName, logParameter, patterns);	
		
//		System.out.println("Type, reqMethod, CalleesSize, CallersSize, CallersCallersSize");
//		for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
//			String reqMethod= methodtrace.getRequirement().ID+"_"+methodtrace.getMethod().ID; 
//			if(methodtrace.logTPFPTNFN(programName).contains("FP_T")) {
//				//inner 
//				if (!methodtrace.getCallees().isEmpty() && !methodtrace.getCallers().isEmpty()) {
//				 System.out.println("Inner,"+reqMethod+","+methodtrace.getCallees().size()+","+methodtrace.getCallers().size()+",,");
//				}
//				//leaf 
//				else if (methodtrace.getCallees().isEmpty() && !methodtrace.getCallers().isEmpty()) {
//					 System.out.println("Leaf,"+reqMethod+",,"+methodtrace.getCallers().size()+","+methodtrace.getCallers().getCallers().size());
//
//				}
//			}
//			System.out.println("*******************************************************");
//			if(methodtrace.logTPFPTNFN(programName).contains("TP_T")) {
//				//inner 
//				if (!methodtrace.getCallees().isEmpty() && !methodtrace.getCallers().isEmpty()) {
//				 System.out.println(methodtrace.getCallees().size()+"   "+methodtrace.getCallers().size());
//				}
//				//inner 
//				else if (methodtrace.getCallees().isEmpty() && !methodtrace.getCallers().isEmpty()) {
//					 System.out.println(methodtrace.getCallees().size()+"   "+methodtrace.getCallers().size());
//
//				}
//			}
//			
//		}
	
	
	
	
	}

	private static void randomForest(FileWriter myWriter) throws Exception {
		// TODO Auto-generated method stub
		
		DatabaseInput.read("chess");
		
				Remove removeFilter = new Remove();
				removeFilter.setAttributeIndicesArray(indices);
				removeFilter.setInvertSelection(true);
		
		
		RandomForest m_classifier  = new RandomForest();
//		File inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\GoldDataAfterSeedingUs.arff");//Training corpus file  

		File inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ganttiTrustJHotTrain.arff");//Training corpus file  
//		File inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\chessiTrustJHotTrain.arff");//Training corpus file  
//		File inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ChessGanttJHotTrain.arff");//Training corpus file  
//		File inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ChessGanttiTrustTrain.arff");//Training corpus file  

        ArffLoader atf = new ArffLoader();   
        atf.setFile(inputFile);  
        Instances instancesTrain = atf.getDataSet(); // Read in training documents      
//        removeFilter.setInputFormat(instancesTrain);
//        instancesTrain = Filter.useFilter(instancesTrain, removeFilter);
       

        
        
//        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ProgramReqMethod.arff");//Training corpus file  
        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\chessProgramReqMethod.arff");//Training corpus file  
//        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ganttProgramReqMethod.arff");//Training corpus file  
//        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\iTrustProgramReqMethod.arff");//Training corpus file  
//        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\JHotDrawProgramReqMethod.arff");//Training corpus file  

        atf = new ArffLoader();   
        atf.setFile(inputFile);  
        Instances ProgramReqMethod = atf.getDataSet(); // Read in training documents      
        
//        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\DataAfterStep2.arff");//Test corpus file  
        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\chessTest.arff");//Test corpus file  
//        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\ganttTest.arff");//Test corpus file  
//        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\iTrustTest.arff");//Test corpus file  
//        inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\src\\mainPackage\\JHotDrawTest.arff");//Test corpus file  

        atf.setFile(inputFile);            
        Instances instancesTest = atf.getDataSet(); // Read in the test file  
//        removeFilter.setInputFormat(instancesTest);
//        instancesTest = Filter.useFilter(instancesTest, removeFilter);
        instancesTest.setClassIndex(0); //Setting the line number of the categorized attribute (No. 0 of the first action), instancesTest.numAttributes() can get the total number of attributes.  

        double sum = instancesTest.numInstances();//Examples of test corpus  
        
        instancesTrain.setClassIndex(0);  
        m_classifier.buildClassifier(instancesTrain); //train
        System.out.println(m_classifier);
        Classifier classifier8 =null; 
        // Preservation model
        SerializationHelper.write("LibSVM.model", m_classifier);//Parameter 1 saves the file for the model, and classifier 4 saves the model.
        try {
             classifier8 = (Classifier) weka.core.SerializationHelper.read("LibSVM.model"); 
            System.out.println("over");
        }catch (Exception e) {
            e.printStackTrace();
           
        }
        double TP_N = 0.0f;
        double TP_T = 0.0f;  
        double FP_N = 0.0f;
        double FP_T = 0.0f; 
        double FN_N = 0.0f;
        double FN_T = 0.0f; 
        double N=0.0f; 
        double T=0.0f; 
        int U=0; 
         boolean modified=true; 
         int totalIts=0; 
         for(int  i = 0;i<sum;i++)//Test classification result 1
         {  
         	int program = (int)ProgramReqMethod.instance(i).toDoubleArray()[0]; 
         	String programName=getProgName(program); 
         	String reqMethod=(int)ProgramReqMethod.instance(i).toDoubleArray()[1]+"-"+(int)ProgramReqMethod.instance(i).toDoubleArray()[2]; 

         		
         		
         	MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setPredictedTraceValue(TraceValue.UndefinedTrace); 
         		
         
         }
         
         
         
         
        while(modified) {
            boolean entered=false; 
        
            if(totalIts!=0) {
            	 m_classifier  = new RandomForest();
                 inputFile = new File("C:\\Users\\mouh\\Downloads\\TraceabilityCDG-master\\TraceabilityCDG-master\\TraceTool\\log\\step2Data.txt");//Test corpus file  
                 atf = new ArffLoader();   
                 atf.setFile(inputFile);  
                 instancesTest = atf.getDataSet(); // Read in the test file  
                 instancesTest.setClassIndex(0); }
         int none=0; 
            for(int  i = 0;i<sum;i++)//Test classification result 1
        {  
            	
            	System.out.println(i);
        
        
        
            double[] probs = classifier8.distributionForInstance(instancesTest.instance(i));
//            System.out.println(Arrays.toString(probs));

        	String reqMethod=(int)ProgramReqMethod.instance(i).toDoubleArray()[1]+"-"+(int)ProgramReqMethod.instance(i).toDoubleArray()[2]; 
        	
        	int program = (int)ProgramReqMethod.instance(i).toDoubleArray()[0]; 
        	String programName=getProgName(program); 
            String classID=MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getMethod().getClazz().ID;
        	String reqClass=(int)ProgramReqMethod.instance(i).toDoubleArray()[1]+"-"+classID;
        	
        	String decision=MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getDecision(); 
        	
        	//RESET U 
        	if(MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getPredictedTraceValue().equals(TraceValue.UndefinedTrace)) {
        		if(instancesTest.instance(i).classValue()==1.0) N++; 
        		else if(instancesTest.instance(i).classValue()==0.0) T++; 
        		
        		if(MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.NoTrace))
        		none++; 
        		//PREDICTION IS N AND PROBA OF N GREATER THAN 0.95
        		if((
//        				instancesTest.instance(i).classValue()==1.0 && 
        				
        				probs[1]>thresholds_N[t] && MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.NoTrace) )
        				
//        				||
//        				(MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getPredictedTraceValue().equals(TraceValue.NoTrace)
//        				&& probs[1]>thresholds_N[1] && MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.NoTrace) ) 
        				
        				) {
        			if(decision.equals("/U")) {
        				U--; 
        			}
            		TP_N++;//Correct value plus 1       
            		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setPredictedTraceValue(TraceValue.NoTrace);
            		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/TP_N");

            		entered=true; 
            	}
        		//PREDICTION IS T AND PROBA OF T IS GREATER THAN 0.65

            	else if(
//            			instancesTest.instance(i).classValue()==0.0 && 
            			probs[0]>thresholds_T[t] &&
            			MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.Trace) 
//            			&& probs[0]>thresholds_T[1] 
            					) {
            		if(decision.equals("/U")) {
            			U--; 
            		}
            		TP_T++;
            		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setPredictedTraceValue(TraceValue.Trace);
            		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/TP_T");

            		entered=true; 

            	}
        		//PREDICTION IS N AND PROBA OF N IS GREATER THAN 0.95

            	else if( 
//            			(
            					probs[1]>thresholds_N[t] 
//            							&& instancesTest.instance(i).classValue()==0.0 
            							&& MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.Trace)
//            			) ||
//            		
//            			(
//            					probs[1]>thresholds_N[1] 
//            					&& MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getPredictedTraceValue().equals(TraceValue.Trace)
//            					
//            					) 
            			) {
            		if(decision.equals("/U")) {
            			U--; 
            		}
            		FN_T++; 
            		FP_N++;  
            		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setPredictedTraceValue(TraceValue.NoTrace);
            		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/FN_T/FP_N");

            		entered=true; 

            	}
        		//PREDICTION IS T AND PROBA OF T IS GREATER THAN 0.65
            	else if( (probs[0]>thresholds_T[t] 
//            			&& instancesTest.instance(i).classValue()==1.0 
            			&& MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.NoTrace))
            			
//            			||	
//            			(probs[0]>thresholds_T[1] 
//            					&& MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getPredictedTraceValue().equals(TraceValue.NoTrace)
//            					)
            			) {
            		if(decision.equals("/U")) {
            			U--; 
            		}
            		FP_T++; 
            		FN_N++; 
            		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setPredictedTraceValue(TraceValue.Trace);
            		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/FN_N/FP_T");

            		entered=true; 

            	}
        		

        		
        		// IF THE CLASS TRACE IS A T THEN WE PREDICT A T 
	        	else if(ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(reqClass).equals(TraceValue.Trace) 
			        	&& MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.Trace)
			            ){ 
	        		if(decision.equals("/U")) {
	        			U--; 
	        		}
	    		TP_T++;
	    		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setPredictedTraceValue(TraceValue.Trace);
	    		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/TP_T");

	    	}
        	
	        	
	        	
	         // WE CANNOT MAKE A PREDICTION
	        	else  if(!MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getDecision().equals("/U")){
	        		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/U");
	        		U++;  
	        	}
        		
	
        		
        	}
            	

            
        } 
        	
            
            System.out.println(TP_N);
            System.out.println(sum);
            System.out.println("N Precision:"+(TP_N/(TP_N+FP_N)));  
            System.out.println("T Precision:"+(TP_T/(TP_T+FP_T)));  
            System.out.println("N Recall:"+(TP_N/(TP_N+FN_N+U)));  
            System.out.println("T Recall:"+(TP_T/(TP_T+FN_T)));  
            System.out.println("TP_T: "+TP_T+" FP_T: "+FP_T+"FN_T: "+FN_T+ " U: "+ U);
            System.out.println("TP_N: "+TP_N+" FP_N: "+FP_N+"FN_N: "+FN_N);
            System.out.println("T: "+T);
            
            System.out.println("T: "+T);
            
        System.out.println("none "+none);
        RecomputeInputFileAfterStep2(myWriter); 
        System.out.println("done2");

        System.out.println("===> "+totalIts);

        totalIts++; 
        if(!entered) {
        	modified=false; 
        }
        }
        
        System.out.println("N: "+N);
    
        
        
        
        
        
        
        System.out.println(TP_N);
        System.out.println(sum);
        System.out.println("N Precision:"+(TP_N/(TP_N+FP_N)));  
        System.out.println("T Precision:"+(TP_T/(TP_T+FP_T)));  
        System.out.println("N Recall:"+(TP_N/(TP_N+FN_N+U)));  
        System.out.println("T Recall:"+(TP_T/(TP_T+FN_T)));  
        System.out.println("TP_T: "+TP_T+" FP_T: "+FP_T+"FN_T: "+FN_T+ " U: "+ U);
        System.out.println("TP_N: "+TP_N+" FP_N: "+FP_N+"FN_N: "+FN_N);
        System.out.println("T: "+T);
        
        System.out.println("T: "+T);
        double PredictedTs=TP_T+FN_T; 
        double PredictedNs=TP_N+FN_N+U; 
        System.out.println("TP_T+FN_T: "+ PredictedTs);
        System.out.println("TP_N+FN_N+U: "+ PredictedNs);
        Map<Integer, String> attributeMap= new HashMap<Integer, String>(); 
        attributeMap.put(0, "gold"); 
        attributeMap.put(1, "MethodType"); 
        attributeMap.put(2, "PredictedTraceValue"); 
        attributeMap.put(3, "CallersT"); 
        attributeMap.put(4, "CallersN"); 
        attributeMap.put(5, "CallersU"); 
        attributeMap.put(6, "CallersCallersT"); 
        attributeMap.put(7, "CallersCallersN"); 
        attributeMap.put(8, "CallersCallersU"); 
        attributeMap.put(9, "CalleesT"); 
        attributeMap.put(10, "CalleesN"); 
        attributeMap.put(11, "CalleesU"); 
        attributeMap.put(12, "CalleesCalleesT"); 
        attributeMap.put(13, "CalleesCalleesN"); 
        attributeMap.put(14, "CalleesCalleesU"); 
        attributeMap.put(15, "classGold"); 

        AttributeSelection selector = new AttributeSelection();
    	InfoGainAttributeEval evaluator = new InfoGainAttributeEval();
    	Ranker ranker = new Ranker();
    	ranker.setNumToSelect(Math.min(500, instancesTrain.numAttributes() - 1));
    	selector.setEvaluator(evaluator);
    	selector.setSearch(ranker);
    	selector.SelectAttributes(instancesTrain);
    	for(int i=0; i<selector.rankedAttributes().length;i++) {
    		int index = (int)selector.rankedAttributes()[i][0]; 
    		System.out.println(attributeMap.get(index));
    	}

//    	int U=0;
//        for(int  i = 0;i<sum;i++) {
//        	System.out.println(i);
//            double[] probs = classifier8.distributionForInstance(instancesTest.instance(i));
////            System.out.println(Arrays.toString(probs));
//
//        	String reqMethod=(int)ProgramReqMethod.instance(i).toDoubleArray()[1]+"-"+(int)ProgramReqMethod.instance(i).toDoubleArray()[2]; 
//        	int program = (int)ProgramReqMethod.instance(i).toDoubleArray()[0]; 
//        	String programName=getProgName(program); 
//            String classID=MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getMethod().getClazz().ID;
//        	String reqClass=(int)ProgramReqMethod.instance(i).toDoubleArray()[1]+"-"+classID;
//        	if( MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getPredictedTraceValue().equals(TraceValue.UndefinedTrace)	) {
//        		
//        	
//        	
//			        	if(ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(reqClass).equals(TraceValue.Trace) 
//			        	&& MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.Trace)
//			            ){ 
//        		TP_T++;
//        		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setPredictedTraceValue(TraceValue.Trace);
//        		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/TP_T");
//
//        	}
//			        	
//			        	
//			         	else if(ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(reqClass).equals(TraceValue.NoTrace) 
//			        			&& MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.Trace)
//			){
//			        		FP_T++; 
//			        		FN_N++; 
//			        		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setPredictedTraceValue(TraceValue.NoTrace);
//			        		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/FN_N/FP_T");
//
//
//			        	}	
//			        	
//			        	
//			        	//////////////////////////////////////////////////////////////
//			        	
//			        	else if(ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(reqClass).equals(TraceValue.NoTrace) 
//                	&& MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.NoTrace)
//                    ){ 
//                		TP_N++;
//                		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setPredictedTraceValue(TraceValue.NoTrace);
//                		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/TP_N");
//
//                	}
//					        	
//        	
//        	
//       
//        	else if(ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(reqClass).equals(TraceValue.Trace) 
//        			&& MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).getGoldTraceValue().equals(TraceValue.Trace)) {
//        		MethodRTMCell.Totalmethodtraces2HashMap.get(programName).get(reqMethod).setDecision("/U");
//        		U++;  
//        	}
//        }
//        }
//
//    	System.out.println("U: "+U);
//    	System.out.println(TP_N);
//        System.out.println(sum);
//        System.out.println("N Precision:"+(TP_N/(TP_N+FP_N)));  
//        System.out.println("T Precision:"+(TP_T/(TP_T+FP_T)));  
//        System.out.println("N Recall:"+(TP_N/(TP_N+FN_N)));  
//        System.out.println("T Recall:"+(TP_T/(TP_T+FN_T+U)));  
//        System.out.println("TP_T: "+TP_T+" FP_T: "+FP_T+"FN_T: "+FN_T);
//        System.out.println("TP_N: "+TP_N+" FP_N: "+FP_N+"FN_N: "+FN_N);
//        System.out.println("T: "+T);
//        System.out.println("N: "+N);

        
        Logger.logDetailed("gantt", "jss"); 
        
        System.out.println("OVER");
        
        
        
	}

	
	

	private static void RecomputeInputFileAfterStep2(FileWriter myWriter) throws IOException {
		// TODO Auto-generated method stub
		printPredictedValues(myWriter);
	}

	private static String getProgName(int program) {
		String progName="";
		// TODO Auto-generated method stub
		if(program==0) progName="chess"; 
		else if(program==1) progName="gantt"; 
		else if(program==2) progName="itrust"; 
		else if(program==3) progName="jhotdraw"; 
		return progName; 

	}

	private static void printPredictedValues( FileWriter myWriter) throws IOException {
		PrintWriter myWriter2 = new PrintWriter("log//step2Data.txt"); 
		myWriter2.print("");
		myWriter2.close();
		 myWriter = new FileWriter("log//step2Data.txt");
		
		myWriter.write("@RELATION traces \r\n" + 
				"\r\n" + 
				"@ATTRIBUTE gold	{T,N,U}\r\n" + 
				"@ATTRIBUTE program 	{chess, gantt, itrust, jhotdraw}\r\n" + 
				"@ATTRIBUTE MethodType 	{Inner, Leaf, Root}\r\n" + 
				"@ATTRIBUTE RequirementID	REAL\r\n" + 
				"@ATTRIBUTE MethodID REAL \r\n" + 
				"@ATTRIBUTE PredictedTraceValue {UndefinedTrace,NoTrace, Trace, NA}\r\n" + 
				"@ATTRIBUTE CallersT REAL \r\n" + 
				"@ATTRIBUTE CallersN REAL \r\n" + 
				"@ATTRIBUTE CallersU REAL \r\n" + 
				"@ATTRIBUTE CallersCallersT REAL \r\n" + 
				"@ATTRIBUTE CallersCallersN REAL \r\n" + 
				"@ATTRIBUTE CallersCallersU REAL \r\n" + 
				"@ATTRIBUTE CalleesT REAL \r\n" + 
				"@ATTRIBUTE CalleesN REAL \r\n" + 
				"@ATTRIBUTE CalleesU REAL \r\n" + 
				"@ATTRIBUTE CalleesCalleesT REAL \r\n" + 
				"@ATTRIBUTE CalleesCalleesN REAL \r\n" + 
				"@ATTRIBUTE CalleesCalleesU REAL \r\n" + 
				"@ATTRIBUTE classGold {Trace,NoTrace,UndefinedTrace} \r\n" + 
				"\r\n" + 
				"@DATA\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"");
		int i=1; 
		for(String programName: MethodRTMCell.Totalmethodtraces2HashMap.keySet()) {
			LinkedHashMap<String, MethodRTMCell> methodTraces = MethodRTMCell.Totalmethodtraces2HashMap.get(programName);
			for(MethodRTMCell methodRTMCell: methodTraces.values()) {
				
			
			MethodRTMCellList Callers= methodRTMCell.getCallers(programName); 
			MethodRTMCellList Callees = methodRTMCell.getCallees(programName); 
			MethodRTMCellList CallersCallers=methodRTMCell.getCallers(programName).getCallers(programName); 
			MethodRTMCellList CalleesCallees=methodRTMCell.getCallees(programName).getCallees(programName); 
			counts countsCallers = CSV.generateCountsTNUAtLeastOneInstance(Callers); 
			counts countsCallees = CSV.generateCountsTNUAtLeastOneInstance(Callees); 
			counts countsCallersCallers = CSV.generateCountsTNUAtLeastOneInstance(CallersCallers); 
			counts countsCalleesCallees = CSV.generateCountsTNUAtLeastOneInstance(CalleesCallees); 
			String methodType=""; 
			if(!methodRTMCell.getCallers(programName).isEmpty() && !methodRTMCell.getCallees(programName).isEmpty()) {
				methodType="Inner"; 
			}else if( methodRTMCell.getCallees(programName).isEmpty()) {
				methodType="Leaf"; 
			}else if(methodRTMCell.getCallers(programName).isEmpty() ) {
				methodType="Root"; 
			}else {
				methodType="Isolated"; 

			}
			if(!methodRTMCell.logGoldTraceValueString().equals("U")) {

				
				String gold=methodRTMCell.logGoldTraceValueString(); 
				

				
				if(ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(methodRTMCell.getRequirement().ID+"-"+methodRTMCell.getMethod().getClazz().ID)!=null ) {
					myWriter.write(gold+","+programName+","+methodType+","+methodRTMCell.getRequirement().ID+","+methodRTMCell.getMethod().ID+","+methodRTMCell.getPredictedTraceValue()
					+","+countsCallers.amountT+","+countsCallers.amountN+","+countsCallers.amountU
					+","+countsCallersCallers.amountT+","+countsCallersCallers.amountN+","+countsCallersCallers.amountU
					+","+countsCallees.amountT+","+countsCallees.amountN+","+countsCallees.amountU			
					+","+countsCalleesCallees.amountT+","+countsCalleesCallees.amountN+","+countsCalleesCallees.amountU+","+					
					ClazzRTMCell.clazzTracesByProgramNameHashMap.get(programName).get(methodRTMCell.getRequirement().ID+"-"+methodRTMCell.getMethod().getClazz().ID)
					+"\n");
				

				
				 
				i++; 
			}
				
			
			}
			}
			
			System.out.println(i);
		}
		
		

		
		myWriter.close();
		
		
		    File inputFile = new File("log//step2Data.txt");//Training corpus file  
	        ArffLoader atf = new ArffLoader();   

		    atf.setFile(inputFile);            
	        Instances instancesTest = atf.getDataSet(); // Read in the test file  
	        instancesTest.setClassIndex(0); //Setting the line number of the categorized attribute (No. 0 of the first action), instancesTest.numAttributes() can get the total number of attributes.  
	}



}