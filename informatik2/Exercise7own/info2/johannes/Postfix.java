package info2.johannes;

import java.util.Scanner;

public class Postfix {

	private boolean stringValid = false;

	public Postfix() {

	}

	public void checkString(String pfx) {
		if (pfx.length() < 5) {
			throw new RuntimeException("The input is too short. Try again!");
		} else {
			String[] substrings = pfx.split(" ");

			for (String s : substrings) {
				for (int i = 0; i < s.length(); i++) {
					Character a = s.charAt(i);
					if (!((a.equals('+') || a.equals('-') || a.equals('*') || a
							.equals('/')) || (Integer.parseInt("" + a) >= 0 && Integer
							.parseInt("" + a) <= 9))) {

						throw new RuntimeException(
								"The input is not well-formed. Try again!");
					}
				}
			}
			stringValid = true;
			this.evaluate(pfx);		
		}

	}

	public String evaluate(String pfx) {
		if (!stringValid) {
			this.checkString(pfx);
		}
		//int l = pfx.length();
		Stack<String> numbers = new Stack();
		String[] substrings = pfx.split(" ");

		for (String s : substrings) {

			if (!s.equals("+") && !s.equals("-") && !s.equals("*")
					&& !s.equals("/")) {
				numbers.push(s);
			} else {
				String operator = s;
				int firstOperand = Integer.parseInt(numbers.top());
				numbers.pop();
				int secondOperand = Integer.parseInt(numbers.top());
				numbers.pop();

				switch (operator) {
				case "+":
					numbers.push((Integer.toString(secondOperand + firstOperand)));
					break;
				case "-":
					numbers.push((Integer.toString(secondOperand - firstOperand)));
					break;
				case "*":
					numbers.push((Integer.toString(secondOperand * firstOperand)));
					break;
				case "/":
					numbers.push((Integer.toString(secondOperand / firstOperand)));
					break;
				}
			}

		}
		// System.out.println("last element on stack: " + chars.top());
		return numbers.top();
	}

	public String evaluateConsole() {
		Scanner in = new Scanner(System.in);
		String pfxFromConsole = in.nextLine();
		String result = this.evaluate(pfxFromConsole);
		return result;
	}
	
	public String infixToPostfix(String ifx) {
		String pfx = "";
		Stack<String> stack = new Stack<String>();
		String[] substrings = ifx.split(" ");
		
		for (String s : substrings) {
			// substring is a number, add to pfx
			if (!s.equals("+") && !s.equals("-") && !s.equals("*")
					&& !s.equals("/")) {
				pfx += s+" ";
			// substring is an operator	
			} else {
				// no operators in stack, push it in
				if (stack.isEmpty()) {
					stack.push(s);
				// operators in stack, compare precedence	
				} else {
					// operator s has higher precedence, push it to stack
					if ( (stack.top().equals("+") || stack.top().equals("-")) && (s.equals("*") || s.equals("/")) ) {
						stack.push(s);	
					// operator s has similar or lower precedence, pop last operator and push s to stack	
					} else {
						pfx += stack.top()+" ";
						stack.pop();
						stack.push(s);
					}
				}
			}
		}
		// pop out all remaining operators
		while (!stack.isEmpty()) {
			pfx += stack.top()+" ";
			stack.pop();
		}
		
		return pfx;
	}


}
