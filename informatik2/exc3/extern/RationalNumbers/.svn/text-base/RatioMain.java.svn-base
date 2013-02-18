package RationalNumbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is thought to be the starter class calling the input and operator objects.
 * @author Tobias Preuﬂ (s0516424) & Niels Richter (s0517512)
 */
public class RatioMain {
	
	public static void main(String[] args) {
		System.out.println("Input your task in form: (-)XX(/(-)XX) (+|-|*|/) (-)XX(/(-)XX)");
		while(true) {
			// Instance of this class
			RatioInput i = new RatioInput();
			RatioOperator myRO = new RatioOperator();
			RatioWriter myRW = new RatioWriter();

			// Try-catch-block for read in
			try {
				// Take the input an make an integer out of it
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String s = new String (in.readLine());
				// Start of time measurance
				long start = System.currentTimeMillis();
				int action = i.workOnString(s);
				switch(action) {
				case 1 : myRO.summate(i.num1,i.den1,i.num2,i.den2); myRW.writeIt(s, myRO, action); break;
				case 2 : myRO.subtract(i.num1,i.den1,i.num2,i.den2); myRW.writeIt(s, myRO, action); break;
				case 3 : myRO.multiply(i.num1,i.den1,i.num2,i.den2); myRW.writeIt(s, myRO, action); break;
				case 4 : myRO.division(i.num1,i.den1,i.num2,i.den2); myRW.writeIt(s, myRO, action); break;
				case 5 : myRO.equalValue(i.num1,i.den1,i.num2,i.den2); myRW.writeIt(s, myRO, action); break;
				case 6 : myRO.greaterthan(i.num1,i.den1,i.num2,i.den2); myRW.writeIt(s, myRO, action); break;
				case 7 : myRO.greaterthan(i.num2,i.den2,i.num1,i.den1); myRW.writeIt(s, myRO, action); break;
				default : System.out.println("Error: We didn't found any operator in your input."); break;
				}
				// Stop of time measurance
				long stop = System.currentTimeMillis();
				// Calculate the millis
				long time = stop - start;

				System.out.println("We needed " + time + " milliseconds\n--------------");

			} catch(IOException e) {
				// If the readin went wrong, give us a message instead of an stupid exception error
				System.out.println("Uuups, we hit a bug!\n");
			} catch(ArrayIndexOutOfBoundsException e) {
				// If the input is not readable for the workOnString-method this gives a nice error-msg
				System.out.println("The input is not valid to: (-)XX(/(-)XX) (+|-|*|/) (-)XX(/(-)XX)\n");
			} catch(NumberFormatException e) {
				// If the input is not readable for the workOnString-Function this give a nice error msg
				System.out.println("Sorry, but we can't read your input.\n");
			}
		}


	}

}
