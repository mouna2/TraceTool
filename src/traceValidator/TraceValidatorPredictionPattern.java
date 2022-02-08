package traceValidator;

import model.MethodRTMCellList;
import model.PredictionPattern;
import model.RTMCell;

import java.util.ArrayList;
import java.util.HashMap;

public class TraceValidatorPredictionPattern extends PredictionPattern {

	static public ArrayList<PredictionPattern> patterns = new ArrayList<>();

	public TraceValidatorPredictionPattern(RTMCell.TraceValue value, String pattern, int likelihood) {
		super(value, pattern, likelihood);
		patterns.add(this);
	}


	public static PredictionPattern getInnerPattern(MethodRTMCellList callers, MethodRTMCellList callees, boolean usePredicted) {
		String callersPattern = calculateTNU(callers, usePredicted);
		String calleesPattern = calculateTNU(callees, usePredicted);
		PredictionPattern p = pattern2Prediction.get(callersPattern+"-x-"+calleesPattern);
		return p;
	}

	public static PredictionPattern getRootPattern(MethodRTMCellList callees, MethodRTMCellList calleesCallees, boolean usePredicted) {
		String calleesPattern = calculateTNU(callees, usePredicted);
		String calleesCalleesPattern = calculateTNU(calleesCallees, usePredicted);
		PredictionPattern p = pattern2Prediction.get("x-"+calleesPattern+"-"+calleesCalleesPattern);
		return p;
	}

	public static PredictionPattern getLeafPattern(MethodRTMCellList callers, MethodRTMCellList callersCallers, boolean usePredicted) {
		String callersPattern = calculateTNU(callers, usePredicted);
		String callersCallersPattern = calculateTNU(callersCallers, usePredicted);
		PredictionPattern p = pattern2Prediction.get(callersCallersPattern+"-"+callersPattern+"-x");
		return p;
	}


	public static String calculateTNU(MethodRTMCellList methodTraces, boolean usePredicted) {
		boolean hasT = false;
		boolean hasN = false;
		boolean hasU = false;
		for(RTMCell cell: methodTraces) {
			if (usePredicted) {
				if (cell.getPredictedTraceValue().equals(RTMCell.TraceValue.Trace)) {
					hasT = true;
				}
				if (cell.getPredictedTraceValue().equals(RTMCell.TraceValue.NoTrace)) {
					hasN = true;
				}
				if (cell.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) {
					hasU = true;
				}
			}
			else {
				if (cell.getTraceValue().equals(RTMCell.TraceValue.Trace)) {
					hasT = true;
				}
				if (cell.getTraceValue().equals(RTMCell.TraceValue.NoTrace)) {
					hasN = true;
				}
				if (cell.getTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) {
					hasU = true;
				}
			}
		}
		return (hasT ? "T":"")+(hasN ? "N":"")+(hasU ? "U":"");
	}

	static public TraceValidatorPredictionPattern isolatedPredictionPattern = new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "isolated", 100);
	static public TraceValidatorPredictionPattern notApplicablePredictionPattern = new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "notApplicable", 100);

	static public void define(int validatorPatternsVersion){
		patterns = new ArrayList<>();
		if (validatorPatternsVersion==1) {
			//*** INNER
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "TN-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "TNU-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "NU-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-x-TNU", 0);


			//*** LEAF
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "TN-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "TNU-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "NU-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-TNU-x", 0);

			//*** ROOT
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-T-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-T-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-T-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-T-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-T-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-T-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-T-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TU-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TU-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TU-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TU-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TU-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TU-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TU-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TN-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TN-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TN-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TN-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TNU-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-TNU-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TNU-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TNU-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TNU-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TNU-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TNU-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-NU-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-NU-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-N-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-N-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-N-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-TNU", 0);
		}
		else if (validatorPatternsVersion==2) {
			//*** INNER
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "U-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "TN-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "TNU-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "NU-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-x-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-x-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-x-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-x-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-x-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-x-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-x-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-x-TNU", 0);


			//*** LEAF
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "U-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "U-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "U-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "T-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "T-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TU-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TU-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TN-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TN-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "TNU-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "TNU-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "NU-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "NU-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "NU-TNU-x", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-T-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-N-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-U-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-TN-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-TU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "N-NU-x", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "N-TNU-x", 0);

			//*** ROOT
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-U-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-T-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-T-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-T-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-T-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-T-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-T-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-T-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TU-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TU-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TU-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TU-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TU-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TU-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.Trace, "x-TU-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TN-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TNU-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TNU-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TNU-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TNU-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TNU-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TNU-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-TNU-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-NU-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "x-NU-TNU", 0);

			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-T", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-N", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-U", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-TN", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-TU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-NU", 0);
			new TraceValidatorPredictionPattern(RTMCell.TraceValue.NoTrace, "x-N-TNU", 0);
		}
	}
}