package fraction;

/**
 * Class gives a nice formated output for the RatioFramework
 * @author Tobias Preuﬂ (s0516424) & Niels Richter (s0517512)
 */
public class RatioWriter {

	/**
	 * Gives a nice formatted console-output of the math-operations
	 * @param stringInput original input by the user
	 * @param myRO current Object of RatioOperator class
	 */
	public void writeIt(String stringInput, RatioOperator myRO, int action) {
		if (action < 5) {
			System.out.println(stringInput + " = " + myRO.resultNumerators + "/" + myRO.resultDenomerators 
					+ " (~" + new Ratio(myRO.resultNumerators,myRO.resultDenomerators).value() + ")");
		}	
		else { 
			System.out.println(stringInput + " = " + myRO.compared);
		}
	}
}
