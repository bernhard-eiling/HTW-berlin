package dwwPostfix;

// Test harness for RPN class
// Exercise 3, Informatik II
// D. Weber-Wulff, 1.1.2007

public class TestRPN {

	public static void main(String[] args) {
	  
	  // Yes, I know. I like to use objects :)	
      RPN rpn = new RPN();

      // Bunch of strings to test
      String[] infix = {
    		  			"1+2*3",				// 123*+			7

    		  			// new testcases added by tp
    		  			
    		  			"1*2+3",				// 12*3				5		
    		  			"1+2-3^4",				// 1234^-+			-78
    		  			"1^2-3*4",				// 12^34*-			-11
    		  			"1+2*3-4^5+6",			// 123*+45^-6+		-1011
    		  			"(1+2)*3+(4^(5-6))",	// 12+3*456-^+		9,25
    		  			"1+2+3/4+5+6*(7+8)",	// 1234/5678+*++++	98,75  oder 1234/++5+678+*+
    		  			"9-1-2-3*2-1",			// 91-2-32*1--		-1
    		  			
//    		  			"(1+2)*3",
//    		  			"1 + 2 * 3 - 4 * 5 + 6 + 7 * 8 * 9",
//    		  			"3+4*2^6",
//    		  			"1+2+3+4+5*6/",
    		  			
    		  			// new testcases added by tp
    		  			
    		  			"(1+2)*3+(4^(5-6))"
    		           };
      
      // This is for storing the result prior to evaluation
      String postfix;

      // For each of my examples
      for (int i = 0; i < infix.length; i++){
    	  postfix = rpn.infix2postfix(infix[i]);
    	  System.out.println ("Infix:  " + infix[i]);
    	  System.out.println ("Postfix:" + postfix);

    	  try {
    		  System.out.println ("Value:  " + rpn.eval(postfix));
    	  } catch (IllegalPostfixException e) {
    		  System.out.println ("Value cannot be calculated, postfix is illegal:");
    	  }

    	  System.out.println ();
      }
	}

}
