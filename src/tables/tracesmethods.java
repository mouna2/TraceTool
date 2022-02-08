package tables;
import java.util.List;


public class tracesmethods {
	public String methodid; 
	
	public String requirement; 
	public String requirementid; 
	public String fullmethod; 
	public String classname; 
	public String classid; 
	public String gold; 
	public String subject; 
	public List<CallerIDName> Callees; 
	public int CalleesSize; 
	public List<CallerIDName> CalleesExecuted; 
	public int CalleesExecutedSize; 
	public List<CallerIDName> Callers; 
	public int CallersSize; 
	public List<CallerIDName> CallersExecuted; 
	public int CallersExecutedSize; 
	
	

	
	
	
	

	

	public tracesmethods(String methodid, String requirement, String requirementid, String fullmethod, String classname,
			String classid, String gold, String subject, List<CallerIDName> callees, int calleesSize,
			List<CallerIDName> calleesExecuted, int calleesExecutedSize, List<CallerIDName> callers, int callersSize,
			List<CallerIDName> callersExecuted, int callersExecutedSize) {
		super();
		this.methodid = methodid;
		this.requirement = requirement;
		this.requirementid = requirementid;
		this.fullmethod = fullmethod;
		this.classname = classname;
		this.classid = classid;
		this.gold = gold;
		this.subject = subject;
		Callees = callees;
		CalleesSize = calleesSize;
		CalleesExecuted = calleesExecuted;
		CalleesExecutedSize = calleesExecutedSize;
		Callers = callers;
		CallersSize = callersSize;
		CallersExecuted = callersExecuted;
		CallersExecutedSize = callersExecutedSize;
	}

	public tracesmethods(String methodid, String requirement, String requirementid, String fullmethod, String classname,
			String classid, String gold, String subject, List<CallerIDName> callees, List<CallerIDName> calleesExecuted,
			List<CallerIDName> callers, List<CallerIDName> callersExecuted) {
		super();
		this.methodid = methodid;
		this.requirement = requirement;
		this.requirementid = requirementid;
		this.fullmethod = fullmethod;
		this.classname = classname;
		this.classid = classid;
		this.gold = gold;
		this.subject = subject;
		Callees = callees;
		CalleesExecuted = calleesExecuted;
		Callers = callers;
		CallersExecuted = callersExecuted;
	}

	public String getMethodid() {
		return methodid;
	}

	public void setMethodid(String methodid) {
		this.methodid = methodid;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getRequirementid() {
		return requirementid;
	}

	public void setRequirementid(String requirementid) {
		this.requirementid = requirementid;
	}

	public String getFullmethod() {
		return fullmethod;
	}

	public void setFullmethod(String fullmethod) {
		this.fullmethod = fullmethod;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<CallerIDName> getCallees() {
		return Callees;
	}

	public void setCallees(List<CallerIDName> callees) {
		Callees = callees;
	}

	public List<CallerIDName> getCalleesExecuted() {
		return CalleesExecuted;
	}

	public void setCalleesExecuted(List<CallerIDName> calleesExecuted) {
		CalleesExecuted = calleesExecuted;
	}

	public List<CallerIDName> getCallers() {
		return Callers;
	}

	public void setCallers(List<CallerIDName> callers) {
		Callers = callers;
	}

	public List<CallerIDName> getCallersExecuted() {
		return CallersExecuted;
	}

	public void setCallersExecuted(List<CallerIDName> callersExecuted) {
		CallersExecuted = callersExecuted;
	}

	public tracesmethods(String requirement, String requirementid, String fullmethod, String methodid, String classname,
			String classid, String gold, String subject) {
		
		this.requirement = requirement;
		this.requirementid = requirementid;
		this.fullmethod = fullmethod;
		this.methodid = methodid;
		this.classname = classname;
		this.classid = classid;
		this.gold = gold;
		this.subject = subject;
	}

	public boolean equals(tracesmethods t) {
		if(  requirementid.equals(t.requirementid)     && methodid.equals(t.methodid)  
				&& classid.equals(t.classid) ) {
			return true; 
		}
	return false; 
	}
	
	public boolean contains(List<tracesmethods> TraceList, tracesmethods t) {
		for(tracesmethods tr: TraceList) {
			if(t.equals(tr)) {
				return true; 
			}
		}
		
		return false;
		
	}
	
	public tracesmethods() {
		
	}

	public tracesmethods(String requirementid2, String methodid2, String classid2) {
		// TODO Auto-generated constructor stub
		this.requirementid=requirementid2; 
		this.methodid=methodid2; 
		this.classid=classid2; 
	}

	public void toString(List<tracesmethods> mylist) {
		
		
	
		for( tracesmethods var2: mylist) {
			System.out.println("METHOD ID "+var2.methodid); 
			System.out.println("requirement "+var2.requirement); 
			System.out.println("requirementID "+var2.requirementid); 
			System.out.println("SHORT METHOD "+var2.fullmethod); 
			System.out.println("CLASS NAME "+var2.classname); 
			System.out.println("CLASS ID "+var2.classid); 
			System.out.println("GOLD "+var2.gold); 
			System.out.println("SUBJECT "+var2.subject); 
			System.out.println("CALLEESSIZE "+var2.CalleesSize); 
			System.out.println("CALLEESEXECUTEDSIZE "+var2.CalleesExecutedSize); 
			System.out.println("CALLERSSIZE "+var2.CallersSize); 
			System.out.println("CALLERSEXECUTEDSIZE "+var2.CallersExecutedSize); 
			System.out.println("----------------------------------------------------------------------------------------"); 
			System.out.println("CALLEES"); 
			if(var2.Callees!=null) {
				for(CallerIDName var: var2.Callees) {
					System.out.println( " Callee ID=" + var.ID + ", Callee NAME=" + var.Name	 +    "]");
						
				}
			}
			System.out.println("----------------------------------------------------------------------------------------"); 
			System.out.println("CALLEES EXECUTED"); 
			if(var2.CalleesExecuted!=null) {
				for(CallerIDName var: var2.CalleesExecuted) {
					System.out.println( "CALLEES EXECUTED" + ", Callee Executed ID=" + var.ID + ", Callee Executed NAME=" + var.Name+    "]");
							 
				}
			}
			System.out.println("----------------------------------------------------------------------------------------"); 
			System.out.println("CALLERS"); 
			if(var2.Callers!=null) {
				for(CallerIDName var: var2.Callers) {
					System.out.println( "CALLERS" + ", Caller ID=" + var.ID + ", Caller NAME=" + var.Name +     "]");
							
				}
			}
			System.out.println("----------------------------------------------------------------------------------------"); 
			System.out.println("CALLERS EXECUTED"); 
			if(var2.CallersExecuted!=null) {
				for(CallerIDName var: var2.CallersExecuted) {
					System.out.println(  "CALLERS EXECUTED" + ", Caller Executed ID=" + var.ID + ", Caller Executed NAME=" + var.Name + "]");
							
				}
			}
			
			System.out.println("**********************************************************************************"); 
			System.out.println("**********************************************************************************"); 
			System.out.println("**********************************************************************************"); 
			
		}
		
		
	}
	
	
	
}