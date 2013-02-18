package postfix;

/**
 * Tester for the Postfix class supplying the Finger exercise's tasks.
 * @author Tobias Preuss (s0516424) & Niels Richter (s0517512)
 * Improved by adapting the TestRPN.java D.Weber-Wulff build - very nice String-array and for-loop!
 */
public class Postfix_Tester {

	public static void main(String[] args) {		
		
		Postfix myPF = new Postfix();
		String[] testPostfix = {
								// postfix string 			and result
								"12*3+",					"5",
								"123*+",					"7",
								"1234^-+",					"-78",
								"12^34*-",					"-11",
								"123*+45^-6+",				"-1011",
								"12+3*456-^+",				"9,25",
								"1234/5678+*++++",			"98,75",
								"1234/++5+678+*+",			"98,75",
								"91-2-32*-1-",				"-1",
								"72/",						"3,5",
								"55+3/",					"3,33"
							   };
		
		for (int i = 0; i < testPostfix.length; i=i+2){
	    		  System.out.println("---> " + testPostfix[i] 
	    		                             + " = " + myPF.evaluate(testPostfix[i]) 
	    		                             + "\t\t(should be " + testPostfix[i+1] + ")");

	      }
	}
}
