package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

import mainPackage.CSV;

public class MethodList extends ArrayList<Method>{


	public MethodList()  {}

	public boolean add(Method method) {
		if (contains(method)) return false;
		return super.add(method);
	}

	public boolean addAll(MethodList methodList) {
		for (Method method : methodList)
			add(method);
		return true;
	}

	public MethodList getCallers() {
		MethodList callers = new MethodList();
		for (Method method : this) {
			callers.addAll(method.getCallers());
		}
		return callers;
	}

	public MethodList getCallees() {
		MethodList callees = new MethodList();
		for (Method method : this) {
			callees.addAll(method.getCallers());
		}
		return callees;
	}

	public ClazzList getClazzes() {
		ClazzList ownerClasses = new ClazzList();
		for (Method method : this) {
			ownerClasses.add(method.getClazz());
		}
		return ownerClasses;
	}

    public MethodList getBasicCallers() {
        MethodList callees = new MethodList();
        for (Method method : this) {
            callees.addAll(method.getBasicCallers());
        }
        return callees;
    }

    public MethodList getBasicCallees() {
        MethodList callees = new MethodList();
        for (Method method : this) {
            callees.addAll(method.getBasicCallees());
        }
        return callees;
    }

    public MethodList getExecutedCallers() {
        MethodList callees = new MethodList();
        for (Method method : this) {
            callees.addAll(method.getExecutedCallers());
        }
        return callees;
    }

    public MethodList getExecutedCallees() {
        MethodList callees = new MethodList();
        for (Method method : this) {
            callees.addAll(method.getExecutedCallees());
        }
        return callees;
    }

    public MethodList getExtendedCallers() {
        MethodList callees = new MethodList();
        for (Method method : this) {
            callees.addAll(method.getExtendedCallers());
        }
        return callees;
    }

    public MethodList getExtendedCallees() {
        MethodList callees = new MethodList();
        for (Method method : this) {
            callees.addAll(method.getExtendedCallees());
        }
        return callees;
    }


    public MethodRTMCellList getMethodRTMCellList(Requirement requirement) {
		MethodRTMCellList cellList = new MethodRTMCellList();
		for (Method method : this) {
			 cellList.add(MethodRTMCell.getMethodRTMCell(requirement, method));
		}
		return cellList;
	}
    
    public MethodRTMCellList getMethodRTMCellList(Requirement requirement, String ProgramName) {
		MethodRTMCellList cellList = new MethodRTMCellList();
		for (Method method : this) {
			 cellList.add(MethodRTMCell.getMethodRTMCell(requirement, method, ProgramName));
		}
		return cellList;
	}

	public String logIDs() {
		return stream().map(Method::getID).collect(Collectors.joining(", "));
	}

}