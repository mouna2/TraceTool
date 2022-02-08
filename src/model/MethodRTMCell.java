package model;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MethodRTMCell extends RTMCell {

	//////////////////////////////////////////////////////////////////////////
	// RTM
	//////////////////////////////////////////////////////////////////////////
	static public boolean modified = true;

	static public MethodRTMCell getMethodRTMCell(model.Requirement requirement, model.Method method) {
		return methodtraces2HashMap.get(requirement.ID + "-" + method.ID);
	}
	static public LinkedHashMap<String, MethodRTMCell> methodtraces2HashMap = new LinkedHashMap<String, MethodRTMCell>();
	static public LinkedHashMap<String, LinkedHashMap<String, MethodRTMCell>> Totalmethodtraces2HashMap = new LinkedHashMap<String, LinkedHashMap<String, MethodRTMCell>>();
	public String ProgramName=""; 

	//////////////////////////////////////////////////////////////////////////
	// RTM CELL
	//////////////////////////////////////////////////////////////////////////

	private Method method = null;
	private Requirement requirement = null;
	public String VariabletraceValue = null;

	

	public String getVariabletraceValue() {
		return VariabletraceValue;
	}

	public void setVariabletraceValue(String variabletraceValue) {
		VariabletraceValue = variabletraceValue;
	}

	public MethodRTMCell(model.Requirement requirement, model.Method method) {
		this.requirement = requirement;
		this.method = method;
		methodtraces2HashMap.put(requirement.ID + "-" + method.ID, this);
	}

	public Method getMethod() {
		return method;
	}
	public String getMethodID() { return method.ID; }
	public void setMethod(Method method) {
		this.method = method;
	}
	public Requirement getRequirement() {
		return requirement;
	}
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	public ClazzRTMCell getClazzRTMCell(){
		return ClazzRTMCell.getClazzRTMCell(requirement, method.getClazz());
	}

	public MethodRTMCellList getCallers() {
		return method.getCallers().getMethodRTMCellList(requirement);	}
	
	
	public MethodRTMCellList getCallers(String ProgramName) {
		return method.getCallers().getMethodRTMCellList(requirement, ProgramName);	}
	
	
	public MethodRTMCellList getCallees() {
		String reqID= requirement.ID+"-"+method.getID(); 
//		System.out.println(reqID);
		return method.getCallees().getMethodRTMCellList(requirement);
	}
	
	public MethodRTMCellList getCallees(String ProgramName) {
		
		return method.getCallees().getMethodRTMCellList(requirement, ProgramName);
	}
	
	public ClazzRTMCellList getParameters() {
		return method.getParameters().getVariableRTMCellList(requirement); 
	}
	
	public ClazzRTMCellList getFieldMethods() {
		return method.getFieldMethods().getVariableRTMCellList(requirement); 
	}

	public ClazzRTMCellList getReturnTypeMethod() {
		return method.getFieldMethods().getVariableRTMCellList(requirement); 
	}
	
	public MethodRTMCellList getInterfaces() {
		return method.getInterfaces().getMethodRTMCellList(requirement);
	}
	public MethodRTMCellList getImplementations() {
		return method.getImplementations().getMethodRTMCellList(requirement);
	}
	public MethodRTMCellList getParents() {
		return method.getParents().getMethodRTMCellList(requirement);
	}
	public MethodRTMCellList getChildren() {
		return method.getChildren().getMethodRTMCellList(requirement);
	}
	public MethodRTMCellList getBasicCallers() {
		return method.getBasicCallers().getMethodRTMCellList(requirement);
	}
	public MethodRTMCellList getBasicCallees() {
		return method.getBasicCallees().getMethodRTMCellList(requirement);
	}
	public MethodRTMCellList getExtendedCallers() {
		return method.getExtendedCallers().getMethodRTMCellList(requirement);
	}
	public MethodRTMCellList getExtendedCallees() {
		return method.getExtendedCallees().getMethodRTMCellList(requirement);
	}
	public MethodRTMCellList getExecutedCallers() {
		return method.getExecutedCallers().getMethodRTMCellList(requirement);
	}
	public MethodRTMCellList getExecutedCallees() {
		return method.getExecutedCallees().getMethodRTMCellList(requirement);
	}

	@Override
	public String toString() {
		return "MethodRTMCell [requirement=" + requirement + ", method=" + getMethod().getName() + ", clazz=" + getClazzRTMCell().getClazz().getName() + ", value="+ getTraceValue() + ", developerGold=" + getGoldTraceValue() + "]";
	}

	public static RTMCell getMethodRTMCell(Requirement requirement, Method method, String programName) {
		return Totalmethodtraces2HashMap.get(programName).get(requirement.ID + "-" + method.ID);

	}
}