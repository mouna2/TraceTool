package model;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Requirement {

	static public LinkedHashMap<String, Requirement> requirementsHashMap= new LinkedHashMap<String, Requirement>();
	static public LinkedHashMap<String, LinkedHashMap<String, Requirement>> totalRequirementsHashMap= new LinkedHashMap<>();

	public String ID;
	public String name;

	public int seedTtoE = 0;
	public int seedNtoE = 0;
	public int seedTtoN = 0;
	public int seedNtoT = 0;

	public Requirement(String iD, String name) {
		super();
		this.ID = iD;
		this.name = name;
		requirementsHashMap.put(this.ID, this);
	}

	public String getID() { return ID; }
	public String getName() { return name; }

	public MethodRTMCellList getRTMMethodCellList() {
		MethodRTMCellList methodRTMCellList = new MethodRTMCellList();
		for(MethodRTMCell cell: MethodRTMCell.methodtraces2HashMap.values()) {
			if(cell.getRequirement().ID.equals(this.ID)) {
				methodRTMCellList.add(cell);
			}
		}
		return methodRTMCellList;
	}

	public void setSeedTtoE(int percentage) { seedTtoE = percentage; }
	public void setSeedNtoE(int percentage) { seedNtoE = percentage; }
	public void setSeedTtoN(int percentage) { seedTtoN = percentage; }
	public void setSeedNtoT(int percentage) { seedNtoT = percentage; }

	@Override
	public String toString() {
		return "Requirement [ID=" + ID + ", name=" + name + "]";
	}
}
