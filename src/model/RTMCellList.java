package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RTMCellList extends ArrayList<RTMCell>{

	public boolean add(RTMCell cell) {
		if (contains(cell)) return false;
		return super.add(cell);
	}

	public boolean addAll(RTMCellList cellList) {
		for (RTMCell cell : cellList)
			add(cell);
		return true;
	}

	//*** ATLEAST
	public boolean atLeast1T() {
		if(size()==0) return false; 
		for (RTMCell cell : this) {
			if (cell.getTraceValue().equals(RTMCell.TraceValue.Trace)) return true;
		}
		return false;
	}

	public boolean atLeast1GoldT() {
		for (RTMCell cell : this) {
			if(cell!=null) {
				if (cell.getGoldTraceValue().equals(RTMCell.TraceValue.Trace)) return true;
			}
		}
		return false;
	}
	
	
	public boolean atLeast2GoldT() {
		int i=0; 
		for (RTMCell cell : this) {
			if(cell!=null) {
				if (cell.getGoldTraceValue().equals(RTMCell.TraceValue.Trace)) i++;
				if(i>2) return true; 
			}
		}
		return false;
	}

	
	
	public boolean atLeast2GoldN() {
		int i=0; 
		for (RTMCell cell : this) {
			if(cell!=null) {
				if (cell.getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace)) i++;
				if(i>2) return true; 
			}
		}
		return false;
	}
	public boolean atLeast1PredictedT() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
			if (cell.getPredictedTraceValue().equals(RTMCell.TraceValue.Trace)) 
				return true;
		}
		return false;
	}

	public boolean atLeast1N() {
		for (RTMCell cell : this) {
			if (cell.getTraceValue().equals(RTMCell.TraceValue.NoTrace)) return true;
		}
		return false;
	}

	public boolean atLeast1GoldN() {
		for (RTMCell cell : this) {
			if(cell!=null) {
			if (cell.getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace)) return true;
		}}
		return false;
	}

	public boolean atLeast1PredictedN() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
			if (cell.getPredictedTraceValue().equals(RTMCell.TraceValue.NoTrace))
				return true;
		}
		return false;
	}

	public boolean atLeast1U() {
		for (RTMCell cell : this) {
			if (cell.getTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) return true;
		}
		return false;
	}

	public boolean atLeast1GoldU() {
		for (RTMCell cell : this) {
			if(cell!=null) {
			if (cell.getGoldTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) return true;
		}}
		return false;
	}
	
	public boolean NoUs() {
		for (RTMCell cell : this) {
			if (cell.getGoldTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) return false;
		}
		return true;
	}

	public boolean atLeast1PredictedU() {
		for (RTMCell cell : this) {
			if (cell.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) return true;
		}
		return false;
	}

	//*** ALL
	public boolean allTs() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
			if(cell!=null)
			if (!cell.getTraceValue().equals(RTMCell.TraceValue.Trace)) return false;
		}
		return true;
	}
	public boolean allGoldT() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
			if(cell!=null)

			if (!cell.getGoldTraceValue().equals(RTMCell.TraceValue.Trace)) return false;
		}
		return true;
	}
	
	public boolean allGoldN() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
			if (!cell.getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace)) return false;
		}
		return true;
	}
	public boolean allPredictedTs() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
			if (!cell.getPredictedTraceValue().equals(RTMCell.TraceValue.Trace)) return false;
		}
		return true;
	}
	
	public boolean allPredictedUs() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
			if (!cell.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) return false;
		}
		return true;
	}

	public boolean allNs() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
			if(cell!=null)
			if (!cell.getTraceValue().equals(RTMCell.TraceValue.NoTrace)) return false;
		}
		return true;
	}
	public boolean allUs() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
			if (!cell.getTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) return false;
		}
		return true;
	}
	public boolean allPredictedNs() {
		if (size()==0) return false;
		for (RTMCell cell : this) {
//			System.out.print(cell.getPredictedTraceValue()+"  ");
			if (!cell.getPredictedTraceValue().equals(RTMCell.TraceValue.NoTrace)) return false;
		}
//		System.out.println();
		return true;
	}

	//*** NO
	public boolean noTs() {
		for (RTMCell cell : this) {
			if (cell.getTraceValue().equals(RTMCell.TraceValue.Trace)) return false;
		}
		return true;
	}

	public boolean noGoldTs() {
		for (RTMCell cell : this) {
			if (cell.getGoldTraceValue().equals(RTMCell.TraceValue.Trace)) return false;
		}
		return true;
	}

	public boolean noPredictedTs() {
		for (RTMCell cell : this) {
			if (cell.getPredictedTraceValue().equals(RTMCell.TraceValue.Trace)) return false;
		}
		return true;
	}

	public boolean noNs() {
		for (RTMCell cell : this) {
			if (cell.getTraceValue().equals(RTMCell.TraceValue.NoTrace)) return false;
		}
		return true;
	}

	public boolean noGoldNs() {
		for (RTMCell cell : this) {
			if (cell.getGoldTraceValue().equals(RTMCell.TraceValue.NoTrace)) return false;
		}
		return true;
	}

	public boolean noPredictedNs() {
		for (RTMCell cell : this) {
			if (cell.getPredictedTraceValue().equals(RTMCell.TraceValue.NoTrace)) return false;
		}
		return true;
	}

	public boolean noUs() {
		for (RTMCell cell : this) {
			if (cell.getTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) return false;
		}
		return true;
	}

	public boolean noGoldUs() {
		for (RTMCell cell : this) {
			if (cell.getGoldTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) return false;
		}
		return true;
	}

	public boolean noPredictedUs() {
		for (RTMCell cell : this) {
			if (cell.getPredictedTraceValue().equals(RTMCell.TraceValue.UndefinedTrace)) return false;
		}
		return true;
	}

	public String logTraceValues() {
		return stream().map(RTMCell::logTraceValueString).collect(Collectors.joining(", "));
	}
	public String logPredictedTraceValues() {
		return stream().map(RTMCell::logPredictedTraceValueString).collect(Collectors.joining(", "));
	}
	public String logGoldTraceValues() {
		return stream().map(RTMCell::logGoldTraceValueString).collect(Collectors.joining(", "));
	}
}
