package dwwPostfix;

// Reverse Polish Notation, solution Exercise 3 Informatik II
// D. Weber-Wulff, 1.1.2007
// People were having so much trouble with this, this is my solution
// Okay, I'm lazy and using the Java stack
import java.util.Stack;

public class RPN {
  // We use named constants to keep the program readable
  private final static char TIMES_OP     = '*';
  private final static char PLUS_OP      = '+';
  private final static char MINUS_OP     = '-';
  private final static char DIVISION_OP  = '/';
  private final static char POWER_OP     = '^';
  private final static char OPEN_PAREN   = '(';
  private final static char CLOSE_PAREN  = ')';
  private final static char BLANK        = ' ';
	
  public double evaluate (char operator, double op1, double op2) 
                throws UnknownOperatorException{
	switch (operator){
	  case TIMES_OP    : return op1 * op2; 
	  case PLUS_OP     : return op1 + op2; 
	  case MINUS_OP    : return op1 - op2;
	  case DIVISION_OP : return op1 / op2;
	  case POWER_OP    : return Math.pow(op1, op2); 
	  // Panic
	  default : throw new UnknownOperatorException();
		}
  }
  
  public int priority (char operator)
             throws UnknownOperatorException{
	switch (operator){
	  case TIMES_OP    : return 2;
	  case PLUS_OP     : return 1; 
	  case MINUS_OP    : return 1;
	  case DIVISION_OP : return 2;
	  case POWER_OP    : return 3; 
	  default : throw new UnknownOperatorException();
	}
}
		
  private boolean isDigit (char ch){
	return ((ch >= '0') && (ch <= '9'));  
  }
		
  private boolean isOperator (char ch){
	return (ch == TIMES_OP)    ||
	       (ch == PLUS_OP)     ||
	       (ch == MINUS_OP)    ||
	       (ch == DIVISION_OP) ||
	       (ch == POWER_OP);
  }
  
  private String appendTopToString (Stack st, String str){
	// pop the top of the Stack and append it to the String
    // It is isolated here because it uses the Java pop,
	// which is top and pop in one operation. 
	if (!st.isEmpty()){
        str = str + ((Character)st.pop()).charValue();
	}
	// This I do not understand. I always thought that objects
	// were call-by-reference, Stack is, as the pop really does
	// shorten the Stack from the actual parameter.
	// But appending anything to a String does not change the
	// acutal parameter, so I must assume that a String, although
	// an Object, is actually call-by-value. Thus, I have to make
	// a new one and return it. Sigh. 
	return str; 
  }
  
  public String infix2postfix (String infix){
	  
	Stack<Character> s = new Stack<Character> ();
	String postfix = "";
	char ch;
	
	// only need to go through infix once
	for (int i=0; i<infix.length(); i++){
		ch = infix.charAt(i);
		
		// ignore blanks
		if (ch == BLANK) continue; // with next loop
		
		// Digits get output right away
		if (isDigit(ch)){
		   postfix = postfix + ch;	
		   continue;
		}
		
		// Open Parentheses get pushed
		if (ch == OPEN_PAREN){
			s.push((Character)ch);		
			continue;
		}
		
		// Close Parentheses - pop and output until you hit open
		if (ch == CLOSE_PAREN){
			while (s.peek() != OPEN_PAREN){
				postfix = appendTopToString(s, postfix); 
			}
			s.pop(); // This better be the Open parenthesis!
			continue;
		}
		
		// Operator : output all operators on same or higher priority	
		try{
		while (!s.isEmpty() && priority(ch) <= priority(s.peek().charValue())){
			postfix = appendTopToString(s, postfix);   
		}
		} 
		catch (UnknownOperatorException e) {
			System.out.println(e.toString());
			}
		
		// Then push operator
		s.push((Character)ch);
	
	} // for
	
	// now output the rest of the stack
	while (!s.isEmpty()){
		postfix = appendTopToString(s, postfix); 
	}
	
	return postfix;    
  }
  
  public double eval (String postfix)
                throws IllegalPostfixException
  {  
	Stack<Double> s = new Stack<Double> (); 
	char ch;
	double op_left, op_right;
	double value = 0.0;

	for (int i=0; i< postfix.length();i++){
    	ch = postfix.charAt(i);

    	// Digits get pushed in their double value
    	if (isDigit (ch)) {
    		s.push(Double.valueOf(""+ch));
    		continue;
    	}
    	
    	// legal operators evaluated
    	if (isOperator(ch)) {
    		// Pick off the operators
    		try {
    		op_right = s.pop();
    		op_left  = s.pop();
    		} catch (Exception e) {
    		    throw new IllegalPostfixException();
    		}
    		// evaluate and push the result
    		try {
    			s.push (evaluate (ch, op_left, op_right));
			} catch (UnknownOperatorException e) {
				throw new IllegalPostfixException();
			}
    	} else
    		throw new IllegalPostfixException();
    }
	  
	// This is now the value of the expression  
	value = s.pop();
	  
	// The stack must be empty, or the expression was illegal
	if (!s.isEmpty()) throw new IllegalPostfixException();
	  
	return value;
  }
 
}
