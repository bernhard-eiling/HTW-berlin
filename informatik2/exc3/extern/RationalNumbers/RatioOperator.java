package RationalNumbers;
/**
 * Class includes several method to do mathwork on fractions. 
 * @author Tobias Preuﬂ (s0516424) & Niels Richter (s0517512)
 */
public class RatioOperator {

	public int numerator2, denomerator2;
	public int resultNumerators;
	public int resultDenomerators;
	public boolean compared;


	/**
	 * Multiplies two integer.
	 * @param m1 as an integer
	 * @param m2 as an integer
	 * @return Product as an integer
	 */
	private int multiplyBoth (int m1, int m2) {
		return m1 * m2;
	}
	
	/**
	 * Calculates the reciprocal by interchanging both integer values 
	 * and stores them onto the global fields: resultNumerators and resultDenomerators.
	 * @param num2 as an integer
	 * @param den2 as an integer
	 */
	private void reciprocal (int num2, int den2) {
		denomerator2 = num2;
		numerator2 = den2;
	}
	/**
	 * Summates two integer.
	 * @param s1 as an integer
	 * @param s2 as an integer
	 * @return Sum as an integer
	 */
	private int summateBoth(int s1, int s2) {
		return s1 + s2;		
	}

	/**
	 * Multiplies the given numerator1 and numerator2, the same with denomerator.
	 * The products are individually stored into the fields: resultNumerators, resultDenomerators.
	 * @param num1 as an integer
	 * @param den1 as an integer
	 * @param num2 as an integer
	 * @param den2 as an integer
	 */
	public void multiply (int num1, int den1, int num2, int den2) {
		resultNumerators = this.multiplyBoth(num1,num2);
		resultDenomerators = this.multiplyBoth(den1,den2);
		this.cancel(resultNumerators, resultDenomerators);
	}

	/**
	 * Calculates the quotients of the given numerator1 and numerator2, the same with denomerator.
     * The quotient are individually stored into the fields: resultNumerators, resultDenomerators.
	 * @param num1 as an integer
	 * @param den1 as an integer
	 * @param num2 as an integer
	 * @param den2 as an integer
	 */
	public void division (int num1, int den1, int num2, int den2){
		this.reciprocal(num2, den2);
		this.multiply(num1, den1, numerator2, denomerator2);
		this.cancel(resultNumerators, resultDenomerators);
	}

	/**
	 * Calculates the sums of the given numerator1 and numerator2, the same with denomerator.
	 * The sums are individually stored into the fields: resultNumerators, resultDenomerators. 
	 * @param num1 as integer
	 * @param num2 as integer
	 */
	public void summate(int num1, int den1, int num2, int den2) {
		if (den1 == den2) {
			resultNumerators = this.summateBoth(num1, num2);
			resultDenomerators = den1;
		} else {
			resultNumerators = this.multiplyBoth(den2, num1) + this.multiplyBoth(den1, num2);
			resultDenomerators = this.multiplyBoth(den1, den2);
		}
		if (resultNumerators != 0) {
			this.cancel(resultNumerators, resultDenomerators); }
		else resultDenomerators = 0;
	}
	
	/**
	 * Calculates the difference between both fraction passed by
	 * @param num1 as an integer
	 * @param den1 as an integer
	 * @param num2 as an integer
	 * @param den2 as an integer
	 */
	public void subtract(int num1, int den1, int num2, int den2) {
		num2 = -1 * num2;
		this.summate(num1, den1, num2, den2);
	}
	
	/**
	 * Calculates whether the denumerator is smaller then the numerator
	 * @param num as an integer 
	 * @param den as an integer
	 * @return true when denumerator < numerator
	 */
	private boolean denIsSmaller(int num, int den) {
		return (num > den);
	}

	/**
	 * Calculates the biggest common factor of two integer.
	 * @param a as an integer
	 * @param b as an integer
	 * @return biggest common factor as an integer
	 */
	private int bcf(int a, int b)
	{
		if (this.denIsSmaller(a, b)) {
			if (b==0)
				return a;
			else
				return bcf(b, a%b);
		} 
		if (a==0)
			return b;
		else
			return bcf(a, b%a);


	}

	/**
	 * Cancels two integer representing a fraction if possible
	 * @param num as an integer
	 * @param den as an integer
	 */
	private void cancel (int num, int den) {
		// first idea to put this hear to avoid division by zero
		// but maybe better at the summate-method
//		if (resultNumerators != 0 && resultDenomerators != 0) {
		if (num % den == 0) {
			resultNumerators = num / den;
			resultDenomerators = 1;
		} if (den % num == 0) {
			resultNumerators = 1;
			resultDenomerators = den / num;
		} int myggt = this.bcf(num, den);
		if (num % myggt == 0 && den % myggt == 0) {
			resultNumerators = num / myggt;
			resultDenomerators = den / myggt;
		}
//		}
}

//	public String printer(String txt, int calc) {
//		return System.out.println(txt + calc + "\n");
//	}

	/**
	 * Compares two fractions if there mathematical values are equal
	 * 
	 * @param num1 as an integer
	 * @param den1 as an integer
	 * @param num2 as an integer
	 * @param den2 as an integer
	 */
	public void equalValue (int num1, int den1, int num2, int den2) {
		this.subtract(num1, den1, num2, den2);
		if(this.resultNumerators == 0 && this.resultDenomerators == 0) {
			compared = true;
		} else {
			compared = false;
		}
	}

	/**
	 * Compares two fractions if the first is greater than the second
	 * 
	 * @param num1 as an integer
	 * @param den1 as an integer
	 * @param num2 as an integer
	 * @param den2 as an integer
	 */
	public void greaterthan (int num1, int den1, int num2, int den2) {
		if((num1/den1) > (num2/den2)) {
			compared = true;
		} else {
			compared = false;
		}
	}

	public static void main(String[] args) {
		RatioOperator myRO = new RatioOperator();

//		myRO.printer("Biggest Common Factor = ", myRO.bcf(4,33));
		System.out.println("Biggest Common Factor = " + myRO.bcf(4,33) + "\n");

		System.out.println("CANCEL ----------------");
		myRO.cancel(4, 33);
		System.out.println("resultNumerators = " + myRO.resultNumerators);
		System.out.println("resultDenomerators = " + myRO.resultDenomerators);

		System.out.println("\n5/4 und 1/4\n");

		System.out.println("MULTIPLY ----------------");
		myRO.multiply(5, 4, 1, 4);
		System.out.println("resultNumerators = " + myRO.resultNumerators);
		System.out.println("resultDenomerators = " + myRO.resultDenomerators + "\n");

		System.out.println("DIVISION ----------------");
		myRO.division(5, 4, 1, 4);
		System.out.println("resultNumerators = " + myRO.resultNumerators);
		System.out.println("resultDenomerators = " + myRO.resultDenomerators + "\n");

		System.out.println("SUMMATE ----------------");
		myRO.summate(15, 15, 15, 15);
		System.out.println("resultNumerators = " + myRO.resultNumerators);
		System.out.println("resultDenomerators = " + myRO.resultDenomerators + "\n");		

		System.out.println("SUBTRACT ----------------");
		myRO.subtract(30, 30, 14, 15);
		System.out.println("resultNumerators = " + myRO.resultNumerators);
		System.out.println("resultDenomerators = " + myRO.resultDenomerators);			
	}
}
