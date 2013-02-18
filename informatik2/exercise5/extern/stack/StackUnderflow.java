package stack;
/**
 * StackUnderflow for the package stack
 * 
 * @author Tobias Preuss (s0516424) & Niels Richter (s0517512)
 */
public class StackUnderflow extends Exception {
	
	/**
	 * If their is a StackUnderFlow, print it to the console
	 */
	private static final long serialVersionUID = 1L;

	public StackUnderflow(String string) {
		System.out.println(string);
	}
}
