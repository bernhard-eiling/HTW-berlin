package PrimeNumbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Checks if the given number is an prime (TRUE/FALSE)
 * also some checks and comparison on calculating checking times
 * 
 * @author Tobias Preuss (s0516424) & Niels Richter (s0517512)
 */
public class PrimeChecker {

	private static Random random = new Random();

	/**
	 * Checks if an given value is a prime number
	 * 
	 * @param n Value of prime-number - expected as integer
	 * @return True or false - depending on the result
	 */
	public boolean isPrime (long n) {
		if(n % 2 == 0) {
			return false;
		} else {
			for (int i = 3; i <= (int) Math.sqrt(n); i = i + 2) {
				if((n % i) == 0) return false;
			}
		}
		return true;
	}

	/**
	 * Returns the binary representation of an integer value
	 * 
	 * @param n Integer value which should be converted
	 * @return String which represents the int in binary
	 */
	public String longInBinary (long n) {
		return Long.toString(n, 2);
	}
	
	/**
	 * Returns the binary representation of an double value
	 * 
	 * @param n Long value which should be converted
	 * @return String which represents the int in binary
	 */
	public String inBinary (long n) {
		String output = null;
		for(long i = 1; i <= n; i = i * 2) {
			if(n % i == 0) {
				output = "1" + output;
			} else {
				output = "0" + output;
			}
			n = n - i;
		}
		return output;
	}
	
	/**
	 * Calculates number of bits in the binary representation of an integer n
	 * 
	 * @param n The value to calculate on
	 * @return An integer which indicates the number of bits
	 */
	public long bitsInBinary (long n) {
		return (Long.toString(n, 2)).length();
	}

	
	/**
	 * Function which takes a high and a low value an returns a random-number between
	 * 
	 * @param low value of the lowest border
	 * @param high value of the highest borger
	 * @return a random value between low and high
	 */
	public long rand(long low, long high) {
		long n = high - low + 1;
		long i = random.nextLong() % n;
		if (i < 0) {
			i = -i;
		}
		return low + i;
	}

	/**
	 * @param args
	 */
	 public static void main(String[] args) {
		 // Instance of this class
		 PrimeChecker p = new PrimeChecker();

		 // Try-catch-block for read in
		 try {
			 // Take the input an make an integer out of it
			 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			 long n = Long.parseLong(new String (in.readLine()));
			 
			 // Calculation of prime-number
			 long start = System.currentTimeMillis();
			 boolean calc = p.isPrime(n);
			 long bits = p.bitsInBinary(n);
			 String binary = p.longInBinary(n);
			 long stop = System.currentTimeMillis();

			 // Output the time-seconds
			 long time = stop - start;
			 System.out.println("Is " + n + " a prime number?: " + calc + "\nYou need " + bits + " bits to represent n as binary: " + binary + "\nWe needed " + time + " milliseconds to calculate this.");

		 } catch(IOException e) {
			 // If the readin went wrong, give us a message instead of an stupid exception error
			 System.out.println("Thanks, but give us a long.");
		 } catch(NumberFormatException e) {
			 // If the readin went wrong, give us a message instead of an stupid exception error
			 System.out.println("Thanks, but give us a long.");
		 }
	 }
}
