package traceValidatorGhabi;

import model.Definitions;
import model.MethodRTMCell;
import traceValidator.TraceValidatorPredictionPattern;

public class TraceValidatorGhabi {

	static public void makePredictions() {
		for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {

			if (methodtrace.getCallers().size() > 0 && methodtrace.getCallees().size() > 0) {
				if (methodtrace.getCallers().atLeast1T() && methodtrace.getCallees().atLeast1T()) {
					if (methodtrace.getCallers().noNs() && methodtrace.getCallees().noNs() && methodtrace.getCallers().noUs() && methodtrace.getCallees().noUs())
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.PureInnerTracePattern);
					else
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.MixedInnerTracePattern);
				} else if (methodtrace.getCallers().atLeast1N() && methodtrace.getCallees().atLeast1N()) {
					if (methodtrace.getCallers().noTs() && methodtrace.getCallees().noTs() && methodtrace.getCallers().noUs() && methodtrace.getCallees().noUs())
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.PureInnerNoTracePattern);
					else if (methodtrace.getCallers().atLeast1U() && methodtrace.getCallees().atLeast1U())
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.incompletePredictionPattern);
					else
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.MixedInnerNoTracePattern);
				} else if (methodtrace.getCallers().atLeast1U() || methodtrace.getCallees().atLeast1U()) {
					methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.incompletePredictionPattern);
				} else {
					methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.boundaryPredictionPattern);
				}
			} else if (methodtrace.getCallers().size() > 0) {
				if (methodtrace.getCallers().atLeast1T() && methodtrace.getCallers().getCallers().atLeast1T()) {
					if (methodtrace.getCallers().noNs() && methodtrace.getCallers().getCallers().noNs() && methodtrace.getCallers().noUs() && methodtrace.getCallers().getCallers().noUs())
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.PureLeafTracePattern);
					else
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.MixedLeafTracePattern);
				} else if (methodtrace.getCallers().atLeast1N() && methodtrace.getCallers().getCallers().atLeast1N()) {
					if (methodtrace.getCallers().noTs() && methodtrace.getCallers().getCallers().noTs() && methodtrace.getCallers().noUs() && methodtrace.getCallers().getCallers().noUs())
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.PureLeafNoTracePattern);
					else if (methodtrace.getCallers().atLeast1U() && methodtrace.getCallers().getCallers().atLeast1U())
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.incompletePredictionPattern);
					else
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.MixedLeafNoTracePattern);
				} else if (methodtrace.getCallers().atLeast1U() || methodtrace.getCallers().getCallers().atLeast1U()) {
					methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.incompletePredictionPattern);
				} else {
					methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.boundaryPredictionPattern);
				}
			} else if (methodtrace.getCallees().size() > 0) {
				if (methodtrace.getCallees().atLeast1T() && methodtrace.getCallees().getCallees().atLeast1T()) {
					if (methodtrace.getCallees().noNs() && methodtrace.getCallees().getCallees().noNs() && methodtrace.getCallees().noUs() && methodtrace.getCallees().getCallees().noUs())
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.PureRootTracePattern);
					else
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.MixedRootTracePattern);
				} else if (methodtrace.getCallees().atLeast1N() && methodtrace.getCallees().getCallees().atLeast1N()) {
					if (methodtrace.getCallees().noTs() && methodtrace.getCallees().getCallees().noTs() && methodtrace.getCallees().noUs() && methodtrace.getCallees().getCallees().noUs())
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.PureRootNoTracePattern);
					else if (methodtrace.getCallees().atLeast1U() && methodtrace.getCallees().getCallees().atLeast1U())
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.incompletePredictionPattern);
					else
						methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.MixedRootNoTracePattern);
				} else if (methodtrace.getCallees().atLeast1U() || methodtrace.getCallees().getCallees().atLeast1U()) {
					methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.incompletePredictionPattern);
				} else {
					methodtrace.setPrediction(TraceValidatorGhabiPredictionPattern.boundaryPredictionPattern);
				}
			}
		}
	}
}
