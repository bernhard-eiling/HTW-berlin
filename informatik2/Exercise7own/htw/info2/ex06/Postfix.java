package htw.info2.ex06;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Java class to handle infix and postfix formatted mathmatical expressions using a self-defined stack
 * 
 * @author Alexander Hinrichs, Konstantin Vogel, Markus Bausdorf
 * @version 1.0
 *
 */
public class Postfix {
	Stack<String> memory = new Stack<String>(); //Stack to save various strings
	Set<String> operators = new HashSet<String>(Arrays.asList("+", "-", "*", 
			"/", "^")); //Set of operators fo easy comparison
	String resultString = ""; //empty result String

	/**
	 *Main program, able to read input from console and to evaluate it 
	 *
	 *@param arguments command-line-parameters
	 */
	public static void main(String[] arguments) {
		String input = "";
		Scanner read = new Scanner(System.in);
		Postfix post = new Postfix();
		Boolean running = true;

		while (running) {
			input = read.nextLine();
			if (input.equals("exit"))
				break;
			System.out.println(post.evaluate(input));
		}
	}

	/**
	 * Method that evaluates a postfix-formatted mathmatical expression
	 * 
	 * @param pfx postfix-formatted String
	 * 
	 * @return result of the mathmatical expression as an int
	 */
	public int evaluate(String pfx) {
		memory.clearStack();
		int result = 0;
		for (int i = 0; i < pfx.length(); i++) {
			String actual = String.valueOf(pfx.charAt(i));
			if (actual.matches("\\d")) {
				memory.push(actual);
			} else {
				int left = 0; 
				int right = 0;
				try {
					left = Integer.parseInt(memory.pop());
					right = Integer.parseInt(memory.pop());
				} catch(Underflow e) {
					System.err.println("Stack Underflow Exception");
					System.exit(1);
				}
				switch (actual) {
				case "+":
					result = left + right;
					break;
				case "-":
					result = left - right;
					break;
				case "*":
					result = left * right;
					break;
				case "/":
					result = left / right;
					break;
				}
				memory.push(Integer.toString(result));

			}
		}
		return result;
	}

	/**
	 * Method that converts a string using infix notation to
	 * a string formatted in postfix notation
	 * 
	 * @param ifx infix-formatted String
	 * @return postfix-formatted String
	 */
	public String infixToPostfix(String ifx) {
		memory.clearStack();
		for (int i = 0; i < ifx.length(); i++) {
			String c = String.valueOf(ifx.charAt(i));
			switch (c) {
			case "+":
				OpHandler(c, 1);
				break;
			case "-":
				OpHandler(c, 1);
				break;
			case "*":
				OpHandler(c, 2);
				break;
			case "/":
				OpHandler(c, 2);
				break;
			case "(":
				memory.push(c);
				break;
			case ")":
				ParenHandler(c);
				break;
			case "^":
				OpHandler(c, 3);
				break;
			default:
				resultString += c;
				break;
			}
		}
		while (!(memory.isEmpty())) {
			try {
				resultString += memory.pop();
			} catch(Underflow e) {
				System.err.println("Stack Undeflow Exception");
			}
		}
			
		return resultString;
	}

	/**
	 * Method to handle operators while converting infix to postfix. It sorts the operators by 
	 * mathmatical precedence and, if needed, adds them to the result
	 * 
	 * @param c Current operator
	 * @param prec Mathmatical precedence of the current operator
	 * 
	 */
	public void OpHandler(String c, int prec) {
		while (!(memory.isEmpty())) {
			String stackTop = memory.top();
			if (stackTop.equals("("))
				break;
			else {
				int stackTopPrec;
				if (stackTop.equals("+") || stackTop.equals("-"))
					stackTopPrec = 1;
				else
					stackTopPrec = 2;
				if (stackTopPrec < prec) {
					break;
				} else {
					resultString += stackTop;
					try {
						memory.pop();
					} catch(Underflow e) {
						System.err.println("Stack Underflow Exception");
					}
				}
			}
		}
		memory.push(c);
	}
	
	/**
	 * Method to handle parenthesis while converting infix to postfix.
	 * 
	 * @param c Current Parenthesis
	 */
	public void ParenHandler(String c) {
		String actual = "";
		while (!(memory.isEmpty())) {
			try {
				actual = memory.pop();
			} catch(Underflow e) {
				
			}

			if (actual.equals("("))
				break;
			else
				resultString += actual;
		}
	}
	
	/**
	 * First try of prefix to postfix conversion - not quite working as of May 16th
	 * 
	 * @param prefix prefix-formatted String
	 * 
	 * @return postfix-formatted String
	 * 
	 */
	public String prefixToPostfix (String prefix) {
		String postfix = "";
		memory.clearStack();
		for(int i = 0; i < prefix.length(); i++){
			String c = String.valueOf(prefix.charAt(i));
			if(c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/")){
				memory.push(c);
			} else {
				postfix += c;
				while(!(memory.isEmpty())){
					try {
						postfix += memory.pop();
					} catch(Underflow e) {
						System.err.println("Stack Underflow Exception");
					}
				}
			}
		}
		return postfix;
	}

}