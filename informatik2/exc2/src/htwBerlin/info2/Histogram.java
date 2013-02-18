package htwBerlin.info2;

import java.io.*;
import java.util.HashMap;

public class Histogram {

	
	
	public static void main(String[] args) {
		
		HashMap<Integer, Integer> numChar = new HashMap<Integer, Integer>();
		
		// preparing the HashMap for storing characters in ASCII Integer form
		for (int i = 97; i < 123; i++) {
			numChar.put(i, 0);
		}
		
		try {
			// the BufferedReader prepares to read a file
		    BufferedReader in = new BufferedReader(new FileReader("testfile.txt"));
		    Integer input = null;
		    
		    // the BufferedReader reads single characters and stores them as integers
		    // it ignores any characters which are not letters
		    // any letter which is upper case gets transformed to a lower case letter by adding 32 to the letters integer value
		    // for any letter the HashMap is counting one up in the designated position of the map
		    while((input = in.read()) != -1) {
		    	if(input > 64 && input < 91) {
		    		input += 32;
		    		numChar.put(input, numChar.get(input) + 1);
		    	} else if (input > 96 && input < 123) {
		    		numChar.put(input, numChar.get(input) + 1);
		    	}
		    }
		    
		    in.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// By creating a BufferedWriter we can write Strings, Integers and single character to a file.
		// the method close() closes the writer and finishes the outputfile
		// the number of letters is printed out by an equal number of stars
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("frequency.txt"));
			
			for (int i = 97; i < 123; i++) {
				System.out.print((char)i + ": ");
				out.write((char)i + ": ");
				for(int j = 0 ; j < numChar.get(i); j++) {
					System.out.print("*");
					out.write("*");
				}
				System.out.println();
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
