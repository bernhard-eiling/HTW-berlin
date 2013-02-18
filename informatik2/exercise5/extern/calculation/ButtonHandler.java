/*
 * Calculator Default ButtonHandler
 *
 * Developed for "Rethinking CS101", a project of Lynn Andrea Stein's AP Group.
 * For more information, see <a href="http://www.ai.mit.edu/projects/cs101">the
 * CS101 homepage</a> or email <las@ai.mit.edu>.
 *
 * Copyright (C) 1996 Massachusetts Institute of Technology.
 * Please do not redistribute without obtaining permission.
 */
package calculation;
import cs101.util.*;
import dwwPostfix.IllegalPostfixException;
import dwwPostfix.RPN;
import fraction.RatioInput;
import fraction.RatioOperator;

/**
 * This class provides the smarts for a basic four-function 
 * calculator.  It repeatedly calls its Calculator(GUI)'s getButton()
 * to consume the buttonIDs that the Calculator object produces. <p>
 *
 * <P>Copyright (c) 1998 Massachusetts Institute of Technology
 *
 * @author  Todd C. Parnell, tparnell@ai.mit.edu
 * @author  Emil Sit, sit@mit.edu
 * @author  Lynn Andrea Stein, las@ai.mit.edu
 * @version $Id: ButtonHandler.java,v 1.5 1998/07/24 16:37:13 tparnell Exp $
 *
 * @see Calculator
 * @see cs101.util.semaphore.IntBuffer
 */

public class ButtonHandler implements Runnable {

	protected Calculator gui;
	protected Thread spirit;

	/**
	 * Flag to tell whether the Thread running this should be stopped.
	 */
	private boolean stopped = false;

	// State variables
	// All are initialized by resetCalc();
	// Note:  currArg is always still being created 
	//        and so is stored in the gui's text 
	protected boolean currArgIsInt, numStartsNewArg, opPending, gotOpSinceEquals;
	protected double prevArg, pendingArg;
	protected int currOp, pendingOp;

	/**
	 * Build a ButtonHandler object from a Calculator gui passed in as
	 * an argument.
	 */
	protected ButtonHandler( Calculator gui ) {
		this.gui = gui;
		this.resetCalc();
		this.spirit = new Thread(this);
		this.spirit.start();
	}


	/**
	 * Provides the interactive control loop of this animate object.
	 * @see java.lang.Runnable
	 */
	public void run () {

		HANDLEBUTTON:
			while (!this.stopped) {
				int buttonID = this.gui.getButton();
				try {
					this.printState("handling: "+ this.gui.getButtonLabel(buttonID));

					if(this.gui.getText().equals("0.0")) {
						this.clearScreen();
						this.resetDecimal();
					}

					// Deal with number buttons and special operators
					if ( (buttonID < 14 || buttonID == 16 || buttonID == 17 || buttonID == 21 || buttonID == 22) && buttonID >= 0) {
						this.handleNumKey( buttonID );
						continue HANDLEBUTTON;
					}

					// deal with everything else
					switch ( buttonID ) {
					case Calculator.ERASE:
						if(this.gui.getText().length() > 0) {
							String task = "";
							for(int i = 0; i < this.gui.getText().length()-1; i++) {
								task = task + this.gui.getText().charAt(i);
							}
							if(task.length() < 1) {
								task = "0.0";
							}
							this.gui.setText(task);
							break;
						}
					case Calculator.IN2POSTFIX:
						if(this.gui.getText().length() > 0) {
							this.handleInfixToPostfix();
							break;
						}
					case Calculator.EQUALS:
						if(this.gui.getText().length() > 0) {
							this.handleEquals();
							break;
						}
					case Calculator.CALC_FRACTION:
						if(this.gui.getText().length() > 0) {
							this.handleFractions();
							break;
						}
					case Calculator.CLEAR:
						this.resetCalc();
						break;
					default:
						this.printState("ERROR:  unmatched buttonID.");
					}
				} finally {
					//this.numStartsNewArg = !(( buttonID < 10 ) || ( buttonID == Calculator.DOT ));
				}
			}
	}

	protected void printState(String s) {
		/*
		 *	  System.out.println (s
		 *			  +"\nnumStartsNewArg: "+numStartsNewArg
		 *			  +"\ngotOpSinceEquals: "+gotOpSinceEquals
		 *			  +"\nprevArg: "+prevArg
		 *			  +"\ncurrOp: "+((currOp == Calculator.NO_OP)?
		 *					 "NO_OP":
		 *					 this.gui.getButtonLabel(currOp))
		 *			  +"\nopPending: "+opPending
		 *			  +"\npendingOp: "+((pendingOp == Calculator.NO_OP)?
		 *					    "NO_OP":
		 *					    this.gui.getButtonLabel(pendingOp))
		 *			  +"\npendingArg: "+pendingArg
		 *			  +"\n");
		 */
	}

