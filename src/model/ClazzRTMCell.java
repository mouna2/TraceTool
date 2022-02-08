package model;

import java.util.HashMap;

public class ClazzRTMCell extends RTMCell {

	//////////////////////////////////////////////////////////////////////////
	// RTM
	//////////////////////////////////////////////////////////////////////////
	static public boolean modified = true;
	

	static public ClazzRTMCell getClazzRTMCell(model.Requirement requirement, model.Clazz clazz) {
		return clazzTraces2HashMap.get(requirement.ID + "-" + clazz.ID);
	}
	static public HashMap<String, ClazzRTMCell> clazzTraces2HashMap = new HashMap<String, ClazzRTMCell>();
	static public HashMap<String, HashMap<String, TraceValue>> clazzTracesByProgramNameHashMap = new HashMap<String,HashMap<String, TraceValue >>(); 


	//////////////////////////////////////////////////////////////////////////
	// RTM
	//////////////////////////////////////////////////////////////////////////
	public Requirement requirement;
	public Clazz clazz;


	public ClazzRTMCell(Requirement requirement, Clazz clazz) {
		super();
		this.requirement = requirement;
		this.clazz = clazz;
		clazzTraces2HashMap.put(requirement.ID + "-" + clazz.ID, this);
	}

	public Requirement getRequirement() {
		return requirement;
	}
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
	public Clazz getClazz() {
		return clazz;
	}
	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}


	@Override
	public String toString() {
		return "ClazzRTMCell [requirement=" + requirement + ", clazz=" + clazz + ", value="+ getTraceValue() + ", developerfinal=" + getGoldTraceValue() + "]";
	}

}
