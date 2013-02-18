package runningTimes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class calculates 6 different loops
 * and is doing some comparison between them.
 * 
 * @author Tobias s0516424 Preuss and Niels Richter s0517512
 *
 */
public class LoopCalculator {

	
	/**
	 * Fragment #1
	 * 
	 * @param n The Number to work on
	 * @return The sum after the algorithm has finished
	 */
	public int fra1 (int n) {
		int sum = 0;
		for (int i = 0; i < n; i ++) {
			sum++;
		}
	return sum;
	}

	/**
	 * Fragment #2
	 * 
	 * @param n The Number to work on
	 * @return The sum after the algorithm has finished
	 */
	public int fra2 (int n) {
		int sum = 0;
		for ( int i = 0; i < n; i ++) {
			for ( int j = 0; j < n; j ++) {
				sum++;
			}
		}
	return sum;
	}

	/**
	 * Fragment #3
	 * 
	 * @param n The Number to work on
	 * @return The sum after the algorithm has finished
	 */
	public int fra3 (int n) {
		int sum = 0;
		for ( int i = 0; i < n; i ++) {
			sum ++;
			for ( int j = 0; j < n; j ++) {
				sum ++;
			}
		}
	return sum;
	}

	/**
	 * Fragment #4
	 * 
	 * @param n The Number to work on
	 * @return The sum after the algorithm has finished
	 */
	public int fra4 (int n) {
		int sum = 0;
		for ( int i = 0; i < n; i ++) {
			for ( int j = 0; j < n*n; j ++) {
				sum++;
			}
		}
	return sum;
	}

	/**
	 * Fragment #5
	 * 
	 * @param n The Number to work on
	 * @return The sum after the algorithm has finished
	 */
	public int fra5 (int n) {
		int sum = 0;
		for ( int i = 0; i < n; i ++) {
			for ( int j = 0; j < i; j ++) {
				sum++;
			}
		}
	return sum;
	}

	/**
	 * Fragment #6
	 * 
	 * @param n The Number to work on
	 * @return The sum after the algorithm has finished
	 */
	public int fra6 (int n) {
		int sum = 0;
		for ( int i = 1; i < n; i ++) {
			for ( int j = 0; j < n*n; j ++) {
				if (j % i == 0) {
					for (int k = 0; k < j; k++) {
						sum++;
					}
				}
			}
		}
	return sum;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Instance of this class
		LoopCalculator t = new LoopCalculator();

		// Try-catch-block for read in
		try {
			// Take the input an make an integer out of it
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			int n = Integer.parseInt(new String (in.readLine()));

			// Calling the fragtions
			System.out.println("Loops F1: " + t.fra1(n));
			System.out.println("Loops F2: " + t.fra2(n));
			System.out.println("Loops F3: " + t.fra3(n));
			System.out.println("Loops F4: " + t.fra4(n));
			System.out.println("Loops F5: " + t.fra5(n));
			
			// Calculation of fra6
			long start = System.currentTimeMillis();
			int calc = t.fra6(n);
			long stop = System.currentTimeMillis();
			
			// Output the time-seconds
			long time = (stop-start);
			System.out.println("Loops F6 needs " + time + " milliseconds for " + n + " and returns " + calc);
			
		} catch(IOException e) {
			// If the read in went wrong, give us a message instead of an stupid exeption error
			System.out.println("Thanks, but give us an integer.");
		} catch(NumberFormatException e) {
			// If the read in went wrong, give us a message instead of an stupid exeption error
			System.out.println("Thanks, but give us an integer.");
		}
	}
}
