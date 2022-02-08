package model;

import model.RTMCell.TraceValue;

public class VariableCell {
	public Requirement requirement; 
	public Variable variable; 
	public TraceValue goldTraceValue;
	public Requirement getRequirement() {
		return requirement;
	}
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
	public Variable getVariable() {
		return variable;
	}
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	public TraceValue getGoldTraceValue() {
		return goldTraceValue;
	}
	public void setGoldTraceValue(TraceValue goldTraceValue) {
		this.goldTraceValue = goldTraceValue;
	} 
	
	
	
}
