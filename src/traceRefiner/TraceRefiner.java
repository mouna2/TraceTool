package traceRefiner;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Clazz;
import model.MethodList;
import model.MethodRTMCell;
import model.MethodRTMCellList;
import model.RTMCell;
import model.RTMCell.TraceValue;
import model.Requirement;
import model.Variable;
import model.VariableList;
import traceValidator.TraceValidatorPredictionPattern;

public class TraceRefiner {

	static public void step1_classNs2MethodNs() {
//		Map<String, MethodRTMCell> treeMap = new TreeMap<String,MethodRTMCell>(MethodRTMCell.methodtraces2HashMap);

		for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
			if (methodtrace.getClazzRTMCell().getTraceValue().equals(RTMCell.TraceValue.NoTrace))
				methodtrace.setPrediction(TraceRefinerPredictionPattern.Step1ClassNoTraceImpliesMethodNoTracePattern);
		}
	}

	static public void step2_propagateMethodNs(int version) {
		int iteration = 0;
//		Map<String, MethodRTMCell> treeMap = new TreeMap<String,MethodRTMCell>(MethodRTMCell.methodtraces2HashMap);

		do {
			RTMCell.modified = false;

			if (version==1) {
				for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
					if (methodtrace.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) {
						if (methodtrace.getCallers().allPredictedNs())
							methodtrace.setPrediction(TraceRefinerPredictionPattern.Step2AllCallersDoNoTracePattern);
						else if (methodtrace.getCallees().allPredictedNs())
							methodtrace.setPrediction(TraceRefinerPredictionPattern.Step2AllCalleesDoNoTracePattern);
//						//added these three 
//						else if (methodtrace.getVariableReads().allPredictedNs())
//							methodtrace.setPrediction(TraceRefinerPredictionPattern.Step2AllParametersDoNoTracePattern);
//						else if (methodtrace.getVariableWrites().allPredictedNs())
//							methodtrace.setPrediction(TraceRefinerPredictionPattern.Step2AllFieldMethodsDoNoTracePattern);
					
						
						
					
					}
				}
			}
			else if (version==2) {
				for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
					if (methodtrace.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) {
						if (!methodtrace.getCallers().isEmpty() && !methodtrace.getCallees().isEmpty())
							methodtrace.setPrediction(TraceValidatorPredictionPattern.getInnerPattern(methodtrace.getCallers(), methodtrace.getCallees(), true));
						else if (!methodtrace.getCallers().isEmpty() && methodtrace.getCallees().isEmpty() && !methodtrace.getCallers().getCallers().isEmpty())
							methodtrace.setPrediction(TraceValidatorPredictionPattern.getLeafPattern(methodtrace.getCallers(), methodtrace.getCallers().getCallers(), true));
						else if (methodtrace.getCallers().isEmpty() && !methodtrace.getCallees().isEmpty() && !methodtrace.getCallees().getCallees().isEmpty())
							methodtrace.setPrediction(TraceValidatorPredictionPattern.getRootPattern(methodtrace.getCallees(), methodtrace.getCallees().getCallees(), true));
					}
				}
			}
			iteration++;
		} while (RTMCell.modified);
	}

	static public void step3_classTs2MethodTs() { 
		//test 2
//		Map<String, MethodRTMCell> treeMap = new TreeMap<String,MethodRTMCell>(MethodRTMCell.methodtraces2HashMap);
		
//		for(Requirement req: Requirement.requirementsHashMap.values()) {
//			System.out.println(req.ID);
//			int N=0; 
//			for(RTMCell methodtrace: req.getRTMMethodCellList()) {
//				if(methodtrace.getPredictedTraceValue().equals(TraceValue.NoTrace)) N++; 
//			}
//			System.out.println(req.ID+"  "+N);
//
//		}
		
		for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
			String reqMethod = methodtrace.getRequirement().ID+"-"+methodtrace.getMethod().ID;
//			System.out.println(reqMethod);
			if (methodtrace.getTraceValue().equals(RTMCell.TraceValue.UndefinedTrace) &&
				methodtrace.getClazzRTMCell().getTraceValue().equals(RTMCell.TraceValue.Trace)) {
//cc
				

			
					//XXX getclasses from first condition missing
					if (methodtrace.getCallers().getClazzesRTMCellList().atLeast1T() &&
							methodtrace.getCallees().getClazzesRTMCellList().atLeast1T() &&
							methodtrace.getCallers().noPredictedNs() &&     //supposed to be noNs
							methodtrace.getCallees().noPredictedNs() 
							) {
						methodtrace.setPrediction(TraceRefinerPredictionPattern.Step3CallerAndCalleeClassTracesImpliesMethodTracePattern);
					} else if (methodtrace.getCallees().isEmpty() &&
							methodtrace.getCallers().getClazzesRTMCellList().atLeast1T() &&
							methodtrace.getCallers().noPredictedNs()) {
						methodtrace.setPrediction(TraceRefinerPredictionPattern.Step3LeafCallerClassTracesImpliesMethodTracePattern);
					} else if (methodtrace.getCallers().getClazzesRTMCellList().atLeast1T() &&
							methodtrace.getCallers().noPredictedNs() &&
							methodtrace.getCallees().noPredictedNs()) {
						methodtrace.setPrediction(TraceRefinerPredictionPattern.Step3CallerClassOnlyTracesImpliesMethodTracePattern);
					} else if (methodtrace.getCallees().getClazzesRTMCellList().atLeast1T() &&
							methodtrace.getCallers().noPredictedNs() &&
							methodtrace.getCallees().noPredictedNs()) {
						methodtrace.setPrediction(TraceRefinerPredictionPattern.Step3CalleeClassOnlyTracesImpliesMethodTracePattern);
					}	
				
				
			
				
			}
		}
	}

	static public void step4_propagateMethodTs(int version) {
		int iteration=0;
//		Map<String, MethodRTMCell> treeMap = new TreeMap<String,MethodRTMCell>(MethodRTMCell.methodtraces2HashMap);

		do {
			RTMCell.modified=false;

			if (version==1) {
				for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
					String reqMethod=methodtrace.getRequirement().ID+"-"+methodtrace.getMethod().ID; 
//					System.out.println(methodtrace.getRequirement().ID+"-"+methodtrace.getMethod().ID);
					
					if (methodtrace.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) {
						if (methodtrace.getCallees().isEmpty() && methodtrace.getCallers().atLeast1PredictedT())
							methodtrace.setPrediction(TraceRefinerPredictionPattern.Step4LeafCallerTracesImpliesMethodTracesPattern);
						else if ( methodtrace.getCallers().allPredictedTs())
							methodtrace.setPrediction(TraceRefinerPredictionPattern.Step4CallerTracesImpliesMethodTracesPattern);
						else if ( methodtrace.getCallees().allPredictedTs())
							methodtrace.setPrediction(TraceRefinerPredictionPattern.Step4CalleeTracesImpliesMethodTracesPattern);
//						//added these three predictions 
//						else if ( methodtrace.getVariableReads().allPredictedTs())
//							methodtrace.setPrediction(TraceRefinerPredictionPattern.Step4ParameterImpliesMethodTracesPattern);
//						else if ( methodtrace.getVariableWrites().allPredictedTs())
//							methodtrace.setPrediction(TraceRefinerPredictionPattern.Step4FieldMethodsImpliesMethodTracesPattern);
					
					
					
					}
				}
			}
			else if (version==2) {
				for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
					if (methodtrace.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) {
						if (!methodtrace.getCallers().isEmpty() && !methodtrace.getCallees().isEmpty())
							methodtrace.setPrediction(TraceValidatorPredictionPattern.getInnerPattern(methodtrace.getCallers(), methodtrace.getCallees(), true));
						else if (!methodtrace.getCallers().isEmpty() && methodtrace.getCallees().isEmpty() && !methodtrace.getCallers().getCallers().isEmpty())
							methodtrace.setPrediction(TraceValidatorPredictionPattern.getLeafPattern(methodtrace.getCallers(), methodtrace.getCallers().getCallers(), true));
						else if (methodtrace.getCallers().isEmpty() && !methodtrace.getCallees().isEmpty() && !methodtrace.getCallees().getCallees().isEmpty())
							methodtrace.setPrediction(TraceValidatorPredictionPattern.getRootPattern(methodtrace.getCallees(), methodtrace.getCallees().getCallees(), true));
					}
				}
			}
			iteration++;
		} while (RTMCell.modified);
	}

	public static void checkGoldPred(String programName) {
		int counterGoldTraceUndefinedPred=0; 
		int counterGoldNoTraceUndefinedPred=0; 
		int counterGoldUndefinedTracePred=0; 
		int counterGoldUndefinedNoTracePred=0; 
		int countGoldTN=0; 
		
		int counter2=0; 
		for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
			if(methodtrace.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace) && methodtrace.getGoldTraceValue().equals(RTMCell.TraceValue.Trace))
			{
				counterGoldTraceUndefinedPred++; 
			}
			else if(methodtrace.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace) && methodtrace.getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace))
			{
				counterGoldNoTraceUndefinedPred++; 
			}
			
			
			if(methodtrace.getPredictedTraceValue().equals(RTMCell.TraceValue.Trace) && methodtrace.getGoldTraceValue().equals(RTMCell.TraceValue.UndefinedTrace))
			{
				counterGoldUndefinedTracePred++; 
			}
			else if(methodtrace.getPredictedTraceValue().equals(RTMCell.TraceValue.NoTrace) && methodtrace.getGoldTraceValue().equals(RTMCell.TraceValue.UndefinedTrace))
			{
				counterGoldUndefinedNoTracePred++; 
			}
			
			if(methodtrace.getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace)|| methodtrace.getGoldTraceValue().equals(RTMCell.TraceValue.Trace)) {
				countGoldTN++; 
			}
//			if((methodtrace.getClazzRTMCell().getGoldTraceValue().equals(RTMCell.TraceValue.Trace)||methodtrace.getClazzRTMCell().getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace))
//					&& (methodtrace.getSubjectT()>0 || methodtrace.getSubjectN()>0)
//					) {
//				counter2++; 
//
//					}else {
//					}
		}
		System.out.println(programName+" ,Gold Trace --- Undefined Pred  ,"+counterGoldTraceUndefinedPred+","+countGoldTN);
		System.out.println(programName+" ,Gold No Trace --- Undefined Pred,  "+counterGoldNoTraceUndefinedPred+","+countGoldTN);
		System.out.println(programName+" ,Gold Undefined --- Trace Pred  ,"+counterGoldUndefinedTracePred);
		System.out.println(programName+" ,Gold Undefined --- No Trace Pred,  "+counterGoldUndefinedNoTracePred);
//		System.out.println("RESULTS=====>subject data available for developer data "+programName+"   "+counter2);
//
//		System.out.println("here");
		
	}

}