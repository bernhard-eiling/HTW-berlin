package eiling.info2.exercise11;

import java.util.ArrayList;

public class HashTable {
	
	ArrayList<String> buckets = new ArrayList<String>();

	HashTable() {
		
	}
	
	String normalize(String in) {
		ArrayList<Character> sorted = new ArrayList<Character>();
		ArrayList<Character> unsorted = new ArrayList<Character>();
		
		char[] unsortedTemp = in.toCharArray();
		for(int i = 0; i < unsortedTemp.length; i++) {
			unsorted.add(unsortedTemp[i]);
		}
		//System.out.println(unsorted.toString());
		
		int compare = Integer.MAX_VALUE;
		int removeIndex = -1;
		while(!unsorted.isEmpty()) {
			for(int i = 0; i < unsorted.size(); i++) {
				if(unsorted.get(i) < compare) {
					compare = unsorted.get(i);
					removeIndex = i;
				}
			}
			unsorted.remove(removeIndex);
			sorted.add((char)compare);
			System.out.println("TEST");
		}
		
		return sorted.toString();
	}
	
	void putElement(String key, String value) {
		
	}
}
