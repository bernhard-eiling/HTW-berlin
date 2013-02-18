package eiling.info2.exe6;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Postfix {

	Postfix() {
	}
	
	public String infixToPostfix (String ifx) {
		
		StringBuffer infix = new StringBuffer(ifx);
		StringBuffer postfix = new StringBuffer();
		
		for (int i = 0; i < infix.length(); i++) {
			char current = infix.charAt(i);
			
			
			Stack<Character> stack = new Stack<Character>();
			
			if (current == '+' || current == '-' || current == '*'
					|| current == '/'){
				while (!stack.isEmpty() && stack.peek() != '(') {
					if (precedence(stack.peek(), current)) {
						postfix.append(current);
						stack.pop();
					} else {
						stack.push(current);
					}
				}
			} else if (current == '(') {
				stack.push(current);
			} else if (current == ')') {
				while (!stack.isEmpty() && stack.peek() == '(') {	
					postfix.append(stack.pop());
				}
				if (!stack.isEmpty()) {
					stack.pop();
				}
			} else if (current == '0' || current == '1' || current == '2'
					|| current == '3' || current == '4' || current == '5'
					|| current == '6' || current == '7' || current == '8'
					|| current == '9' ) {
				postfix.append(current);
			}
		}
		
		return postfix.toString();
	}
	
	private boolean precedence (char c1, char c2) {
		if (c1 == '+' || c1 == '-' && c2 == '*' && c2 == '/') {
			return false;
		} else {
			return true;
		}
	}
	
	public void readPostfix() {
		
		String input = null;
		
		System.out.println("Enter a postfix line for evaluation: ");
		InputStreamReader stream = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(stream);
		try {
			input = in.readLine();
			System.out.println(evaluate(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int evaluate(String pfx) {

		StringBuffer postfix = new StringBuffer(pfx);
		Stack<Character> stack = new Stack<Character>();

		for (int i = 0; i < postfix.length(); i++) {
			char current = postfix.charAt(i);

			if (current == '0' || current == '1' || current == '2'
					|| current == '3' || current == '4' || current == '5'
					|| current == '6' || current == '7' || current == '8'
					|| current == '9') {
				stack.push(current);
			} else if (current == '+' || current == '-' || current == '*'
					|| current == '/') {
				char result = (char)this.applyOperator((int)stack.pop(), (int)stack.pop(), current);
				stack.push(result);
			}
		}

		return (int)stack.pop();
	}

	private int applyOperator(int rhs, int lhs, char op) {

		int result = 0;
		
		//norm ascii code to integer
		if(rhs >= 48)
		rhs -= 48;
		if(lhs >= 48) 
		lhs -= 48;

		try {
			if (op == '+') {
				result = lhs + rhs;
//				System.out.println("+ lhs: " + lhs);
//				System.out.println("+ rhs: " + rhs);
//				System.out.println("+ op: " + result);
			}
			if (op == '-') {
				result = lhs - rhs;
				System.out.println("- op: " + result);
			}
			if (op == '*') {
				result = lhs * rhs;
			}
			if (op == '/') {
				result = (int) (lhs / rhs);
			}
			
		} catch (Exception e) {
			System.err.println("No valid operator.");
			e.printStackTrace();
		}

		return result;
	}
}
