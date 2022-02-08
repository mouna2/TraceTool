package Chess;

public class SubjectTSubjectNObject {
	public String MethodName; 
	public String RequirementID; 
	public String SubjectT; 
	public String SubjectN;
	public String goldfinal;
	public String SubjectGold;

	public String getMethodName() {
		return MethodName;
	}
	public String getGoldfinal() {
		return goldfinal;
	}
	public void setGoldfinal(String goldfinal) {
		this.goldfinal = goldfinal;
	}
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	public String getRequirementID() {
		return RequirementID;
	}
	public void setRequirementID(String RequirementID) {
		this.RequirementID = RequirementID;
	}
	public String getSubjectT() {
		return SubjectT;
	}
	public void setSubjectT(String subjectT) {
		SubjectT = subjectT;
	}
	public String getSubjectN() {
		return SubjectN;
	}
	public void setSubjectN(String subjectN) {
		SubjectN = subjectN;
	}
	public SubjectTSubjectNObject(String methodName, String requirementID, String subjectT, String subjectN) {
		super();
		MethodName = methodName;
		RequirementID = requirementID;
		SubjectT = subjectT;
		SubjectN = subjectN;
	} 
	
	
	public SubjectTSubjectNObject() {
		
	}
	@Override
	public String toString() {
		return "SubjectTSubjectNObject [MethodName=" + MethodName + ", RequirementID=" + RequirementID + ", SubjectT="
				+ SubjectT + ", SubjectN=" + SubjectN + "]";
	} 
	
}
