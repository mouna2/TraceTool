package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClazzList extends ArrayList<Clazz>{


	public boolean add(Clazz clazz) {
		if (contains(clazz)) return false;
		return super.add(clazz);
	}

	public boolean addAll(ClazzList clazzList) {
		for (Clazz clazz : clazzList)
			add(clazz);
		return true;
	}

	public String logIDs() {
		return stream().map(Clazz::getID).collect(Collectors.joining(", "));
	}
	
	public String logGodValues() {
		return stream().map(Clazz::getTcount).collect(Collectors.joining(", "));
	}
}
