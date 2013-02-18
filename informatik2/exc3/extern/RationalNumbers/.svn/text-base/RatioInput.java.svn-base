package RationalNumbers;
/**
 * Class to calculate fractions with the help of the console
 * the input must be int the format (-)XX(/(-)XX) (+|-|*|/) (-)XX(/(-)XX)
 * 
 * @author Tobias Preuss (s0516424) & Niels Richter (s0517512)
 */
public class RatioInput {

	/**
	 * Some private integer variables
	 */
	public int num1, num2, den1, den2, op;

	/**
	 * Extracts the integer-values of the given array of strings
	 * and writes it to the private integer values
	 * 
	 * @param part array of strings in format {"(-)XX(/(-)XX)","(-)XX(/(-)XX)"}
	 */
	private void workOnPart(String[] part) {
		String nums[];
		// Concentrate on X at X/Y
		if(part[0].contains("/")) {
			// Split the string into another array
			nums = part[0].split("\\/");
			num1 = Integer.parseInt(nums[0]);
			den1 = Integer.parseInt(nums[1]);
		} else {
			// Not second part, create to 1
			num1 = Integer.parseInt(part[0]);
			den1 = 1;
		}
		// Concentrate on Y at X/Y
		if(part[1].contains("/")) {
			// Split the string into another array
			nums = part[1].split("\\/");
			num2 = Integer.parseInt(nums[0]);
			den2 = Integer.parseInt(nums[1]);
		} else {
			// Not second part, create to 1
			num2 = Integer.parseInt(part[1]);
			den2 = 1;
		}
		
		// if one denumerator is negative, multiply both with -1
		if(den1 < 1) {
			num1 = num1 * (-1);
			den1 = den1 * (-1);
		}
		if(den2 < 1) {
			num2 = num2 * (-1);
			den2 = den2 * (-1);
		}
	}

	/**
	 * Needs a String in the form (-)XX(/(-)XX) (+|-|*|/|=|<|>) (-)XX(/(-)XX)
	 * and calls the workOnPart with an array of the parts
	 * 
	 * @param s String to be converted
	 * @return an integer value of the operation 1 = +, 2 = -, 3 = *, 4 = /, 5 = =, 6 = >, 7 = <
	 */
	public int workOnString(String s) {
		if(s.contains(" + ")) {
			workOnPart(s.split(" \\+ "));
			op = 1;
		}
		if(s.contains(" - ")) {
			workOnPart(s.split(" - "));
			op = 2;
		}
		if(s.contains(" * ")) {
			workOnPart(s.split(" \\* "));
			op = 3;
		}
		if(s.contains(" / ")) {
			workOnPart(s.split(" \\/ "));
			op = 4;
		}
		if(s.contains(" = ")) {
			workOnPart(s.split(" = "));
			op = 5;
		}
		if(s.contains(" > ")) {
			workOnPart(s.split(" > "));
			op = 6;
		}
		if(s.contains(" < ")) {
			workOnPart(s.split(" < "));
			op = 7;
		}
		return op;
	}
}
