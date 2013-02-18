package postfix;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import stack.StackList;
import stack.StackOverflow;
import stack.StackUnderflow;

/**
 * Converts an Infix string into Postfix
 * http://www.spsu.edu/cs/faculty/bbrown/web_lectures/postfix/
 * @author Tobias Preuss (s0516424) & Niels Richter (s0517512)
 */
public class Infix_to_Postfix {

	//StackArray myStack = new StackArray(20);
	StackList myStack = new StackList();

	/**
	 * Checks whether supposedOperator is plus or minus 
	 * @param supposedOperator as char
	 * @return true/false
	 */
	private boolean isLowerOperator(char supposedOperator) {
		int operatorCollection[] = {43,45};
		for (int i=0; i < operatorCollection.length; i++) {
			if (supposedOperator == operatorCollection[i]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether supposedOperator is multiply, divide, power
	 * @param supposedOperator as char
	 * @return true/false
	 */
	private boolean isHigherOperator(char supposedOperator) {
		int operatorCollection[] = {42,47,94};
		for (int i=0; i < operatorCollection.length; i++) {
			if (supposedOperator == operatorCollection[i]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the current character is a digit
	 * @param current as char
	 * @return true/false
	 */
	private boolean isDigit(char current) {
		return ((current >= 49) && (current <=57));		// improved by DWW		
	}

	/**
	 * Checks if the current character is an operator
	 * @param current as char
	 * @return true/false
	 */
	private boolean isOperator(char current) {
		if (isLowerOperator(current) || isHigherOperator(current)) {
			return true;	
		}
		return false;
	}



	public String makePostfix (String infix) {
		int i = 0;
		String postfix = "";
		while (i < infix.length()) {
			char current = infix.charAt(i);
			//System.out.println("loop(" + i + "); current = " + current + " postfix = " + postfix + " Stack = " + this.myStack.print());

			// when right bracket occurs get everything out of the stack until left bracket is hit 

			if (current == '(') {
				try {
					this.myStack.push(new Character(current));
				} catch (StackOverflow e) {
					System.out.println("-- We hit a bug. Sorry. --");
				}
				i++;
				//System.out.println("Klammerauf");
			}
			
			// (1+2)*3+(4^(5-6))
			if (current == ')') {
					try {
						while(!this.myStack.isEmpty() && this.myStack.topWpop().toString().charAt(0) != '(') {
							postfix = postfix + this.myStack.top();
						}
						//this.myStack.pop();
						//System.out.println("Klammerzu");
						i++;
					} catch (StackUnderflow e) {
						System.out.println("-- FEHLER IST HIER --");
					}
			}
			
			
				 else {

					// every digit is written to postfix-string
					if(isDigit(current)) {
						postfix = postfix + current;
						i++;
					} else {




						// every operator calls a compare-method
//						else 
						if(isOperator(current)) {
							//System.out.println("\t\t\t--> Operator");

							// --------------------------------

							char currentTop = 0;

							// --------------------------------

							// Stack is empty
								if(this.myStack.isEmpty()) {
									try {
										this.myStack.push(new Character(current));
									} catch (StackOverflow e) {
										e.printStackTrace();
									}
									i++;
								} else {
									// get the current top of the stack
									try {
										currentTop = this.myStack.top().toString().charAt(0);
									} catch (StackUnderflow e1) {
										System.out.println("2");
										e1.printStackTrace();
									}
									if(this.isLowerOperator(current) && this.isLowerOperator(currentTop)) {
										postfix = postfix + currentTop;
									} else {
										if(this.isLowerOperator(current) && this.isHigherOperator(currentTop)) {
											postfix = postfix + currentTop;
										} else {
											if(this.isHigherOperator(current) && this.isLowerOperator(currentTop)) {
												try {
													this.myStack.push(new Character(currentTop));
												} catch (StackOverflow e) {
													e.printStackTrace();
												}
												try {
													this.myStack.push(new Character(current));
												} catch (StackOverflow e) {
													e.printStackTrace();
												}
												i++;
											} else {
												if(this.isHigherOperator(current) && this.isHigherOperator(currentTop)) {
													postfix = postfix + currentTop;
												} else {
													if(i == infix.length() && this.isLowerOperator(currentTop) || this.isHigherOperator(currentTop)) {
														try {
															this.myStack.push(new Character(current));
														} catch (StackOverflow e) {
															e.printStackTrace();
														}
														i++;
													}
												}
											}
										}
									}
								}

						}
					}
				}
			
		}


		while (!this.myStack.isEmpty()) {
			try {
				postfix = postfix + this.myStack.top();
			} catch (StackUnderflow e) {
				System.out.println("3");
				e.printStackTrace();
			}
		}
		return postfix;
	}




	public static void main(String[] args) {
		System.out.println("Please insert a infix we should calculate");
		while(true) {
			// Instance of this class
			Infix_to_Postfix myI2P = new Infix_to_Postfix();
			Postfix myPostfix = new Postfix();
			String postfix = "";

//			infix = "1*2+3/4*5-6+7*8*9";
//			postfix = "12*34/5*+6-78*9*+";

			// Try-catch-block for read in
			try {
				// Take the input an make an integer out of it
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String s = new String (in.readLine());
				postfix = myI2P.makePostfix(s);
				System.out.println("Postfix   = " + postfix);
				System.out.println("\n========= CALCULATION PART STARTS =========\n");


				System.out.println("Should be = " + myPostfix.evaluate(postfix));
			} catch(IOException e) {
				// If the readin went wrong, give us a message instead of an stupid exception error
				System.out.println("Uuups, we hit a bug!\n");
			} catch(NumberFormatException e) {
				// If the input is not readable
				System.out.println("Sorry, but we can't read your input.\n");
			} catch(ArithmeticException e) {
				// If the input is not readable
				System.out.println("Sorry, but we can't read your input.\n");
			}
		}
//		infix = "1*2+3";postfix = "12*3+";
//		infix = "1+2*3";postfix = "123*+";
//		infix = "1+2-3^4";postfix = "1234^-+";
//		infix = "12^34*-";postfix = "";
//		infix = "123*+45^-6+";postfix = "";
//		infix = "12+3*456-^+";postfix = "";
//		infix = "1234/5678+*++++";postfix = "";
//		infix = "1234/++5+678+*+";postfix = "";
//		infix = "91-2-32*1--";postfix = "";
//		System.out.println("---> " + infix + " = " + " (should be " + postfix + ")");
	}

}
