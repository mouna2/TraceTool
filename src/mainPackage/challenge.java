package mainPackage;

import java.util.Arrays;

public class challenge {
	public static void main (String [] args) {
		Integer[] intArray = new Integer[]{24,  1,2,3,4,5,6,7,8,9,10 }; 
		 highAndLow(intArray);
	}

	public static void highAndLow(Integer [] ints ) {	
	
		Arrays.sort(ints);
		System.out.println(ints[ints.length-1]);

	}
}
