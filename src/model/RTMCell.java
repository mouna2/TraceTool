package model;

import static traceRefiner.TraceRefinerPredictionPattern.Step1ClassNoTraceImpliesMethodNoTracePattern;

import mainPackage.TraceProcessor;
import mainPackage.TraceProcessor.Algorithm;

public class RTMCell {

	public enum TraceValue {Trace, NoTrace, UndefinedTrace};
	static public boolean modified = false;


	private TraceValue traceValue = TraceValue.UndefinedTrace;
	private TraceValue goldTraceValue = TraceValue.UndefinedTrace;
	private TraceValue VSMTraceValue = TraceValue.UndefinedTrace; 
	private TraceValue subjectTraceValue = TraceValue.UndefinedTrace;
	private int subjectT;
	private int subjectN;
	private TraceValue predictedTraceValue = TraceValue.UndefinedTrace;
	
	
	private String decision=""; 
	private PredictionPattern predictionPattern = null;
	private TraceValue LSITraceValue = TraceValue.UndefinedTrace; 

	public TraceValue getLSITraceValue() {
		return LSITraceValue;
	}
	public void setLSITraceValue(TraceValue lSITraceValue) {
		LSITraceValue = lSITraceValue;
	}
	public TraceValue getVSMTraceValue() {
		return VSMTraceValue;
	}
	public void setVSMTraceValue(TraceValue vSMTraceValue) {
		VSMTraceValue = vSMTraceValue;
	}
	public void setTraceValue(TraceValue traceValue) {
		this.traceValue = traceValue;
	}
	public TraceValue getTraceValue() {
		return traceValue;

	}
	public TraceValue getSubjectTraceValue() {
		return subjectTraceValue;
	}
	public void setSubjectTraceValue(TraceValue subjectTraceValue) {
		this.subjectTraceValue = subjectTraceValue;
	}
	public TraceValue getGoldTraceValue() {
		return goldTraceValue;
	}
	public void setGoldTraceValue(TraceValue value) {
		if (goldTraceValue.equals(TraceValue.Trace))
			goldTraceValue.toString();
		goldTraceValue = value;
	}

