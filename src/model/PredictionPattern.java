package model;

import evaluation.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class PredictionPattern {

	static public HashMap<String, PredictionPattern> pattern2Prediction = new HashMap<String, PredictionPattern>();

	public int T = 0;
	public int N = 0;
	public int U = 0;

	static public void reset() {
		for(PredictionPattern pattern: pattern2Prediction.values()) {
			pattern.T=0;
			pattern.N=0;
			pattern.U=0;
		}
	}

	public RTMCell.TraceValue value=null;
	public int likelihood;
	public String pattern="";

	public PredictionPattern(RTMCell.TraceValue value, String pattern, int likelihood) {
		super();
		this.pattern = pattern;
		this.value = value;
		this.likelihood=likelihood;

		pattern2Prediction.put(pattern, this);
	}

	public RTMCell.TraceValue getValue() { return value; }
	public String getPattern() {
		return pattern;
	}
	public int getLikelihood() { return likelihood; }
}


