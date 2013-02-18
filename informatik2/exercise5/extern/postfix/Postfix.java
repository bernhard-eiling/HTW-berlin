package postfix;
import stack.Stack;
import stack.StackArray;
import stack.StackList;
import stack.StackOverflow;
import stack.StackUnderflow;

/**
 * Evaluates which character of a String is a Integer-value or Operator
 * @author Tobias Preuss (s0516424) & Niels Richter (s0517512)
 * problem: double-results are casted into integer while calulating which is bad!
 */
public class Postfix {

	private int asciiOffset = 48;

	//StackArray myStack = new StackArray(20);
	StackList myStack = new StackList();

	/**
	 * Console output for checking the value of a character
	 * @param name of the character as a string
	 * @param c the character as a char
	 */
	private void printcurrentValue(String name, double d) {
		System.out.println(name + " = " + d);
	}
	/**
	 * Console output for checking the value of an integer
	 * @param name of the character as a string
	 * @param i the character as a int
	 */
	private void printcurrentValue(String name, int i) {
		System.out.println(name + " = " + i);
	}

	/**
	 * Console output for the ASCII value of a character
	 * @param c the character as a char
	 */
	private void printASCII(char c) {
		System.out.println("ASCII-value of " + c + " is " + (int) c + "\n----------------------");
	}

	/**
	 * Returns true when given char is a digit
	 * @return true or false
	 */
	private boolean currentCharIsDigit(char supposedInteger) {
		return ((supposedInteger >= 49) && (supposedInteger <=57));		// improved by DWW
	}
	
	/**
	 * Checks if the current character is a Operator like + - / * ^
	 * @param supposedOperator as char
	 * @return true/false
	 */
	private boolean currentCharIsOperator(char supposedOperator) {
		int operatorCollection[] = {42,43,45,47,94};
		for (int i=0; i < operatorCollection.length; i++) {
			if (supposedOperator == operatorCollection[i]) {
				return true;
			}
		}
		return false; 
	}

	/**
	 * Calculates certain mathematical operations dependent on the operator given
	 * @param number2 as int
	 * @param number1 as int
	 * @param operator as char
	 * @return integer value
	 */
	private double myCalculationMethods(double number2, double number1, char operator) {
		double result = -99999;
//		System.out.println("\nCurrent operator = " + operator);
//		this.printcurrentValue("number1", number1);
//		this.printcurrentValue("number2", number2);
		switch(operator) {
			case '+' : result = number1 + number2; break;
			case '-' : result = number1 - number2; break;
			case '*' : result = number1 * number2; break;
			case '/' : result = number1 / number2; break;
			case '^' : result = Math.pow(number1, number2); break;
			default : System.out.println("Error: The given operator is not supported."); break;
		}
//		this.printcurrentValue("result", result);
		return result;

	}
	/**
	 * Resolves the top-Object of the stack and gets its ascii value
	 * The value is change to its numerical value before returned
	 * @return integer value
	 */
	private double convertObjectToDouble() {
		double myDouble = 0; // bad initialization
		try {
			myDouble = ( (Double)myStack.top() ).doubleValue();
		} catch (NumberFormatException e) {
			System.out.println("\t!!! NumberFormatException @ topObjectToInt !!!");
		} catch (StackUnderflow e) {
			System.out.println("\t!!! StackUnderflow @ topObjectToInt !!!");
		}
//		this.printcurrentValue("myDouble", myDouble);
		return myDouble;
	}

	/**
	 * Method evaluates sorts the values of a postfix string to do math with it
	 * @param pfx as a String
	 * @return integer value as the result of the mathematical operation
	 */
	public double evaluate (String pfx){
		// isolate every character of the String
		for (int i=0; i < pfx.length(); i++) {
			char currentChar = pfx.charAt(i);

			// check whether char is an integer
			if (currentCharIsDigit(currentChar)) {
				try {
					// push it into stack because it is a digit
//					System.out.println("this goes to the stack now: " + Float.valueOf(currentChar-asciiOffset));
					this.myStack.push(Double.valueOf(currentChar-asciiOffset));
				} catch (StackOverflow e) {
					System.out.println("\t!!! StackOverflow @ evaluate !!!");
				}
			}

			// check whether char is a char
			if (currentCharIsOperator(currentChar)) {

				try {
					this.myStack.push(this.myCalculationMethods(convertObjectToDouble(),convertObjectToDouble(),currentChar));
					//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
				} catch (StackOverflow e) {
					System.out.println("\t!!! StackOverflow !!! storing the result");					
				}
			}
		
		}
		double solution = 0;
		try {
			solution = (Double) this.myStack.top();
		} catch (StackUnderflow e) {
			e.printStackTrace();
		}
		
		return solution;
		
	}

	/**
	 * Main method is for testing only
	 * @param args
	 */
	public static void main(String[] args) {

		String pfx = "23+";
		Postfix myPF = new Postfix();
		myPF.evaluate(pfx);

	}

}
