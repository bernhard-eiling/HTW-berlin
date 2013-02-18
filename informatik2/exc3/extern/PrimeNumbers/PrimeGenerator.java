package PrimeNumbers;
/**
 * Generates random numbers in ranges of 20bit and 40 bit
 * 
 * @author Tobias Preuss (s0516424) & Niels Richter (s0517512)
 */
public class PrimeGenerator {

	private PrimeChecker p = new PrimeChecker();
	private String output = "";
	
	private String primeIt(int till) {
		long count = 0;
		for(int e = 0; e < till; e++) {
			if(p.isPrime(e)) {
				output = output + e + "\n";
				count = count + 1;
			}
		}
	return output + count + " prime found from 0 to " + till;
	}

	/**
	 * Generates random numbers in ranges of 20bit and 40 bit
	 * calculates if the number is a prime
	 * calculates the binary representation
	 * calculates the times for calulation 
	 * 
	 * @param what 20 or 40 bit numbers should be checked? (20/40)
	 * @param howmuch How many numbers would you like to check?
	 * @return String with an overview of what we did
	 */
	private String generator(int what, int howmuch) {
		long a, from, to, start, stop, time;
		String output = "";

		// Check the cases and set the borders
		if(what == 20) {
			from = 524288;
			to   = 1048575;
		} else {
			from = 1099511627776L;
			to   = 2199023255551L;
		}
		start = System.currentTimeMillis();
		for(int e = 0; e < howmuch; e++) {
			a = p.rand(from,to);
			boolean calc = p.isPrime(a);
			String bits = p.longInBinary(a);
			output = output + "Is " + a + "("+ bits +") a prime number?: " + calc + "\n";
		}
		stop = System.currentTimeMillis();
		time = stop - start;
		output = output + "Prime check and bit-conversion of " + howmuch + " " + what + "-bit-numbers took us " + time + " milliseconds.";
		return output; 
	}

	/**
	 * Just the main, today and tomorrow.
	 * @param args
	 */
	public static void main(String[] args) {
		PrimeGenerator g = new PrimeGenerator();
		System.out.println(g.generator(20,100));
		System.out.println(g.generator(40,100));
		//System.out.println(g.primeIt(100000));
	}

}
