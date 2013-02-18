import static java.lang.Math.*;

import java.util.ArrayList;


public class Main {

	
	public static void main(String[] args) {
		test testk = new test();
		//testk.testStatistic();
		
		testk.testStatistic5();
//		testk.testPermutation2();
		testk.testPermutation5letter();
		//testk.testPermutation3letter();
		
//		System.out.println(generateWord(7));
//		
//		Substring sub = new Substring("JAVA");
//		ArrayList<String> subs = sub.getSubstrings();
//		for(int i = 0; i < subs.size(); i++) {
//			System.out.println(subs.get(i));
//		}
		
	}

	public static String generateWord(int length) {
		
		String word = "";
		
		for(int i = 1; i <= length; i++) {
			int randomInt = (int)(random() * (123 - 97) + 97); 
			word += (char)randomInt;
		}
		
		return word;
	}
}
