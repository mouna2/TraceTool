package traceRefiner;

import model.PredictionPattern;
import model.RTMCell;

import java.util.ArrayList;

public class TraceRefinerPredictionPattern extends PredictionPattern {

	static public ArrayList<PredictionPattern> patterns = new ArrayList<>();

	public TraceRefinerPredictionPattern(RTMCell.TraceValue value, String pattern, int likelihood) {
		super(value, pattern, likelihood);
		patterns.add(this);
	}

	static public TraceRefinerPredictionPattern IsolatedPattern = null;
	static public TraceRefinerPredictionPattern Step1ClassNoTraceImpliesMethodNoTracePattern = null;
	static public TraceRefinerPredictionPattern Step2AllCallersDoNoTracePattern = null;
	static public TraceRefinerPredictionPattern Step2AllCalleesDoNoTracePattern = null;
	static public TraceRefinerPredictionPattern Step5AllVariablesWrittenNoTrace = null;
	static public TraceRefinerPredictionPattern Step2AllFieldMethodsDoNoTracePattern = null;
	static public TraceRefinerPredictionPattern Step2AllParametersMethodsDoNoTracePattern = null;
	static public TraceRefinerPredictionPattern Step2ReturnTypeDoNoTracePattern = null;

	static public TraceRefinerPredictionPattern Step5AllVariablesReadNoTrace = null;

	static public TraceRefinerPredictionPattern Step3CallerAndCalleeClassTracesImpliesMethodTracePattern = null;
	static public TraceRefinerPredictionPattern Step3LeafCallerClassTracesImpliesMethodTracePattern = null;
	static public TraceRefinerPredictionPattern Step3CallerClassOnlyTracesImpliesMethodTracePattern = null;
	static public TraceRefinerPredictionPattern Step3CalleeClassOnlyTracesImpliesMethodTracePattern = null;
	static public TraceRefinerPredictionPattern Step4LeafCallerTracesImpliesMethodTracesPattern = null;
	static public TraceRefinerPredictionPattern Step4CallerTracesImpliesMethodTracesPattern = null;
	static public TraceRefinerPredictionPattern Step4CalleeTracesImpliesMethodTracesPattern = null;
	static public TraceRefinerPredictionPattern Step5AllVariablesWrittenTrace = null;
	static public TraceRefinerPredictionPattern Step4FieldMethodsImpliesMethodTracesPattern = null;
	static public TraceRefinerPredictionPattern Step4ParametersImpliesMethodTracesPattern = null;
	static public TraceRefinerPredictionPattern Step4ReturnTypeImpliesMethodTracesPattern = null;

	static public TraceRefinerPredictionPattern Step5AllVariablesReadTrace = null;


	static public void define() {
		 IsolatedPattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "IsolatedPattern", 0);
		 Step1ClassNoTraceImpliesMethodNoTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.NoTrace, "Step1ClassNoTraceImpliesMethodNoTracePattern", 0);
		
		 Step2AllCallersDoNoTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.NoTrace, "Step2AllCallersDoNoTracePattern", 0);
		 Step2AllCalleesDoNoTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.NoTrace, "Step2AllCalleesDoNoTracePattern", 0);
		 Step2AllFieldMethodsDoNoTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.NoTrace, "Step2AllFieldMethodsDoNotTrace", 0);
		 Step2AllParametersMethodsDoNoTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.NoTrace, "Step2AllParametersDoNotTrace", 0);
		 Step2ReturnTypeDoNoTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.NoTrace, "Step2ReturnTypeDoesNotTrace", 0);

		 Step3CallerAndCalleeClassTracesImpliesMethodTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step3CallerAndCalleeClassTracesImpliesMethodTracePattern", 0);
		 Step3LeafCallerClassTracesImpliesMethodTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step3LeafCallerClassTracesImpliesMethodTracePattern", 0);
		 Step3CallerClassOnlyTracesImpliesMethodTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step3CallerClassOnlyTracesImpliesMethodTracePattern", 0);
		 Step3CalleeClassOnlyTracesImpliesMethodTracePattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step3CalleeClassOnlyTracesImpliesMethodTracePattern", 0);
		
		 Step4LeafCallerTracesImpliesMethodTracesPattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step4LeafCallerTracesImpliesMethodTracesPattern", 0);
		 Step4CallerTracesImpliesMethodTracesPattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step4CallerTracesImpliesMethodTracesPattern", 0);
		 Step4CalleeTracesImpliesMethodTracesPattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step4CalleeTracesImpliesMethodTracesPattern", 0);
		 Step4FieldMethodsImpliesMethodTracesPattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step4AllFieldMethodsTrace", 0);
		 Step4ParametersImpliesMethodTracesPattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step4AllParametersTrace", 0);
		 Step4ReturnTypeImpliesMethodTracesPattern = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step4ReturnTypeTrace", 0);

		 
		
		 
		 Step5AllVariablesWrittenNoTrace = new TraceRefinerPredictionPattern(RTMCell.TraceValue.NoTrace, "Step5AllVariablesWrittenNoTrace", 0);
		 Step5AllVariablesWrittenTrace = new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step5AllVariablesWrittenTrace", 0);
		 Step5AllVariablesReadTrace= new TraceRefinerPredictionPattern(RTMCell.TraceValue.Trace, "Step5AllVariablesReadTrace", 0);
		 Step5AllVariablesReadNoTrace= new TraceRefinerPredictionPattern(RTMCell.TraceValue.NoTrace, "Step5AllVariablesReadNoTrace", 0);

	}
}