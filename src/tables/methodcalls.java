package tables;
import java.util.List;

public class methodcalls {
	public String methodcalledid; 
	public String methodcalledname; 
	public String menthodcalledclass; 
	public String callingmethodid; 
	public String callingmethodname; 
	public String callingmethodclass;
	public String calleeclassid;
	
	public methodcalls(String methodcalledid, String methodcalledname, String menthodcalledclass, String callingmethodid, String calleeclassid, 
			String callingmethodname, String callingmethodclass) {

		this.methodcalledid = methodcalledid;
		this.methodcalledname = methodcalledname;
		this.menthodcalledclass = menthodcalledclass;
		this.callingmethodid = callingmethodid;
		this.callingmethodname = callingmethodname;
		this.callingmethodclass = callingmethodclass;
		this.calleeclassid=calleeclassid; 
	}
	
	public methodcalls(String methodcalledid, String methodcalledname, String menthodcalledclass, String callingmethodid, 
			String callingmethodname, String callingmethodclass) {

		this.methodcalledid = methodcalledid;
		this.methodcalledname = methodcalledname;
		this.menthodcalledclass = menthodcalledclass;
		this.callingmethodid = callingmethodid;
		this.callingmethodname = callingmethodname;
		this.callingmethodclass = callingmethodclass;
	}
	public boolean equals(methodcalls mc) {
//		System.out.println(mc.toString());
		if(methodcalledid.equals(mc.methodcalledid) && calleeclassid.equals(mc.calleeclassid) && methodcalledname.equals(mc.methodcalledname) && menthodcalledclass.equals(mc.menthodcalledclass) && callingmethodid.equals(mc.callingmethodid)  && callingmethodname.equals(mc.callingmethodname) && callingmethodclass.equals(mc.callingmethodclass) ) {
			return true; 
		}
	return false; 
	}
	
	public boolean contains(List<methodcalls> MethodCallsList, methodcalls f) {
		for(methodcalls mc: MethodCallsList) {
//			System.out.println("mc="+mc.toString());
//			System.out.println("f="+f.toString());

			if(mc.equals(f)) {
				return true; 
			}
		}
		
		return false;
		
	}

	@Override
	public String toString() {
		return "methodcalls [methodcalledid=" + methodcalledid + ", methodcalledname=" + methodcalledname
				+ ", menthodcalledclass=" + menthodcalledclass + ", callingmethodid=" + callingmethodid
				+ ", callingmethodname=" + callingmethodname + ", callingmethodclass=" + callingmethodclass
				+ ", calleeclassid=" + calleeclassid + "]";
	}
	
}