	public int getSubjectT() {
		return subjectT;
	}
	public int getSubjectN() {
		return subjectN;
	}
	public void setSubjectTraceVotes(int T, int N) {
		subjectT = T;
		subjectN = N;
	}

	
	
	
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String logTPFPTNFN(String programName){
		
//		if (getPredictedTraceValue().equals(TraceValue.UndefinedTrace)) return "U";
//		if (getGoldTraceValue().equals(TraceValue.UndefinedTrace)) return "U";
//		else if (getPredictedTraceValue().equals(TraceValue.Trace) && getGoldTraceValue().equals(TraceValue.Trace)) return "TP";
//		else if (getPredictedTraceValue().equals(TraceValue.Trace) && getGoldTraceValue().equals(TraceValue.NoTrace)) return "FP";
//		else if ((getPredictedTraceValue().equals(TraceValue.NoTrace)|| getPredictedTraceValue().equals(TraceValue.UndefinedTrace)) && getGoldTraceValue().equals(TraceValue.Trace)) return "FN";
//		else if (getPredictedTraceValue().equals(TraceValue.NoTrace) && getGoldTraceValue().equals(TraceValue.NoTrace)) return "TN";
//		else if (getPredictedTraceValue().equals(TraceValue.UndefinedTrace)) return "U";	

		String res=""; 

		if (getGoldTraceValue().equals(TraceValue.UndefinedTrace)) res=res+"/"+"U";
		 if (getPredictedTraceValue().equals(TraceValue.NoTrace)	&& getGoldTraceValue().equals(TraceValue.Trace)) res=res+"/"+"FN_T";
		 if (getPredictedTraceValue().equals(TraceValue.NoTrace) && getGoldTraceValue().equals(TraceValue.Trace)) res=res+"/"+"FP_N";

		 if (getPredictedTraceValue().equals(TraceValue.Trace)&& getGoldTraceValue().equals(TraceValue.NoTrace)) res=res+"/"+"FN_N";	 
		 
//		 if (getPredictedTraceValue().equals(TraceValue.UndefinedTrace) && getGoldTraceValue().equals(TraceValue.NoTrace)) res=res+"/"+"FN_N_U";


		 if (getPredictedTraceValue().equals(TraceValue.Trace)&& getGoldTraceValue().equals(TraceValue.Trace)) res=res+"/"+"TN_N";
		 
		 if (getPredictedTraceValue().equals(TraceValue.Trace) && getGoldTraceValue().equals(TraceValue.NoTrace)) res=res+"/"+"FP_T";
		 if (getPredictedTraceValue().equals(TraceValue.NoTrace) && getGoldTraceValue().equals(TraceValue.NoTrace))res=res+"/"+"TP_N";
		 
		 if (getPredictedTraceValue().equals(TraceValue.Trace) && getGoldTraceValue().equals(TraceValue.Trace)) res=res+"/"+"TP_T";
		 if (getPredictedTraceValue().equals(TraceValue.NoTrace)&& getGoldTraceValue().equals(TraceValue.NoTrace)) res=res+"/"+"TN_T";

		
		
		if(res.equals("")) res="/U"; 
		return res;
	}
	
	
	public static void logTPTNFPFN2(String programName, String Step) {
		int U=0; int FP_T=0; int FN_T=0; int TP_T=0; int TN_T=0; int FP_N=0; int FN_N=0; int TP_N=0; int TN_N=0; int FN_NT_undefinedPred=0; int FN_NT_tracePred=0;  
		int FN_T_undefinedPred=0; int FN_T_NoTracePred=0;   int TPred=0; int NPred=0; int UPred=0; 
//		System.out.println("Program, TP_T, FP_T, FN_T, FN_T_undefined, FN_T_NoTraces, ,,TP_NT, FP_NT, FN_NT, FN_NT_undefined, FN_NT_trace, ,,");
		
//		System.out.println("Program, Step, TP_T, TP_NT, FP_T, FP_NT, FN_T, FN_T_undefined, FN_T_NoTrace,  FN_NT,FN_NT_Trace, FN_NT_undefined, TN_NoTrace, TN_Trace");

		for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {
			TraceValue predictedValue= null; 
			
			if(TraceProcessor.test.equals(Algorithm.Refiner)) {
				 predictedValue= methodtrace.getPredictedTraceValue(); 

			}else if(TraceProcessor.test.equals(Algorithm.VSM)) {
				predictedValue= methodtrace.getVSMTraceValue(); 
//				System.out.println("=====>"+predictedValue+","+methodtrace.getGoldTraceValue());
			}else if(TraceProcessor.test.equals(Algorithm.LSI)) {
				predictedValue= methodtrace.getLSITraceValue(); 
			}
			
			if(predictedValue.equals(TraceValue.Trace)) TPred++; 
			else if (predictedValue.equals(TraceValue.NoTrace)) NPred++; 
			else if (predictedValue.equals(TraceValue.UndefinedTrace)) UPred++; 

			if (methodtrace.getGoldTraceValue().equals(TraceValue.UndefinedTrace)) U++; 
			 if (predictedValue.equals(TraceValue.NoTrace)	&& methodtrace.getGoldTraceValue().equals(TraceValue.Trace)) FN_T++; 
			 if (predictedValue.equals(TraceValue.Trace)	&& methodtrace.getGoldTraceValue().equals(TraceValue.NoTrace)) FN_N++; 
			 
//			 if (predictedValue.equals(TraceValue.UndefinedTrace)&& methodtrace.getGoldTraceValue().equals(TraceValue.Trace)) FN_T_undefinedPred++; 	 
//			 if (predictedValue.equals(TraceValue.NoTrace)&& methodtrace.getGoldTraceValue().equals(TraceValue.Trace)) FN_T_NoTracePred++; 
//			 
//			 if (predictedValue.equals(TraceValue.Trace) && methodtrace.getGoldTraceValue().equals(TraceValue.NoTrace)) FN_NT_tracePred++;  
//			 if (predictedValue.equals(TraceValue.UndefinedTrace) && methodtrace.getGoldTraceValue().equals(TraceValue.NoTrace)) FN_NT_undefinedPred++;  


			 if (predictedValue.equals(TraceValue.Trace)	&& methodtrace.getGoldTraceValue().equals(TraceValue.Trace)) TN_N++; 
			 if (predictedValue.equals(TraceValue.NoTrace)&& methodtrace.getGoldTraceValue().equals(TraceValue.NoTrace))TN_T++; 
			 
			 if (predictedValue.equals(TraceValue.Trace) && methodtrace.getGoldTraceValue().equals(TraceValue.NoTrace)) FP_T++; 
			 if (predictedValue.equals(TraceValue.NoTrace) && methodtrace.getGoldTraceValue().equals(TraceValue.Trace)) FP_N++;
			 if (predictedValue.equals(TraceValue.NoTrace) && methodtrace.getGoldTraceValue().equals(TraceValue.NoTrace)) TP_N++;
			 if (predictedValue.equals(TraceValue.Trace) && methodtrace.getGoldTraceValue().equals(TraceValue.Trace)) TP_T++;


		}
//		System.out.println(programName+","+Step+","+TP_T+","+TP_NT+","+FP_T+","+FP_NT+","+FN_T+","+FN_T_undefinedPred+","+FN_T_NoTracePred+","+FN_NT+","+FN_NT_tracePred
//				+","+FN_NT_undefinedPred+","+TN_NT+","+TN_T); 
		System.out.println(programName+","+TPred+","+NPred+","+UPred+","+TP_T+","+FP_T+","+FN_T+","+FN_T_undefinedPred+","+FN_T_NoTracePred+","+""+","+""+","+TP_N
				+","+FP_N+","+FN_N+","+FN_NT_undefinedPred+","+FN_NT_tracePred); 

	}
	public String logPredictionPattern(){
		if (predictionPattern==null) return "null";
		return predictionPattern.getPattern();
	}


	public TraceValue getPredictedTraceValue() { return predictedTraceValue; }
	public PredictionPattern getPredictionPattern() {
		return predictionPattern;
	}
	public void setPrediction(PredictionPattern pattern) {
		if (predictionPattern!=pattern) modified=true;
		predictionPattern = pattern;
		predictedTraceValue = predictionPattern.getValue();

		if (goldTraceValue.equals(TraceValue.Trace))
			pattern.T++;
		else if (goldTraceValue.equals(TraceValue.NoTrace))
			pattern.N++;
		else if (goldTraceValue.equals(TraceValue.UndefinedTrace))
			pattern.U++;
	}
	public void resetPrediction() {
		predictionPattern = null;
		predictedTraceValue = TraceValue.UndefinedTrace;
	}
	public void setPredictedTraceValue(TraceValue predictedTraceValue) {
		modified=true;
		this.predictedTraceValue = predictedTraceValue;
	}

	public String logTraceValueString() { return getTraceValue().toString().substring(0,1); }
	public String logGoldTraceValueString() { return getGoldTraceValue().toString().substring(0,1); }
	public String logPredictedTraceValueString() { return getPredictedTraceValue().toString().substring(0,1); }
}