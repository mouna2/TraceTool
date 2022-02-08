package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class VariableList extends ArrayList<Variable>{
	public VariableList()  {}

	public boolean add(Variable variable) {
		if (contains(variable)) return false;
		return super.add(variable);
	}

	public boolean addAll(VariableList variableList) {
		for (Variable variable : variableList)
			add(variable);
		return true;
	}


	

	public ClazzList getClazzes() {
		ClazzList ownerClasses = new ClazzList();
		for (Variable variable : this) {
			ownerClasses.add(variable.ownerclazz);
		}
		return ownerClasses;
	}

   

    public ClazzRTMCellList getVariableRTMCellList(Requirement requirement) {
    	ClazzRTMCellList cellList = new ClazzRTMCellList();
		for (Variable variable : this) {
			cellList.add (ClazzRTMCell.getClazzRTMCell(requirement, variable.dataType)); 
		}
		return cellList;
	}

	

	public MethodRTMCellList getMethods(Requirement requirement) {
		// TODO Auto-generated method stub
		MethodRTMCellList cellList = new MethodRTMCellList();
		for (Variable variable : this) {
			cellList.add (MethodRTMCell.getMethodRTMCell(requirement, variable.method)); 
		}
		return cellList;
	}

	

}
