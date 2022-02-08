package traceValidatorGhabi;

import model.PredictionPattern;
import model.RTMCell;

import java.util.ArrayList;

public class TraceValidatorGhabiPredictionPattern extends PredictionPattern {

	static public ArrayList<PredictionPattern> patterns = new ArrayList<>();

	public TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue value, String pattern, int likelihood) {
		super(value, pattern, likelihood);
		patterns.add(this);
	}

	static public TraceValidatorGhabiPredictionPattern PureInnerTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern MixedInnerTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern PureInnerNoTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern MixedInnerNoTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern PureRootTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern MixedRootTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern PureRootNoTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern MixedRootNoTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern PureLeafTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern MixedLeafTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern PureLeafNoTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern MixedLeafNoTracePattern = null;
	static public TraceValidatorGhabiPredictionPattern incompletePredictionPattern = null;
	static public TraceValidatorGhabiPredictionPattern boundaryPredictionPattern = null;

	static public void define() {
		PureInnerTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.Trace, "PureInnerTrace", 0);
		MixedInnerTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.Trace, "MixedInnerTrace", 0);
		PureInnerNoTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.NoTrace, "PureInnerNoTrace", 0);
		MixedInnerNoTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.NoTrace, "MixedInnerNoTrace", 0);
		PureRootTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.Trace, "PureRootTrace", 0);
		MixedRootTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.Trace, "MixedRootTrace", 0);
		PureRootNoTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.NoTrace, "PureRootNoTrace", 0);
		MixedRootNoTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.NoTrace, "MixedRootNoTrace", 0);
		PureLeafTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.Trace, "PureLeafTrace", 0);
		MixedLeafTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.Trace, "MixedLeafTrace", 0);
		PureLeafNoTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.NoTrace, "PureLeafNoTrace", 0);
		MixedLeafNoTracePattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.NoTrace, "MixedLeafNoTrace", 0);
		incompletePredictionPattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "isolated", 100);
		boundaryPredictionPattern = new TraceValidatorGhabiPredictionPattern(RTMCell.TraceValue.UndefinedTrace, "notApplicable", 100);
	}

}