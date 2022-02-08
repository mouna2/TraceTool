package model;

import java.util.stream.Collectors;

import model.RTMCell.TraceValue;

public class MethodRTMCellList extends RTMCellList {

	public MethodRTMCellList retainTraceValuesEqualTraces(boolean gold) {

        MethodRTMCellList methodRTMCellList = new MethodRTMCellList();
        for(RTMCell cell: this) {
        	TraceValue val=null; 
			if(gold==true) val=cell.getGoldTraceValue(); 
			else val=cell.getTraceValue(); 
				
            if(val.equals(RTMCell.TraceValue.Trace)) {
                methodRTMCellList.add(cell);
			}
		}
		return methodRTMCellList;
	}

    public MethodRTMCellList retainTraceValuesEqualNoTraces(boolean gold) {

        MethodRTMCellList methodRTMCellList = new MethodRTMCellList();
        for(RTMCell cell: this) {
        	TraceValue val=null; 
        	if(gold==true) val=cell.getGoldTraceValue(); 
			else val=cell.getTraceValue(); 
            if(val.equals(RTMCell.TraceValue.NoTrace)) {
                methodRTMCellList.add(cell);
            }
        }
        return methodRTMCellList;
    }

    public ClazzRTMCellList getClazzesRTMCellList() {
        ClazzRTMCellList list = new ClazzRTMCellList();
        for (RTMCell cell : this)
            list.add(((MethodRTMCell) cell).getClazzRTMCell());
        return list;
    }
    public ClazzList getClazzes() {
        ClazzList list = new ClazzList(); 
        for (RTMCell cell : this)
            list.add(((MethodRTMCell) cell).getClazzRTMCell().clazz);
        return list;
    }

    public MethodRTMCellList getCallers() {
        MethodRTMCellList list = new MethodRTMCellList();
        for (RTMCell cell : this)
        	if(cell!=null)
            list.addAll(((MethodRTMCell) cell).getCallers());
        return list;
    }
    
    public MethodRTMCellList getCallers(String ProgramName) {
        MethodRTMCellList list = new MethodRTMCellList();
        for (RTMCell cell : this)
            list.addAll(((MethodRTMCell) cell).getCallers(ProgramName));
        return list;
    }
    
    public MethodRTMCellList getCallees(String ProgramName) {
        MethodRTMCellList list = new MethodRTMCellList();
        for (RTMCell cell : this)
            list.addAll(((MethodRTMCell) cell).getCallees(ProgramName));
        return list;
    }

    public MethodRTMCellList getCallees() {
        MethodRTMCellList list = new MethodRTMCellList();
        for (RTMCell cell : this)
        	if(cell!=null)
            list.addAll(((MethodRTMCell) cell).getCallees());
        return list;
    }

    public MethodRTMCellList getBasicCallers() {
        MethodRTMCellList list = new MethodRTMCellList();
        for (RTMCell cell : this)
            list.addAll(((MethodRTMCell) cell).getBasicCallers());
        return list;
    }

    public MethodRTMCellList getExtendedCallers() {
        MethodRTMCellList list = new MethodRTMCellList();
        for (RTMCell cell : this)
            list.addAll(((MethodRTMCell) cell).getExtendedCallers());
        return list;
    }

    public MethodRTMCellList getBasicCallees() {
        MethodRTMCellList list = new MethodRTMCellList();
        for (RTMCell cell : this)
            list.addAll(((MethodRTMCell) cell).getBasicCallees());
        return list;
    }

    public MethodRTMCellList getExtendedCallees() {
        MethodRTMCellList list = new MethodRTMCellList();
        for (RTMCell cell : this)
            list.addAll(((MethodRTMCell) cell).getExtendedCallees());
        return list;
    }

    public String logMethodIDs() {
        return stream().map(cell -> (MethodRTMCell)cell).map(MethodRTMCell::getMethodID).collect(Collectors.joining(", "));
    }

	
}
