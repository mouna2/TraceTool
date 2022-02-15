package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Clazz {

	static public LinkedHashMap<String, Clazz> clazzesHashMap= new LinkedHashMap<String, Clazz>();

	public String ID;
	public String name;
	private ClazzList children = new ClazzList();
	private ClazzList parents = new ClazzList();
	private ClazzList interfaces = new ClazzList();
	private ClazzList implementations = new ClazzList();
	private MethodList methods = new MethodList();
	private VariableList fieldClasses= new VariableList(); 
	//how many requirements does a class implement 
	private int RequirementNumber=0;
	
	
	public int getRequirementNumber() {
		return RequirementNumber;
	}

	public void setRequirementNumber(int requirementNumber) {
		RequirementNumber = requirementNumber;
	}

	private String Tcount="0"; 

	public String getTcount() {
		return Tcount;
	}
	public void setTcount(String tcount) {
		Tcount = tcount;
	}
	private String Ncount="0"; 

	public String getNcount() {
		return Ncount;
	}
	public void setNcount(String ncount) {
		Ncount = ncount;
	}
	

	public Clazz(String classid, String name) {
		super();
		this.ID = classid;
		this.name = name;
		clazzesHashMap.put(this.ID, this);
	}

	public String getID() {
		return ID;
	}
	public String getName() {
		return name;
	}

	public List<Variable> getFieldClasses() {
		return fieldClasses;
	}

	public void setFieldClasses(VariableList fieldClasses) {
		this.fieldClasses = fieldClasses;
	}

	public ClazzList getChildren() { return children; }
	public ClazzList getParents() { return parents; }
	public ClazzList getInterfaces() { return interfaces; }
	public ClazzList getImplementations() { return implementations; }

	public MethodList getMethods() { return methods; }

	@Override
	public String toString() {
		return "Clazz [" + ID + ", name=" + name + "]";
	}
}

