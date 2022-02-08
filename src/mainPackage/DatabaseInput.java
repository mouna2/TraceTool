package mainPackage;

import model.*;
import model.RTMCell.TraceValue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DatabaseInput {

	public static void read(String ProgramName) throws Exception {
		Clazz.clazzesHashMap.clear();
		ClazzRTMCell.clazzTraces2HashMap.clear();
		Method.methodsHashMap.clear();
		MethodRTMCell.methodtraces2HashMap.clear();
		Requirement.requirementsHashMap.clear();
		Variable.variablesHashMap.clear();
		ClassField.classFieldHashMap.clear(); 
		MethodField.methodFieldHashMap.clear();
		
		
		
		createClassHashMap(ProgramName);
		createSuperclassesChildrenHashMap(ProgramName);
		createInterfacesImplementations(ProgramName);
		createMethodHashMap(ProgramName);
		createMethodCallsHashMapCallersCallees(ProgramName);
		createMethodCallsExecutedHashMap(ProgramName);
		createRequirementsHashMap(ProgramName);
		createClassTraces(ProgramName);
		createMethodTraces(ProgramName);
		createParametersHashMap(ProgramName);
		createFieldClassesHashMap(ProgramName);
		if(!ProgramName.equals("vod")) {
			createFieldMethodsHashMap(ProgramName);

		}

		createAssignments(ProgramName); 
//		addVSMTraces(ProgramName); 
//		addLSITraces(ProgramName);
		if(ProgramName.equals("Gantt")) {
			System.out.println("yes");
		}
		MethodRTMCell.Totalmethodtraces2HashMap.put(ProgramName, (LinkedHashMap<String, MethodRTMCell>) MethodRTMCell.methodtraces2HashMap.clone()); 
		

		System.out.println();
	}

	private static void addVSMTraces(String programName) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
		JSONParser jsonParser = new JSONParser();
		File file = new File("log/"+programName+"/VSM/1.json");
		try (FileReader reader = new FileReader(file.getAbsolutePath())) {
			//Read JSON file
			JSONObject obj = (JSONObject)jsonParser.parse(reader);
            for(Object key: obj.keySet()) {
            	String mykey=key.toString();
            	String[] subkeys = mykey.split("-"); 
            	String requirementid=subkeys[0]; 
            	String methodid=subkeys[1]; 
            	
            	MethodRTMCell methodTrace = model.MethodRTMCell.methodtraces2HashMap.get(requirementid + "-" + methodid); 
    			if(obj.get(key).toString().equals("T"))
    				methodTrace.setVSMTraceValue(RTMCell.TraceValue.Trace);
    			else if(obj.get(key).toString().equals("N"))
    				methodTrace.setVSMTraceValue(RTMCell.TraceValue.NoTrace);
    			
    			
            }
            
            
		}
		
	}
	
	private static void addLSITraces(String programName) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
		JSONParser jsonParser = new JSONParser();
		File file = new File("log/"+programName+"/LSI/1.json");
		try (FileReader reader = new FileReader(file.getAbsolutePath())) {
			//Read JSON file
			JSONObject obj = (JSONObject)jsonParser.parse(reader);
            for(Object key: obj.keySet()) {
            	String mykey=key.toString();
            	String[] subkeys = mykey.split("-"); 
            	String requirementid=subkeys[0]; 
            	String methodid=subkeys[1]; 
            	
            	MethodRTMCell methodTrace = model.MethodRTMCell.methodtraces2HashMap.get(requirementid + "-" + methodid); 
    			if(obj.get(key).toString().equals("T"))
    				methodTrace.setLSITraceValue(RTMCell.TraceValue.Trace);
    			else if(obj.get(key).toString().equals("N"))
    				methodTrace.setLSITraceValue(RTMCell.TraceValue.NoTrace);
            }
		}
		
	}

	private static void createAssignments(String programName) {
		// TODO Auto-generated method stub
		JSONArray assignmentList = parse("database/"+programName+"/assignments.json");
//		System.out.println(programName);
		if(assignmentList!=null)
		for (Object o : assignmentList)
		{
			JSONObject mymethod = (JSONObject) o;
			String methodid = mymethod.get("methodid").toString();
			String read = mymethod.get("read").toString();
			String variableName = mymethod.get("variable").toString();
			String id = mymethod.get("id").toString();

//			System.out.println(programName+" "+id);
			String variableDataTypeid = mymethod.get("variableDataTypeid").toString();
			String classid = mymethod.get("classid").toString();

		
			
			Method method = Method.methodsHashMap.get(methodid);
			Clazz variableclazz= Clazz.clazzesHashMap.get(variableDataTypeid); 

			Variable variable = new Variable(method.getClazz(), variableName, variableclazz, method); 
//			Variable variableMap = new Variable(variableName, method.getClazz()); 
//			System.out.println("==========================   "+id);
			if(read.equals("1"))
				method.getVariableReads().add(variable);
			else
				method.getVariableWrites().add(variable);

			String mykey = variableName+"-"+method.getClazz().ID; 
//			System.out.println();
			if(read.equals("1")) {
				if(model.Variable.variablesReadHashMap.get(mykey)!=null) {
					model.Variable.variablesReadHashMap.get(mykey).add(variable); 
				}
				else{
					VariableList variableList = new VariableList(); 
					variableList.add(variable); 
					model.Variable.variablesReadHashMap.put(mykey, variableList); 
				}
			}
			else {
				if(model.Variable.variablesWrittenHashMap.get(mykey)!=null) {
					model.Variable.variablesWrittenHashMap.get(mykey).add(variable); 
				}
			else{
				VariableList variableList = new VariableList(); 
				variableList.add(variable); 
				model.Variable.variablesWrittenHashMap.put(mykey, variableList); 
			}}

		}
		
		
//		for(String mykey: model.Variable.variablesWrittenHashMap.keySet()) {
//			VariableList variables = model.Variable.variablesWrittenHashMap.get(mykey); 
//			for(Variable variable: variables) {
//				if(model.Variable.variablesReadHashMap.get(mykey)!=null) {
//					System.out.println(variable.variableName);
//				}
//			}
//		}
	}

	private static void createFieldClassesHashMap(String programName) {
		JSONArray fieldMethodList = parse("database/"+programName+"/fieldclasses.json");
		LinkedHashMap<String, Variable> variableHashMapLocal = new  LinkedHashMap<>(); 
		LinkedHashMap<String, Variable> variableTracesHashMapLocal = new  LinkedHashMap<>(); 

		
		for (Object o : fieldMethodList)
		{
			JSONObject mymethod = (JSONObject) o;
			String id = mymethod.get("id").toString();

			String fieldname = mymethod.get("fieldname").toString();
			String fieldtypeclassid = mymethod.get("fieldtypeclassid").toString();
			String fieldtype = mymethod.get("fieldtype").toString();
			String ownerclassid = mymethod.get("ownerclassid").toString();
			String classname = mymethod.get("classname").toString();
		
			
			model.Clazz ownerClass= Clazz.clazzesHashMap.get(ownerclassid);
			model.Clazz fieldTypeDataType= Clazz.clazzesHashMap.get(fieldtypeclassid);
			
			Variable var = new Variable( ownerClass, fieldname, fieldTypeDataType); 
			var.setId(id);
			var.type=fieldtype; 
			var.typeID=fieldtypeclassid; 
			Clazz.clazzesHashMap.get(ownerclassid).getFieldClasses().add(var); 
			
			Variable.variablesHashMap.put(id, var); 
			ClassField.classFieldHashMap.put(id, var); 
			variableHashMapLocal.put(id, var); 
			
			for(Requirement requirement: Requirement.requirementsHashMap.values()) {
				variableTracesHashMapLocal.put(requirement.ID+"-"+var.getId(), var); 
			}
			
		}
		VariableRTMCell.totalVariableTracesHashMap.put(programName,variableTracesHashMapLocal); 
		Variable.totalVariablesHashMap.put(programName, variableHashMapLocal); 
	}

	private static void createFieldMethodsHashMap(String programName) {
		JSONArray fieldMethodList = parse("database/"+programName+"/sootfieldmethods.json");
		int i=1; 
		for (Object o : fieldMethodList)
		{
			JSONObject mymethod = (JSONObject) o;
			String fieldName = mymethod.get("fieldname").toString();
			String classFieldid = mymethod.get("fieldclassid").toString();
	
			String ownerclassname = mymethod.get("ownerclassname").toString();
			String ownerclassid = mymethod.get("ownerclassid").toString();
			String ownermethodname = mymethod.get("ownermethodname").toString();
			String ownermethodid = mymethod.get("ownermethodid").toString();
			
			Variable myclassField = ClassField.classFieldHashMap.get(classFieldid); 
			
			model.Clazz ownerClass= Clazz.clazzesHashMap.get(ownerclassid);
			model.Clazz fieldTypeDataType= Variable.variablesHashMap.get(classFieldid).getDataType();
			model.Method method= Method.methodsHashMap.get(ownermethodid); 
			
			Variable var = new Variable(ownerClass, fieldName, fieldTypeDataType, method); 
			var.setId(classFieldid); 
			var.type=myclassField.type; 
			var.typeID=myclassField.typeID; 
			Method.methodsHashMap.get(method.ID).getFieldMethods().add(var); 
			Method.methodsHashMap.get(method.ID).getMethodVars().add(var); 

			var.getMethodList().add(method); 
			Variable.variablesHashMap.get(classFieldid).getMethodList().add(method); 

			System.out.println();
//			System.out.println();
			i++; 

			
		}
	}

	private static void createParametersHashMap(String programName) {
		JSONArray parameterList = parse("database/"+programName+"/parameters.json");
		for (Object o : parameterList)
		{
			JSONObject mymethod = (JSONObject) o;
			String parametername = mymethod.get("parametername").toString();
			String parametertype = mymethod.get("parametertype").toString();
			String parameterclass = mymethod.get("parameterclass").toString();
			String classid = mymethod.get("classid").toString();
			String classname = mymethod.get("classname").toString();
			String methodid = mymethod.get("methodid").toString();
			String methodname = mymethod.get("methodname").toString();
			String isreturn = mymethod.get("isreturn").toString();
			
			model.Clazz ownerClass= Clazz.clazzesHashMap.get(classid);
			model.Clazz parameterDataType= Clazz.clazzesHashMap.get(parameterclass);
			model.Method method= Method.methodsHashMap.get(methodid); 
			if(isreturn.equals("0")) {
				Variable var = new Variable(ownerClass, parametername, parameterDataType, method); 
				Method.methodsHashMap.get(methodid).getParameters().add(var); 
			}else {
				Variable var = new Variable(ownerClass, "", parameterDataType, method); 
				Method.methodsHashMap.get(methodid).setReturnType(var);

			}
			

//			System.out.println();

			
		}
	}

	private static JSONArray parse(String path) {
		JSONParser jsonParser = new JSONParser();
		File file = new File(path);
		try (FileReader reader = new FileReader(file.getAbsolutePath())) {
			return (JSONArray) jsonParser.parse(reader);
		}
		catch (Exception ex) { 
//			System.out.println(ex);
			return null;
			}
	}

	public static void createSuperclassesChildrenHashMap(String ProgramName) throws Exception {
		JSONArray superclassList = parse("database/"+ProgramName+"/superclasses.json");
		for (Object o : superclassList)
		{
			JSONObject mymethod = (JSONObject) o;
			String parentid = mymethod.get("superclassid").toString();
			String childid = mymethod.get("ownerclassid").toString();

			model.Clazz parent= Clazz.clazzesHashMap.get(parentid);
			model.Clazz child = Clazz.clazzesHashMap.get(childid);

			parent.getChildren().add(child);
			child.getParents().add(parent);
		}
	}


	public static void createInterfacesImplementations(String ProgramName) throws Exception{
		JSONArray interfaceList = parse("database/"+ProgramName+"/interfaces.json");
		for (Object o : interfaceList)
		{
			JSONObject myinterface = (JSONObject) o;
			String myinterfaceid = myinterface.get("interfaceclassid").toString();
			String myownerclassid = myinterface.get("ownerclassid").toString();


			model.Clazz interf= Clazz.clazzesHashMap.get(myinterfaceid);
			model.Clazz implementation = Clazz.clazzesHashMap.get(myownerclassid);

			interf.getImplementations().add(implementation);
			implementation.getInterfaces().add(interf);
		}
	}

	public static void createMethodCallsHashMapCallersCallees(String ProgramName) throws Exception {
		JSONArray methodCallsList = parse("database/"+ProgramName+"/methodcalls.json");
		for (Object o : methodCallsList)
		{
			JSONObject mymethod = (JSONObject) o;
			String callermethodid = mymethod.get("callermethodid").toString();
			String calleemethodid = mymethod.get("calleemethodid").toString();

			model.Method callerMethod = Method.methodsHashMap.get(callermethodid);
			model.Method calleeMethod = Method.methodsHashMap.get(calleemethodid);

			callerMethod.getBasicCallees().add(calleeMethod);
			calleeMethod.getBasicCallers().add(callerMethod);
		}
	}


	public static void createMethodCallsExecutedHashMap(String ProgramName) throws Exception {
		JSONArray methodCallsList = parse("database/"+ProgramName+"/methodcallsexecuted.json");
		for (Object o : methodCallsList)
		{
			JSONObject mymethod = (JSONObject) o;
			String callermethodid = mymethod.get("callermethodid").toString();
			String calleemethodid = mymethod.get("calleemethodid").toString();

			model.Method callerMethod = Method.methodsHashMap.get(callermethodid);
			model.Method calleeMethod = Method.methodsHashMap.get(calleemethodid);

			if (callerMethod != null && calleeMethod != null) {
				callerMethod.getExecutedCallees().add(calleeMethod);
				calleeMethod.getExecutedCallers().add(callerMethod);
			}
		}
	}

	public static void createMethodHashMap(String ProgramName) throws Exception {
		JSONArray methodList = parse("database/"+ProgramName+"/methods.json");
		LinkedHashMap<String, Method> methodHashMapLocal = new LinkedHashMap<String, Method>();
		for (Object o : methodList)
		{
			JSONObject mymethod = (JSONObject) o;
			String methodid = mymethod.get("id").toString();
			String methodName = mymethod.get("methodname").toString();
			String classid = mymethod.get("classid").toString();
			Clazz clazz = Clazz.clazzesHashMap.get(classid);

			Method method = new Method(methodid, methodName, clazz);
			clazz.getMethods().add(method);
			Method.methodsHashMap.put(methodid, method);
			methodHashMapLocal.put(methodid, method); 

		}
		Method.totalMethodsHashMap.put(ProgramName, methodHashMapLocal); 

		for (Method method : Method.methodsHashMap.values()) {
			Clazz clazz = method.getClazz();

			if (clazz.getInterfaces().size() > 0) {
				for (Clazz myinterfaceclass : clazz.getInterfaces()) {        //TODO bug: what if inheritance over multiple levels
					for (Method interfaceMethod : myinterfaceclass.getMethods()) {
						if (interfaceMethod.name.equals(method.name)) {
							method.getInterfaces().add(interfaceMethod);
							interfaceMethod.getImplementations().add(method);
						}
					}
				}
			}
			if (clazz.getChildren().size() > 0) {
				for (Clazz child : clazz.getChildren()) {
					for (Method ChildMethod : child.getMethods()) {
						if (ChildMethod.name.equals(method.name)) {
							method.getChildren().add(ChildMethod);
							ChildMethod.getParents().add(method);
						}
					}
				}
			}
		}
		System.out.println();
	}

	public static void createClassHashMap(String ProgramName) throws Exception {
		JSONArray classList = parse("database/"+ProgramName+"/classes.json");
		
		for (Object o : classList)
		{
			JSONObject myclass = (JSONObject) o;
			String classid = myclass.get("id").toString();
			String classname = myclass.get("classname").toString();

			Clazz clazz = new Clazz(classid, classname);
			Clazz.clazzesHashMap.put(classid, clazz);
		}
	}

	public static void createRequirementsHashMap(String ProgramName) throws Exception {
		JSONArray requirementsList = parse("database/"+ProgramName+"/requirements.json");
		LinkedHashMap<String, Requirement> requirementsHashMap= new LinkedHashMap<String, Requirement>();
		for (Object o : requirementsList)
		{
			JSONObject req = (JSONObject) o;
			String requirementid = req.get("id").toString();
			String requirementname = req.get("requirementname").toString();

			Requirement requirement= new Requirement(requirementid, requirementname);
			Requirement.requirementsHashMap.put(requirementid, requirement);
			requirementsHashMap.put(requirementid, requirement);
		}
		Requirement.totalRequirementsHashMap.put(ProgramName, requirementsHashMap); 
		System.out.println("over");
	}

	public static void createClassTraces(String ProgramName) throws Exception {
		JSONArray tracesClassesList = parse("database/"+ProgramName+"/tracesclasses.json");
		for (Object o : tracesClassesList)
		{
			JSONObject traceClass = (JSONObject) o;
			String requirementid = traceClass.get("requirementid").toString();
			String classid = traceClass.get("classid").toString();
			Requirement requirement = Requirement.requirementsHashMap.get(requirementid);
			Clazz clazz = Clazz.clazzesHashMap.get(classid);

			ClazzRTMCell myclasstrace= new ClazzRTMCell(requirement, clazz);
			String value = traceClass.get("goldfinal").toString();
			String SubjectGold = traceClass.get("SubjectGold").toString();
			if (value.equals("T")) {
				myclasstrace.setGoldTraceValue(RTMCell.TraceValue.Trace);
				// Added these 3 lines for how many requirements does a class implement?
				int Tcount=Integer.parseInt(clazz.getTcount()); 
				Tcount=Tcount+1; 
				clazz.setTcount(String.valueOf(Tcount));
//				Clazz.clazzesHashMap.put(classid, clazz); 
			}
			
			
			if (value.equals("N")) myclasstrace.setGoldTraceValue(RTMCell.TraceValue.NoTrace);
			if (value.equals("E")) myclasstrace.setGoldTraceValue(RTMCell.TraceValue.UndefinedTrace);
			
			if (SubjectGold.equals("T")) myclasstrace.setSubjectTraceValue(RTMCell.TraceValue.Trace);
			if (SubjectGold.equals("N")) myclasstrace.setSubjectTraceValue(RTMCell.TraceValue.NoTrace);
			if (SubjectGold.equals("E")) myclasstrace.setSubjectTraceValue(RTMCell.TraceValue.UndefinedTrace);
			
			
			if(ClazzRTMCell.clazzTracesByProgramNameHashMap.get(ProgramName) ==null) {
				ClazzRTMCell.clazzTracesByProgramNameHashMap.put(ProgramName, new HashMap<String, TraceValue>()); 
			}else {
				HashMap<String, TraceValue> myHashmap = ClazzRTMCell.clazzTracesByProgramNameHashMap.get(ProgramName); 
				myHashmap.put(requirementid+"-"+clazz.ID, myclasstrace.getGoldTraceValue()); 
			}
			
			ClazzRTMCell.clazzTraces2HashMap.put(requirement.ID+"-"+clazz.ID, myclasstrace); 

		 }
	}

	public static void createMethodTraces(String ProgramName) throws Exception {
		JSONArray tracesMethodsList = parse("database/"+ProgramName+"/traces.json");		//TODO rename to tracesmethods
		int countGanttT=0; 	int countGanttN=0; 	int countGanttU=0;int countJHotDrawT=0; int countJHotDrawN=0; int countJHotDrawU=0; 
		for (Object o : tracesMethodsList)
		{
			JSONObject traceMethod = (JSONObject) o;
			String requirementid = traceMethod.get("requirementid").toString();
			String methodid = traceMethod.get("methodid").toString();

			Method method = Method.methodsHashMap.get(methodid);
			Requirement requirement=Requirement.requirementsHashMap.get(requirementid);

			MethodRTMCell methodTrace = new MethodRTMCell(requirement, method);
			
			String value = traceMethod.get("goldfinal").toString();
			
			if (value.equals("T")) methodTrace.setGoldTraceValue(RTMCell.TraceValue.Trace);
			if (value.equals("N")) methodTrace.setGoldTraceValue(RTMCell.TraceValue.NoTrace);
			if (value.equals("E")) methodTrace.setGoldTraceValue(RTMCell.TraceValue.UndefinedTrace);
			
			if (method.getName().equals("setPublicHolidays(java.net.URL,net.sourceforge.ganttproject.GanttProject)") && requirement.getName().equals("16: Add/Remove Holidays and Vacation Days"))
				traceMethod.toString();

			try {
				methodTrace.setSubjectTraceVotes(Integer.parseInt(traceMethod.get("SubjectT").toString()), Integer.parseInt(traceMethod.get("SubjectN").toString()));

				if (ProgramName.equals("gantt")) {
					if(methodTrace.getClazzRTMCell().getGoldTraceValue().equals(methodTrace.getClazzRTMCell().getSubjectTraceValue())) {		
					if((methodTrace.getSubjectT()>=2 && methodTrace.getSubjectN()==0) || (methodTrace.getSubjectT()>=3)) {
						methodTrace.setGoldTraceValue(RTMCell.TraceValue.Trace);
						countGanttT++; 
					}
					else if(methodTrace.getSubjectT()==0 && methodTrace.getSubjectN()>=2) {
						methodTrace.setGoldTraceValue(RTMCell.TraceValue.NoTrace);
						countGanttN++; 
					}
					else {
						methodTrace.setGoldTraceValue(RTMCell.TraceValue.UndefinedTrace);
						countGanttU++; 

					}
						
					}else {
						methodTrace.setGoldTraceValue(RTMCell.TraceValue.UndefinedTrace);
						countGanttU++; 

					}
					
					
					}
				else if (ProgramName.equals("jhotdraw")) {
					if(methodTrace.getClazzRTMCell().getGoldTraceValue().equals(methodTrace.getClazzRTMCell().getSubjectTraceValue())) {
					if((methodTrace.getSubjectT()>=1 && methodTrace.getSubjectN()==0) || (methodTrace.getSubjectT()>=2)) {
						methodTrace.setGoldTraceValue(RTMCell.TraceValue.Trace);
						countJHotDrawT++; 
					}
					else if(methodTrace.getSubjectT()==0 && methodTrace.getSubjectN()>=2) {
						methodTrace.setGoldTraceValue(RTMCell.TraceValue.NoTrace);
						countJHotDrawN++; 
					}
					else {
						methodTrace.setGoldTraceValue(RTMCell.TraceValue.UndefinedTrace);
						countJHotDrawU++; 
					}
						
				}else {
					methodTrace.setGoldTraceValue(RTMCell.TraceValue.UndefinedTrace);
					countJHotDrawU++; 

				}
				
				}
			} catch (Exception ex) {}
		}
//		System.out.println("COUNT GANTT T  "+countGanttT+" COUNT GANTT N "+countGanttN+" COUNT GANTT U  "+countGanttU);
//		System.out.println("COUNT JHOTDRAW T  "+countJHotDrawT+" COUNT JHOTDRAW N "+countJHotDrawN+" COUNT JHOTDRAW U  "+countJHotDrawU);

//		System.out.println("here");
		
	}
}