	/**
	 * Handles the conversion from infix-input to postfix-input
	 * writes back direct to the input-string
	 */
	protected void handleInfixToPostfix() {
		// Calling Infix2Postfix from dwwPostfix-package
		RPN rpn = new RPN();
		String postfix = rpn.infix2postfix(this.gui.getText());
		// Clear the screen
		this.clearScreen();
		// Write the solution to the screen
		this.gui.setText( String.valueOf( postfix ) );
	}

	/**
	 * Handling the behaviour of the numkeys
	 * @param num
	 */
	protected void handleNumKey( int num ) {
		if (this.numStartsNewArg ) {
			// If there is an input
			if (this.gui.getText().length() > 0) {
				// Get the last character
				char last = this.gui.getText().charAt(this.gui.getText().length()-1);
				// If the last character is an operator it can only be followed by a number or brakets
				if((last == '+' || last == '-' || last == '/' || last == '*' || last == ':' || last == '(' || last == ')' || last == '^') && (num < 10 || num == 16 || num == 17)) {
					this.gui.setText( this.gui.getText() + this.gui.getButtonLabel(num) );
					// If the last character is a number, it can only be followed by an operator
				} else if((last == '0' || last == '1' || last == '2' || last == '3' || last == '4' || last == '5' || last == '6' || last == '7' || last == '8' || last == '9') && num > 9) {
					this.gui.setText(this.gui.getText() + this.gui.getButtonLabel(num));
					// Behind a closing braket can follow an operator
				} else if((last == '+' || last == '-' || last == '/' || last == '*' || last == ':' || last == '(' || last == ')' || last == '^') && last == ')') {
					this.gui.setText(this.gui.getText() + this.gui.getButtonLabel(num));
				}
				// Write the numbers to the input
			} else if (this.gui.getText().length() == 0 && (num < 10 || num == 16 || num == 17 || num == 21 || num == 22)) {
				this.gui.setText( this.gui.getText() + this.gui.getButtonLabel(num) );
			}
		}
	}

	protected void handleDecimal () {
		if ( this.numStartsNewArg ) {
			this.handleNumKey( 0 );
		}
		if ( this.currArgIsInt ) {
			this.currArgIsInt = false;
			this.gui.setText( this.gui.getText() + ".");
		}
	}

	protected void handleOp( int op ) {
		if ( this.gotOpSinceEquals ) {
			if ( this.numStartsNewArg ) {  // +-
				// System.out.println("handling +-:  change op & return.\n");
				this.currOp = op;
				return;
			} else {
				boolean newOpIsMajor =( op == Calculator.OP_MUL ) || ( op == Calculator.OP_DIV );
				boolean currOpIsMinor = ( this.currOp == Calculator.OP_ADD ) || ( this.currOp == Calculator.OP_SUB );
				if ( ! this.opPending  && newOpIsMajor && currOpIsMinor ) {
					// System.out.println("newOpMajor, currOpMinor,"
					// + "set up pending op.\n");
					this.opPending = true;
					this.pendingOp = this.currOp;
					this.pendingArg = this.prevArg;
				} else {
					if ( this.opPending && newOpIsMajor && (! currOpIsMinor) ) {
						this.opPending = false;
						// System.out.println("handling =, faking it.\n");
						this.handleEquals();
						this.opPending = true;
					} else {
						// System.out.println("handling =\n");
						this.handleEquals();
					}
				}
			}
			this.printState("After pending, handleEquals\n");
		} else {
			// System.out.println("first op since =\n");
		}
		this.currOp = op;
		this.prevArg = Coerce.stringToDouble( this.gui.getText() );
		this.gotOpSinceEquals = true;
	}


	/**
	 * Handling the Fractions, called hiting the a/b-button
	 */
	protected void handleFractions () {
		String task		= "";
		// Do we have an operator at the end, than throw it away and refresh the screen
		char last = this.gui.getText().charAt(this.gui.getText().length()-1);
		if(last == '+' || last == '-' || last == '/' || last == '*' || last == '(' || last == '^') {
			for(int i = 0; i < this.gui.getText().length()-1; i++) {
				task = task + this.gui.getText().charAt(i);
			}
			this.clearScreen();
			this.gui.setText( String.valueOf( task ) );
			// otherwise the task is the text from the input, we calculate with fractions
		} else {
			RatioInput i = new RatioInput();
			RatioOperator myRO = new RatioOperator();

			int action = i.workOnString(this.gui.getText());

			switch(action) {
			case 1 : myRO.summate(i.num1,i.den1,i.num2,i.den2);break;
			case 2 : myRO.subtract(i.num1,i.den1,i.num2,i.den2); break;
			case 3 : myRO.multiply(i.num1,i.den1,i.num2,i.den2); break;
			case 4 : myRO.division(i.num1,i.den1,i.num2,i.den2); break;
			case 5 : myRO.equalValue(i.num1,i.den1,i.num2,i.den2); break;
			case 6 : myRO.greaterthan(i.num1,i.den1,i.num2,i.den2); break;
			case 7 : myRO.greaterthan(i.num2,i.den2,i.num1,i.den1); break;
			default : myRO.resultNumerators = 0; myRO.resultDenomerators = 0; break;
			}
			this.clearScreen();
			this.gui.setText(String.valueOf(myRO.resultNumerators + "/" + myRO.resultDenomerators));
		}
	}

