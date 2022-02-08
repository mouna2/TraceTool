package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import model.RTMCell.TraceValue;

public class Variable {
	public String id; 
	
	public Clazz ownerclazz; 
	public String variableName; 
	public Clazz dataType; 
	public String type; 
	public String typeID; 
	public Method method;
	public Variable fieldClass; 
	public static  LinkedHashMap<String, Variable> variablesHashMap = new LinkedHashMap<>(); 
	public static  LinkedHashMap<String, LinkedHashMap<String, Variable>> totalVariablesHashMap = new LinkedHashMap<>(); 

	 TraceValue traceValue; 

	public TraceValue getTraceValue() {
		return traceValue;
	}
	public void setTraceValue(TraceValue traceValue) {
		this.traceValue = traceValue;
	}
	public static  LinkedHashMap<String, VariableList> variablesReadHashMap = new LinkedHashMap<>(); 
	public static  LinkedHashMap<String, VariableList> variablesWrittenHashMap = new LinkedHashMap<>(); 

	public   List<Method> methodList = new ArrayList<>(); 

	

	public  List<Method> getMethodList() {
		return methodList;
	}
	public  void setMethodList(List<Method> MethodList) {
		methodList = MethodList;
	}
	public Variable(Clazz ownerclazz, String variableName, String type, String typeID, Method method) {
		super();
		this.ownerclazz = ownerclazz;
		this.variableName = variableName;
		this.type = type;
		this.typeID = typeID;
		this.method = method;
	}
	public Variable(Clazz ownerclazz, String variableName, Clazz dataType, Method method) {
		super();
		this.ownerclazz = ownerclazz;
		this.variableName = variableName;
		this.dataType = dataType;
		this.method = method;
	}
	public Variable() {
		// TODO Auto-generated constructor stub
	}
	public Variable(Clazz ownerClass, String fieldname, Clazz fieldTypeDataType) {super();
		this.ownerclazz = ownerClass;
		this.variableName = fieldname;
		this.dataType = fieldTypeDataType;}
	public Variable(String variableName, Clazz variableclazz) {
		super();
		this.ownerclazz = variableclazz;
		this.variableName = variableName;
		
	} 
	  public ClazzRTMCellList getVariableRTMCell(Requirement requirement) {
	    	ClazzRTMCellList cellList = new ClazzRTMCellList();
				cellList.add (ClazzRTMCell.getClazzRTMCell(requirement, this.dataType)); 
			
			return cellList;
		}
	  public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}

	
		public Clazz getDataType() {
			return dataType;
		}
		public void setDataType(Clazz dataType) {
			this.dataType = dataType;
		}
	  
	  
}
