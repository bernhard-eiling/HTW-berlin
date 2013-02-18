package eiling.info2.exersice7;

import rpn.InvalidExpressionException;
import rpn.Postfix;


/**
 * The main part of the calculator doing the calculations.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class CalcEngine {
	// The calculator's state is maintained in three fields:
	// buildingDisplayValue, haveLeftOperand, and lastOperator.

	// Are we already building a value in the display, or will the
	// next digit be the first of a new one?
	private boolean buildingDisplayValue;
	// Has a left operand already been entered (or calculated)?
	private boolean haveLeftOperand;
	// The most recent operator that was entered.
	private char lastOperator;

	// The current value (to be) shown in the display.
	private int displayValue;
	private String display = "";
	// The value of an existing left operand.
	private int leftOperand;
	private int base = 10;

	private Postfix postfix = new Postfix();

	/**
	 * Create a CalcEngine.
	 */
	public CalcEngine() {
		clear();
	}

	/**
	 * @return The value that should currently be displayed on the calculator
	 *         display.
	 */
	public String getDisplayValue() {

		return display;
		/*
		 * if (base == 16) { return Integer.toHexString(displayValue); } return
		 * Integer.toString(displayValue);
		 */
	}

	public void setBase(int base) {
		this.base = base;
	}

	/**
	 * A number button was pressed. Either start a new operand, or incorporate
	 * this number as the least significant digit of an existing one.
	 * 
	 * @param number
	 *            The number pressed on the calculator.
	 */
	public void numberPressed(int number) {

		display += number;
		/*
		 * if (buildingDisplayValue) { // Incorporate this digit. displayValue =
		 * displayValue * base + number; } else { // Start building a new
		 * number. displayValue = number; buildingDisplayValue = true; }
		 */
	}

	/**
	 * The 'plus' button was pressed.
	 */
	public void plus() {
		display += '+';
		// applyOperator('+');
	}

	/**
	 * The 'minus' button was pressed.
	 */
	public void minus() {
		display += '-';
		// applyOperator('-');
	}

	/**
	 * The 'multiply' button was pressed.
	 */
	public void multi() {
		display += '*';
		// applyOperator('*');
	}

	/**
	 * The 'power' button was pressed.
	 */
	public void power() {
		display += '^';
		// applyOperator('^');
	}

	/**
	 * The 'division' button was pressed.
	 */
	public void div() {
		display += '/';
		// applyOperator('/');
	}

	/**
	 * The '=' button was pressed.
	 */
	public void equals() {

		// display = postfix.evaluate(postfix.infixToPostfix(display));

		String inToPost = "";

		try {
			inToPost = Postfix.infixToPostfix(display);
		} catch (InvalidExpressionException e) {
			System.out.println(e.getMessage());
		}

		try {
			Integer postEval = Postfix.evaluate(inToPost);
			display = postEval.toString();
		} catch (InvalidExpressionException e) {
			System.out.println(e.getMessage());
		}

		/*
		 * Integer postfixEval =
		 * postfix.evaluate(postfix.infixToPostfix(display)); display =
		 * postfixEval.toString();
		 */

		postfix = new Postfix();
		// This should completes the building of a second operand,
		// so ensure that we really have a left operand, an operator
		// and a right operand.
		/*
		 * if (haveLeftOperand && lastOperator != '?' && buildingDisplayValue) {
		 * calculateResult(); lastOperator = '?'; buildingDisplayValue = false;
		 * } else { keySequenceError(); }
		 */
	}

	/**
	 * The 'C' (clear) button was pressed. Reset everything to a starting state.
	 */
	public void clear() {
		postfix = new Postfix();
		lastOperator = '?';
		haveLeftOperand = false;
		buildingDisplayValue = false;
		display = "";
	}

	/**
	 * @return The title of this calculation engine.
	 */
	public String getTitle() {
		return "Java Calculator";
	}

	/**
	 * @return The author of this engine.
	 */
	public String getAuthor() {
		return "Bernhard Eiling and Arianova Menderoz";
	}

	/**
	 * @return The version number of this engine.
	 */
	public String getVersion() {
		return "Version 1.0";
	}

	/**
	 * Combine leftOperand, lastOperator, and the current display value. The
	 * result becomes both the leftOperand and the new display value.
	 */
	private void calculateResult() {
		switch (lastOperator) {
		case '+':
			displayValue = leftOperand + displayValue;
			haveLeftOperand = true;
			leftOperand = displayValue;
			break;
		case '-':
			displayValue = leftOperand - displayValue;
			haveLeftOperand = true;
			leftOperand = displayValue;
			break;
		case '*':
			displayValue = leftOperand * displayValue;
			haveLeftOperand = true;
			leftOperand = displayValue;
			break;
		case '/':
			if (displayValue == 0) {
				keySequenceError();
				break;
			} else {
				displayValue = (int) (leftOperand / displayValue);
				haveLeftOperand = true;
				leftOperand = displayValue;
			}
			break;
		case '^':
			displayValue = (int) Math.pow(leftOperand, displayValue);
			haveLeftOperand = true;
			leftOperand = displayValue;
			break;
		default:
			keySequenceError();
			break;
		}
	}

	/**
	 * Apply an operator.
	 * 
	 * @param operator
	 *            The operator to apply.
	 */
	private void applyOperator(char operator) {
		// If we are not in the process of building a new operand
		// then it is an error, unless we have just calculated a
		// result using '='.
		if (!buildingDisplayValue && !(haveLeftOperand && lastOperator == '?')) {
			keySequenceError();
			return;
		}

		if (lastOperator != '?') {
			// First apply the previous operator.
			calculateResult();
		} else {
			// The displayValue now becomes the left operand of this
			// new operator.
			haveLeftOperand = true;
			leftOperand = displayValue;
		}
		lastOperator = operator;
		buildingDisplayValue = false;
	}

	/**
	 * Report an error in the sequence of keys that was pressed.
	 */
	private void keySequenceError() {
		System.out.println("A key sequence error has occurred.");
		// Reset everything.
		clear();
	}

}
