package BoxPlots;

import mainPackage.CSV;
import model.MethodRTMCellList;
import model.RTMCell;
import model.RTMCell.TraceValue;

public class counts {
	public int T=0; 
	public int N=0; 
	public int U=0; 
	public String amountT="-1"; 
	public String amountN="-1"; 
	public String amountU="-1"; 

	public int amountTperc=0; 
	public int amountNperc=0; 
	public int amountUperc=0; 
	

	
	
	public static counts countMethods(MethodRTMCellList methodsRTMs){
		counts counts= new counts(); 
		for(RTMCell methodRTM: methodsRTMs) {
			TraceValue val=null;
			
			if(CSV.Seeding)  val=methodRTM.getTraceValue(); 
			else val=methodRTM.getGoldTraceValue(); 
			
			if(val.equals(RTMCell.TraceValue.Trace)) {
				counts.T++; 
			}else if(val.equals(RTMCell.TraceValue.NoTrace)) {
				counts.N++; 
			}else if(val.equals(RTMCell.TraceValue.UndefinedTrace)) {
				counts.U++; 

			}
		}
		
		
		return counts;
		
	}
	
}
