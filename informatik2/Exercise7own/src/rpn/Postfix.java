package rpn;

import java.util.Scanner;

/**
 * 6 * @author Andy 7 * 8
 */
public class Postfix {

	public static int evaluate(String pfx) throws InvalidExpressionException {
		Stack<String> pfxStack = new Stack<String>();
		Stack<Integer> numberStack = new Stack<Integer>();
		for (int i = pfx.length() - 1; i >= 0; i--) {
			String currPfx = String.valueOf(pfx.charAt(i));
			if (!currPfx.equals(" ")) {
				pfxStack.push(currPfx);
			}
		}
		while (!pfxStack.isEmpty()) {
			String currentPfx = "";
			try {
				currentPfx = pfxStack.top();
				pfxStack.pop();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if ("0123456789".contains(currentPfx)) {
				numberStack.push(new Integer(Integer.parseInt(currentPfx)));
			} else if ("-+*/%^".contains(currentPfx)) {
				try {
					int rhs = numberStack.top().intValue();
					numberStack.pop();
					int lhs = numberStack.top().intValue();
					numberStack.pop();
					Integer result = 0;
					if (currentPfx.equals("-"))
						result = lhs - rhs;
					if (currentPfx.equals("+"))
						result = lhs + rhs;
					if (currentPfx.equals("*"))
						result = lhs * rhs;
					if (currentPfx.equals("/"))
						result = lhs / rhs;
					if (currentPfx.equals("%"))
						result = lhs % rhs;
					if (currentPfx.equals("^"))
						result = (int) Math.pow((double) lhs, (double) rhs);
					numberStack.push(new Integer(result));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				throw new InvalidExpressionException();
			}
		}
		int returnInt = 0;
		try {
			returnInt = numberStack.top().intValue();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnInt;
	}

	public static String infixToPostfix(String ifx)
			throws InvalidExpressionException {
		Stack<String> ifxStack = new Stack<String>();
		Stack<String> opStack = new Stack<String>();
		String returnString = "";
		for (int i = ifx.length() - 1; i >= 0; i--) {
			String currIfx = String.valueOf(ifx.charAt(i));
			if (!currIfx.equals(" ")) {
				ifxStack.push(currIfx);
			}
		}
		while (!ifxStack.isEmpty()) {
			String currIfx = "";
			try {
				currIfx = ifxStack.top();
				ifxStack.pop();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if ("0123456789".contains(currIfx)) {
				returnString += currIfx;
			} else if ("-+*/%^".contains(currIfx)) {
				if (opStack.isEmpty()) {
					opStack.push(currIfx);
				} else {
					boolean stackIsRelevant = (getPrecedence(opStack.top()) >= getPrecedence(currIfx));
					if (!stackIsRelevant)
						opStack.push(currIfx);
					while (stackIsRelevant && !opStack.isEmpty()) {
						if (getPrecedence(opStack.top()) > getPrecedence(currIfx)) {
							returnString += opStack.top();
							try {
								opStack.pop();
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							if (opStack.isEmpty())
								opStack.push(currIfx);
						} else if (getPrecedence(opStack.top()) == getPrecedence(currIfx)) {
							if (currIfx.equals("^")) {
								opStack.push(currIfx);
								stackIsRelevant = false;
							} else {
								returnString += opStack.top();
								try {
									opStack.pop();
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
								opStack.push(currIfx);
								stackIsRelevant = false;
							}
						}
					}
				}
			} else {
				throw new InvalidExpressionException();
			}
		}
		while (!opStack.isEmpty()) {
			returnString += opStack.top();
			try {
				opStack.pop();
			} catch (StackUnderflowException e) {
				System.out.println(e.getMessage());
			}
		}
		return returnString;
	}

	private static int getPrecedence(String op) {
		int returnInt = 4;
		if (op.equals("+") || op.equals("-"))
			returnInt = 1;
		if (op.equals("*") || op.equals("/") || op.equals("%"))
			returnInt = 2;
		if (op.equals("^"))
			returnInt = 3;
		return returnInt;
	}

	public static void userInput() {
		String inputString = "";
		String pfx = "";
		int result = 0;
		Scanner sysin = new Scanner(System.in);
		System.out.print("input an infix formula: ");
		inputString = sysin.nextLine();
		try {
			pfx = infixToPostfix(inputString);
		} catch (InvalidExpressionException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("in postfix: " + pfx);
		try {
			result = evaluate(pfx);
		} catch (InvalidExpressionException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("result: " + result);
	}
}