package serverandclient;

import java.util.*;
import java.io.*;

/**
 * this is an experiment with HashMap, HashCode ... Note: if you hashcode an
 * object (string). it will always return the same hashcode if the objects are
 * equal by the equals definition in the class (string class in this case)
 * 
 * @author kenneth.ng
 * 
 */

public class Hashing {

	public static void main(String[] args) {

		// create map to store
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		// create list one and store values
		List<String> valSetOne = new ArrayList<String>();
		valSetOne.add("Apple");
		valSetOne.add("Aeroplane");

		// create list two and store values
		List<String> valSetTwo = new ArrayList<String>();
		valSetTwo.add("Bat");
		valSetTwo.add("Banana");

		// create list three and store values
		List<String> valSetThree = new ArrayList<String>();
		valSetThree.add("Cat");
		valSetThree.add("Car");

		List<String> valSetFour = new ArrayList<String>();
		valSetFour.add("Dear");
		valSetFour.add("Deer");

		// put values into map
		checkPut(map, "A", valSetOne);
		checkPut(map, "B", valSetTwo);
//		checkPut(map, "C", valSetThree);
		checkPut(map, "C", valSetOne);
//		checkPut(map, "D", valSetFour);
//		checkPut(map, "A", valSetOne);
//		checkPut(map, "A", valSetOne);

		String key = "";

		// to prevent overwrite of the entry

		/*
		 * System.out.println(map.entrySet());
		 * 
		 * System.out.println(map.hashCode());
		 * System.out.println(map.getClass()); System.out.println(map.keySet());
		 * System.out.println(map.get(key));
		 */

		// get all the duplication for a certain key

		checkGet(map, "A");
//		checkGet(map, "B");
		checkGet(map, "C");
//		checkGet(map, "D");

		// iterate and display values
		/*
		 * System.out.println("Fetching Keys and corresponding [Multiple] Values n"
		 * ); for (Map.Entry<String, List<String>> entry : map.entrySet()) { key
		 * = entry.getKey(); List<String> values = entry.getValue();
		 * System.out.println("Key = " + key); System.out.println("Values = " +
		 * values + "n"); }
		 */
	}

	public static void checkPut(Map<String, List<String>> map, String key,
			List<String> value) {
		Integer count = 1;
		int flag = 0; // > 0 to put the key+"count" into the hashmap or not
		String tempKey = key;
		while (map.containsKey(tempKey)) {
			tempKey = key + count.toString();
			count++;
			flag++;
			// System.out.println(count);
		}
		map.put(tempKey, value);
		if (flag == 0) {
			System.out.println(map.keySet());
		} else {
			tempKey = key + "count";
			List<String> tempValue = new ArrayList<String>();
			tempValue.add(count.toString());
			map.put(tempKey, tempValue);
			System.out.println(map.keySet());
		}
	}

	// only prints out the values corresponding to the key

	public static void checkGet(Map<String, List<String>> map, String key) {
		String tempKey = key + "count";
		System.out.println();
		Integer count = 0;
		if (map.containsKey(tempKey)) {
			count = Integer.parseInt(map.get(tempKey).get(0));
			System.out.println(count);
			for (int i = 0; i < count; i++) {
				if (i == 0)
					System.out.println(map.get(key));
				else
					System.out.println(map.get(key + i));
			}

		} else {
			System.out.println(map.get(key));
		}
	}
}
