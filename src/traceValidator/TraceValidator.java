package traceValidator;

import model.Definitions;
import model.MethodRTMCell;
import model.RTMCell;

public class TraceValidator {

	static public void makePredictions() {

		for (MethodRTMCell methodtrace : MethodRTMCell.methodtraces2HashMap.values()) {

			String reqMethod = methodtrace.getRequirement().getID() + "-" + methodtrace.getMethod().getID();

			//INNER METHOD
			if (!methodtrace.getCallers().isEmpty() && !methodtrace.getCallees().isEmpty()) {
				methodtrace.setPrediction(TraceValidatorPredictionPattern.getInnerPattern(methodtrace.getCallers(), methodtrace.getCallees(), false));
			}
			//LEAF METHOD
			else if (!methodtrace.getCallers().isEmpty() && methodtrace.getCallees().isEmpty() && !methodtrace.getCallers().getCallers().isEmpty()) {
				methodtrace.setPrediction(TraceValidatorPredictionPattern.getLeafPattern(methodtrace.getCallers(), methodtrace.getCallers().getCallers(), false));
			}
			//ROOT METHOD
			else if (methodtrace.getCallers().isEmpty() && !methodtrace.getCallees().isEmpty() && !methodtrace.getCallees().getCallees().isEmpty()) {
				methodtrace.setPrediction(TraceValidatorPredictionPattern.getRootPattern(methodtrace.getCallees(), methodtrace.getCallees().getCallees(), false));
			}
			//E ISOLATED
			else if (methodtrace.getCallers().isEmpty() && methodtrace.getCallees().isEmpty()) {
				methodtrace.setPrediction(TraceValidatorPredictionPattern.isolatedPredictionPattern);
			}
			//E NOT APPLICABLE
			else {
				methodtrace.setPrediction(TraceValidatorPredictionPattern.notApplicablePredictionPattern);
			}
		}
	}
}





