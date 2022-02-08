package tables;


import java.util.ArrayList;
import java.util.List;

public class CallerIDName {
	public String ID; 
	public String Name;
	List<CallerIDName > mylist= new ArrayList<CallerIDName>(); 
	
	public CallerIDName(String id, String name) {
		super();
		ID = id;
		Name = name;
	} 
	
	
	public CallerIDName() {
		super();
	}


	public void PrintList(List<CallerIDName > mylist) {
		for(CallerIDName var: mylist) {
//			System.out.println("CALLERIDNAME :     "+var.ID+ "CALLER NAME: "+ var.Name); 
			var.toString(); 
		}
		
	}


	@Override
	public String toString() {
		return "CallerIDName [ID=" + ID + ", Name=" + Name + "]";
	}
	
	
}