	/**
	 * Handling the normal request, enabled by hiting the =
	 */
	protected void handleEquals () {
		// Some variables
		String postfix 	= "";
		String task		= "";

		// Do we have an operator at the end, than throw it away and refresh the screen
		char last = this.gui.getText().charAt(this.gui.getText().length()-1);
		if(last == '+' || last == '-' || last == '/' || last == '*' || last == '(' || last == '^') {
			for(int i = 0; i < this.gui.getText().length()-1; i++) {
				task = task + this.gui.getText().charAt(i);
			}
			this.clearScreen();
			this.gui.setText( String.valueOf( task ) );
			// otherwise the task is the text from the input, we calculate with infix2postfix
		} else {
			// Get the task at the variable
			task = this.gui.getText();

			// Instanciate the solution-variable
			double solution = 0;

			// Calling Infix2Postfix from dwwPostfix-package
			RPN rpn = new RPN();
			postfix = rpn.infix2postfix(task);

			// calculating the result
			try {
				solution = rpn.eval(postfix);
			} catch (IllegalPostfixException e) {
				e.printStackTrace();
			}

			// Clear the screen
			this.clearScreen();

			// Write the solution to the screen
			this.gui.setText( String.valueOf( solution ) );
		}
	}


	double doPendingOp( double val ) {
		double ans = this.doOperation ( this.pendingOp, 
				this.pendingArg,
				val );
		this.pendingOp = Calculator.NO_OP;
		this.pendingArg = 0.0;
		this.opPending = false;
		return ans;
	}

	protected double doOperation ( int op, double prevArg, double currArg ) {
		switch ( op ) {
		case Calculator.OP_MUL:
			return ( prevArg * currArg );
		case Calculator.OP_DIV:
			return ( prevArg / currArg );
		case Calculator.OP_SUB:
			return ( prevArg - currArg );
		case Calculator.OP_ADD:
			return ( prevArg + currArg );
		case Calculator.NO_OP:
			// fall through....
		default:
			return ( currArg );
		}
	}

	protected void resetDecimal () {
		this.currArgIsInt = true;
	}

	protected void clearScreen() {
		this.gui.setText( "" );
	}

	protected void resetCalc() {
		this.clearScreen();
		this.gui.setText( String.valueOf( 0.0 ) );
		this.prevArg   = this.pendingArg = 0.0;
		this.currArgIsInt = true;
		this.numStartsNewArg = true;
		this.opPending = false;
		this.gotOpSinceEquals = false;
		this.currOp = this.pendingOp = Calculator.NO_OP;
	}

}


/* Comments:
 *
 * History:
 *     $Log: ButtonHandler.java,v $
 *     Revision 1.5  1998/07/24 16:37:13  tparnell
 *     Placate new javadoc behavior
 *
 *     Revision 1.4  1998/07/23 14:28:26  tparnell
 *     made class public
 *
 *     Revision 1.3  1998/07/06 20:22:56  tparnell
 *     changed from while (true)... to while (!this.stopped)... in support of
 *     JDK1.2 deprecation of Thread.stop()
 *
 *     Revision 1.2  1998/06/05 05:19:26  craigh
 *     added getButtonLabel() to Calculator interface.  Implemented the
 *     method in CalculatorGUI, and made use of it in ButtonHandler.
 *
 *     Revision 1.1  1998/02/26 17:25:43  tparnell
 *     Reconstruction from hard drive failure.  Everything appears intact.
 *
 *     Revision 1.3  1997/10/05 21:11:18  shong
 *     Updated for fall97, to Java 1.1
 *     changed GUI, using 1.1 Event Model
 *
 *     Revision 1.2  1997/07/16 14:15:19  tparnell
 *     *** empty log message ***
 *
 *     Revision 1.2  1996/10/04 16:20:17  las
 *     Transformed Calculator into an application and made it a package.  See
 *     STAFF_SETUP for which files are public.  To run, use Calculator.Main.
 *
 *     Specifics:
 *         Added Main.java, which starts the calculator program (both
 *     CalculatorGUI and ButtonHandler);
 *         Made Calculator an interface;
 *         Moved GUI implementation (previously in Calculator) to
 *     CalculatorGUI.
 *         Added clear button, which looks pretty gross right now.  (It can
 *     be deleted in a single line, though.)
 *
 *
 */

