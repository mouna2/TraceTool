package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class Method {

	 public static LinkedHashMap<String, Method> methodsHashMap= new LinkedHashMap<String, Method>();
	 public static LinkedHashMap<String, LinkedHashMap<String, Method>> totalMethodsHashMap= new LinkedHashMap<>();

	public String ID;
	public String name;
	public String fullMethodName;
	
	private MethodRTMCellList methodRTMList = null;

	private Clazz clazz = null;
	private MethodList basicCallees = new MethodList();
	private MethodList basicCallers = new MethodList();
	private MethodList executedCallees = new MethodList();
	private MethodList executedCallers = new MethodList();
	private MethodList interfaces= new MethodList();
	private MethodList implementations= new MethodList();
	private MethodList parents= new MethodList();
	private MethodList children= new MethodList();
	private VariableList parameters= new VariableList(); 
	private Variable returnType= new Variable(); 
	private VariableList fieldMethods= new VariableList(); 
	private HashSet<Variable> methodVars= new HashSet<Variable>(); 
	
	
	public HashSet<Variable> getMethodVars() {
		return methodVars;
	}

	public void setMethodVars(HashSet<Variable> methodVars) {
		this.methodVars = methodVars;
	}
	
	

	private VariableList variableReads=new VariableList();
	private VariableList variableWrites=new VariableList(); 

	public VariableList getVariableReads() {
		return variableReads;
	}

	public void setVariableReads(VariableList variableReads) {
		this.variableReads = variableReads;
	}

	public VariableList getVariableWrites() {
		return variableWrites;
	}

	public void setVariableWrites(VariableList variableWrites) {
		this.variableWrites = variableWrites;
	}




	public VariableList getParameters() {
		return parameters;
	}

	public void setParameters(VariableList parameters) {
		this.parameters = parameters;
	}

	public Variable getReturnType() {
		return returnType;
	}

	public void setReturnType(Variable returnType) {
		this.returnType = returnType;
	}

	public VariableList getFieldMethods() {
		return fieldMethods;
	}
	
	public Variable getReturnTypeMethod() {
		return returnType;
	}

	public void setFieldMethods(VariableList fieldMethods) {
		this.fieldMethods = fieldMethods;
	}

	public Method(String methodid, String name, Clazz clazz) {
		super();
		this.ID = methodid;
		this.name = name;
		this.clazz = clazz;
		this.fullMethodName = clazz.getName()+"."+ name;

		methodsHashMap.put(this.ID, this);
	}

	public Clazz getClazz() {
		return clazz;
	}
	public String getID() { return ID; }
	public String getName() { return name; }

	public MethodList getBasicCallees() {
		return basicCallees;
	}
	public MethodList getBasicCallers() { return basicCallers; }

	public MethodList getExecutedCallers() {
		return executedCallers;
	}
	public MethodList getExecutedCallees() {
		return executedCallees;
	}

	public MethodList getParents() {
		return parents;
	}
	public MethodList getChildren() {
		return children;
	}
	public MethodList getInterfaces() {
		return interfaces;
	}
	public MethodList getImplementations() {
		return implementations;
	}


	@Override
	public String toString() {
		return "Method ["+  ID + ": name=" + name + ": clazz=" + clazz.name + "]";
	}

	public MethodList getCallees() {
		if(Definitions.callerType== Definitions.CallerType.extended) {
			return getExtendedCallees();
		}else if(Definitions.callerType== Definitions.CallerType.basic ) {
			return getBasicCallees();
		}else if(Definitions.callerType== Definitions.CallerType.executed) {
			return getExecutedCallees();
		}
		return null;

	}

	public MethodList getCallers() {
		MethodList callers= new MethodList();
		if(Definitions.callerType== Definitions.CallerType.extended) {
			callers =getExtendedCallers();
		}else if(Definitions.callerType== Definitions.CallerType.basic) {
			callers =getBasicCallers();
		}else if(Definitions.callerType== Definitions.CallerType.executed) {
			callers =this.executedCallers;
		}
		return callers;
	}

	public MethodList getExtendedCallers() {
		MethodList extendedcallers= new MethodList();
		extendedcallers.addAll(this.basicCallers);

		if(Definitions.InterfaceImplementationFlag) {

			//case 20
			for(Method CallerInterface: this.interfaces) {
				extendedcallers.addAll(CallerInterface.basicCallers);
			}
			//case 21
			for(Method CallerImplementation: this.implementations) {
				extendedcallers.addAll(CallerImplementation.basicCallers);	//TODO should this be extendedCallers?
			}
			//case 22
			for(Method Caller: this.basicCallers) {
				extendedcallers.addAll(Caller.interfaces);

			}
		}
		if(Definitions.InheritanceFlag) {
			//case 28
			for(Method mysuperclass: this.parents) {
				extendedcallers.addAll(mysuperclass.basicCallers);
			}
		}
		return extendedcallers;
	}

	public MethodList getExtendedCallees() {

		MethodList Extendedcallees= new MethodList();
		Extendedcallees.addAll(this.basicCallees);

		if(Definitions.InterfaceImplementationFlag) {
			for(Method callee: this.basicCallees) {

				//case 5
				Extendedcallees.addAll(callee.implementations);
				//case 6
				Extendedcallees.addAll(callee.interfaces);

			}
			//case 7
			for(Method implementation: this.implementations) {
				Extendedcallees.addAll(implementation.basicCallees);
			}
		}
		if(Definitions.InheritanceFlag) {
			//case 12
			for(Method callee: this.basicCallees) {
				Extendedcallees.addAll(callee.children);
			}
		}
		return Extendedcallees;
	}

	
	

	

}